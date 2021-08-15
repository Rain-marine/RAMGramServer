package models.requests;

import controllers.ClientHandler;
import models.responses.LoggedUserResponse;
import models.responses.Response;
import models.trimmed.TrimmedLoggedUser;
import org.codehaus.jackson.annotate.JsonTypeName;

@JsonTypeName("updateLoggedUser")
public class UpdateLoggedUserRequest implements Request {

    private long userId;


    public UpdateLoggedUserRequest() {
    }

    public UpdateLoggedUserRequest(long userId) {
        this.userId = userId;
    }

    @Override
    public Response execute(ClientHandler clientHandler) {
        return new LoggedUserResponse(new TrimmedLoggedUser(userId));
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
