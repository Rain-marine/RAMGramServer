package models.requests;

import controllers.ClientHandler;
import controllers.Controllers;
import models.responses.BooleanResponse;
import models.responses.Response;
import org.codehaus.jackson.annotate.JsonTypeName;


@JsonTypeName("deleteAccount")
public class DeleteAccountRequest implements Request, Controllers {

    private String token;
    private long userId;

    public DeleteAccountRequest(String token, long userId) {
        this.token = token;
        this.userId = userId;
    }

    public DeleteAccountRequest() {
    }

    @Override
    public Response execute(ClientHandler clientHandler) {
        if (clientHandler.getToken().equals(token)){
            SETTING_CONTROLLER.deleteAccount(userId);
            return new BooleanResponse(true);
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
}
