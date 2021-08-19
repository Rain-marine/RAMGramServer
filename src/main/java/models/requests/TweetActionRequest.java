package models.requests;

import controllers.ClientHandler;
import controllers.Controllers;
import models.responses.BooleanResponse;
import models.responses.ConnectionErrorResponse;
import models.responses.Response;
import models.types.TweetActionType;
import org.codehaus.jackson.annotate.JsonTypeName;

import javax.persistence.PersistenceException;

@JsonTypeName("tweetAction")
public class TweetActionRequest implements Request, Controllers {


    private String token;
    private long userId;
    private long tweetId;
    private TweetActionType action;

    public TweetActionRequest(String token, long userId, long tweetId, TweetActionType action) {
        this.token = token;
        this.userId = userId;
        this.tweetId = tweetId;
        this.action = action;
    }

    public TweetActionRequest() {
    }

    @Override
    public Response execute(ClientHandler clientHandler) {
        try{
        if(clientHandler.getToken().equals(token)){
            switch (action){
                case LIKE -> TWEET_CONTROLLER.like(tweetId , userId);
                case SAVE -> TWEET_CONTROLLER.saveTweet(tweetId , userId);
                case REPORT -> {
                    return new BooleanResponse(TWEET_CONTROLLER.reportSpam(tweetId, userId));
                }
                case RETWEET -> TWEET_CONTROLLER.retweet(tweetId, userId);
            }
        return new BooleanResponse(true);
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

    public long getTweetId() {
        return tweetId;
    }

    public void setTweetId(long tweetId) {
        this.tweetId = tweetId;
    }

    public TweetActionType getAction() {
        return action;
    }

    public void setAction(TweetActionType action) {
        this.action = action;
    }
}
