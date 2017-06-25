package com.leetcode.os;

/**
 * Created by charles on 6/24/17.
 * another scenario apply round-robin is for load balancing among servers
 * reference : http://blog.sina.com.cn/s/blog_68158ebf0100w3kh.html
 *
 * besides, Weighted-RoundRobin is updating version
 * in real case, weigth-roundrobin is used more frequency
 * involve weight concept while schedule server by considering capability difference of each server
 */
public class RoundRobin_LoadBalance {
    /**
     * Advantage: simplicity, no need to record connection state
     * thus, this is no-state scheduling
     * n is number of servers
     */
    private static final int[] server = {1,2,3,4,5};

    /** regular round_robin ignore connection numbers and response time of each server */
    /** disadvantage : when interval service request change heavily, will cause load unbalance */
    public int round_robin(int n) {
        int i = n-1;
        int j = i;
        do {
            j = (j + 1) % n;
            i = j;
            return server[i];
        } while (j != i);
    }
}
