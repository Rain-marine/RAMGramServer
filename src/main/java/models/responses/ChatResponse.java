package models.responses;

import models.trimmed.TrimmedChat;
import models.trimmed.TrimmedMessage;
import org.codehaus.jackson.annotate.JsonTypeName;

@JsonTypeName("chat")
public class ChatResponse implements Response{

    private TrimmedChat trimmedChat;

    public ChatResponse(TrimmedChat trimmedChat) {
        this.trimmedChat = trimmedChat;
    }

    public ChatResponse() {
    }

    @Override
    public void unleash() {

    }

    public TrimmedChat getTrimmedChat() {
        return trimmedChat;
    }

    public void setTrimmedChat(TrimmedChat trimmedChat) {
        this.trimmedChat = trimmedChat;
    }
}
