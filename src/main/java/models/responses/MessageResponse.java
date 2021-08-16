package models.responses;

import models.trimmed.TrimmedMessage;
import org.codehaus.jackson.annotate.JsonTypeName;

@JsonTypeName("message")
public class MessageResponse implements Response{

    private TrimmedMessage trimmedMessage;

    public MessageResponse(TrimmedMessage trimmedMessage) {
        this.trimmedMessage = trimmedMessage;
    }

    public MessageResponse() {
    }

    @Override
    public void unleash() {

    }

    public TrimmedMessage getTrimmedMessage() {
        return trimmedMessage;
    }

    public void setTrimmedMessage(TrimmedMessage trimmedMessage) {
        this.trimmedMessage = trimmedMessage;
    }
}
