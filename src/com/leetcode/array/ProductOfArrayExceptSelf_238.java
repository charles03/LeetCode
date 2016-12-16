package com.leetcode.array;

import java.util.Arrays;

/**
 * Created by charles on 12/15/16.
 * Given an array of n integers where n > 1, nums, return an array output such that output[i] is equal to the product of all the elements of nums except nums[i].

 Solve it without division and in O(n).
 For example, given [1,2,3,4], return [24,12,8,6].

 Follow up:
 Could you solve it with constant space complexity? (Note: The output array does not count as extra space for the purpose of space complexity analysis.)
 */
public class ProductOfArrayExceptSelf_238 {
    /**
     * Thought
     * [1, 2, 3, 4] left shift
     * [1, 1, 1*2, 1*2*3]
     * [4*3*2, 4*3, 4, 1]
     */
    public int[] productExceptSelf(int[] nums) {
        if (nums == null || nums.length == 0) {
            return new int[0];
        }
        int len = nums.length;
        int[] res = new int[len];

        // from left to right
        res[0] = 1;
        for (int i = 1; i < len; i++) {
            res[i] = res[i - 1] * nums[i - 1];
        }
        // from right to left
        int right = 1;
        for (int i = len - 1; i >= 0; i--) {
            res[i] *= right;
            right *= nums[i];
        }
        return res;
    }

    public static void main(String[] args) {
        ProductOfArrayExceptSelf_238 p = new ProductOfArrayExceptSelf_238();
        int[] nums = {1, 2, 3, 4};
        print(p.productExceptSelf(nums));
    }

    private static void print(int[] arr) {
        Arrays.stream(arr).forEach(t -> System.out.print(t + ", "));
        System.out.println();
    }
}
