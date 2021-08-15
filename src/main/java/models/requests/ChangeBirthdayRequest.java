package models.requests;

import controllers.ClientHandler;
import controllers.Controllers;
import models.responses.BooleanResponse;
import models.responses.LoggedUserResponse;
import models.responses.Response;
import models.trimmed.TrimmedLoggedUser;
import org.codehaus.jackson.annotate.JsonTypeName;

import java.util.Date;

@JsonTypeName("changeBirthday")
public class ChangeBirthdayRequest implements Request, Controllers {

    private String token;
    private long userId;
    private Date newBirthday;

    public ChangeBirthdayRequest(String token, long userId, Date newBirthday) {
        this.token = token;
        this.userId = userId;
        this.newBirthday = newBirthday;
    }

    public ChangeBirthdayRequest() {
    }

    @Override
    public Response execute(ClientHandler clientHandler) {
        if(clientHandler.getToken().equals(token)){
            USER_CONTROLLER.changeBirthday(newBirthday , userId);
            return new LoggedUserResponse(new TrimmedLoggedUser(userId));
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

    public Date getNewBirthday() {
        return newBirthday;
    }

    public void setNewBirthday(Date newBirthday) {
        this.newBirthday = newBirthday;
    }
}
