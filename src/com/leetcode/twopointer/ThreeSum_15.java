package com.leetcode.twopointer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by charles on 4/13/17.
 * Given an array S of n integers, are there elements a, b, c in S such that a + b + c = 0? Find all unique triplets in the array which gives the sum of zero.

 Note: The solution set must not contain duplicate triplets.

 For example, given array S = [-1, 0, 1, 2, -1, -4],

 A solution set is:
 [
 [-1, 0, 1],
 [-1, -1, 2]
 ]
 */
public class ThreeSum_15 {

    /**
     * Thought:
     * for sorted array,
     * pick arr[i] as base, then look for sum in range(i+1, len)
     * we also need to skp equivalent elements to avoid duplicates in final result when without using set
     */
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        if (nums == null || nums.length < 3) {
            return res;
        }
        Arrays.sort(nums);
        int left = 0, right = 0;
        int len = nums.length;
        int sum = 0;
        for (int i = 0; i < len - 2; i++) {
            if (i > 0 && nums[i] == nums[i-1]) {
                // i > 0 is to avoid runtime exception
                continue;
            }
            left = i+1;
            right = len - 1;
            sum = 0 - nums[i];
            twoSum(res, nums, left, right, sum);
        }
        return res;
    }

    private void twoSum(List<List<Integer>> res, int[] nums, int start, int end, int target) {
        while (start < end) {
            if (nums[start] + nums[end] == target) {
                res.add(Arrays.asList(-target, nums[start], nums[end]));
                start++;
                end--;
                // skip duplicate pairs with same left
                while (start < end && nums[start] == nums[start - 1]) {
                    start++;
                }
                // skip with same right
                while (start < end && nums[end] == nums[end+1]) {
                    end--;
                }
            } else if (nums[start] + nums[end] < target) {
                start++;
            } else {
                end--;
            }
        }
    }
}
