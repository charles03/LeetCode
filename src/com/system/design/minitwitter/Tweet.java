package com.system.design.minitwitter;

/**
 * Created by charles on 8/2/16.
 */
public class Tweet {
    private int id;
    private int userId;
    private String text;

    private Tweet() {};

    public static Tweet createTweet(int userId, String text) {
        Tweet tweet = new Tweet();
        tweet.userId = userId;
        tweet.text = text;
        tweet.id = text.hashCode();
        return tweet;
    }
    public String getTweetText() {
        return text;
    }

    public int getUserId() {
        return userId;
    }

    public int getId() {
        return id;
    }
}
