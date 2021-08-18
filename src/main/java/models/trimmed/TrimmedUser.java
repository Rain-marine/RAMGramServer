package models.trimmed;

import controllers.Controllers;

public class TrimmedUser implements Controllers {

    private long userId;
    private String lastSeen;
    private String phoneNumber;
    private String email;
    private String birthday;
    private String bio;
    private String username;
    private String fullName;
    private byte[] profilePhoto;


    public TrimmedUser(long userId, long loggedUserId) {
        this.userId = userId;
        this.username = USER_CONTROLLER.getUsername(userId);
        this.fullName = USER_CONTROLLER.UserFullName(userId);
        lastSeen = SETTING_CONTROLLER.lastSeenForLoggedUser(userId, loggedUserId);
        phoneNumber = SETTING_CONTROLLER.phoneNumberForLoggedUser(userId, loggedUserId);
        email = SETTING_CONTROLLER.emailForLoggedUser(userId, loggedUserId);
        birthday = SETTING_CONTROLLER.birthdayForLoggedUser(userId, loggedUserId);
        bio = USER_CONTROLLER.getUserBio(userId);
        profilePhoto = USER_CONTROLLER.getProfilePhoto(userId);
    }

    public TrimmedUser() {
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(String lastSeen) {
        this.lastSeen = lastSeen;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public byte[] getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(byte[] profilePhoto) {
        this.profilePhoto = profilePhoto;
    }
}
