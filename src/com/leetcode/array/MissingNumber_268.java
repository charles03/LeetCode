package com.leetcode.array;

/**
 * Created by charles on 12/15/16.
 * Given an array containing n distinct numbers taken from 0, 1, 2, ..., n, find the one that is missing from the array.

 For example,
 Given nums = [0, 1, 3] return 2.

 Note:
 Your algorithm should run in linear runtime complexity. Could you implement it using only constant extra space complexity?
 */
public class MissingNumber_268 {
    /**
     * Thought:
     * The basic idea is to use XOR operation. We all know that a^b^b =a,
     * which means two xor operations with the same number will eliminate the number and reveal the original number.
     In this solution, I apply XOR operation to both the index and value of the array. In a complete array with no missing numbers,
     the index and value should be perfectly corresponding( nums[index] = index), so in a missing array, what left finally is the missing number.
     */
    public int missingNumber(int[] nums) {
        int res = 0;
        for (int i = 0; i < nums.length; i++) {
            System.out.println(" res : " + res);
            res ^= i;
            System.out.println(" res after i : " + res);
            res ^= nums[i];
            System.out.println(" res after nums[i]: " + res);
        }
        return res ^ nums.length;
    }

    public static void main(String[] args) {
        MissingNumber_268 m = new MissingNumber_268();
        int[] nums = {0,3,1};
        System.out.println(m.missingNumber(nums));
    }

}
