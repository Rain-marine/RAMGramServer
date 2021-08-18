package models.responses;

import controllers.Controllers;
import org.codehaus.jackson.annotate.JsonTypeName;

import java.util.ArrayList;

@JsonTypeName("chatInfo")
public class ChatInfoResponse implements Response, Controllers {

    private long loggedUserId;
    private long chatId;
    private long frontUserId = 0L;
    private String frontUsername = null;
    private String lastSeen = null;
    private byte[] frontProfile = null;
    private ArrayList<String> membersNames = new ArrayList<>();

    public ChatInfoResponse(long chatId , long loggedUserId) {
        this.loggedUserId = loggedUserId;
        this.chatId = chatId;
    }

    @Override
    public void unleash() {
        this.frontUsername = CHAT_CONTROLLER.getChatName(chatId , loggedUserId);
        if(CHAT_CONTROLLER.isGroup(chatId)){
            this.membersNames = CHAT_CONTROLLER.getMembersNames(chatId);
        }
        else {
            this.frontUserId = CHAT_CONTROLLER.getFrontUserId(chatId ,loggedUserId);
            this.frontProfile = USER_CONTROLLER.getProfilePhoto(frontUserId);
            this.lastSeen = SETTING_CONTROLLER.lastSeenForLoggedUser(frontUserId , loggedUserId);
        }
    }

    public ChatInfoResponse() {
    }

    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    public long getFrontUserId() {
        return frontUserId;
    }

    public void setFrontUserId(long frontUserId) {
        this.frontUserId = frontUserId;
    }

    public String getFrontUsername() {
        return frontUsername;
    }

    public void setFrontUsername(String frontUsername) {
        this.frontUsername = frontUsername;
    }

    public String getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(String lastSeen) {
        this.lastSeen = lastSeen;
    }

    public byte[] getFrontProfile() {
        return frontProfile;
    }

    public void setFrontProfile(byte[] frontProfile) {
        this.frontProfile = frontProfile;
    }

    public ArrayList<String> getMembersNames() {
        return membersNames;
    }

    public void setMembersNames(ArrayList<String> membersNames) {
        this.membersNames = membersNames;
    }

    public long getLoggedUserId() {
        return loggedUserId;
    }

    public void setLoggedUserId(long loggedUserId) {
        this.loggedUserId = loggedUserId;
    }
}
