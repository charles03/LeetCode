package com.leetcode.dynamicprogram;

/**
 * Created by charles on 3/1/17.
 * You are a professional robber planning to rob houses along a street. Each house has a certain amount of money stashed, the only constraint stopping you from robbing each of them is that adjacent houses have security system connected and it will automatically contact the police if two adjacent houses were broken into on the same night.

 Given a list of non-negative integers representing the amount of money of each house, determine the maximum amount of money you can rob tonight without alerting the police.
 */
public class HouseRobber_198 {
    /**
     * DP solution
     * States : dp[i] is max value of robbery at index i
     * Function : dp[i] = max(dp[i-2] + nums[i], dp[i-1])
     * either take prev value or take the one before prev plus nums[i]
     * Init: dp[0] = 0, dp[1] = nums[0]
     */
    public int rob(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int len = nums.length;
        // only need two variable to maintain status prev and curr
        int[] res = new int[2];
        res[0] = 0;
        res[1] = nums[0];

        for (int i = 2; i <= len; i++) {
            res[i % 2] = Math.max(res[(i-1) % 2], res[(i-2) % 2] + nums[i-1]);
        }
        return res[len % 2];
    }

    public int robII(int[] nums) {
        int robPrev = 0;
        int notRobPrev = 0;
        int robCurr = 0;
        int notRobCurr = 0;
        for (int i = 0; i < nums.length; i++) {
            robCurr = notRobPrev + nums[i];
            notRobCurr = Math.max(robPrev, notRobPrev);
            robPrev = robCurr;
            notRobPrev = notRobCurr;
        }
        return Math.max(robPrev, notRobPrev);
    }
}
