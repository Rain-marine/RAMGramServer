package models.requests;

import controllers.ClientHandler;
import models.responses.BooleanResponse;
import models.responses.ChatInfoResponse;
import models.responses.ConnectionErrorResponse;
import models.responses.Response;
import org.codehaus.jackson.annotate.JsonTypeName;
import org.hibernate.HibernateException;

import javax.persistence.PersistenceException;

@JsonTypeName("chatInfo")
public class ChatInfoRequest implements Request {

    private long chatId;
    private String token;
    private long userId;

    public ChatInfoRequest(long chatId, String token, long userId) {
        this.chatId = chatId;
        this.token = token;
        this.userId = userId;
    }

    public ChatInfoRequest() {
    }

    @Override
    public Response execute(ClientHandler clientHandler) {
        try{
        if(clientHandler.getToken().equals(token)){
            return new ChatInfoResponse(chatId , userId);
        }
        else
            return new BooleanResponse(false);
        }catch (PersistenceException dateBaseConnectionError){
            return new ConnectionErrorResponse(dateBaseConnectionError.getMessage());
        }
    }

    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
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
}
