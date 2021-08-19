package models.requests;

import controllers.ClientHandler;
import controllers.Controllers;
import models.responses.BooleanResponse;
import models.responses.ConnectionErrorResponse;
import models.responses.Response;
import models.types.FactionActionType;
import org.codehaus.jackson.annotate.JsonTypeName;

import javax.persistence.PersistenceException;
import java.util.List;

@JsonTypeName("factionAction")
public class FactionActionRequest implements Request, Controllers {

    private String token;
    private long userId;
    private FactionActionType type;
    private long otherUserId;
    private int factionId;
    private List<String> users;
    private String name;


    public FactionActionRequest(String token, long userId, FactionActionType type, int factionId, long otherUserId, List<String> users , String name) {
        this.token = token;
        this.userId = userId;
        this.type = type;
        this.otherUserId = otherUserId;
        this.factionId = factionId;
        this.users = users;
        this.name = name;
    }

    @Override
    public Response execute(ClientHandler clientHandler) {
        try {
        if (clientHandler.getToken().equals(token)) {
            switch (type) {
                case ADD_MEMBER -> FACTIONS_CONTROLLER.addUserToFaction(factionId, name);
                case NEW_FACTION -> FACTIONS_CONTROLLER.insertNewFaction(name , users , userId);
                case DELETE_MEMBER -> FACTIONS_CONTROLLER.deleteUserFromFaction(factionId , otherUserId);
                case DELETE_FACTION -> FACTIONS_CONTROLLER.deleteFaction(factionId , userId);
            }
            return new BooleanResponse(false);
        } else
            return new BooleanResponse(false);
        }catch (PersistenceException dateBaseConnectionError){
            return new ConnectionErrorResponse(dateBaseConnectionError.getMessage());
        }
    }

    public FactionActionRequest() {
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

    public FactionActionType getType() {
        return type;
    }

    public void setType(FactionActionType type) {
        this.type = type;
    }

    public long getOtherUserId() {
        return otherUserId;
    }

    public void setOtherUserId(long otherUserId) {
        this.otherUserId = otherUserId;
    }

    public int getFactionId() {
        return factionId;
    }

    public void setFactionId(int factionId) {
        this.factionId = factionId;
    }

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
