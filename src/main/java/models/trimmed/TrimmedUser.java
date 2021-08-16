package models.trimmed;

import controllers.Controllers;

public class TrimmedUser implements Controllers {
    private long loggedUserId;
    private long userId;
    private String lastSeen;
    private String phoneNumber;
    private String email;
    private String birthday;
    private String bio;


    public TrimmedUser(long userId) {
        lastSeen = SETTING_CONTROLLER.lastSeenForLoggedUser(userId, loggedUserId);
        phoneNumber = SETTING_CONTROLLER.phoneNumberForLoggedUser(userId, loggedUserId);
        email = SETTING_CONTROLLER.emailForLoggedUser(userId, loggedUserId);
        birthday = SETTING_CONTROLLER.birthdayForLoggedUser(userId, loggedUserId);
        bio = USER_CONTROLLER.getUserBio(userId);
    }
}
