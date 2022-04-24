package com.mankomania.game;

import java.net.URISyntaxException;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class Connection{
    private final String server = "https://mankomania-backend.herokuapp.com/";
    //private final int port = 53216;
    private Socket cs;

    /**
     * Constructor:
     * Creates a socket connection to the server
     */
    public Connection() {

        try {
            cs = IO.socket(server);
            cs.connect();
            System.out.println("Connection created");

        } catch (URISyntaxException e) {
            e.printStackTrace();
            System.out.println("Couldn't connect to server");
        }
    }

    public void joinRoom(Emitter.Listener el){
        cs.emit("join-room", "");
        cs.once("join-room", el);

    }

    public void readyForGame(Emitter.Listener el){
        cs.emit("readyForGame", "");
        cs.once("NAN", el);

        System.out.println("Emit Event to server");

    }


    /**
     * Close connection to server
     */
    public void closeConnection(){

        cs.disconnect();
        cs.off();
        cs.close();

        System.out.println("Connection closed");
    }
}
