package models.responses;


import models.trimmed.TrimmedLoggedUser;
import org.codehaus.jackson.annotate.JsonTypeName;

@JsonTypeName("LoggedUser")
public class LoggedUserResponse implements Response{

    private TrimmedLoggedUser trimmedLoggedUser;

    public LoggedUserResponse(TrimmedLoggedUser trimmedLoggedUser) {
        this.trimmedLoggedUser = trimmedLoggedUser;
    }

    public LoggedUserResponse() {
    }

    @Override
    public void unleash() {

    }

    public TrimmedLoggedUser getTrimmedLoggedUser() {
        return trimmedLoggedUser;
    }

    public void setTrimmedLoggedUser(TrimmedLoggedUser trimmedLoggedUser) {
        this.trimmedLoggedUser = trimmedLoggedUser;
    }
}
