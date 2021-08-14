package controllers;

import models.ServerMain;
import models.User;
import models.requests.Request;
import models.responses.Response;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler extends Thread{

    private User loggedUser;
    private final Socket socket;
    private boolean killed;
    public PrintWriter printWriter;
    public Scanner scanner;
    public final ObjectMapper objectMapper;


    public ClientHandler(Socket socket) {
        this.socket = socket;
        this.objectMapper = new ObjectMapper();
        this.killed = false;
        try {
            this.scanner = new Scanner(socket.getInputStream());
            this.printWriter = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        while (!killed){
            try {
                Request request = objectMapper.readValue(scanner.nextLine(), Request.class);
                Response response = request.execute(this);
                response.unleash();
                printWriter.println(objectMapper.writeValueAsString(response));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public User getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
    }

    public boolean isKilled() {
        return killed;
    }

    public void setKilled(boolean killed) {
        this.killed = killed;
    }
}
