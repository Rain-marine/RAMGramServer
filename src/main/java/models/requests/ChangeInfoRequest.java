package models.requests;

import controllers.ClientHandler;
import controllers.Controllers;
import models.responses.BooleanResponse;
import models.responses.LoggedUserResponse;
import models.responses.Response;
import models.trimmed.TrimmedLoggedUser;
import org.codehaus.jackson.annotate.JsonTypeName;

@JsonTypeName("changeInfo")
public class ChangeInfoRequest implements Request, Controllers {

    public enum TYPE { FULL_NAME ,USERNAME , EMAIL , NUMBER , BIO , LAST_SEEN , PROFILE}
    private String token;
    private long userId;
    private String newInfo;
    private byte[] newPhoto;
    private TYPE type;

    public ChangeInfoRequest(String token, long userId,TYPE type, String newInfo) {
        this.token = token;
        this.userId = userId;
        this.newInfo = newInfo;
        this.type = type;
    }

    public ChangeInfoRequest() {
    }

    @Override
    public Response execute(ClientHandler clientHandler) {
        if(clientHandler.getToken().equals(token)){
            switch (type){
                case LAST_SEEN -> {
                    SETTING_CONTROLLER.changeLastSeenStatus(newInfo, userId);
                    return new LoggedUserResponse(new TrimmedLoggedUser(userId));
                }
                case BIO -> {
                    return new BooleanResponse(USER_CONTROLLER.changeBio(newInfo , userId));
                }
                case EMAIL -> {
                    return new BooleanResponse(USER_CONTROLLER.changeEmail(newInfo , userId));
                }
                case NUMBER -> {
                    return new BooleanResponse(USER_CONTROLLER.changeNumber(newInfo , userId));
                }
                case USERNAME -> {
                    return new BooleanResponse(USER_CONTROLLER.changeUsername(newInfo ,userId));
                }
                case FULL_NAME -> {
                    return new BooleanResponse(USER_CONTROLLER.changeName(newInfo , userId));
                }
                case PROFILE -> {
                    return new BooleanResponse(USER_CONTROLLER.changeProfilePhoto(newPhoto , userId));
                }
                default -> {return new BooleanResponse(false);}
            }

        }
        else
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

    public String getNewInfo() {
        return newInfo;
    }

    public void setNewInfo(String newInfo) {
        this.newInfo = newInfo;
    }

    public TYPE getType() {
        return type;
    }

    public void setType(TYPE type) {
        this.type = type;
    }

    public byte[] getNewPhoto() {
        return newPhoto;
    }

    public void setNewPhoto(byte[] newPhoto) {
        this.newPhoto = newPhoto;
    }
}
