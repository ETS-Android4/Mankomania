package com.mankomania.game;

import java.net.URISyntaxException;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class Connection {
    private static final String server = "https://mankomania-backend.herokuapp.com/";
    private static Socket cs;

    private static boolean start = false;
    private static String lobbyID;
    private static String[] players = new String[4];

    public static void createConnection(){
        try {
            cs = IO.socket(server);
            cs.connect();

        } catch (URISyntaxException e) {
            System.out.println("Couldn't connect to server");

        }
    }

    private Connection(){
        
    }

    public static Socket getCs() {
        return cs;
    }

    public static void setCs(Socket cs) {
        Connection.cs = cs;
    }

    public static boolean getStart() {
        return start;
    }

    public static void setStart(boolean start) {
        Connection.start = start;
    }

    public static String getLobbyID() {
        return lobbyID;
    }

    public static void setLobbyID(String lobbyID) {
        Connection.lobbyID = lobbyID;
    }

    public static String[] getPlayers() {
        return players;
    }

    public static void setPlayers(String[] players) {
        Connection.players = players;
    }





    public static void joinRoom(Emitter.Listener el) {
        cs.on("join-room", el);
        cs.emit("join-room", "");
    }

    public static void readyForGame(Emitter.Listener el) {
        cs.on("startGame", el);
    }

    public static void closeConnection() {
        cs.emit("leaveRoom", "");
        cs.disconnect();
        cs.off();
        cs.close();
    }

}
