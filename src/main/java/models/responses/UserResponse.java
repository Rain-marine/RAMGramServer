package models.responses;

import models.trimmed.TrimmedUser;
import org.codehaus.jackson.annotate.JsonTypeName;

@JsonTypeName("user")
public class UserResponse implements Response {

    private TrimmedUser trimmedUser;

    public UserResponse(TrimmedUser trimmedUser) {
        this.trimmedUser = trimmedUser;
    }

    public UserResponse() {
    }

    @Override
    public void unleash() {

    }

    public TrimmedUser getTrimmedUser() {
        return trimmedUser;
    }

    public void setTrimmedUser(TrimmedUser trimmedUser) {
        this.trimmedUser = trimmedUser;
    }
}
