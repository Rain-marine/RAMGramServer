package models.requests;

import controllers.ClientHandler;
import controllers.Controllers;
import models.responses.BooleanResponse;
import models.responses.Response;
import org.codehaus.jackson.annotate.JsonTypeName;

@JsonTypeName("isPublic")
public class IsPublicRequest implements Request, Controllers {

    private long userId;
    private String token;

    public IsPublicRequest(long userId, String token) {
        this.userId = userId;
        this.token = token;
    }

    public IsPublicRequest() {
    }

    @Override
    public Response execute(ClientHandler clientHandler) {
        if (clientHandler.getToken().equals(token)){
            boolean result = SETTING_CONTROLLER.isAccountPublic(USER_CONTROLLER.getUsername(userId));
            return new BooleanResponse(result);
        }
        else{
            System.out.println("wrong token from " + userId);
            return new BooleanResponse(false);
        }

    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
