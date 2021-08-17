package models.requests;

import controllers.ClientHandler;
import controllers.Controllers;
import models.responses.BooleanResponse;
import models.responses.Response;
import org.codehaus.jackson.annotate.JsonTypeName;

import java.util.List;

@JsonTypeName("sendMessage")
public class SendMessageRequest implements Request, Controllers {

    public enum TYPE {SEND, NEW_GROUP, FORWARD, FORWARD_TWEET, ADD_MEMBER , CHAT}

    private String token;
    private long userId;
    private TYPE type;
    private long contentId;
    private List<String> users;
    private List<String> factions;
    private String content;
    private byte[] contentImage;
    private String targetUsername;

    public SendMessageRequest(String token, long userId, TYPE type, long contentId, List<String> users, List<String> factions, String content, byte[] contentImage, String targetUsername) {
        this.token = token;
        this.userId = userId;
        this.type = type;
        this.contentId = contentId;
        this.users = users;
        this.factions = factions;
        this.content = content;
        this.contentImage = contentImage;
        this.targetUsername = targetUsername;
    }

    @Override
    public Response execute(ClientHandler clientHandler) {
        if (clientHandler.getToken().equals(token)) {
            switch (type) {
                case SEND -> MESSAGE_CONTROLLER.sendMessage(content,contentImage ,users ,factions , userId);
                case FORWARD -> MESSAGE_CONTROLLER.forward(contentId,users,factions, userId);
                case FORWARD_TWEET -> MESSAGE_CONTROLLER.forwardTweet(contentId,targetUsername, userId);
                case NEW_GROUP -> CHAT_CONTROLLER.createGroupChat(users,content, userId);
                case ADD_MEMBER -> CHAT_CONTROLLER.addMemberToGroupChat(targetUsername,contentId);
                case CHAT -> MESSAGE_CONTROLLER.getChatWithUser(contentId , userId);
            }
            return new BooleanResponse(true);
        } else
            return new BooleanResponse(false);
    }

    public SendMessageRequest() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public TYPE getType() {
        return type;
    }

    public void setType(TYPE type) {
        this.type = type;
    }

    public long getContentId() {
        return contentId;
    }

    public void setContentId(long contentId) {
        this.contentId = contentId;
    }

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }

    public List<String> getFactions() {
        return factions;
    }

    public void setFactions(List<String> factions) {
        this.factions = factions;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public byte[] getContentImage() {
        return contentImage;
    }

    public void setContentImage(byte[] contentImage) {
        this.contentImage = contentImage;
    }

    public String getTargetUsername() {
        return targetUsername;
    }

    public void setTargetUsername(String targetUsername) {
        this.targetUsername = targetUsername;
    }
}
