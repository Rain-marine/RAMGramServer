package models.responses;

import org.codehaus.jackson.annotate.JsonTypeName;

@JsonTypeName("error")
public class ConnectionErrorResponse implements Response{

    private String message;

    public ConnectionErrorResponse(String message) {
        this.message = message;
    }

    public ConnectionErrorResponse() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public void unleash() {

    }
}
