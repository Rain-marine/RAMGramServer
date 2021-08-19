package models.requests;

import controllers.ClientHandler;
import models.responses.*;
import models.trimmed.TrimmedMessage;
import models.trimmed.TrimmedTweet;
import org.codehaus.jackson.annotate.JsonTypeName;

import javax.persistence.PersistenceException;

@JsonTypeName("message")
public class MessageRequest implements Request{

    private String token;
    private long userId;
    private long messageId;

    public MessageRequest(String token, long userId, long messageId) {
        this.token = token;
        this.userId = userId;
        this.messageId = messageId;
    }

    public MessageRequest() {
    }

    @Override
    public Response execute(ClientHandler clientHandler) {
        try{
        if(clientHandler.getToken().equals(token)){
            return new MessageResponse(new TrimmedMessage(messageId , userId));
        }
        else
            return new BooleanResponse(false);
        }catch (PersistenceException dateBaseConnectionError){
            return new ConnectionErrorResponse(dateBaseConnectionError.getMessage());
        }
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
}
