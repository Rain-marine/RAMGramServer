import controllers.SettingController;
import gui.controllers.popups.SimpleConfirmBox;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import models.ServerMain;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import util.ConfigLoader;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Objects;


public class Main {
    static Logger log = LogManager.getLogger(Main.class);
    public static void main(String[] args) {
       log.info("Application Started");
       try {
           new ServerMain(Integer.parseInt(ConfigLoader.readNetworkProperty("port"))).run();
       }catch (IOException e){
           try {
               new ServerMain(Integer.parseInt(ConfigLoader.readNetworkProperty("defaultPort"))).run();
           }catch (IOException exception){
               System.err.println("server connection failed!");
               exception.printStackTrace();
           }

       }

    }


}
