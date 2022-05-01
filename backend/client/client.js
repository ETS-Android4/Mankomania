
import { io } from 'socket.io-client'
const socket = io('http://localhost:3000');

socket.on("connect", () => {
    console.log(`You connected with id: ${socket.id}`)
    socket.emit("custom-event", 10, "Hi", {a: "a"} )
})

const messageInput = document.getElementById("message");
const roomInput = document.getElementById("room");

const sendMessageButton = document.getElementById("sendMessage");
const joinRoomButton = document.getElementById("joinRoom");






sendMessageButton.addEventListener("click", e => {
    const message = messageInput.value;
    const room = roomInput.value;

    displayMessage(message);

})

function displayMessage(message){
    const div = document.createElement("div")
    div.textContent = message;
    document.getElementById("txt").append(div);




}
