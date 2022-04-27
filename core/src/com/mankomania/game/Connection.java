package com.mankomania.game;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class Connection {
    private final String server = "https://mankomania-backend.herokuapp.com/";
    private Socket cs;

    public static boolean start = false;
    public static Connection con;
    public static String lobbyID;
    public static String[] players = new String[4]; //Array with players ID
    public static void createConnection(){
        con = new Connection();
    }

    public Connection() {

        try {
            cs = IO.socket(server);
            cs.connect();

        } catch (URISyntaxException e) {

        }
    }

    public void joinRoom(Emitter.Listener el) {
        cs.on("join-room", el);
        cs.emit("join-room", "");
    }

    public void readyForGame(Emitter.Listener el, String lobbyID) {
        cs.once("test2", el);
        cs.emit("readyForGame", lobbyID);
    }

    public void closeConnection() {
        cs.disconnect();
        cs.off();
        cs.close();
    }
}
