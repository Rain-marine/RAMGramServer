package controllers;

import models.requests.Request;
import models.responses.Response;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler extends Thread{

    private long loggedUserId;
    private final Socket socket;
    private boolean killed;
    public PrintWriter printWriter;
    public Scanner scanner;
    public final ObjectMapper objectMapper;
    private String token;


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
        scanner.close();
        printWriter.close();
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("client disconnected");
    }

    public long getLoggedUserId() {
        return loggedUserId;
    }

    public void setLoggedUserId(long loggedUserId) {
        this.loggedUserId = loggedUserId;
    }

    public boolean isKilled() {
        return killed;
    }

    public void setKilled(boolean killed) {
        this.killed = killed;
    }

    public Socket getSocket() {
        return socket;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
