package models.responses;

import models.trimmed.TrimmedLoggedUser;
import models.trimmed.TrimmedUser;
import org.codehaus.jackson.annotate.JsonTypeName;

@JsonTypeName("login")
public class LoginResponse implements Response{
    private boolean isLoginValid;
    private String error;
    private TrimmedLoggedUser trimmedLoggedUser;
    private String token;

    public LoginResponse() {
    }

    public LoginResponse(boolean isLoginValid, String error, TrimmedLoggedUser trimmedLoggedUser, String token) {
        this.isLoginValid = isLoginValid;
        this.error = error;
        this.trimmedLoggedUser = trimmedLoggedUser;
        this.token = token;
    }

    @Override
    public void unleash() {
    }

    public boolean isLoginValid() {
        return isLoginValid;
    }

    public void setLoginValid(boolean loginValid) {
        isLoginValid = loginValid;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public TrimmedLoggedUser getTrimmedLoggedUser() {
        return trimmedLoggedUser;
    }

    public void setTrimmedLoggedUser(TrimmedLoggedUser trimmedLoggedUser) {
        this.trimmedLoggedUser = trimmedLoggedUser;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
