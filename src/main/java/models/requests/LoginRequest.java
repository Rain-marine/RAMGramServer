package models.requests;

import controllers.AuthController;
import controllers.ClientHandler;
import controllers.Controllers;
import controllers.UserController;
import exceptions.InvalidInputException;
import models.responses.ConnectionErrorResponse;
import models.responses.LoginResponse;
import models.responses.Response;
import models.trimmed.TrimmedLoggedUser;
import models.trimmed.TrimmedUser;
import org.codehaus.jackson.annotate.JsonTypeName;
import repository.UserRepository;

import javax.persistence.PersistenceException;
import java.util.ArrayList;

@JsonTypeName("login")
public class LoginRequest implements Request {
    private String username;
    private String password;

    public LoginRequest() {
    }

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public Response execute(ClientHandler clientHandler) {
        try {
            ArrayList<String> tokenId = new AuthController().login(username, password , clientHandler);
            clientHandler.setToken(tokenId.get(0));
            clientHandler.setLoggedUserId(new UserRepository().getByUsername(username).getId());
            return new LoginResponse(true , "login successful", new TrimmedLoggedUser(Long.parseLong(tokenId.get(1))), tokenId.get(0));
        } catch (InvalidInputException | PersistenceException e) {
            return new LoginResponse(false , e.getMessage(), null, null);
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
