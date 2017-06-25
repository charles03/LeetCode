package com.advanced.followup;

import java.util.Arrays;

/**
 * Created by charles on 9/15/16.
 *
 * Given an array of integers,
 * find how many pairs in the array such that their sum is bigger than a specific target number.
 * Please return the number of pairs.
 */

/*
* Given numbers = [2, 7, 11, 15], target = 24. Return 1. (11 + 15 is the only pair)
*
* Do it in O(1) space O(nlg(n)) time
* */
public class TwoSumII {
    public int twoSum2(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        Arrays.sort(nums);
        // use two pointers
        int start = 0;
        int end = nums.length - 1;
        int count = 0;
        while (start < end) {
            if (nums[start] + nums[end] > target) {
                count += end - start;
                end--;
            } else {
                start++;
            }
        }
        return count;
    }
}
