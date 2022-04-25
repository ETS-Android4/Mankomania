const express = require('express')
const app = express();
const http = require('http');
const server = http.createServer(app);
const {Server} = require("socket.io");
const io = new Server(server, { cors: { origin: '*'} });
const PORT = process.env.PORT || 3000;
//const { instrument } = require("@socket.io/admin-ui");

let clientNo = 1;
const lobbies = new Map();
lobbies.set("0", 0);




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

        socket.on('readyForGame', (room) =>{
            console.log(room)
        });

        socket.on('disconnect', () => {
            console.log('A user has disconnected.');

        })

    });


})



 async function validateRoom(room, socket) {
     if (room === '') {
         clientNo++;
         let room = Math.round(clientNo / 4)
         socket.join(room);
         
         idsInRoom(room).then(
             function(ids){
                 console.log(ids);
                 socket.emit("join-room", room, ids)
                 socket.to(room).emit("join-room", room, ids)
                 console.log(socket.id + " joined " + room)
             }
         )


     } else {
         //client --> check if room is naN
         socket.join(room);
         socket.emit('join-room', room);
         console.log(socket.id + " joined " + room)
     }
 }

 async function idsInRoom(room){
     //return JSON.stringify(Array.from(await io.in(room).allSockets()));
     return Array.from(await io.in(room).allSockets());
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
