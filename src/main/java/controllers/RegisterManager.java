package controllers;

import models.User;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import repository.Repository;

import java.util.Date;

public class RegisterManager  implements Repository {
    private final static Logger log = LogManager.getLogger(RegisterManager.class);


    public synchronized void makeNewUser(String[] info)   {
        String fullName = info[0];
        String username = info[1];
        String password = info[2];
        String email = info[3];
        String phoneNumber = info[4];
        String bio = info[5];
        String birthday = info[6];
        Date birthdayDate = DateFormat.stringToDate(birthday);
        User user = new User(username,fullName,email,password,phoneNumber, bio, birthdayDate);
        USER_REPOSITORY.insert(user);
        log.info(" new User: " + username);
    }

    public synchronized boolean isUsernameAvailable(String username) {
        User user = USER_REPOSITORY.getByUsername(username);
        return (user == null);
    }

    public synchronized boolean isEmailAvailable(String email) {
        User user = USER_REPOSITORY.getByEmail(email);
        return user == null;
    }

    public synchronized boolean isPhoneNumberAvailable(String phoneNumber) {
        User user = USER_REPOSITORY.getByPhoneNumber(phoneNumber);
        return user == null;
    }
}
