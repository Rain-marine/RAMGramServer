package util;

import java.io.*;
import java.util.Properties;

public class ConfigLoader {
    static Properties prop = new Properties();

    private static String readConfigURL(String configFile) {
        try{
            InputStream inputStream = new FileInputStream("src/main/resources/configurations/configurations.properties");
            prop.load(inputStream);
            return (prop.getProperty(configFile));}
        catch (IOException e){
            System.err.println("config file for " + configFile + " is missing");
        }
        return null;
    }
    

    public static String readNetworkProperty(String property){
        String networkConfigFilePath = readConfigURL("networkConfig");
        try {
            assert networkConfigFilePath != null;
            InputStream inputStream = new FileInputStream(networkConfigFilePath);
            prop.load(inputStream);
            return (prop.getProperty(property));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "404 : property not found";
    }


    public static void writeProperty(String key , String value,String comment){
        try {
            OutputStream outputStream = new FileOutputStream("src/main/resources/configurations/configurations.properties");
            prop.setProperty(key, value);
            prop.store(outputStream,comment);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
