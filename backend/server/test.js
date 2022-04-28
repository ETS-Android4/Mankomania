const { v4: uuidv4 } = require('uuid');
const rooms = {};
function createRoom(){
    const room = {
        id: uuidv4(),// generate a unique id for the new room, that way we don't need to deal with duplicates.
        sockets: [],
        status: false
    };
    rooms[room.id] = room;
    const room2 = {
        id: uuidv4(),// generate a unique id for the new room, that way we don't need to deal with duplicates.
        sockets: [],
        status: true
    };
    rooms[room2.id] = room2;

    room2.sockets.push("1")
    room2.sockets.push("2")
    room2.sockets.push("3")
    room2.sockets.push("4")

    room.sockets.push("1")
    room.sockets.push("2")
    room.sockets.push("3")
    room.sockets.push("4")


}

function search (){
    for (const id in rooms) {
        const room = rooms[id];
        if(room.sockets.length < 4){
            return room.id;
        }
    }
}

function check(){
    if(search() === undefined){
        console.log("undefined")
        let id = create();

    }
}

function create(){
    const room = {
        id: uuidv4(),// generate a unique id for the new room, that way we don't need to deal with duplicates.
        sockets: [],
        status: false
    };
    rooms[room.id] = room;
    return room.id;
}





/*

createRoom();
check();
console.log(rooms)
*/

const room = {
    id: uuidv4(),// generate a unique id for the new room, that way we don't need to deal with duplicates.
    sockets: [],
    status: false
};
rooms[room.id] = room;
let find = 'room.id';

const room2 = {
    id: uuidv4(),// generate a unique id for the new room, that way we don't need to deal with duplicates.
    sockets: [],
    status: false
};
rooms[room2.id] = room2;

//console.log(rooms)

console.log(rooms[find])