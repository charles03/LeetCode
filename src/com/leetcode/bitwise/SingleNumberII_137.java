package com.leetcode.bitwise;

/**
 * Created by charles on 2/10/17.
 * Given an array of integers, every element appears three times except for one, which appears exactly once. Find that single one.

 Note:
 Your algorithm should have a linear runtime complexity. Could you implement it without using extra memory?
 */
public class SingleNumberII_137 {
    /**
     * allocate 32 bit array to store state
     */
    public int singleNumberUseExtraSpace(int[] nums) {
        if (nums == null || nums.length == 0) {
            return -1;
        }
        int res = 0;
        int[] bits = new int[32];

        for (int i = 0; i < 32; i++) {
            for (int j = 0; j < nums.length; j++) {
                bits[i] += nums[j] >> i & 1; // to check nums[j] at i bit
                bits[i] %= 3; // if there are 3 same numbers in array, then bits[i] is 0, else 1
            }
            res |= (bits[i] << i); // recover to location bit i
        }
        return res;
    }

    /**
     * https://discuss.leetcode.com/topic/11877/detailed-explanation-and-generalization-of-the-bitwise-operation-method-for-single-numbers
     * k = 3, p = 1.
     k is 3, then m = 2, we need two 32-bit integers(x2, x1) as the counter. And 2^m > k so we do need a mask.
     Write k in its binary form: k = '11', then k1 = 1, k2 = 1, so we have mask = ~(x1 & x2).
     */
    public int singleNumber(int[] nums) {
        int x1 = 0;
        int x2 = 0;
        int mask = 0;

        for (int i : nums) {
            x2 ^= x1 & i;
            x1 ^= i;
            mask = ~(x1 & x2);
            x1 &= mask;
            x2 &= mask;
        }
        // p = 1, in binary form p = '01', then p1 = 1, so we should return x1;
        // if p = 2, in binary form p = '10', then p2 = 1, so we should return x2.
        return x1;
    }
}
