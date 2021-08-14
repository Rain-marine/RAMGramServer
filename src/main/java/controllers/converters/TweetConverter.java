package controllers.converters;

import controllers.Controllers;
import models.Tweet;
import models.User;
import models.trimmed.TrimmedTweet;
import repository.Repository;

import java.util.ArrayList;

public class TweetConverter implements Converter<TrimmedTweet>, Repository, Controllers {

    private long tweetId;

    public TweetConverter(long tweetID) {
        this.tweetId = tweetID;
    }


    @Override
    public TrimmedTweet convert() {
        Tweet mainTweet = TWEET_REPOSITORY.getById(tweetId);
        return new TrimmedTweet(tweetId);
    }
}
