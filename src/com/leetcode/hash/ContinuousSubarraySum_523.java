package com.leetcode.hash;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by charles on 5/18/17.
 * Given a list of non-negative numbers and a target integer k, write a function to check if the array has a continuous subarray of size at least 2 that sums up to the multiple of k, that is, sums up to n*k where n is also an integer.

 Example 1:
 Input: [23, 2, 4, 6, 7],  k=6
 Output: True
 Explanation: Because [2, 4] is a continuous subarray of size 2 and sums up to 6.
 Example 2:
 Input: [23, 2, 6, 4, 7],  k=6
 Output: True
 Explanation: Because [23, 2, 6, 4, 7] is an continuous subarray of size 5 and sums up to 42.
 */
public class ContinuousSubarraySum_523 {
    /**
     * Math:
     * (a+b)mod n -> (a mod n + b mod n) mod n
     * use map to keep track of modular prefix sum
     *
     * [a,b,c] mod n
     * a = x1 * n + y1
     * b = x2 * n + y2
     * c = x3 * n + y3
     *
     * a mod n -> y1
     * (y1 + b) mod n -> (y1+y2) mod n
     * (a + b) mod n -> (y1+y2) mod n
     *
     * for map: key is modular prefix sum, value is index
     */
    public boolean checkSubarraySum(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        int modPrefixSum = 0;
        Integer index = null;
        for (int i = 0; i < nums.length; i++) {
            modPrefixSum += nums[i];
            if (k > 0) {
                modPrefixSum %= k;
            }
            index = map.get(modPrefixSum);
            if (index != null && i-index > 1) {
                // at least has continuous two elems
                return true;
            } else {
                map.put(modPrefixSum, i);
            }
        }
        return false;
    }

    /**
     * without map, use set
     */
    public boolean checkSubarraySumII(int[] nums, int k) {
        if (k == 0) {
            return checkAtleastTwoZeros(nums);
        }
        Set<Integer> set = new HashSet<>();
        int modPrefixSum = 0;
        for (int i = 0; i < nums.length; i++) {
            modPrefixSum += nums[i];
            modPrefixSum %= k;
            if (modPrefixSum == 0 && i > 0) {
                return true;
            }
            if (!set.add(modPrefixSum)) {
                return true;
            }
        }
        return false;
    }
    private boolean checkAtleastTwoZeros(int[] nums) {
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == 0 && nums[i-1] == 0) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        ContinuousSubarraySum_523 c = new ContinuousSubarraySum_523();
        int[] a1 = {1,1};
        c.checkSubarraySumII(a1, 2);
        int[] a2 = {0};
        c.checkSubarraySumII(a2, -1);
    }
}
