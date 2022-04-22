import express from 'express';
import { Server } from 'socket.io';
import { instrument } from "@socket.io/admin-ui"
const app = express();
const server = app.listen(3000, function(){
    console.log("Server running at " + 3000);
})
const io = new Server(server, { cors: { origin: '*'} });




io.on("connection",socket => {
    console.log(socket.id)
    socket.on("send-message", (message, room) => {
        if(room === ""){
            socket.broadcast.emit('receive-message', message)
        }else{
            socket.to(room).emit('receive-message', message)
        }


    })

    socket.on('join-room', (room, cb) =>{
        socket.join(room)
        cb(`Joined ${room}`)
    })
})

instrument(io, {
    auth: false
});


