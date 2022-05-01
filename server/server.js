const app = require("express")();
const server = require("http").Server(app);
const io = require("socket.io")(server);
const port = 3000;

server.listen(port, function(){
    console.log("Server running at " + port);
})

io.on("connection",socket => {
    console.log(socket.id)
    socket.on("custom-event", (number, string, obj) =>{
        console.log(number, string, obj)
    })
})

