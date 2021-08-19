package models.requests;

import controllers.ClientHandler;
import controllers.Controllers;
import controllers.UserController;
import models.responses.BooleanResponse;
import models.responses.ConnectionErrorResponse;
import models.responses.ExploreResponse;
import models.responses.Response;
import org.codehaus.jackson.annotate.JsonTypeName;

import javax.persistence.PersistenceException;

@JsonTypeName("explore")
public class ExploreRequest implements Request, Controllers {

    private String token;
    private long userId;
    private String usernameToFind;


    @Override
    public Response execute(ClientHandler clientHandler) {
        try{
        if(clientHandler.getToken().equals(token)){
            return new ExploreResponse(USER_CONTROLLER.getUserByUsername(usernameToFind));
        }
        else
            return new BooleanResponse(false);
        }catch (PersistenceException dateBaseConnectionError){
            return new ConnectionErrorResponse(dateBaseConnectionError.getMessage());
        }
    }

    public ExploreRequest() {
    }

    public ExploreRequest(String token, long userId, String usernameToFind) {
        this.token = token;
        this.userId = userId;
        this.usernameToFind = usernameToFind;
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

    public String getUsernameToFind() {
        return usernameToFind;
    }

    public void setUsernameToFind(String usernameToFind) {
        this.usernameToFind = usernameToFind;
    }
}
