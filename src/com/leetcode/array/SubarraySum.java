package com.leetcode.array;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by charles on 6/24/17.
 * Given an Integer array, find a subarray where the sum of numbers is zero.
 * your code should return the index of the first number and the index of last number
 *
 * Given [-3,1,2,-3,4] return [0,2] or [1,3]
 */
public class SubarraySum {
    /**
     * Thought use hashmap to record key : prefix sum, value is index
     * if prefix sum already in map, then find subarray sum is 0 between current index and index in map
     */
    public List<Integer> subarraySum(int[] nums) {
        int len = nums.length;
        List<Integer> res = new ArrayList<>();
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0,-1);
        int prefixSum = 0;
        for (int i = 0; i < len; i++) {
            prefixSum += nums[i];
            if (map.containsKey(prefixSum)) {
                /** be careful index + 1 for right edge */
                res.add(map.get(prefixSum) + 1);
                res.add(i);
                return res;
            }
            map.put(prefixSum, i);
        }
        return res;
    }
}
