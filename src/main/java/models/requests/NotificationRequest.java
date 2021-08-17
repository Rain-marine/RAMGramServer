package models.requests;

import controllers.ClientHandler;
import models.responses.Response;
import org.codehaus.jackson.annotate.JsonTypeName;

@JsonTypeName("notification")
public class NotificationRequest implements Request {

    @Override
    public Response execute(ClientHandler clientHandler) {
        return null;
    }
}
