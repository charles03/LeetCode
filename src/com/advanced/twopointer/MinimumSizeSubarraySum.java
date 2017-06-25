package com.advanced.twopointer;

import java.util.Arrays;

/**
 * Created by charles on 11/11/16.
 *
 * Given an array of n positive integers and a positive integer s,
 * find the minimal length of a subarray of which the sum ≥ s. If there isn't one, return -1 instead.
 * Example
 Given the array [2,3,1,2,4,3] and s = 7, the subarray [4,3] has the minimal length under the problem constraint.
 If you have figured out the O(n) solution, try coding another solution of which the time complexity is O(n log n).
 */
public class MinimumSizeSubarraySum {
    /**
     * @param nums: an array of integers
     * @param s: an integer
     * @return: an integer representing the minimum size of subarray
     */
    public int minimumSize(int[] nums, int s) {
        // write your code here
        int size = Integer.MAX_VALUE;
        if (nums == null || nums.length == 0) {
            return size;
        }

        int j = 0;
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            while (j < nums.length && sum < s) {
                sum += nums[j];
                j++;
            }
            if (sum >= s) { // the case is sum larger or equal than target
                // update min len
                size = Math.min(size, j - i);
            }
            // remove from left
            sum -= nums[i];
        }
        if (size == Integer.MAX_VALUE) {
            size = -1;
        }
        return size;
    }
}
