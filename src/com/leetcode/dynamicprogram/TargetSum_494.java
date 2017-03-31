package com.leetcode.dynamicprogram;

/**
 * Created by charles on 1/23/17.
 * You are given a list of non-negative integers, a1, a2, ..., an, and a target, S. Now you have 2 symbols + and -.
 * For each integer, you should choose one from + and - as its new symbol.

 Find out how many ways to assign symbols to make sum of integers equal to target S.
 Example 1:
 Input: nums is [1, 1, 1, 1, 1], S is 3.
 Output: 5
 Explanation:

 -1+1+1+1+1 = 3
 +1-1+1+1+1 = 3
 +1+1-1+1+1 = 3
 +1+1+1-1+1 = 3
 +1+1+1+1-1 = 3

 There are 5 ways to assign symbols to make the sum of nums be target 3

 Noted:
 The length of the given array is positive and will not exceed 20.
 The sum of elements in the given array will not exceed 1000.
 Your output answer is guaranteed to be fitted in a 32-bit integer.
 */
public class TargetSum_494 {
    /**
     * This problem can be converted to subset sum problem, by adding nums sum to target, then doubling every number of nums

     For example:

     // adding (1+2+3+4+5) to both sides gives subset sum problem
     original:  1 2 3 4 5   target =  3
                +1+2+3+4+5          = 15
     sum:       2 4 6 8 10  target = 18

     // choosing + sign for 3 is like including 6 in the subset
     // choosing - sign for 4 is like excluding 8 in the subset
     original: +1-2+3-4+5   target =  3
                +1+2+3+4+5          = 15
     sum:       2 0 6 0 10  target = 18
     Each solution to the subset sum problem should correspond to one solution to the original problem
     The numbers with + sign are in the subset.
     The numbers with - sign are not in the subset.
     In the above example, the subset [2, 6, 10] should correspond to +1 +3 +5
     */
    /**
     * State : dp[i][j] means numbers of ways to get sum i using subsets of first j numbers
     * function : dp[i][j] = dp[i][j - 1]
     *          if (i - nums[j - 1] >= 0) {
     *              dp[i][j] += dp[i - nums[j - 1]][j - 1] }
     * init : dp[0][0] = 1
     * Answer : dp[s][n]; s is target sum , n is total n numbers
     */
    public int findTargetSumWays(int[] nums, int s) {
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            nums[i] += nums[i];
        }
        return sum < s ? 0 : subsetSum(nums, s + sum);
    }

    private int subsetSum(int[] nums, int t) {
        int n = nums.length;
        int[][] dp = new int[t + 1][n + 1];
        dp[0][0] = 1;

        for (int i = 0; i <= t; i++) {
            for (int j = 1; j <= n; j++) {
                dp[i][j] = dp[i][j - 1];
                if (i - nums[j - 1] >= 0) {
                    dp[i][j] += dp[i-nums[j-1]][j-1];
                }
            }
        }
        return dp[t][n];
    }

    /**
     * Space Optimization
     */
    public int findTargetSumWaysII(int[] nums, int s) {
        int sum = 0;
        for (int n : nums) {
            sum += n;
        }
        if (sum < s || (s + sum) % 2 > 0) {
            return 0;
        }
        return subsetSumII(nums, (s + sum) >>> 1);
    }

    private int subsetSumII(int[] nums, int t) {
        int[] dp = new int[t + 1];
        dp[0] = 1;
        for (int i = 0; i < nums.length; i++) {
            for (int j = t; j >= nums[i]; j--) {
                dp[j] += dp[j - nums[i]];
            }
        }
        return dp[t];
    }
}
