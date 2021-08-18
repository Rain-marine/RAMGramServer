package models.requests;

import controllers.ClientHandler;
import controllers.ProfileAccessController;
import models.responses.BooleanResponse;
import models.responses.PermissionResponse;
import models.responses.Response;
import models.responses.UserResponse;
import models.trimmed.TrimmedUser;
import org.codehaus.jackson.annotate.JsonTypeName;

import java.util.ArrayList;


@JsonTypeName("permission")
public class PermissionRequest implements Request{

    public enum TYPE {REGISTER , PROFILE}
    private String token;
    private long userId;
    private long otherUserId;
    private TYPE type;
    private ArrayList<String> info; //username , email , number


    public PermissionRequest() {
    }

    @Override
    public Response execute(ClientHandler clientHandler) {
        if (clientHandler.getToken().equals(token)) {
            switch (type){
                case PROFILE -> {
                    return new PermissionResponse(new ProfileAccessController(otherUserId , userId).checkAccessibility());
                }
                default -> {return  new BooleanResponse(true);}
            }

        } else
            return new BooleanResponse(false);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getOtherUserId() {
        return otherUserId;
    }

    public void setOtherUserId(long otherUserId) {
        this.otherUserId = otherUserId;
    }

    public TYPE getType() {
        return type;
    }

    public void setType(TYPE type) {
        this.type = type;
    }

    public ArrayList<String> getInfo() {
        return info;
    }

    public void setInfo(ArrayList<String> info) {
        this.info = info;
    }
}
