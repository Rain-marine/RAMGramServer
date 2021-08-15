package models.requests;

import controllers.ClientHandler;
import controllers.Controllers;
import models.responses.BooleanResponse;
import models.responses.Response;
import org.codehaus.jackson.annotate.JsonTypeName;

@JsonTypeName("changePass")
public class ChangePasswordRequest implements Request, Controllers {

    private String token;
    private long userId;
    private String newPass;


    public ChangePasswordRequest() {
    }

    @Override
    public Response execute(ClientHandler clientHandler) {
        if (clientHandler.getToken().equals(token)){
            SETTING_CONTROLLER.changePassword(newPass , userId);
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

    public String getNewPass() {
        return newPass;
    }

    public void setNewPass(String newPass) {
        this.newPass = newPass;
    }
}
