package controllers;


import models.LoggedUser;
import models.User;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import repository.Repository;

import java.util.Date;
import java.util.List;


public class SettingController implements Repository {
    private final static Logger log = LogManager.getLogger(SettingController.class);


    public SettingController() {
    }

    public void logout() {
        if (!(LoggedUser.getLoggedUser() == null))
            USER_REPOSITORY.setLastSeen(LoggedUser.getLoggedUser().getId(), new Date());
        LoggedUser.setLoggedUser(null);
    }

    public void deleteAccount(long userId) {
        USER_REPOSITORY.deleteAccount(userId);
        log.info("account deleted ");
        LoggedUser.setLoggedUser(null);
    }

    public boolean isPasswordCorrect(String password , long userId) {
        return USER_REPOSITORY.getById(userId).getPassword().equals(password);
    }

    public boolean isAccountPublic(String username) {
        User user = USER_REPOSITORY.getByUsername(username);
        return user.isPublic();
    }

    public void changeAccountVisibility(boolean newVisibility , long userId) {
        USER_REPOSITORY.changeAccountVisibility(userId, newVisibility);
    }

    public void deActiveAccount(long userId) {
        USER_REPOSITORY.deactivateAccount(userId);
        log.info(userId + " account deActivated");
        logout();
    }

    public String getUserLastSeenStatus(String username) {
        User user = USER_REPOSITORY.getByUsername(username);
        return user.getLastSeenStatus();
    }

    public void changeLastSeenStatus(String newStatus , long userId) {
        USER_REPOSITORY.changeLastSeenStatus(userId, newStatus);
        log.info("last seen status for " + userId + " was changed to " + newStatus);
    }

    public void changePassword(String newPassword , long userId) {
        USER_REPOSITORY.changePassword(userId, newPassword);
    }

    public String lastSeenForLoggedUser(long rawUserId) {
        User user = USER_REPOSITORY.getById(rawUserId);
        long loggedUserId = LoggedUser.getLoggedUser().getId();
        String status = USER_REPOSITORY.getById(user.getId()).getLastSeenStatus();
        if (user.getFollowings().stream().noneMatch(it -> it.getId() == loggedUserId)) {
            return ("last seen recently");
        } else if (status.equals("everybody"))
            return (user.getLastSeen().toString());
        else if (status.equals("following")) {
            List<User> userFollowing = user.getFollowings();
            for (User following : userFollowing) {
                if (following.getId() == loggedUserId) {
                    return (user.getLastSeen().toString());
                }
            }
        }
        return ("last seen recently");
    }

    public String birthdayForLoggedUser(long userId) {
        User user = USER_REPOSITORY.getById(userId);
        User.Level status = user.isBirthDayVisible();
        if (user.getBirthday() != null){
            if (status == User.Level.FOLLOWING) {
                long loggedUserId = LoggedUser.getLoggedUser().getId();
                List<User> following = user.getFollowings();
                for (User followed : following) {
                    if (followed.getId() == loggedUserId) {
                        return user.getBirthday().toString();
                    }
                }
                return "not visible";
            } else if (status == User.Level.ALL) {
                return user.getBirthday().toString();
            } else {
                return "not visible";
            }
        }
        else
            return "not visible";
    }


    public String emailForLoggedUser(long userId) {
        User user = USER_REPOSITORY.getById(userId);
        User.Level status = user.isEmailVisible();
        if (status == User.Level.FOLLOWING) {
            long loggedUserId = LoggedUser.getLoggedUser().getId();
            List<User> following = user.getFollowings();
            for (User followed : following) {
                if (followed.getId() == loggedUserId) {
                    return user.getEmail();
                }
            }
            return "not visible";
        } else if (status == User.Level.ALL) {
            return user.getEmail();
        } else {
            return "not visible";
        }

    }

    public String phoneNumberForLoggedUser(long userId) {
        User user = USER_REPOSITORY.getById(userId);
        User.Level status = user.isPhoneNumberVisible();
        if (!user.getPhoneNumber().equals("")){
            if (status == User.Level.FOLLOWING) {
                long loggedUserId = LoggedUser.getLoggedUser().getId();
                List<User> following = user.getFollowings();
                for (User followed : following) {
                    if (followed.getId() == loggedUserId) {
                        return user.getPhoneNumber();
                    }
                }
                return "not visible";
            } else if (status == User.Level.ALL) {
                return user.getPhoneNumber();
            } else {
                return "not visible";
            }
        }
        else {
            return "not visible";
        }


    }

    public User.Level getUserNumberStatus(User loggedUser) {
        return USER_REPOSITORY.getById(loggedUser.getId()).isPhoneNumberVisible();
    }

    public User.Level getUserEmailStatus(User loggedUser) {
        return USER_REPOSITORY.getById(loggedUser.getId()).isEmailVisible();
    }

    public User.Level getUserBirthdayStatus(User loggedUser) {
        return USER_REPOSITORY.getById(loggedUser.getId()).isBirthDayVisible();
    }

    public void changeNumberStatus(String newStatus) {
        User.Level status = switch (newStatus) {
            case "FOLLOWING" -> User.Level.FOLLOWING;
            case "ALL" -> User.Level.ALL;
            default -> User.Level.NONE;
        };
        USER_REPOSITORY.changeNumberStatus(LoggedUser.getLoggedUser().getId(), status);
    }

    public void changeEmailStatus(String newStatus) {
        User.Level status = switch (newStatus) {
            case "FOLLOWING" -> User.Level.FOLLOWING;
            case "ALL" -> User.Level.ALL;
            default -> User.Level.NONE;
        };
        USER_REPOSITORY.changeEmailStatus(LoggedUser.getLoggedUser().getId(), status);
    }

    public void changeBirthdayStatus(String newStatus) {
        User.Level status = switch (newStatus) {
            case "FOLLOWING" -> User.Level.FOLLOWING;
            case "ALL" -> User.Level.ALL;
            default -> User.Level.NONE;
        };
        USER_REPOSITORY.changeBirthdayStatus(LoggedUser.getLoggedUser().getId(), status);
    }

    public void activateAccount(long userId) {
        User deActiveUser = USER_REPOSITORY.getById(userId);
        USER_REPOSITORY.activateAccount(deActiveUser.getId());
        log.info(userId + " account activated");
    }
}
