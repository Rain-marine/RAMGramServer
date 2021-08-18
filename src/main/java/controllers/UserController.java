package controllers;

import models.Group;
import models.User;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import repository.Repositories;

import java.util.Date;

public class UserController implements Repositories {
    final RegisterManager REGISTER_MANAGER ;
    private final static Logger log = LogManager.getLogger(UserController.class);


    public UserController() {
        REGISTER_MANAGER = new RegisterManager();
    }

    public void blockUser(long userToBlockId , long loggedUserId) {
        User loggedUser = USER_REPOSITORY.getById(loggedUserId);
        User userToBlock = USER_REPOSITORY.getById(userToBlockId);
        for (User user : loggedUser.getFollowers())
            if(user.getUsername().equals(userToBlock.getUsername())) {
                NOTIFICATION_REPOSITORY.removeFromFollowers(loggedUser.getId(), user.getId());
                break;
            }
        for (User user : USER_REPOSITORY.getById(loggedUserId).getFollowings())
            if(user.getUsername().equals(userToBlock.getUsername())) {
                NOTIFICATION_REPOSITORY.removeFromFollowings(loggedUserId, user.getId());
                break;
            }
        for (Group group : USER_REPOSITORY.getById(loggedUserId).getGroups()) {
            for (User member : group.getMembers()) {
                if(member.getUsername().equals(userToBlock.getUsername())) {
                    FACTION_REPOSITORY.removeUserFromGroup(member.getId(), group.getId());
                    break;
                }
            }
        }
        FACTION_REPOSITORY.addUserToBlackList(loggedUser.getId(), userToBlock.getId());
    }

    public void muteUser(long userId , long loggedUserId) {
        USER_REPOSITORY.mute(loggedUserId, userId);
    }

    public long getUserByUsername(String usernameToFind) {
        User user = USER_REPOSITORY.getByUsername(usernameToFind);
        if (user == null){
            return 0L;
        }
        return user.getId();
    }

    public void reportUser(long userId) {
        USER_REPOSITORY.increaseReportCount(userId);
        log.warn(userId + " account was reported");
    }

    public boolean changeUsername(String newUsername, long loggedUserId) {
        if (REGISTER_MANAGER.isUsernameAvailable(newUsername)) {
            USER_REPOSITORY.changeUsername(loggedUserId, newUsername);
            log.info(loggedUserId + " user name was changed to "+ newUsername);
            return true;
        }
        return false;
    }

    public boolean changeBio(String newBio , long loggedUserId) {
        USER_REPOSITORY.changeBio(loggedUserId, newBio);
        return true;
    }

    public boolean changeName(String newName, long loggedUserId) {
        USER_REPOSITORY.changeFullName(loggedUserId, newName);
        return true;
    }

    public void changeBirthday(Date birthday , long userId) {
        USER_REPOSITORY.changeBirthdayDate(userId, birthday);
    }

    public boolean changeEmail(String newEmail, long loggedUserId) {
        if (REGISTER_MANAGER.isEmailAvailable(newEmail)) {
            USER_REPOSITORY.changeEmail(loggedUserId, newEmail);
            log.info(loggedUserId + " email name was changed to "+ newEmail);

            return true;
        }
        return false;
    }

    public boolean changeNumber(String newNumber , long loggedUserId) {
        if(REGISTER_MANAGER.isPhoneNumberAvailable(newNumber)) {
            USER_REPOSITORY.changePhoneNumber(loggedUserId, newNumber);
            return true;
        }
        return false;
    }

    public void unblockUser(long userId , long loggedUserId) {
        USER_REPOSITORY.unblock(loggedUserId, userId);
    }

    public boolean isAccountPublic(String username) {
        return USER_REPOSITORY.getByUsername(username).isPublic();
    }

    public boolean changeProfilePhoto(byte[] newPhoto , long loggedUserId){
        USER_REPOSITORY.changeProfilePhoto(loggedUserId, newPhoto);
        return true;
    }

    public User getById(long id) {
        return USER_REPOSITORY.getById(id);
    }

    public String getUsername(long frontUserID) {
        return  USER_REPOSITORY.getById(frontUserID).getUsername();
    }

    public byte[] getProfilePhoto(long frontUserID) {
        return USER_REPOSITORY.getById(frontUserID).getProfilePhoto();

    }

    public String getUserBio(long userId) {
        return USER_REPOSITORY.getById(userId).getBio();
    }

    public String UserFullName(long userId) {
        return USER_REPOSITORY.getById(userId).getFullName();
    }

    public String getEmail(long userId) {
        return USER_REPOSITORY.getById(userId).getEmail();
    }

    public String getPhoneNumber(long userId) {
        return USER_REPOSITORY.getById(userId).getPhoneNumber();
    }

    public Date getBirthday(long userId) {
        return USER_REPOSITORY.getById(userId).getBirthday();
    }

    public boolean getActive(long userId) {
        return  USER_REPOSITORY.getById(userId).isActive();
    }
}
