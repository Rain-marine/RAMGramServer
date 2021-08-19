package models.requests;

import controllers.ClientHandler;
import controllers.Controllers;
import controllers.ProfileAccessController;
import models.responses.*;
import models.trimmed.TrimmedUser;
import org.codehaus.jackson.annotate.JsonTypeName;

import javax.persistence.PersistenceException;
import java.util.ArrayList;


@JsonTypeName("permission")
public class PermissionRequest implements Request , Controllers {

    private String token;
    private long userId;
    private long otherUserId;



    public PermissionRequest() {
    }

    @Override
    public Response execute(ClientHandler clientHandler) {
        try{
        if (clientHandler.getToken().equals(token)) {
            return new PermissionResponse(new ProfileAccessController(otherUserId , userId).checkAccessibility());
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

    public long getOtherUserId() {
        return otherUserId;
    }

    public void setOtherUserId(long otherUserId) {
        this.otherUserId = otherUserId;
    }

}
