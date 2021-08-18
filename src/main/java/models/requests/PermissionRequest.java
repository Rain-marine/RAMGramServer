package models.requests;

import controllers.ClientHandler;
import controllers.Controllers;
import controllers.ProfileAccessController;
import models.responses.BooleanResponse;
import models.responses.PermissionResponse;
import models.responses.Response;
import models.responses.UserResponse;
import models.trimmed.TrimmedUser;
import org.codehaus.jackson.annotate.JsonTypeName;

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
        if (clientHandler.getToken().equals(token)) {
            return new PermissionResponse(new ProfileAccessController(otherUserId , userId).checkAccessibility());
        } else
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

    public long getOtherUserId() {
        return otherUserId;
    }

    public void setOtherUserId(long otherUserId) {
        this.otherUserId = otherUserId;
    }

}
