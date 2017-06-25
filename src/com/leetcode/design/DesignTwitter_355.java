package com.leetcode.design;

import java.util.*;

/**
 * Created by charles on 5/7/17.
 */
public class DesignTwitter_355 {
    private Map<Integer, List<Node>> tweetMap;
    private Map<Integer, Set<Integer>> friendMap;
    private int orderNum;

    public DesignTwitter_355() {
        tweetMap = new HashMap<>();
        friendMap = new HashMap<>();
    }
    /** compose a new tweet */
    public void postTweet(int userId, int tweetId) {
        Tweet tweet = Tweet.createTweet(userId, tweetId);
        if (!tweetMap.containsKey(userId)) {
            tweetMap.put(userId, new ArrayList<>()); // create new entry in map
        }
        orderNum++;
        tweetMap.get(userId).add(new Node(tweet, orderNum));
    }

}
class Node {
    private Tweet tweet;
    private int orderNum;

    public Node(Tweet tweet, int orderNum) {
        this.tweet = tweet;
        this.orderNum = orderNum;
    }
    public Tweet getTweet() {
        return this.tweet;
    }
    public int getOrderNum() {
        return this.orderNum;
    }
}

class Tweet {
    private int id;
    private int userId;
    private String text;
    // singleton
    private Tweet() {};
    public static Tweet createTweet(int userId, int tweetId) {
        Tweet tweet = new Tweet();
        tweet.userId = userId;
        tweet.id = tweetId;
        return tweet;
    }
    // this method should be static
    public static Tweet createTweet(int userId, String text) {
        Tweet tweet = new Tweet();
        tweet.userId = userId;
        tweet.text = text;
        tweet.id = text.hashCode();
        return tweet;
    }
    public int getUserId() {
        return this.userId;
    }
    public String getTweetText() {
        return this.text;
    }
    public int getId() {
        return this.id;
    }
}
