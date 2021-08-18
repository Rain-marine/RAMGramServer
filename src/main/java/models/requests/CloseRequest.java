package models.requests;

import controllers.ClientHandler;
import models.responses.BooleanResponse;
import models.responses.Response;
import org.codehaus.jackson.annotate.JsonTypeName;

@JsonTypeName("close")
public class CloseRequest implements Request{

    @Override
    public Response execute(ClientHandler clientHandler) {
        clientHandler.setKilled(true);
        return new BooleanResponse(true);
    }

    public CloseRequest() {
    }
}
