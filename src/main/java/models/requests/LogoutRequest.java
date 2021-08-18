package models.requests;

import controllers.ClientHandler;
import controllers.Controllers;
import models.responses.BooleanResponse;
import models.responses.Response;
import models.responses.TweetResponse;
import models.trimmed.TrimmedTweet;
import org.codehaus.jackson.annotate.JsonTypeName;

@JsonTypeName("logout")
public class LogoutRequest implements Request, Controllers {

    private String token;
    private long userId;


    @Override
    public Response execute(ClientHandler clientHandler) {
        if(clientHandler.getToken().equals(token)){
            SETTING_CONTROLLER.logout(userId);
            clientHandler.setLoggedUserId(0L);
            return new BooleanResponse(true);
        }
        else
            return new BooleanResponse(false);
    }

    public LogoutRequest() {
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
