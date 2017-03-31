package com.leetcode.array;

import java.util.Arrays;

/**
 * Created by charles on 12/18/16.
 * Rotate an array of n elements to the right by k steps.

 For example, with n = 7 and k = 3, the array [1,2,3,4,5,6,7] is rotated to [5,6,7,1,2,3,4].
 */
public class RotateArray_189 {
    /**
     * Solution One: space complexity O(1)
     * Thought: apply reverse method 3 times
     * (1234567) -> (7654321) -> (5674321) -> (5671234)
     */
    public void rotate(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return;
        }
        int len = nums.length;
        k %= len; // pretreatment to make sure k is in num.length
        reverse(nums, 0, len - 1);
        reverse(nums, 0, k - 1);
        reverse(nums, k, len - 1);
    }

    private void reverse(int[] nums, int start, int end) {
        int temp = 0;
        while (start < end) {
            temp = nums[start];
            nums[start] = nums[end];
            nums[end] = temp;
            start++;
            end--;
        }
    }

    /**
     * Solution Two, backup data which need moved
     */
    public void rotate2(int[] nums, int k) {
        if(nums == null || nums.length == 1) {
            return;
        }
        int len = nums.length;
        k %= len; // pretreatment of k
        int[] tmp = new int[k];

        for (int i = 0; i < k; i++) {
            tmp[i] = nums[len - k + i];
        }
        // bring front part to right shift
        for (int i = len - k - 1; i >= 0; i--) {
            nums[i + k] = nums[i];
        }
        // copy back tail part
        for (int i = 0; i < k; i++) {
            nums[i] = tmp[i];
        }
    }

    public static void main(String[] args) {
        int[] nums = {1,2,3,4,5,6,7};
        RotateArray_189 r = new RotateArray_189();
        r.rotate(nums, 3);
        print(nums);
    }

    private static void print(int[] nums) {
        Arrays.stream(nums).forEach(t -> System.out.print(t + ","));
        System.out.println();
    }
}
