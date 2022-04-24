package com.mankomania.game;

import java.net.URISyntaxException;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class Connection{
    private final String server = "https://mankomania-backend.herokuapp.com/";
    //private final int port = 53216;
    private Socket cs;
    private String response;

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

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }


    /**
     * Emits, that the player wants to join a lobby.
     * Server response with the lobby id.
     * @return lobby id
     */

    public String joinRoom(){

        cs.emit("join-room", "");

        cs.once("join-room", new Emitter.Listener() {
            @Override
            public void call(Object... args) {

                System.out.println("Ausgabe in methode: " + args[0].toString());

                response = args[0].toString();
            }
        });

        return this.response;
    }

    /**
     * Sends a "ready to play" request
     * @return  server message (String)
     */

    public String readyForGame(){

        cs.emit("readyForGame", "");

        cs.once("NAN", new Emitter.Listener() {
            @Override
            public void call(Object... args) {

                System.out.println("Ready in Class: " + args[0]);

                response = args[0].toString();
            }
        });

        return response;
    }


    /**
     * Close connection to server
     */
    public void closeConnection(){
        cs.emit("disconnect", "");
        cs.off();
        cs.disconnect();
        cs.close();
    }
}
