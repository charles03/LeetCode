package com.leetcode.dynamicprogram;

/**
 * Created by charles on 2/19/17.
 * Given an integer array nums, find the sum of the elements between indices i and j (i ≤ j), inclusive.

 Example:
 Given nums = [-2, 0, 3, -5, 2, -1]

 sumRange(0, 2) -> 1
 sumRange(2, 5) -> -1
 sumRange(0, 5) -> -3
 Note:
 You may assume that the array does not change.
 There are many calls to sumRange function.
 */
public class RangeSumQueryImmutable_303 {
    /**
     * DP solution, use prefix sum
     * build prefix sum array in constructor while init
     */
    private int[] prefixSum;

    public RangeSumQueryImmutable_303(int[] nums) {
        prefixSum = new int[nums.length + 1];
        prefixSum[0] = 0;
        for (int i = 1; i <= nums.length; i++) {
            prefixSum[i] = prefixSum[i-1] + nums[i-1];
        }
    }
    public int sumRange(int i, int j) {
        /** be careful index start at j + 1 instead of j */
        return prefixSum[j+1] - prefixSum[i];
    }

}
