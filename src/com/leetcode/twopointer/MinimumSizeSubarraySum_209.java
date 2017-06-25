package com.leetcode.twopointer;

import java.util.Arrays;

/**
 * Created by charles on 4/16/17.
 * Given an array of n positive integers and a positive integer s, find the minimal length of a contiguous subarray of which the sum ≥ s. If there isn't one, return 0 instead.

 For example, given the array [2,3,1,2,4,3] and s = 7,
 the subarray [4,3] has the minimal length under the problem constraint.

 More practice:
 If you have figured out the O(n) solution, try coding another solution of which the time complexity is O(n log n).
 */
public class MinimumSizeSubarraySum_209 {
    /**
     * thought:
     * two pointers, left = 0, right = 0
     * move right first until sum(left, right) >= target
     * then move left until sum(left, right) < target
     * use global min len record current min len
     */
    public int minSubArrayLen(int s, int[] nums) {
        int left = 0, right = 0;
        int len = nums.length;
        int sum = 0;
        int res = Integer.MAX_VALUE;
        for (int i = 0; i <= len; i++) { // be careful, should include i = len condition
            while (right < len && sum < s) {
                sum += nums[right];
                right++;
            }
            while (left < right && sum >= s) {
                res = Math.min(res, right - left);
                sum -= nums[left];
                left++;
            }
            i = right;
        }
        return res == Integer.MAX_VALUE ? 0 : res;
    }

    public int minSubArrayLenII(int s, int[] a) {
        if (a == null || a.length == 0) {
            return 0;
        }
        int i = 0, j = 0, sum = 0, len = a.length;
        int min = Integer.MAX_VALUE;

        while (j < len) {
            sum += a[j++];

            while (sum >= s) {
                min = Math.min(min, j - i);
                sum -= a[i++];
            }
        }
        return min == Integer.MAX_VALUE ? 0 : min;
    }

    public static void main(String[] args) {
        MinimumSizeSubarraySum_209 m = new MinimumSizeSubarraySum_209();
        int[] a2 = {1,4,4};
        System.out.println(m.minSubArrayLenIII(4, a2));

        int[] a1 = {2,3,1,2,4,3};
        m.minSubArrayLen(7, a1);
        System.out.println(m.minSubArrayLenIII(7, a1));
    }

    /**
     * Nlg(N) solution,
     * use prefix sum and binary search
     */
    public int minSubArrayLenIII(int s, int[] nums) {
        int len = nums.length;
        int[] sums = new int[len + 1];
        for (int i = 1; i <= len; i++) {
            sums[i] = sums[i-1] + nums[i - 1];
        }
        int min = Integer.MAX_VALUE;
        int end = 0;
        for (int i = 0; i <= len; i++) {
            if (i <= len) {
                end = Arrays.binarySearch(sums, i, len+1, sums[i] + s);
            }
            end = end < 0 ? -end - 1 : end;
            if (end == len + 1) {
                break;
            }
            min = Math.min(min, end - i);
        }
        return min == Integer.MAX_VALUE ? 0 : min;
    }
}
