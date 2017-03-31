package com.leetcode.array;

/**
 * Created by charles on 12/18/16.
 *  Given an array of n positive integers and a positive integer s,
 *  find the minimal length of a subarray of which the sum ≥ s. If there isn't one, return 0 instead.

 For example, given the array [2,3,1,2,4,3] and s = 7,
 the subarray [4,3] has the minimal length under the problem constraint.
 */
public class MinimumSizeSubarraySum_209 {

    public int minSubArrayLen(int s, int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        return solveByBinarySearch(s, nums);
//        return solveByTwoPointer(s, nums);
//        return solveBySimplifiedTwoPointer(s, nums);
    }

    public int solveByTwoPointer(int target, int[] nums) {
        int start = 0, end = 0, sum = 0;
        int minLen = Integer.MAX_VALUE; // if look for min, initial with MAX
        int len = nums.length;

        while (end < len) {
            while (end < len && sum < target) {
                sum += nums[end];
                end++;
            }
            if (sum < target) {
                // should not return 0 directly
                // only to break out from while loop
                // return final result after compare minLen with MAX_VALUE
                break;
            }
            while (start < end && sum >= target) {
                sum -= nums[start];
                start++;
            }
            minLen = Math.min(end - start + 1, minLen);
        }
        if (minLen == Integer.MAX_VALUE) {
            return 0; // no valid answer
        }
        return minLen;
    }

    public int solveBySimplifiedTwoPointer(int target, int[] nums) {
        int start = 0, end = 0, sum = 0;
        int len = nums.length;
        int minLen = Integer.MAX_VALUE;

        while (end < len) {
            sum += nums[end++]; // forward one number
            // attempt to remove from start
            while (sum >= target) {
                // update minLen when meet sum >= target
                System.out.println(String.format("end : %s, start : %s", end, start));
                minLen = Math.min(minLen, end - start);
                sum -= nums[start++];
            }
        }

        return minLen == Integer.MAX_VALUE ? 0 : minLen;
    }

    /**
     * Thought:
     * Initial Window size as whole length of array
     * then reduce window size,
     * to search if a window of size k exist answer that satisfy the condition
     */
    public int solveByBinarySearch(int target, int[] nums) {
        int minWindow = 1, maxWindow = nums.length; // initial with window size from 1 to nums.length
        int minLen = 0;
        int midWindowSize = 0; // binary search to reduce window size

        while (minWindow <= maxWindow) {
            midWindowSize = (minWindow + maxWindow) / 2;
            if (isExist(nums, midWindowSize, target)) {
                // update minLen and search small window size
                maxWindow = midWindowSize - 1;
                minLen = midWindowSize;
            } else { // search large window size
                minWindow = midWindowSize + 1;
            }
        }
        return minLen;
    }

    /** given window size, loop whole array to see if there is sum >= target */
    private boolean isExist(int[] nums, int windowSize, int target) {
        int sum = 0;
        int len = nums.length;
        for (int i = 0; i < len; i++) {
            if (i >= windowSize) { // remove front number if over size
                sum -= nums[i - windowSize];
            }
            sum += nums[i];
            if (sum >= target) { // window is existing
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        MinimumSizeSubarraySum_209 m = new MinimumSizeSubarraySum_209();
        int[] nums = {2,3,1,2,4,3};
        int[] num1 = {10,5,13,4,8,4,5,11,14,9,16,10,20,8};
        System.out.println(m.minSubArrayLen(7, nums));
        System.out.println(m.minSubArrayLen(80, num1));
    }
}
