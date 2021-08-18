package controllers;

import models.types.NotificationType;
import models.User;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import repository.Repositories;

import java.util.List;

public class ProfileAccessController implements Repositories {
    static Logger log = LogManager.getLogger(ProfileAccessController.class);

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
        log.info(loggedUserId + " viewed profile of " + otherUserID);
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
                    break;
                }
            }
            //am I following them?
            List<User> loggedUserFollowing = loggedUser.getFollowings();
            for (User user : loggedUserFollowing) {
                if (user.getId() == otherUserId) {
                    results[2] = true;
                    break;
                }
            }
            //am I blocked?
            List<User> blackList = otherUser.getBlackList();
            for (User user : blackList) {
                if (user.getId() == loggedUserId) {
                    results[3] = true;
                    break;
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
