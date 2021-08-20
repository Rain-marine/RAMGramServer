package controllers;

import models.Tweet;
import models.User;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import repository.Repositories;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TweetController implements Repositories {
    private final static Logger log = LogManager.getLogger(TweetController.class);


    private final FactionsController factionsController;

    public TweetController() {
        factionsController = new FactionsController();
    }


    public void addTweet(String text, byte[] image , long loggedUserId){
        Tweet tweet = new Tweet(USER_REPOSITORY.getById(loggedUserId),text, image);
        TWEET_REPOSITORY.insert(tweet);
        log.info("new tweet");
    }

    public ArrayList<Long> getAllTweets(long userId) {
        User user = USER_REPOSITORY.getById(userId);
        List<Tweet> userAllTweets = TWEET_REPOSITORY.getAllTweets(user.getId());
        userAllTweets.addAll(user.getRetweetTweets());
        List<Tweet> finalTweets =  userAllTweets.stream().sorted(Comparator.comparing(Tweet::getTweetDateTime)).
                collect(Collectors.toList());
        ArrayList<Long> finalTweetsIDs = new ArrayList<>();
        for (Tweet finalTweet : finalTweets) {
            finalTweetsIDs.add(finalTweet.getId());
        }
        return finalTweetsIDs;
    }

    public ArrayList<Long> getTopTweets(long loggedUserId) {
        User loggedUser = USER_REPOSITORY.getById(loggedUserId);
        List<Tweet> topTweets = TWEET_REPOSITORY.getTopTweets(loggedUserId);
        ArrayList<Long> topTweetsIDs = new ArrayList<>();
        for (Tweet topTweet : topTweets) {
            User topTweetUser = USER_REPOSITORY.getById(topTweet.getUser().getId());
            if (topTweetUser.getBlackList().stream().noneMatch(it -> it.getId() == loggedUserId)) {
                if(loggedUser.getMutedUsers().stream().noneMatch(it -> it.getId() == topTweetUser.getId()))
                    topTweetsIDs.add(topTweet.getId());
            }
        }
        return topTweetsIDs;



    }

    public ArrayList<Long> getFollowingTweets(long userId) {
        List<Tweet> followingTweets = new ArrayList<>();
        User currentUser = USER_REPOSITORY.getById(userId);
        List<User> following = factionsController.getActiveFollowings(userId);
        List<User> muted = currentUser.getMutedUsers();

        for (User user : following) {
            if (muted.stream().noneMatch(it -> it.getId() == user.getId())) {
                followingTweets.addAll(getAllTweetsModel(user.getId()));
            }
        }
        List<Tweet> finalTweetList = followingTweets.stream().sorted(Comparator.comparing(Tweet::getTweetDateTime).reversed()).
                 collect(Collectors.toList());
        ArrayList<Long> finalTweetsIDs = new ArrayList<>();
        for (Tweet tweet : finalTweetList) {
            finalTweetsIDs.add(tweet.getId());
        }
        return finalTweetsIDs;
    }

    private List<Tweet> getAllTweetsModel(long userId) {
        User user = USER_REPOSITORY.getById(userId);
        List<Tweet> userAllTweets = TWEET_REPOSITORY.getAllTweets(user.getId());
        userAllTweets.addAll(user.getRetweetTweets());
        return userAllTweets.stream().sorted(Comparator.comparing(Tweet::getTweetDateTime)).
                collect(Collectors.toList());
    }

    public void saveTweet(long tweetId ,long loggedUserId) {
        USER_REPOSITORY.addFavoriteTweet(loggedUserId, tweetId);

    }

    public void retweet(long currentTweetId , long loggedUserId) {
        USER_REPOSITORY.addRetweet(currentTweetId, loggedUserId);
    }

    public boolean reportSpam(long currentTweetId , long loggedUserId) {
        Tweet reportedTweet = TWEET_REPOSITORY.getById(currentTweetId);
        if (reportedTweet.getReportCounter() >= 2 ){
            TWEET_REPOSITORY.delete(reportedTweet.getId());
            return true;
        }
        else {
            TWEET_REPOSITORY.increaseReportCount(currentTweetId);
            USER_REPOSITORY.addReportedTweet(currentTweetId, loggedUserId);
        }
        return false;
    }

    public void addComment(String comment,byte[] image , long rawParentTweetId , long loggedUserId) {
        Tweet parentTweet = TWEET_REPOSITORY.getById(rawParentTweetId);
        Tweet commentTweet = new Tweet(USER_REPOSITORY.getById(loggedUserId),comment, image);
        TWEET_REPOSITORY.addComment(parentTweet,commentTweet);
    }


    public boolean like(long tweetId , long loggedUserId) {
        Tweet completeTweet = TWEET_REPOSITORY.getById(tweetId);
        for (User user : completeTweet.getUsersWhoLiked()) {
            if (user.getId() == loggedUserId)
                return false;
        }
        TWEET_REPOSITORY.like(loggedUserId, tweetId);
        return true;
    }


    public long getWriterId(long tweetId) {
        return TWEET_REPOSITORY.getById(tweetId).getUser().getId();
    }

    public String getTweetText(long tweetId) {
        return TWEET_REPOSITORY.getById(tweetId).getText();
    }

    public String getWriterUsername(long tweetId) {
        return TWEET_REPOSITORY.getById(tweetId).getUser().getUsername();
    }

    public String getTweetDate(long tweetId) {
        return TWEET_REPOSITORY.getById(tweetId).getTweetDateTime().toString();
    }

    public byte[] getTweetImage(long tweetId) {
        return TWEET_REPOSITORY.getById(tweetId).getImage();
    }

    public ArrayList<Long> getTweetComments(long tweetId) {
        List<Tweet> comments = TWEET_REPOSITORY.getById(tweetId).getComments();
        ArrayList<Long> commentId = new ArrayList<>();
        for (Tweet comment : comments) {
            commentId.add(comment.getId());
        }
        return commentId;
    }

    public ArrayList<String> getLikedList(long tweetId) {
        ArrayList<String> liked = new ArrayList<>();
        for (User user : TWEET_REPOSITORY.getById(tweetId).getUsersWhoLiked()) {
            liked.add(user.getUsername());
        }
        return liked;
    }
}
