package controllers;

import models.Group;
import models.User;
import repository.Repository;

import java.util.ArrayList;
import java.util.List;

public class FactionsController implements Repository {

    public FactionsController() {
    }

    public void insertNewFaction(String name, List<String> users , long loggedUserId) {
        User loggedUser = USER_REPOSITORY.getById(loggedUserId);
        Group newGroup = new Group(name, loggedUser);

        List<User> members = new ArrayList<>();
        for (String username : users) {
            if (members.stream().noneMatch(it -> it.getUsername().equals(username)))
                members.add(USER_REPOSITORY.getByUsername(username));
        }
        newGroup.setMembers(members);
        FACTION_REPOSITORY.insert(newGroup);
    }

    public boolean canAddToGroup(String username , long loggedUserId) {
        User loggedUser = USER_REPOSITORY.getById(loggedUserId);
        List<User> followings = loggedUser.getFollowings();
        for (User user : followings) {
            if(username.equals(user.getUsername())){
                if(user.isActive())
                    return true;
            }
        }
        return false;
    }

    public List<Group> getFactions(long loggedUserId) {
        User loggedUser = USER_REPOSITORY.getById(loggedUserId);
        return loggedUser.getGroups();
    }

    public List<User> getActiveFollowers(long loggedUserId) {
        List<User> followers = USER_REPOSITORY.getById(loggedUserId).getFollowers();
        List<User> activeFollowers = new ArrayList<>();
        followers.forEach(following -> {
            if (following.isActive())
                activeFollowers.add(following);
        });
        return activeFollowers;
    }

    public List<User> getActiveFollowings(long userId) {
        List<User> followings = USER_REPOSITORY.getById(userId).getFollowings();
        List<User> activeFollowings = new ArrayList<>();
        followings.forEach(following -> {
            if (following.isActive())
                activeFollowings.add(following);
        });
        return activeFollowings;
    }

    public List<User> getActiveBlockedUsers(long loggedUserId) {
        List<User> blockedUsers = USER_REPOSITORY.getById(loggedUserId).getBlackList();
        List<User> activeBlockedUsers = new ArrayList<>();
        blockedUsers.forEach(following -> {
            if(following.isActive())
                activeBlockedUsers.add(following);
        });
        return activeBlockedUsers;
    }

    public void deleteFaction(int groupId , long loggedUserId) {
        FACTION_REPOSITORY.deleteFaction(groupId);
        USER_REPOSITORY.getById(loggedUserId).getGroups().remove(FACTION_REPOSITORY.getFactionById(groupId));
    }

    public void deleteUserFromFaction(int factionId, long userId) {
        FACTION_REPOSITORY.deleteUserFromFaction(factionId, userId);
    }

    public void addUserToFaction(int factionId, String username) {
        FACTION_REPOSITORY.addUserToFaction(factionId, USER_REPOSITORY.getByUsername(username).getId());
    }

}