package models.trimmed;

import java.util.ArrayList;

public class TrimmedTweet {

    private String tweetText;
    private String writerUsername;
    private ArrayList<String> likedUsernames;


    public TrimmedTweet(String tweetText, String writerUsername, ArrayList<String> likedUsernames) {
        this.tweetText = tweetText;
        this.writerUsername = writerUsername;
        this.likedUsernames = likedUsernames;
    }
}
