package models.requests;

import controllers.ClientHandler;
import models.responses.BooleanResponse;
import models.responses.ConnectionErrorResponse;
import models.responses.Response;
import models.responses.UserResponse;
import models.trimmed.TrimmedUser;
import org.codehaus.jackson.annotate.JsonTypeName;

import javax.persistence.PersistenceException;

@JsonTypeName("user")
public class UserRequest implements Request{

    private String token;
    private long userId;
    private long otherUserId;

    public UserRequest(String token, long userId, long otherUserId) {
        this.token = token;
        this.userId = userId;
        this.otherUserId = otherUserId;
    }

    @Override
    public Response execute(ClientHandler clientHandler) {
        try{
        if (clientHandler.getToken().equals(token)) {
            return new UserResponse(new TrimmedUser(otherUserId , userId));
        } else
            return new BooleanResponse(false);
        }catch (PersistenceException dateBaseConnectionError){
            return new ConnectionErrorResponse(dateBaseConnectionError.getMessage());
        }
    }

    public UserRequest() {
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

    public long getOtherUserId() {
        return otherUserId;
    }

    public void setOtherUserId(long otherUserId) {
        this.otherUserId = otherUserId;
    }
}
