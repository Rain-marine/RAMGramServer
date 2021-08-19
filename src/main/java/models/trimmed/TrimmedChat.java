package models.trimmed;

import controllers.Controllers;
import models.types.ChatType;

public class TrimmedChat implements Controllers {

    private long loggedUserId;
    private long id;
    private boolean isGroup;
    private String name;
    private String unseenCount;

    public TrimmedChat() {
    }

    public TrimmedChat(long id , long loggedUserId) {
        this.loggedUserId = loggedUserId;
        this.id = id;
        this.isGroup = CHAT_CONTROLLER.isGroup(id);
        this.name= CHAT_CONTROLLER.getChatName(id , loggedUserId);
        this.unseenCount = CHAT_CONTROLLER.getUnseenCount(id , loggedUserId);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isGroup() {
        return isGroup;
    }

    public void setGroup(boolean group) {
        isGroup = group;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnseenCount() {
        return unseenCount;
    }

    public void setUnseenCount(String unseenCount) {
        this.unseenCount = unseenCount;
    }

    public long getLoggedUserId() {
        return loggedUserId;
    }

    public void setLoggedUserId(long loggedUserId) {
        this.loggedUserId = loggedUserId;
    }
}
