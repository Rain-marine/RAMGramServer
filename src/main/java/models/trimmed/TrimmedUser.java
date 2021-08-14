package models.trimmed;

import controllers.Controllers;

public class TrimmedUser implements Controllers {
    private long userId;
    private String lastSeen ;
    private String phoneNumber;
    private String email;
    private String birthday;
    private String bio;


    public TrimmedUser(long userId) {
        lastSeen = SETTING_CONTROLLER.lastSeenForLoggedUser(userId);
        phoneNumber = SETTING_CONTROLLER.phoneNumberForLoggedUser(userId);
        email = SETTING_CONTROLLER.emailForLoggedUser(userId);
        birthday = SETTING_CONTROLLER.birthdayForLoggedUser(userId);
        bio = USER_CONTROLLER.getUserBio(userId);
    }
}
