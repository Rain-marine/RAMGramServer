package models.requests;

import controllers.ClientHandler;
import controllers.Controllers;
import models.responses.BooleanResponse;
import models.responses.ConnectionErrorResponse;
import models.responses.Response;
import models.responses.TweetResponse;
import models.trimmed.TrimmedTweet;
import org.codehaus.jackson.annotate.JsonTypeName;

import javax.persistence.PersistenceException;

@JsonTypeName("tweet")
public class TweetRequest implements Request, Controllers {

    private String token;
    private long userId;
    private long tweetId;

    public TweetRequest() {
    }

    public TweetRequest(String token, long userId, long tweetId) {
        this.token = token;
        this.userId = userId;
        this.tweetId = tweetId;
    }

    @Override
    public Response execute(ClientHandler clientHandler) {
        try{
        if(clientHandler.getToken().equals(token)){
            return new TweetResponse(new TrimmedTweet(tweetId));
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
}
