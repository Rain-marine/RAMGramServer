package models.trimmed;

import controllers.Controllers;
import models.Tweet;
import org.codehaus.jackson.annotate.JsonTypeInfo;
import repository.Repository;

import java.util.ArrayList;


public class TrimmedTweet implements Repository, Controllers {

    private long Id;
    private long writerId;
    private String tweetText;
    private String writerUsername;
    private ArrayList<String> likedUsernames;
    private byte[] tweetImage;
    private ArrayList<Long> commentsIds;
    private byte[] writerProfile;
    private String date;


    public TrimmedTweet(long id) {
        Id = id;
        this.writerId = TWEET_CONTROLLER.getWriterId(id);
        this.tweetText = TWEET_CONTROLLER.getTweetText(id);
        this.writerUsername = TWEET_CONTROLLER.getWriterUsername(id);
        this.likedUsernames = TWEET_CONTROLLER.getLikedList(id);
        this.tweetImage = TWEET_CONTROLLER.getTweetImage(id);
        this.commentsIds = TWEET_CONTROLLER.getTweetComments(id);
        this.writerProfile = USER_CONTROLLER.getProfilePhoto(writerId);
        this.date = TWEET_CONTROLLER.getTweetDate(id);
    }
}
