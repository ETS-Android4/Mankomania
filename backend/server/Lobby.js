
     lobby = new Map();

      function createNewLobby(roomName){
        if(!lobby.has(roomName))
            lobby.set(roomName, 0)

        console.log("New Lobby Created")
    }
     function readyForGameLobby(roomName){
        if(lobby.has(roomName)){
            let counter = lobby.get(roomName)
            lobby.set(roomName, counter++)
        }


}








