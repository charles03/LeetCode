package com.leetcode.array;

import com.princeton.stdlib.In;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by charles on 4/9/17.
 * Given an array of integers, return indices of the two numbers such that they add up to a specific target.

 You may assume that each input would have exactly one solution, and you may not use the same element twice.

 Example:
 Given nums = [2, 7, 11, 15], target = 9,

 Because nums[0] + nums[1] = 2 + 7 = 9,
 return [0, 1].
 */
public class TwoSum_1 {
    /**
     * Thought: efficient way to check if complement exists in the array
     * if complement exists, we need to lookup its index.
     * HashTable is the best way to maintain a mapping of each element in array in its index
     *
     * use two pass iterations
     */
    public int[] twoSum(int[] nums, int target) {
        int[] res = new int[0];
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            map.put(nums[i], i);
        }
        int complement = 0;
        for (int i = 0; i < nums.length; i++) {
            complement = target - nums[i];
            if (map.containsKey(complement) && map.get(complement) != i) {
                // be careful should not use num twice like 3 + 3 = 6
                return new int[] {i, map.get(complement)};
            }
        }
        return res;
    }
    /** one pass iteration, can look current element's complement already exist in table */
    public int[] twoSumII(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        int[] res = new int[0];
        int complement = 0;
        for (int i = 0; i < nums.length; i++) {
            complement = target - nums[i];
            if (map.containsKey(complement)) {
                return new int[]{map.get(complement), i};
            }
            map.put(nums[i], i);
        }
        return res;
    }
}
