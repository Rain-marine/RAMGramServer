package models.requests;

import controllers.ClientHandler;
import controllers.Controllers;
import models.responses.BooleanResponse;
import models.responses.ConnectionErrorResponse;
import models.responses.ExploreResponse;
import models.responses.Response;
import models.types.MessageAccessType;
import org.codehaus.jackson.annotate.JsonTypeName;

import javax.persistence.PersistenceException;

@JsonTypeName("messageAccess")
public class MessageAccessRequest implements Request, Controllers {


    private String token;
    private long userId;
    private MessageAccessType type;
    private String targetUsername;

    public MessageAccessRequest(String token, long userId, MessageAccessType type, String targetUsername) {
        this.token = token;
        this.userId = userId;
        this.type = type;
        this.targetUsername = targetUsername;
    }

    @Override
    public Response execute(ClientHandler clientHandler) {
        try{
        if (clientHandler.getToken().equals(token)) {
            switch (type) {
                case USER -> {
                    return new BooleanResponse(MESSAGE_CONTROLLER.canSendMessageToUser(targetUsername, userId));
                }
                case GROUP -> {
                    return new BooleanResponse(MESSAGE_CONTROLLER.canSendMessageToGroup(targetUsername, userId));
                }
                case FACTION -> {
                    return new BooleanResponse(FACTIONS_CONTROLLER.canAddToGroup(targetUsername , userId));
                }
                default -> {
                    return new BooleanResponse(true);
                }
            }
        } else
            return new BooleanResponse(false);
        }catch (PersistenceException dateBaseConnectionError){
            return new ConnectionErrorResponse(dateBaseConnectionError.getMessage());
        }
    }

    public MessageAccessRequest() {
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

    public String getTargetUsername() {
        return targetUsername;
    }

    public void setTargetUsername(String targetUsername) {
        this.targetUsername = targetUsername;
    }

    public MessageAccessType getType() {
        return type;
    }

    public void setType(MessageAccessType type) {
        this.type = type;
    }
}
