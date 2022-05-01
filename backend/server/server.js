import express from 'express';
import { Server } from 'socket.io';
const app = express();
const server = app.listen(3000, function(){
    console.log("Server running at " + 3000);
})
const io = new Server(server, { cors: { origin: '*' } });




io.on("connection",socket => {
    console.log(socket.id)
    socket.on("custom-event", (number, string, obj) =>{
        console.log(number, string, obj)
    })
})


