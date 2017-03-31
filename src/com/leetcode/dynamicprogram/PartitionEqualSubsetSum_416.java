package com.leetcode.dynamicprogram;

/**
 * Created by charles on 2/2/17.
 * Given a non-empty array containing only positive integers, find if the array can be partitioned into two subsets such that the sum of elements in both subsets is equal.

 Note:
 Each of the array element will not exceed 100.
 The array size will not exceed 200.
 Example 1:

 Input: [1, 5, 11, 5]
 Output: true
 Explanation: The array can be partitioned as [1, 5, 5] and [11].
 Example 2:
 Input: [1, 2, 3, 5]
 Output: false
 Explanation: The array cannot be partitioned into equal sum subsets.
 */
public class PartitionEqualSubsetSum_416 {
    /**
     * 0-1 backpack problem, divided by 2
     * State : dp[i] is for volume i, backpack true or false
     * Function : dp[i] = dp[i] || dp[i - nums[j - 1]]
     * int dp[0] = true;
     * answer dp[volume]
     */
    public boolean canPartition(int[] nums) {
        if (nums == null || nums.length == 0) {
            return true;
        }
        // preprocess
        int volume = 0;
        for (int num : nums) {
            volume += num;
        }
        if (volume % 2 != 0) { // because should be integer
            return false;
        }
        volume /= 2; // get target volume for backpack
        boolean[] dp = new boolean[volume + 1];
        // init
        dp[0] = true;
        // dp transition
        for (int i = 1; i <= nums.length; i++) {
            for (int j = volume; j >= nums[i - 1]; j--) {
                dp[j] = dp[j] || dp[j - nums[i-1]];
            }
        }
        return dp[volume];
    }
}
