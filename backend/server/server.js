const express = require('express')
const app = express();
const http = require('http');
const server = http.createServer(app);
const {Server} = require("socket.io");
const io = new Server(server, { cors: { origin: '*'} });
const PORT = process.env.PORT || 3000;
//const { instrument } = require("@socket.io/admin-ui");



let clientNo = 1;
let lobby = new Map();





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
            increaseGameLobbyCounter(room);
            checkIfRoomIsReady(room);
        });

        socket.on('disconnect', () => {
            console.log('A user has disconnected.');
        })
    });
})

function increaseGameLobbyCounter (room){
    
    lobby.set(room, lobby.get(room) + 1);
}

function checkIfRoomIsReady(room){
    io.in(room).allSockets().then(result=>{
    if(result.size === lobby.get(room)){
        io.emit("startGame");
    }
      })
}

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
                 createLobby(room);
             },
             function(ids){
                console.log(ids)
                 //toDo
             }
         );

     } else {
         //client --> check if room is naN
         socket.join(room);
         socket.emit('join-room', room);
         console.log(socket.id + " joined " + room)
     }
 }

 async function idsInRoom(room){
     return Array.from(await io.in(room).allSockets());
 }

 function createLobby(room){
    if(lobby.size === 0){
        lobby.set(room, 0);
        console.log("Lobby with " + room + " created");
    }
 }








function startGame(){

}
/*
instrument(io, {
    auth: false
});
*/
