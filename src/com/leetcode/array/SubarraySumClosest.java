package com.leetcode.array;

import java.util.Arrays;

/**
 * Created by charles on 6/24/17.
 * Given an integer array, fina a subarray with sum closest to zero.
 * Return indexes of first number and last number
 *
 * Example
 * Given [-3,1,1,-3,5], return any of {[0,2],[1,3],[1,1],[2,2],[0,4]}
 *
 * Thought:
 * having Pair auxiliary class to save detail prefix sum of raw array under what index,
 *
 * Because the relation:
 * first k number sum is sum[0, k-1] till k-1 index
 * thus first 0 sum is prefix[0,-1],
 * in order to conveniently handling, move prefix sum one index right.
 */
public class SubarraySumClosest {
    public int[] subarraySumclosest(int[] nums) {
        int[] res = new int[2];
        if (nums == null || nums.length == 0) {
            return res;
        }
        int len = nums.length;
        if (len == 1) {
            res[0] = res[1] = 0;
            return res;
        }
        Pair[] sums = new Pair[len + 1]; // allocate one more space for Pair(0,0)
        int prefixSum = 0;
        sums[0] = new Pair(0,0); // which is 0 numbers of elem -> prefix sum until index 0
        for (int i = 1; i <= len; i++) {
            prefixSum += nums[i-1];
            sums[i] = new Pair(prefixSum, i);
        }
        Arrays.sort(sums, (a,b) -> a.sum - b.sum);// from small to large
        /** to find min diff among each prefix sum */
        int diff = Integer.MAX_VALUE;
        for (int i = 1; i <= len; i++) {
            if (diff > sums[i].sum - sums[i-1].sum) {
                diff = sums[i].sum - sums[i-1].sum;
                res[0] = Math.min(sums[i-1].index - 1, sums[i].index - 1);
                res[1] = Math.max(sums[i-1].index - 1, sums[i].index - 1);
            }
        }
        return res;
    }
    private class Pair {
        int sum;
        int index;
        public Pair(int sum, int index) {
            this.sum = sum;
            this.index = index;
        }
    }
}
