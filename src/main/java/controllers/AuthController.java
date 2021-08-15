package controllers;

import exceptions.InvalidInputException;
import models.LoggedUser;
import models.ServerMain;
import models.User;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import repository.Repository;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;


public class AuthController implements Repository {
    private final static Logger log = LogManager.getLogger(AuthController.class);

    public ArrayList<String> login(String username, String password, ClientHandler clientHandler) throws InvalidInputException {
        User user = USER_REPOSITORY.getByUsername(username);
        if (user == null) {
            throw new InvalidInputException("Username not found");
        } else {
            String rightPass = user.getPassword();
            if (!rightPass.equals(password)) {
                log.warn(username + " entered wrong password");
                throw new InvalidInputException("Wrong password");
            }
            else {
                clientHandler.setLoggedUserId(user.getId());
                String token = generateToken();
                ServerMain.addOnlineUser(token , user.getId());
                log.info(username + " logged in");
                if (user.isActive()) {
                    log.info(username + " last seen updated: ");
                    USER_REPOSITORY.setLastSeen(user.getId(), new Date());
                }
                return new ArrayList<>() {{
                    add(token);
                    add(String.valueOf(user.getId()));
                }};
            }

        }

    }


    private String generateToken() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] bytes = new byte[20];
        secureRandom.nextBytes(bytes);
        Base64.Encoder encoder = Base64.getUrlEncoder().withoutPadding();
        String token = encoder.encodeToString(bytes);
        return token;
    }
}
