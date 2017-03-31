package com.leetcode.dynamicprogram;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by charles on 1/31/17.
 * Given a set of distinct positive integers, find the largest subset such that every pair (Si, Sj) of elements in this subset satisfies: Si % Sj = 0 or Sj % Si = 0.
 If there are multiple solutions, return any subset is fine.
 Example 1:
 nums: [1,2,3]
 Result: [1,2] (of course, [1,3] will also be ok)
 Example 2:
 nums: [1,2,4,8]
 Result: [1,2,4,8]
 */
public class LargestDivisibleSubset_368 {
    /**
     * State : dp[i] is max length of subset at index i
     * function : if (num[i] % num[j] == 0)
     *      dp[i] = max(dp[i], dp[j] + 1);
     * init dp[0] = 1;
     *
     * Thought;
     * 1. sort
     * 2. find length of longest subset
     * 3. record largest element of it
     * 4. loop form largest elem to num[0]
     *      add each elem b belong to longest subset.
     */
    public List<Integer> largestDivisibleSubset(int[] nums) {
        List<Integer> res = new ArrayList<>();
        if (nums == null || nums.length == 0) {
            return res;
        }
        // sort
        Arrays.sort(nums);
        int len = nums.length;
        // init
        int[] dp = new int[len];
        dp[0] = 1;
        // find longest subset
        for (int i = 1; i < len; i++) {
            for (int j = i - 1; j >= 0; j--) {
                if (nums[i] % nums[j] == 0) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
        }
        // pick index of largest elem in dp
        int maxIdx = 0;
        for (int i = 1; i < len; i++) {
            maxIdx = dp[i] > dp[maxIdx] ? i : maxIdx;
        }
        // from nums[maxIdx] to 0; add every elem belongs to largest subset
        int temp = nums[maxIdx];
        int size = dp[maxIdx];
        for (int i = maxIdx; i >= 0; i--) {
            if (temp % nums[i] == 0 && dp[i] == size) {
                res.add(nums[i]);
                temp = nums[i];
                size--;
            }
        }
        return res;
    }
}
