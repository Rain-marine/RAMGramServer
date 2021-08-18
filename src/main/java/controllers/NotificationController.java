package controllers;

import models.*;
import models.types.NotificationType;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import repository.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class NotificationController implements Repository {
    private final static Logger log = LogManager.getLogger(NotificationController.class);

    public NotificationController() {
    }

    public void sendFollowRequestToUser(long receiverId, long loggedUserId) {
        Notification followRequestNotification = new Notification(loggedUserId, receiverId, NotificationType.FOLLOW_REQ);
        NOTIFICATION_REPOSITORY.insert(followRequestNotification);
        log.info( loggedUserId + " requested " + receiverId);
    }

    public void followUser(long receiverId, long loggedUserId) {
        User loggedUser = USER_REPOSITORY.getById(loggedUserId);
        if (loggedUser.getFollowings().stream().noneMatch(it -> it.getId() == receiverId)) {
            Notification followNotification = new Notification(loggedUserId, receiverId, NotificationType.START_FOLLOW);
            NOTIFICATION_REPOSITORY.insert(followNotification);
            NOTIFICATION_REPOSITORY.addNewFollower(receiverId, loggedUserId);
            log.info( loggedUser.getUsername() + " followed " + receiverId);
        }


    }

    public void unfollowUserWithNotification(long receiverId, long loggedUserId) {
        Notification unfollowNotification = new Notification(loggedUserId, receiverId, NotificationType.UNFOLLOW);
        NOTIFICATION_REPOSITORY.insert(unfollowNotification);
        NOTIFICATION_REPOSITORY.removeFromFollowings(loggedUserId, receiverId);
        NOTIFICATION_REPOSITORY.removeFromFollowers(receiverId, loggedUserId);
        for (Group group : USER_REPOSITORY.getById(loggedUserId).getGroups()) {
            for (User member : group.getMembers()) {
                if (member.getId() == receiverId) {
                    FACTION_REPOSITORY.removeUserFromGroup(receiverId, group.getId());
                    break;
                }
            }
        }
        log.info(loggedUserId + " unfollowed with notification " + receiverId);
    }

    public void unfollowUserWithoutNotification(long userId , long loggedUserId) {
        User loggedUser = USER_REPOSITORY.getById(loggedUserId);

        NOTIFICATION_REPOSITORY.removeFromFollowings(loggedUser.getId(), userId);
        NOTIFICATION_REPOSITORY.removeFromFollowers(userId, loggedUser.getId());

        List<Group> loggedUserGroups = USER_REPOSITORY.getById(loggedUser.getId()).getGroups();
        for (Group group : loggedUserGroups) {
            for (User member : group.getMembers()) {
                if (member.getId() == userId) {
                    FACTION_REPOSITORY.removeUserFromGroup(userId, group.getId());
                    break;
                }
            }
        }
        log.info(loggedUser.getUsername() + " unfollowed without notification " + userId );
    }

    public List<Notification> getFollowingRequestsNotifications(long loggedUserId) {
        User user = USER_REPOSITORY.getById(loggedUserId);
        List<Notification> notifications = user.getReceiverNotifications();
        List<Notification> followNotification = new ArrayList<>();
        for (Notification notification : notifications) {
            if (notification.getReceiver().getId() == loggedUserId) {
                if (notification.getType() == NotificationType.FOLLOW_REQ)
                    followNotification.add(notification);
            }
        }
        return followNotification;
    }

    public List<Notification> getYourFollowingRequestNotification(long loggedUserId) {
        User user = USER_REPOSITORY.getById(loggedUserId);
        List<Notification> notifications = user.getSenderNotifications();
        List<Notification> followNotification = new ArrayList<>();
        for (Notification notification : notifications) {
            if (notification.getSender().getId() == loggedUserId) {
                if (notification.getType() == NotificationType.FOLLOW_REQ)
                    followNotification.add(notification);
            }
        }
        return followNotification;
    }

    public List<Notification> getSystemNotification(long loggedUserId) {
        User user = USER_REPOSITORY.getById(loggedUserId);
        List<Notification> notifications = user.getReceiverNotifications();
        List<Notification> followNotification = new ArrayList<>();
        for (Notification notification : notifications) {
            if (notification.getReceiver().getId() == loggedUserId) {
                if (notification.getType() != NotificationType.FOLLOW_REQ)
                    followNotification.add(notification);
            }
        }
        return followNotification;
    }

    public void acceptFollowRequest(long notificationId , long loggedUserId) {
        Notification notification = NOTIFICATION_REPOSITORY.getById(notificationId);
        deleteNotification(notificationId , loggedUserId);
        NOTIFICATION_REPOSITORY.addNewFollower(loggedUserId, notification.getSender().getId());
        log.info( loggedUserId + " accepted " + notification.getSender().getUsername() );
    }

    public void rejectFollowRequestWithNotification(long notificationId , long loggedUserId) {
        Notification rejectFollowRequest = new Notification(loggedUserId, (NOTIFICATION_REPOSITORY.getById(notificationId)).getSender().getId(), NotificationType.FOLLOW_REQ_REJECT);
        NOTIFICATION_REPOSITORY.insert(rejectFollowRequest);
        deleteNotification(notificationId , loggedUserId);
    }

    public void rejectFollowRequestWithoutNotification(long notificationId , long loggedUserId) {
        deleteNotification(notificationId , loggedUserId);
    }

    public void deleteNotification(long notificationId , long loggedUserId) {
        Notification notification = NOTIFICATION_REPOSITORY.getById(notificationId);
        NOTIFICATION_REPOSITORY.deleteNotification(notificationId);
        User loggedUser = USER_REPOSITORY.getById(loggedUserId);
        loggedUser.getReceiverNotifications().remove(notification);
    }

    public void deleteRequest(long rawUserId , long loggedUserId) {
        User user = USER_REPOSITORY.getById(rawUserId);
        Notification request = user.getReceiverNotifications().stream()
                .filter(it -> ((it.getSender().getId() == loggedUserId) && (it.getType() == NotificationType.FOLLOW_REQ)))
                .collect(Collectors.toList()).get(0);
        NOTIFICATION_REPOSITORY.deleteNotification(request.getId());
    }
}