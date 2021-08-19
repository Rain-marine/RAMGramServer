package models.requests;

import controllers.ClientHandler;
import controllers.Controllers;
import models.responses.BooleanResponse;
import models.responses.ConnectionErrorResponse;
import models.responses.Response;
import org.codehaus.jackson.annotate.JsonTypeName;
import org.hibernate.HibernateException;

import javax.persistence.PersistenceException;

@JsonTypeName("visibility")
public class ChangeAccountVisibilityRequest implements Request, Controllers {

    private String token;
    private long userId;
    private boolean isPublic;

    public ChangeAccountVisibilityRequest(String token, long userId, boolean isPublic) {
        this.token = token;
        this.userId = userId;
        this.isPublic = isPublic;
    }

    public ChangeAccountVisibilityRequest() {
    }

    @Override
    public Response execute(ClientHandler clientHandler) {
        try{
        if(clientHandler.getToken().equals(token)){
            SETTING_CONTROLLER.changeAccountVisibility(isPublic , userId);
            return new BooleanResponse(true);
        }
        else
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

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }
}
