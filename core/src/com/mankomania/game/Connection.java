package com.mankomania.game;

import java.net.URISyntaxException;
import java.util.logging.Logger;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class Connection {
    private static final String server = "https://mankomania-backend.herokuapp.com/";
    private Socket cs;

    private boolean start = false;
    private static Connection con;
    private static String lobbyID;
    private static String[] players = new String[4]; //Array with players ID
    public static void createConnection(){
        con = new Connection();
    }

    public Connection() {

        try {
            cs = IO.socket(server);
            cs.connect();

        } catch (URISyntaxException e) {
            Logger logger = Logger.getLogger("Connection failed");

        }
    }

    public String getServer() {
        return server;
    }

    public Socket getCs() {
        return cs;
    }

    public void setCs(Socket cs) {
        this.cs = cs;
    }

    public boolean getStart() {
        return start;
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    public static Connection getCon() {
        return con;
    }

    public static void setCon(Connection con) {
        Connection.con = con;
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
