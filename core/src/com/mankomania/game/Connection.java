package com.mankomania.game;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class Connection {
    private final String server = "https://mankomania-backend.herokuapp.com/";
    //private final int port;
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

    public void joinRoom(Emitter.Listener el) {
        cs.on("join-room", el);
        cs.emit("join-room", "");
    }

    public void readyForGame(Emitter.Listener el, String lobbyID) {
        cs.on("startGame", el);
        System.out.println(lobbyID);
        cs.emit("readyForGame", lobbyID);
    }


    /**
     * Close connection to server
     */
    public void closeConnection() {

        cs.disconnect();
        cs.off();
        cs.close();

        System.out.println("Connection closed");
    }
}
