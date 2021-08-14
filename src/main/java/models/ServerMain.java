package models;

import controllers.ClientHandler;
import org.codehaus.jackson.map.ObjectMapper;

import javax.swing.event.CaretListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class ServerMain {

    private ServerSocket serverSocket;
    private ObjectMapper objectMapper;
    private int port;
    private static HashMap<String, Long> onlineUsers;

    public ServerMain(int port) throws IOException {
        onlineUsers = new HashMap<>();
        this.port = port;
        this.serverSocket = new ServerSocket(port);
        this.objectMapper = new ObjectMapper();


    }

    public void run() {

        while (true) {
            try {
                System.out.println("waiting for client");
                Socket clientSocket = serverSocket.accept();
                System.out.println("client connected");
                new ClientHandler(clientSocket).start();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static synchronized boolean findOnlineUser(String token, Long userId) {
        return Objects.equals(onlineUsers.get(token), userId);
    }

    public static synchronized void addOnlineUser(String token, Long userId) {
        onlineUsers.put(token, userId);
    }

    public static synchronized HashMap<String, Long> getOnlineUsers() {
        return onlineUsers;
    }

    public static synchronized void setOnlineUsers(HashMap<String, Long> onlineUsers) {
        ServerMain.onlineUsers = onlineUsers;
    }
}
