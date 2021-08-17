package models.requests;

import controllers.ClientHandler;
import controllers.Controllers;
import models.responses.BooleanResponse;
import models.responses.Response;
import org.codehaus.jackson.annotate.JsonTypeName;

@JsonTypeName("deleteMessage")
public class DeleteMessageRequest implements Request , Controllers {

    private String token;
    private long userId;
    private long messageId;


    public DeleteMessageRequest(String token, long userId, long messageId) {
        this.token = token;
        this.userId = userId;
        this.messageId = messageId;
    }

    @Override
    public Response execute(ClientHandler clientHandler) {
        if(clientHandler.getToken().equals(token)){
            MESSAGE_CONTROLLER.deleteMessage(messageId);
            return new BooleanResponse(true);
        }
        else
            return new BooleanResponse(false);
    }

    public DeleteMessageRequest() {
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
