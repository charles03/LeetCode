package com.advanced.dynamicprogram;

/**
 * Created by charles on 11/12/16.
 * You are a professional robber planning to rob houses along a street.
 * Each house has a certain amount of money stashed,
 * the only constraint stopping you from robbing each of them
 * is that adjacent houses have security system connected and
 * it will automatically contact the police if two adjacent houses were broken into on the same night.

 Given a list of non-negative integers representing the amount of money of each house,
 determine the maximum amount of money you can rob tonight without alerting the police.
 Given [3, 8, 4], return 8.
 Challenge O(n) time and O(1) memory
 */
public class HouseRobber {
    /** DP Thought
     *  state: f[i] stands for Max value of robbery from all indexs before i and inclusive,
     *  Function: f[i] = max(f[i - 1], f[i - 2] + A[i])
     *  Initialization: f[0] = 0; f[1] = A[0];
     *  Answer: f[n]
     */
    public long maxRobber(int[] A) {
        if (A == null || A.length == 0) {return 0;}

        long[] dp = new long[2];
        dp[0] = 0;
        dp[1] = A[0]; // For sequence DP, pre reserve A[0] in dp[1], convenient for coding

        for (int i = 2; i <= A.length; i++) {
            dp[i%2] = Math.max(dp[(i - 1)%2], dp[(i-2)%2] + A[i - 1]);
        }
        return dp[A.length % 2];
    }

    /**
     * minor diff between maxRobber() and houseRobber()
     * initilization and answer (be careful of index)
     * @param A: An array of non-negative integers.
     * return: The maximum amount of money you can rob tonight
     */
    public long houseRobber(int[] A) {
        // use Rolling Array for space optimization
        if (A == null || A.length == 0) {return 0;}
        int len = A.length;
        // only need constant size of array
        long[] dp = new long[2];
        dp[0] = A[0];
        dp[1] = Math.max(A[0], A[1]);

        for (int i = 2; i < len; i++) {
            dp[i % 2] = Math.max(dp[(i - 1) % 2], dp[(i - 2) % 2] + A[i]);
        }
        return dp[(len - 1) % 2];
    }

    public static void main(String[] args) {
        HouseRobber r = new HouseRobber();
        int[] A = {3,8,4,2,4};
        System.out.println(r.maxRobber(A));
        System.out.println(r.houseRobber(A));
    }
}
