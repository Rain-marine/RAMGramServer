import controllers.ClientHandler;
import org.codehaus.jackson.map.ObjectMapper;

import javax.swing.event.CaretListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class Server {

    private ServerSocket serverSocket;
    private ObjectMapper objectMapper;
    private int port;

    public Server(int port) {
        try {
            this.port = port;
            this.serverSocket = new ServerSocket(port);
            this.objectMapper = new ObjectMapper();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void run(){

        while(true){
            try {
                Socket clientSocket = serverSocket.accept();
                new ClientHandler(clientSocket).start();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
