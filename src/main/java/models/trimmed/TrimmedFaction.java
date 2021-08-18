package models.trimmed;

import controllers.Controllers;
import models.User;
import repository.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TrimmedFaction implements Repository, Controllers {

    private int id;
    private long ownerId;
    private String name;
    private HashMap<Long , String> members;

    public TrimmedFaction() {
    }

    public TrimmedFaction(int id , long ownerId) {
        this.id = id;
        this.ownerId = ownerId;
        members = new HashMap<>();
        List<User> users;
        switch (id){
            case -1 -> {
                this.name = "FOLLOWERS";
                users = FACTIONS_CONTROLLER.getActiveFollowings(ownerId);
            }
            case -2 ->{
                this.name = "FOLLOWINGS";
                users = FACTIONS_CONTROLLER.getActiveFollowings(ownerId);
            }
            case -3 ->{
                this.name = "BLACKLIST";
                users = FACTIONS_CONTROLLER.getActiveBlockedUsers(ownerId);
            }
            default -> {
                this.name = FACTION_REPOSITORY.getFactionById(id).getName();
                users = FACTION_REPOSITORY.getFactionById(id).getMembers();
            }

        }
        for (User user : users) {
            members.put(user.getId() , user.getUsername());
        }


    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<Long, String> getMembers() {
        return members;
    }

    public void setMembers(HashMap<Long, String> members) {
        this.members = members;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }
}
