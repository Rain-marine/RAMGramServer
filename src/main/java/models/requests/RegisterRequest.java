package models.requests;

import controllers.ClientHandler;
import controllers.Controllers;
import models.responses.BooleanResponse;
import models.responses.ConnectionErrorResponse;
import models.responses.Response;
import models.types.RegisterType;
import org.codehaus.jackson.annotate.JsonTypeName;

import javax.persistence.PersistenceException;

@JsonTypeName("register")
public class RegisterRequest implements Request, Controllers {

    private String infoCheck;
    private RegisterType type;
    private String[] finalInfo;

    public RegisterRequest(String infoCheck, RegisterType type, String[] finalInfo) {
        this.infoCheck = infoCheck;
        this.type = type;
        this.finalInfo = finalInfo;
    }

    @Override
    public Response execute(ClientHandler clientHandler) {
        try{
        switch (type){
            case USERNAME -> {return new BooleanResponse(REGISTER_MANAGER.isUsernameAvailable(infoCheck));}
            case NUMBER -> {return new BooleanResponse(REGISTER_MANAGER.isPhoneNumberAvailable(infoCheck));}
            case EMAIL -> {return new BooleanResponse(REGISTER_MANAGER.isEmailAvailable(infoCheck));}
            case INSERT -> REGISTER_MANAGER.makeNewUser(finalInfo);
        }
        return new BooleanResponse(true);
        }catch (PersistenceException dateBaseConnectionError){
            return new ConnectionErrorResponse(dateBaseConnectionError.getMessage());
        }
    }

    public RegisterRequest() {
    }

    public String getInfoCheck() {
        return infoCheck;
    }

    public void setInfoCheck(String infoCheck) {
        this.infoCheck = infoCheck;
    }

    public RegisterType getType() {
        return type;
    }

    public void setType(RegisterType type) {
        this.type = type;
    }

    public String[] getFinalInfo() {
        return finalInfo;
    }

    public void setFinalInfo(String[] finalInfo) {
        this.finalInfo = finalInfo;
    }
}
