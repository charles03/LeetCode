package com.leetcode.array;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by charles on 5/16/17.
 * Given an array of integers and an integer k, you need to find the total number of continuous subarrays whose sum equals to k.

 Example 1:
 Input:nums = [1,1,1], k = 2
 Output: 2
 */
public class SubarraySumEqualsK_560 {
    /**
     * accumulative sum
     * to cal the sum of elems in subarray num[i:j], use sum[j+1] - sum[i]
     */
    public int subarraySum(int[] nums, int k) {
        int len = nums.length;
        int[] prefixSum = new int[len + 1];
        prefixSum[0] = 0;

        for (int i = 1; i <= len; i++) {
            prefixSum[i] = prefixSum[i-1] + nums[i-1];
        }
        int count = 0;
        for (int start = 0; start < len; start++) {
            for (int end = start + 1; end <= len; end++) {
                if (prefixSum[end] - prefixSum[start] == k) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * use Hashmap to store cumulative sum upto all indices possible along with number of times
     * key(sum[j] - k), value: count of occurrence of sum[i]
     */
    public int subarraySumII(int[] nums, int k) {
        int len = nums.length;
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0,1);
        int sum = 0;
        int count = 0;
        for (int i = 0; i < len; i++) {
            sum += nums[i];
            if (map.containsKey(sum - k)) {
                count += map.get(sum - k);
            }
            map.put(sum, map.getOrDefault(sum, 0) + 1);
        }
        return count;
    }
}
