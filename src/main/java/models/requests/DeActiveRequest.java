package models.requests;

import controllers.ClientHandler;
import controllers.Controllers;
import models.responses.BooleanResponse;
import models.responses.ConnectionErrorResponse;
import models.responses.Response;
import org.codehaus.jackson.annotate.JsonTypeName;
import org.hibernate.HibernateException;

import javax.persistence.PersistenceException;

@JsonTypeName("deActive")
public class DeActiveRequest implements Request, Controllers {

    private String token;
    private long userId;
    private boolean isActive;

    public DeActiveRequest(String token, long userId, boolean isActive) {
        this.token = token;
        this.userId = userId;
        this.isActive = isActive;
    }

    public DeActiveRequest() {
    }

    @Override
    public Response execute(ClientHandler clientHandler) {
        try{
        if (clientHandler.getToken().equals(token)) {
            if (isActive)
                SETTING_CONTROLLER.activateAccount(userId);
            else
                SETTING_CONTROLLER.deActiveAccount(userId);
            return new BooleanResponse(true);
        } else
            return new BooleanResponse(false);
        }catch (PersistenceException dateBaseConnectionError){
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

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
