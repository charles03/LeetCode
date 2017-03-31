package com.leetcode.dynamicprogram;

import java.util.Arrays;

/**
 * Created by charles on 2/26/17.
 * Given an unsorted array of integers, find the length of longest increasing subsequence.

 For example,
 Given [10, 9, 2, 5, 3, 7, 101, 18],
 The longest increasing subsequence is [2, 3, 7, 101], therefore the length is 4. Note that there may be more than one LIS combination, it is only necessary for you to return the length.

 Your algorithm should run in O(n2) complexity.

 Follow up: Could you improve it to O(n log n) time complexity?
 */
public class LongestIncreasingSubsequence_300 {
    /**
     * DP
     * State : dp[i] is longest increasing subsequence upto i index
     * Function: dp[i] = max(dp[j]) + 1, j in (0, i)
     *          LIS len = max(dp[i]) i in (0, n)
     */
    public int lengthOfLIS(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        int[] dp = new int[nums.length];
        dp[0] = 1;
        int res = 1;
        for (int i = 1; i < dp.length; i++) {
            int max = 0;
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    max = Math.max(max, dp[j]);
                }
            }
            dp[i] = max + 1;
            res = Math.max(res, dp[i]);
        }
        return res;
    }

    /**
     * DP with Binary Search
     * we consider only that portion of the dpdp array in which we have made the updations by inserting some elements at their correct positions(which remains always sorted). Thus, only the elements upto the i^{th}i
     ​th
     ​​ index in the dpdp array can determine the position of the current element in it.
     Since, the element enters its correct position(ii) in an ascending order in the dpdp array, the subsequence formed so far in it is surely an increasing subsequence.
     Whenever this position index ii becomes equal to the length of the LIS formed so far(lenlen), it means, we need to update the lenlen as len = len + 1len=len+1.
     */
    public int lengthOfLISII(int[] nums) {
        int[] dp = new int[nums.length];
        int len = 0;
        for (int num : nums) {
            /**
             * Arrays.binarySearch() method returns index of the search key, if it is contained in the array, else it returns (-(insertion point) - 1).
             * The insertion point is the point at which the key would be inserted into the array: the index of the first element greater than the key,
             * or a.length if all elements in the array are less than the specified key.
             */
            int i = Arrays.binarySearch(dp, 0, len, num);
            if (i < 0) {
                i = -(i + 1);
            }
            dp[i] = num;
            if (i == len) {
                len++;
            }
        }
        return len;
    }
}
