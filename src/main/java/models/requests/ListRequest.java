package models.requests;

import controllers.ClientHandler;
import controllers.Controllers;
import models.responses.BooleanResponse;
import models.responses.ListResponse;
import models.responses.Response;
import models.responses.TweetResponse;
import models.trimmed.TrimmedTweet;
import org.codehaus.jackson.annotate.JsonTypeName;

@JsonTypeName("list")
public class ListRequest implements Request, Controllers {

    public enum TYPE {TIMELINE, EXPLORER , COMMENT ,CHAT ,MESSAGE , NOTIFICATION ,FACTION}

    private String token;
    private long userId;
    private TYPE type;
    private long superId;

    public ListRequest(String token, long userId, TYPE type, long superId) {
        this.token = token;
        this.userId = userId;
        this.type = type;
        this.superId = superId;
    }

    public ListRequest() {
    }

    @Override
    public Response execute(ClientHandler clientHandler) {
        if(clientHandler.getToken().equals(token)){
            //todo
            switch (type){
                case TIMELINE -> {
                    return new ListResponse(TWEET_CONTROLLER.getFollowingTweets(userId));
                }
                case EXPLORER -> {
                   return new ListResponse(TWEET_CONTROLLER.getTopTweets(userId));
                }
                default -> {
                    return new BooleanResponse(false);
                }
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

    public TYPE getType() {
        return type;
    }

    public void setType(TYPE type) {
        this.type = type;
    }

    public long getSuperId() {
        return superId;
    }

    public void setSuperId(long superId) {
        this.superId = superId;
    }
}
