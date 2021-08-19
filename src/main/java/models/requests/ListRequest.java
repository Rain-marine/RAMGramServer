package models.requests;

import controllers.ClientHandler;
import controllers.Controllers;
import models.responses.*;
import models.types.ListType;
import org.codehaus.jackson.annotate.JsonTypeName;
import repository.FactionRepository;

import javax.persistence.PersistenceException;

@JsonTypeName("list")
public class ListRequest implements Request, Controllers {

    private String token;
    private long userId;
    private ListType type;
    private long superId;

    public ListRequest(String token, long userId, ListType type, long superId) {
        this.token = token;
        this.userId = userId;
        this.type = type;
        this.superId = superId;
    }

    public ListRequest() {
    }

    @Override
    public Response execute(ClientHandler clientHandler) {
        try{
        if (clientHandler.getToken().equals(token)) {
            switch (type) {
                case TIMELINE -> {
                    return new ListResponse(TWEET_CONTROLLER.getFollowingTweets(userId));
                }
                case EXPLORER -> {
                    return new ListResponse(TWEET_CONTROLLER.getTopTweets(userId));
                }
                case CHAT -> {
                    return new ListResponse(CHAT_CONTROLLER.getChatsIds(userId));
                }
                case SAVED_TWEET -> {
                    return new ListResponse(MESSAGE_CONTROLLER.getSavedTweets(userId));
                }
                case SAVED_MESSAGES -> {
                    return new ListResponse(MESSAGE_CONTROLLER.getSavedMessage(userId));
                }
                case COMMENT -> {
                    return new ListResponse(TWEET_CONTROLLER.getTweetComments(superId));
                }
                case MESSAGE -> {
                    CHAT_CONTROLLER.seeChat(superId, userId);
                    return new ListResponse(CHAT_CONTROLLER.getMessages(superId));
                }
                case TWEETS -> {
                    return new ListResponse(TWEET_CONTROLLER.getAllTweets(superId));
                }
                case FACTION -> {
                    return new FactionResponse(FACTIONS_CONTROLLER.getFactions(userId) , userId);
                }
                case FOLLOWERS -> {
                    return new FactionResponse(-1 , userId);
                }
                case FOLLOWINGS -> {
                    return new FactionResponse(-2 , userId);
                }
                case BLACKLIST -> {
                    return new FactionResponse(-3 , userId);
                }
                default -> {
                    return new BooleanResponse(false);
                }
            }
        } else
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

    public ListType getType() {
        return type;
    }

    public void setType(ListType type) {
        this.type = type;
    }

    public long getSuperId() {
        return superId;
    }

    public void setSuperId(long superId) {
        this.superId = superId;
    }
}
