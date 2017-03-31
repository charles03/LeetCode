package com.leetcode.dynamicprogram;

import java.util.AbstractCollection;

/**
 * Created by charles on 2/1/17.
 * After robbing those houses on that street, the thief has found himself a new place for his thievery so that he will not get too much attention. This time, all houses at this place are arranged in a circle. That means the first house is the neighbor of the last one. Meanwhile, the security system for these houses remain the same as for those in the previous street.
 Given a list of non-negative integers representing the amount of money of each house, determine the maximum amount of money you can rob tonight without alerting the police.
 */
public class HouseRobberII_213 {
    /**
     * Split this circle as two ranges
     * and then use house robber 1 to solve single array question
     */
    public int robHouse2(int[] nums) {
        if (nums.length == 1) {
            return nums[0];
        }
        int len = nums.length;
        return Math.max(robbing(nums, 0, len - 2), robbing(nums, 1, len - 1));
    }

    /**
     * dp[i] = Math.max(dp[i-2] + a[i], dp[i - 1])
     * for case 7,2,3,9,5
     * (index of array, max value)
     * (0, 7), (1, 7), (2, 10), (3, 16)
     */
    private int robbing(int[] arr, int l, int r) {
        int[] dp = new int[2];
        if (l == r) {
            return arr[l];
        }
        if (l + 1 == r) { // adjacent
            return Math.max(arr[l], arr[r]);
        }
        dp[l % 2] = arr[l];
        dp[(l + 1) % 2] = Math.max(arr[l], arr[l + 1]);
        for (int i = l + 2; i <= r; i++) {
            dp[i % 2] = Math.max(dp[(i - 2)% 2] + arr[i], dp[(i-1) % 2]);
        }
        return dp[r % 2];
    }

    private int robbing2(int[] arr, int l, int r) {
        int adjacentMax = 0; // neighbor max
        int addedMax = 0; // add interval and current value
        int intervalMax = 0; // gap distane is 2
        int currentMax = 0; // compare addedMax and adjacentMax

        for (int i = l; i <= r; i++) {
            addedMax = intervalMax + arr[i];
            currentMax = Math.max(addedMax, adjacentMax);
            // move to next;
            intervalMax = adjacentMax;
            adjacentMax = currentMax;
        }
        return Math.max(currentMax, adjacentMax);
    }
}
