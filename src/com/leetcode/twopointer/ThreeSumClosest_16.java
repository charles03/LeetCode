package com.leetcode.twopointer;

import java.util.Arrays;

/**
 * Created by charles on 4/13/17.
 * Given an array S of n integers, find three integers in S such that the sum is closest to a given number, target. Return the sum of the three integers. You may assume that each input would have exactly one solution.

 For example, given array S = {-1 2 1 -4}, and target = 1.

 The sum that is closest to the target is 2. (-1 + 2 + 1 = 2).
 */
public class ThreeSumClosest_16 {
    /**
     * sorted array, sum (arr[i], arr[i+1], arr[len-1])
     * compare with global closest sum to target
     * if (sum < target) {
     *     increment from i+1
     * } else {
     *     decrement from len-1
     * }
     */
    public int threeSumClosest(int[] nums, int target) {
        int sum = 0;
        if (nums == null || nums.length < 3) {
            for (int i : nums) {
                sum += i;
            }
            return sum;
        }
        Arrays.sort(nums);
        int len = nums.length;
        int leftSum = 0, rightSum = 0;
        for (int i = 0; i < 3; i++) {
            leftSum += nums[i];
        }
        for (int i = len - 3; i < len; i++) {
            rightSum += nums[i];
        }
        if (target <= leftSum) {
            return leftSum;
        } else if (target >= rightSum) {
            return rightSum;
        }
        int closeSum = leftSum;
        int left = 0, right = 0;
        for (int i = 0; i < len - 2; i++) {
            left = i+1;
            right = len - 1;
            while (left < right) {
                sum = nums[i] + nums[left] + nums[right];
                if (Math.abs(target - sum) < Math.abs(target - closeSum)) {
                    closeSum = sum;
                }
                if (sum < target) {
                    left++;
                } else {
                    right--;
                }
            }
        }
        return closeSum;
    }
}
