package models.requests;


import controllers.ClientHandler;
import models.responses.Response;
import org.codehaus.jackson.annotate.JsonSubTypes;
import org.codehaus.jackson.annotate.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "model")
@JsonSubTypes({
        @JsonSubTypes.Type(value = LoginRequest.class, name = "login"),
})

public interface Request {

    Response execute(ClientHandler clientHandler);

}
