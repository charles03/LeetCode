package com.system.design.minitwitter;
import sun.tools.jstat.Literal;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by charles on 8/2/16.
 */
public class MiniTwitter {
    // Constructor
    private Map<Integer, Set<Integer>> friendMap;
    private Map<Integer, List<Node>> userTweets;
    private int order;
    private static final int SIZE = 10;

    public MiniTwitter() {
        this.friendMap = new HashMap<Integer, Set<Integer>>();
        this.userTweets = new HashMap<Integer, List<Node>>();
        this.order = 0;
    }
    /** to create new tweet feed */
    public Tweet postTweet(int userId, String tweetText) {
        Tweet tweet = Tweet.createTweet(userId, tweetText);
        if (!userTweets.containsKey(userId)) {
            userTweets.put(userId, new ArrayList<Node>());
        }
        order++;
        userTweets.get(userId).add(new Node(tweet, order));
        return tweet;
    }
    /** news should include both current user and user's friends */
    public List<Tweet> getNewsFeed(int userId) {
        List<Node> list = new ArrayList<>();
        // for current userId, add latest SIZE of feed from list into global container
        if (userTweets.containsKey(userId)) {
            list.addAll(getLastTen(userTweets.get(userId)));
        }
        // add each friend's latest SIZE of feed into global container
        if (friendMap.containsKey(userId)) {
            for (Integer friendId : friendMap.get(userId)) {
                if (userTweets.containsKey(friendId)) {
                    list.addAll(getLastTen(userTweets.get(friendId)));
                }
            }
        }
        // sort container, large OrderId is at front of list
        Collections.sort(list, ((o1,o2) -> o2.getOrder() - o1.getOrder()));
        List<Tweet> res = getFirstTen(list).stream().map(Node::getTweet).collect(Collectors.toList());
        return res;
    }
    /** timeline only contain feeds for current user self, not include user's friends*/
    public List<Tweet> getTimeline(int userId) {
        List<Node> list = new ArrayList<>();
        if (userTweets.containsKey(userId)) {
            list.addAll(getLastTen(userTweets.get(userId)));
        }
        Collections.sort(list, ((o1,o2) -> o2.getOrder() - o1.getOrder()));
        List<Tweet> res = getFirstTen(list).stream().map(Node::getTweet).collect(Collectors.toList());
        return res;
    }

    public void follow(int follower, int followee) {
        if (!friendMap.containsKey(follower)) {
            friendMap.put(follower, new HashSet<Integer>());
        }
        friendMap.get(follower).add(followee);
    }

    public void unFollow(int follower, int followee) {
        if (friendMap.containsKey(follower)) {
            friendMap.get(follower).remove(followee);
        }
    }
    /** fetch SIZE of nodes from tail of list */
    private List<Node> getLastTen(List<Node> source) {
        int size = source.size();
        if (size < SIZE) {
            return source;
        }
        return source.subList(size - SIZE, size);
    }
    /** fetch SIZE of nodes from beginning of list */
    private List<Node> getFirstTen(List<Node> source) {
        int size = source.size();
        if (size < SIZE) {
            return source;
        }
        return source.subList(0, SIZE);
    }
}

class Node {
    private Tweet tweet;
    private int order;
    public Node(Tweet tweet, int order) {
        this.tweet = tweet;
        this.order = order;
    }

    public Tweet getTweet() {
        return this.tweet;
    }

    public int getOrder() {
        return this.order;
    }
}

