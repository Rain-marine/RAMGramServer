package controllers;

import models.LoggedUser;
import models.NotificationType;
import models.User;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import repository.Repository;

import java.util.List;

public class ProfileAccessController implements Repository {

    private final User loggedUser;
    private final long loggedUserId;
    private final User otherUser;
    private final long otherUserId;
    private boolean[] results = new boolean[6];

    public ProfileAccessController(long otherUserID, long loggedUserId) {
        this.loggedUser = USER_REPOSITORY.getById(loggedUserId);
        this.otherUser = USER_REPOSITORY.getById(otherUserID);
        this.loggedUserId = loggedUserId;
        this.otherUserId = otherUserID;
    }

    public boolean[] checkAccessibility() {
        results[0] = otherUser.isActive();
        for (int i = 1; i < 6; i++) {
            results[i] = false;
        }
        if (!results[0]) {
            return results;
        }
        else {
            //have I blocked them?
            List<User> loggedUserBlacklist = loggedUser.getBlackList();
            for (User user : loggedUserBlacklist) {
                if (user.getId() == otherUserId) {
                    results[1] = true;
                }
            }
            //am I following them?
            List<User> loggedUserFollowing = loggedUser.getFollowings();
            for (User user : loggedUserFollowing) {
                if (user.getId() == otherUserId) {
                    results[2] = true;
                }
            }
            //am I blocked?
            List<User> blackList = otherUser.getBlackList();
            for (User user : blackList) {
                if (user.getId() == loggedUserId) {
                    results[3] = true;
                }
            }
            //is their account private?
            if (otherUser.isPublic()) {
                results[4] = true;
            }

            //have I sent request?
            if (otherUser.getReceiverNotifications().stream().anyMatch(it -> ((it.getSender().getId() == loggedUserId)
                    && (it.getType() == NotificationType.FOLLOW_REQ)))) {
                results[5] = true;
            }
            return results;
        }
    }

}
