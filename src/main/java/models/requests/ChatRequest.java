package models.requests;

import controllers.ClientHandler;
import models.responses.BooleanResponse;
import models.responses.ChatResponse;
import models.responses.MessageResponse;
import models.responses.Response;
import models.trimmed.TrimmedChat;
import models.trimmed.TrimmedMessage;
import org.codehaus.jackson.annotate.JsonTypeName;

@JsonTypeName("chat")
public class ChatRequest implements Request{

    private String token;
    private long userId;
    private long chatId;

    public ChatRequest(String token, long userId, long chatId) {
        this.token = token;
        this.userId = userId;
        this.chatId = chatId;
    }

    public ChatRequest() {
    }

    @Override
    public Response execute(ClientHandler clientHandler) {
        if(clientHandler.getToken().equals(token)){
            return new ChatResponse(new TrimmedChat(chatId , userId));
        }
        else
            return new BooleanResponse(false);
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

    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }
}
