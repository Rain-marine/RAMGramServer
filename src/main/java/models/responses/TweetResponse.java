package models.responses;


import models.trimmed.TrimmedTweet;
import org.codehaus.jackson.annotate.JsonTypeName;

@JsonTypeName("tweet")
public class TweetResponse implements Response{

    private TrimmedTweet trimmedTweet;

    public TweetResponse(TrimmedTweet trimmedTweet) {
        this.trimmedTweet = trimmedTweet;
    }

    public TweetResponse() {

    }

    @Override
    public void unleash() {

    }

    public TrimmedTweet getTrimmedTweet() {
        return trimmedTweet;
    }

    public void setTrimmedTweet(TrimmedTweet trimmedTweet) {
        this.trimmedTweet = trimmedTweet;
    }
}
