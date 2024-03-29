package models.requests;

import controllers.ClientHandler;
import controllers.Controllers;
import models.responses.BooleanResponse;
import models.responses.ConnectionErrorResponse;
import models.responses.LoggedUserResponse;
import models.responses.Response;
import models.trimmed.TrimmedLoggedUser;
import models.types.ChangeInfoType;
import org.codehaus.jackson.annotate.JsonTypeName;
import org.hibernate.HibernateException;

import javax.persistence.PersistenceException;

@JsonTypeName("changeInfo")
public class ChangeInfoRequest implements Request, Controllers {

    private String token;
    private long userId;
    private String newInfo;
    private byte[] newPhoto;
    private ChangeInfoType type;

    public ChangeInfoRequest(String token, long userId,ChangeInfoType type, String newInfo) {
        this.token = token;
        this.userId = userId;
        this.newInfo = newInfo;
        this.type = type;
    }

    public ChangeInfoRequest() {
    }

    @Override
    public Response execute(ClientHandler clientHandler) {
        try{
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
        }catch (PersistenceException dateBaseConnectionError){
            return new ConnectionErrorResponse(dateBaseConnectionError.getMessage());
        }
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

    public ChangeInfoType getType() {
        return type;
    }

    public void setType(ChangeInfoType type) {
        this.type = type;
    }

    public byte[] getNewPhoto() {
        return newPhoto;
    }

    public void setNewPhoto(byte[] newPhoto) {
        this.newPhoto = newPhoto;
    }
}
