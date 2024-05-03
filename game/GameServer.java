package game;

import java.io.*;
import java.net.*;

public class GameServer {
    private ServerSocket serverSocket;
    private int numPlayers;

    public GameServer() {

        try {
            serverSocket = new ServerSocket(51734);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void acceptConnections() {
        try {
            while (numPlayers < 2) {
                Socket socket = serverSocket.accept();
                numPlayers++;

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        GameServer gameServer = new GameServer();
        gameServer.acceptConnections();
    }
}
