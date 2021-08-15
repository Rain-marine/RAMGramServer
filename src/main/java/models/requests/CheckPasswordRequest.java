package models.requests;

import controllers.ClientHandler;
import controllers.Controllers;
import models.responses.BooleanResponse;
import models.responses.Response;
import org.codehaus.jackson.annotate.JsonTypeName;


@JsonTypeName("checkPass")
public class CheckPasswordRequest implements Request, Controllers {

    private String password;
    private long userId;
    private String token;

    public CheckPasswordRequest(String password, long userId, String token) {
        this.password = password;
        this.userId = userId;
        this.token = token;
    }

    public CheckPasswordRequest() {
    }

    @Override
    public Response execute(ClientHandler clientHandler) {
        if(clientHandler.getToken().equals(token)){
            return new BooleanResponse(SETTING_CONTROLLER.isPasswordCorrect(password , userId));
        }
        else {
            System.out.println("wrong token");
            return new BooleanResponse(false);
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
