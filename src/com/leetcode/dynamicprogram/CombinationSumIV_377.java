package com.leetcode.dynamicprogram;

import java.util.Arrays;

/**
 * Created by charles on 2/1/17.
 * Given an integer array with all positive numbers and no duplicates, find the number of possible combinations that add up to a positive integer target.
 * nums = [1, 2, 3]
 target = 4

 The possible combination ways are:
 (1, 1, 1, 1)
 (1, 1, 2)
 (1, 2, 1)
 (1, 3)
 (2, 1, 1)
 (2, 2)
 (3, 1)

 Note that different sequences are counted as different combinations.
 Therefore the output is 7.

 Follow UP
 What if negative numbers are allowed in the given array?
 How does it change the problem?
 What limitation we need to add to the question to allow negative numbers?
 */
public class CombinationSumIV_377 {
    /**
     * target is the sum of numbers in the array. Imagine we only need one more number to reach target,
     * this number can be any one in the array, right? So the # of combinations of target, comb[target] = sum(comb[target - nums[i]]), where 0 <= i < nums.length, and target >= nums[i]
     * In the example given, we can actually find the # of combinations of 4 with the # of combinations of 3(4 - 1), 2(4- 2) and 1(4 - 3). As a result, comb[4] = comb[4-1] + comb[4-2] + comb[4-3] = comb[3] + comb[2] + comb[1].

     Then think about the base case. Since if the target is 0, there is only one way to get zero, which is using 0, we can set comb[0] = 1.
     */
    // recursion
    public int combinationSum4(int[] nums, int target) {
        if (target == 0) {
            return 1;
        }
        int res = 0;
        for (int i = 0; i < nums.length; i++) {
            if (target >= nums[i]) {
                // go for recursion
                res += combinationSum4(nums, target - nums[i]);
            }
        }
        return res;
    }

    // top-down DP
    /**
     * use dp to store intermediate results, to avoid repeating calculation
     * fill array with -1 to indicate result hasn't been calculated yet.
     * 0 is not a good choice because it means there is no combination sum for target
     */
    public int combinationSum4DP(int[] nums, int target) {
        int[] dp = new int[target + 1];
        Arrays.fill(dp, -1);
        dp[0] = 1;
        return helper(nums, target, dp);
    }
    private int helper(int[] nums, int target, int[] dp) {
        if (dp[target] != -1) {
            return dp[target]; // already been calculated
        }
        int res = 0;
        for (int i = 0; i < nums.length; i++) {
            if (target >= nums[i]) {
                res += helper(nums, target - nums[i], dp);
            }
        }
        dp[target] = res;
        return res;
    }

    /**
     * Bottom-up DP solution
     * State : dp[i] is for total numbers of combination solution for current target value i
     * Function : if (i - nums[j]) >= 0 {dp[i] += dp[i - nums[j]]}
     * init : dp[0] = 1;
     * answer : dp[target]
     */
    public int combinationSum4Bottomup(int[] nums, int target) {
        int[] comb = new int[target + 1];
        comb[0] = 1;

        for (int i = 1; i < comb.length; i++) {
            for (int j = 0; j < nums.length; j++) {
                if (i - nums[j] >= 0) {
                    comb[i] += comb[i - nums[j]];
                }
            }
        }
        return comb[target];
    }
}
