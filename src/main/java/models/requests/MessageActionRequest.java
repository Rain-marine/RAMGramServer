package models.requests;

import controllers.ClientHandler;
import controllers.Controllers;
import models.responses.BooleanResponse;
import models.responses.ConnectionErrorResponse;
import models.responses.Response;
import models.types.MessageAccessType;
import models.types.MessageActionType;
import org.codehaus.jackson.annotate.JsonTypeName;

import javax.persistence.PersistenceException;

@JsonTypeName("messageAction")
public class MessageActionRequest implements Request , Controllers {

    private String token;
    private long userId;
    private long messageId;
    private String newText;
    private MessageActionType type;


    public MessageActionRequest(String token, long userId, MessageActionType type , long messageId ) {
        this.token = token;
        this.userId = userId;
        this.messageId = messageId;
        this.type = type;
    }

    public MessageActionRequest(String token, long userId, MessageActionType type, long messageId, String newText) {
        this.token = token;
        this.userId = userId;
        this.messageId = messageId;
        this.newText = newText;
        this.type = type;
    }

    @Override
    public Response execute(ClientHandler clientHandler) {
        try{
        if(clientHandler.getToken().equals(token)){
            switch (type){
                case DELETE -> MESSAGE_CONTROLLER.deleteMessage(messageId);
                case EDIT -> MESSAGE_CONTROLLER.editMessage(messageId ,newText);
            }
            return new BooleanResponse(true);
        }
        else
            return new BooleanResponse(false);
        }catch (PersistenceException dateBaseConnectionError){
            return new ConnectionErrorResponse(dateBaseConnectionError.getMessage());
        }
    }

    public MessageActionRequest() {
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

    public long getMessageId() {
        return messageId;
    }

    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }

    public String getNewText() {
        return newText;
    }

    public void setNewText(String newText) {
        this.newText = newText;
    }

    public MessageActionType getType() {
        return type;
    }

    public void setType(MessageActionType type) {
        this.type = type;
    }
}
