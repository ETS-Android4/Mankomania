const express = require('express')
const app = express();
const http = require('http');
const server = http.createServer(app);
const {Server} = require("socket.io");
const io = new Server(server, { cors: { origin: '*'} });
const PORT = process.env.PORT || 3000;
const { v4: uuidv4 } = require('uuid');
//const { instrument } = require("@socket.io/admin-ui");



/**
 * Instance Variables
 */
const rooms = {};



function validateRoom(socket, room, io){
    if(room === ''){
        let roomID = searchEmptyRooms();
        if (roomID === undefined){
            joinRoom(socket, createRoom(), io);

        }else{
            joinRoom(socket,roomID, io)
        }

    }else{
        //ToDo private Game
    }
}



function  createRoom () {
    const room = {
        id: uuidv4(), // generate a unique id for the new room, that way we don't need to deal with duplicates.
        status: false,
        sockets: [],
        ready: 0
    };
    rooms[room.id] = room;
    // have the socket join the room they've just created.
    console.log("Room created " + room.id)
    return room;
}

function searchEmptyRooms(){
    for (const id in rooms) {
        const room = rooms[id];
        if(room.sockets.length < 4 && room.status === false){
            return room;
        }
    }
}
/**
 * Will connect a socket to a specified room
 * @param socket A connected socket.io socket
 * @param room An object that represents a room from the `rooms` instance variable object
 */
function joinRoom(socket, room, io) {
    room.sockets.push(socket.id);
    socket.join(room.id);
    console.log(socket.id, "Joined", room.id);
    io.in(room.id).emit('join-room', room.id, room.sockets)


    if(room.sockets.length === 4){
        room.status = true;
        console.log(room.id + " is full")
        createRoom();
    }
}

function leaveRooms(socket){
    const roomsToDelete = [];
    // check to see if the socket is in the current room
    for(const id in rooms){
        const room = rooms[id];

        if(room.sockets.includes(socket.id)){
            //console.log("Includes")
            socket.leave(id)
            // remove the socket from the room object
            room.sockets = room.sockets.filter((item) => item !== socket.id);
            console.log(room.sockets)

        }
        // Prepare to delete any rooms that are now empty
        if (room.sockets.length === 0) {
            roomsToDelete.push(room);
        }
    }
    // Delete all the empty rooms that we found earlier
    for (const room of roomsToDelete) {
        delete rooms[room.id];
    }
}

app.get('/', (req, res) =>{
    res.write(`<h1>Socket IO Start on Port : ${PORT}</h1>`)
    res.end();
})

server.listen(PORT, ()=>{
    console.log('Listing on*:3000')

})


io.on('connection', (socket) => {
    console.log(socket.id)
    console.log(rooms)



    socket.on('join-room', (room) => {
        validateRoom(socket, room, io);


    });

    socket.on("readyForGame", (room, socket) =>{

    });

    socket.on('leaveRoom', () => {
        leaveRooms(socket);
    });

    socket.on('disconnect', () => {
        console.log('user disconnected');
        leaveRooms(socket);

    });
});









/*
instrument(io, {
    auth: false
});
*/





