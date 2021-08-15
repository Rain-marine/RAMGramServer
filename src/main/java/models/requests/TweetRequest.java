package models.requests;

import controllers.ClientHandler;
import controllers.Controllers;
import models.responses.BooleanResponse;
import models.responses.LoggedUserResponse;
import models.responses.Response;
import models.responses.TweetResponse;
import models.trimmed.TrimmedLoggedUser;
import models.trimmed.TrimmedTweet;
import org.codehaus.jackson.annotate.JsonTypeName;
import repository.Repository;

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
        if(clientHandler.getToken().equals(token)){
            return new TweetResponse(new TrimmedTweet(tweetId));
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

    public long getTweetId() {
        return tweetId;
    }

    public void setTweetId(long tweetId) {
        this.tweetId = tweetId;
    }
}
