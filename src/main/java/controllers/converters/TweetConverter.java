package controllers.converters;

import models.Tweet;
import models.User;
import models.trimmed.TrimmedTweet;
import repository.Repository;

import java.util.ArrayList;

public class TweetConverter implements Converter<TrimmedTweet> , Repository {

    private long tweetId;

    public TweetConverter(long tweetID) {
        this.tweetId = tweetID;
    }


    @Override
    public TrimmedTweet convert() {
        Tweet mainTweet = TWEET_REPOSITORY.getById(tweetId);
        ArrayList<String> likedUsers = new ArrayList<>();
        for (User user : mainTweet.getUsersWhoLiked()) {
            likedUsers.add(user.getUsername());
        }
        return new TrimmedTweet(mainTweet.getText() , mainTweet.getUser().getUsername() , likedUsers );
    }
}
