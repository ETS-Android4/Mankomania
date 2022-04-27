package com.mankomania.game;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class Connection {
    private final String server = "https://mankomania-backend.herokuapp.com/";
    private Socket cs;

    /**
     * Static fields
     */
    public static boolean start = false;
    public static Connection con;
    public static String lobbyID;
    public static String[] players = new String[4]; //Array with players ID
    public static void createConnection(){
        con = new Connection();
    }

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
            System.out.println("Couldn't connect to server");
            System.out.println("Error thrown in line 36 in class Connection");
        }
    }

    public void joinRoom(Emitter.Listener el) {
        cs.on("join-room", el);
        cs.emit("join-room", "");
    }

    public void readyForGame(Emitter.Listener el, String lobbyID) {
        cs.once("test2", el);
        System.out.println(lobbyID);
        cs.emit("readyForGame", lobbyID);
    }


    public void closeConnection() {

        cs.disconnect();
        cs.off();
        cs.close();

        System.out.println("Connection closed");
    }
}
