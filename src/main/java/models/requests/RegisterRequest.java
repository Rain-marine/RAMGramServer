package models.requests;

import controllers.ClientHandler;
import controllers.Controllers;
import models.responses.BooleanResponse;
import models.responses.Response;
import org.codehaus.jackson.annotate.JsonTypeName;

@JsonTypeName("register")
public class RegisterRequest implements Request, Controllers {

    public enum TYPE { USERNAME , EMAIL , NUMBER , INSERT}
    private String infoCheck;
    private TYPE type;
    private String[] finalInfo;

    public RegisterRequest(String infoCheck, TYPE type, String[] finalInfo) {
        this.infoCheck = infoCheck;
        this.type = type;
        this.finalInfo = finalInfo;
    }

    @Override
    public Response execute(ClientHandler clientHandler) {
        switch (type){
            case USERNAME -> {return new BooleanResponse(REGISTER_MANAGER.isUsernameAvailable(infoCheck));}
            case NUMBER -> {return new BooleanResponse(REGISTER_MANAGER.isPhoneNumberAvailable(infoCheck));}
            case EMAIL -> {return new BooleanResponse(REGISTER_MANAGER.isEmailAvailable(infoCheck));}
            case INSERT -> REGISTER_MANAGER.makeNewUser(finalInfo);
        }
        return new BooleanResponse(true);
    }

    public RegisterRequest() {
    }

    public String getInfoCheck() {
        return infoCheck;
    }

    public void setInfoCheck(String infoCheck) {
        this.infoCheck = infoCheck;
    }

    public TYPE getType() {
        return type;
    }

    public void setType(TYPE type) {
        this.type = type;
    }

    public String[] getFinalInfo() {
        return finalInfo;
    }

    public void setFinalInfo(String[] finalInfo) {
        this.finalInfo = finalInfo;
    }
}
