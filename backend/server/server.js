const express = require('express')
const app = express();
const http = require('http');
const server = http.createServer(app);
const {Server} = require("socket.io");
const io = new Server(server, { cors: { origin: '*'} });
const PORT = process.env.PORT || 3000;
//const { instrument } = require("@socket.io/admin-ui");

let clientNo = 1;

app.get('/', (req, res) =>{
    res.write(`<h1>Socket IO Start on Port : ${PORT}</h1>`)
    res.end();
})

server.listen(PORT, ()=>{
console.log('Listing on*:3000')

    io.on('connection', (socket) => {
        console.log(socket.id)

        socket.on("join-room", (room) =>{
            validateRoom(room, socket);
        });

        socket.on('disconnect', () => {
            console.log('A user has disconnected.');

        })
    });


})

function validateRoom(room, socket){
    //toDo with Map
    if(room === ''){
        clientNo++;
        socket.join(Math.round(clientNo/4));
        socket.emit("join-room", Math.round(clientNo/4))
        console.log(socket.id + " joined " + Math.round(clientNo/4))

    }else{
        //client --> check if room is naN
       socket.join(room);
       socket.emit('join-room', room);
       console.log(socket.id + " joined " + room)
    }

}

function startGame(){

}
/*
instrument(io, {
    auth: false
});
*/
/*
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

    socket.on("disconnect",function(){
        console.log(socket.id + "Disconnected")

    });

})

 */
/*
io.on('connection', () =>{
    socket.on('message', msg =>{
        io.emit('message', msg)
    })
})
*/
