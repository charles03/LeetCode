package com.leetcode.array;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by charles on 6/10/17.
 * Given an array of integers and an integer k, you need to find the total number of continuous subarrays whose sum equals to k.

 Example 1:
 Input:nums = [1,1,1], k = 2
 Output: 2
 Note:
 The length of the array is in range [1, 20,000].
 The range of numbers in the array is [-1000, 1000] and the range of the integer k is [-1e7, 1e7].
 */
public class SubarraySumEqualK_560 {
    public int subarraySum(int[] nums, int k) {
        int len = nums.length;
        if (len == 0) {
            return 0;
        }
        Map<Integer, Integer> map = new HashMap<>(); // key is target sum, value is count of subarray which sum equal to target
        map.put(0, 1);
        int prefixSum = 0;
        int count = 0;
        for (int i = 0; i < len; i++) {
            prefixSum += nums[i];
            if (map.containsKey(prefixSum - k)) {
                // find subarray sum equal to k,
                // prefix[j] - prefix[i] = k,
                // numbers of ways to get prefix[i] is count[i];
                // result is accumulate
                count += map.get(prefixSum - k);
            }
            map.put(prefixSum, map.getOrDefault(prefixSum, 0) + 1);
        }
        return count;
    }

    /**
     * use O[n^2] time complexity and O[n] space
     */
    public int subarraySumII(int[] nums, int k) {
        int count = 0;
        int len = nums.length;
        int[] prefix = new int[len + 1];
        for (int i = 1; i <= len; i++) {
            prefix[i] = prefix[i - 1] + nums[i - 1];
        }
        // in order to calculate sum of elements lying between two indices
        // use prefix[j+1] - prefix[i] => get range sum [i,j]
        for (int start = 0; start < len; start++) {
            for (int end = start + 1; end <= len; end++) {
                if (prefix[end] - prefix[start] == k) {
                    count++;
                }
            }
        }
        return count;
    }
}
