package models.trimmed;

import controllers.Controllers;

import java.util.Date;

public class TrimmedLoggedUser implements Controllers {

    private long id;
    private String lastSeenStatus;
    private boolean isPublic;
    private boolean isActive;
    private String username;
    private String fullName;
    private String bio;
    private String phoneNumber;
    private String email;
    private Date birthday;
    private byte[] profilePic;

    public TrimmedLoggedUser() {
    }

    public TrimmedLoggedUser(long userId) {
        id = userId;
        isActive = USER_CONTROLLER.getActive(userId);
        username = USER_CONTROLLER.getUsername(userId);
        fullName = USER_CONTROLLER.UserFullName(userId);
        email = USER_CONTROLLER.getEmail(userId);
        phoneNumber = USER_CONTROLLER.getPhoneNumber(userId);
        bio = USER_CONTROLLER.getUserBio(userId);
        birthday = USER_CONTROLLER.getBirthday(userId);
        profilePic = USER_CONTROLLER.getProfilePhoto(userId);
        isPublic = SETTING_CONTROLLER.isAccountPublic(username);
        lastSeenStatus = SETTING_CONTROLLER.getUserLastSeenStatus(username);
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
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

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
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

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public byte[] getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(byte[] profilePic) {
        this.profilePic = profilePic;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLastSeenStatus() {
        return lastSeenStatus;
    }

    public void setLastSeenStatus(String lastSeenStatus) {
        this.lastSeenStatus = lastSeenStatus;
    }
}
