package controllers;

import models.User;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler extends Thread{

    private User loggedUser;
    private final Socket socket;
    public PrintWriter printWriter;
    public Scanner scanner;
    public final ObjectMapper objectMapper;


    public ClientHandler(Socket socket) {
        this.socket = socket;
        this.objectMapper = new ObjectMapper();
        try {
            this.scanner = new Scanner(socket.getInputStream());
            this.printWriter = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        super.run();
    }

    public User getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
    }

    public Socket getSocket() {
        return socket;
    }
}
