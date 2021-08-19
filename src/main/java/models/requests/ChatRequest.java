package models.requests;

import controllers.ClientHandler;
import models.responses.*;
import models.trimmed.TrimmedChat;
import models.trimmed.TrimmedMessage;
import models.types.ChatType;
import org.codehaus.jackson.annotate.JsonTypeName;
import org.hibernate.HibernateException;

import javax.persistence.PersistenceException;

@JsonTypeName("chat")
public class ChatRequest implements Request {

    private String token;
    private long userId;
    private long chatId;
    private ChatType mode;

    public ChatRequest(String token, long userId, long chatId) {
        this.token = token;
        this.userId = userId;
        this.chatId = chatId;
    }

    public ChatRequest() {
    }

    @Override
    public Response execute(ClientHandler clientHandler) {
        try {
            if (clientHandler.getToken().equals(token)) {
                if (mode == ChatType.NORMAL)
                    return new ChatResponse(new TrimmedChat(chatId, userId));
                else
                    return new ChatResponse(new TrimmedChat(chatId, userId , ChatType.VIEW));
            } else
                return new BooleanResponse(false);
        } catch (PersistenceException dateBaseConnectionError) {
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

    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    public ChatType getMode() {
        return mode;
    }

    public void setMode(ChatType mode) {
        this.mode = mode;
    }
}
