package com.leetcode.array;

import java.util.Arrays;

/**
 * Created by charles on 12/15/16.
 * Given an array nums, write a function to move all 0's to the end of it while maintaining the relative order of the non-zero elements.

 For example, given nums = [0, 1, 0, 3, 12], after calling your function, nums should be [1, 3, 12, 0, 0].

 Note:
 You must do this in-place without making a copy of the array.
 Minimize the total number of operations.
 */
public class MoveZeros_283 {
    public void moveZeros(int[] nums) {
        if (nums == null || nums.length == 0) {
            return;
        }
        int index = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                nums[index] = nums[i];
                index++;
            }
        }
        while (index < nums.length) {
            nums[index++] = 0;
        }
    }

    public static void main(String[] args) {
        MoveZeros_283 m = new MoveZeros_283();
        int[] nums = {0,0,0,1,0,2,3,4};
        m.moveZeros(nums);
        print(nums);
    }

    private static void print(int[] nums) {
        Arrays.stream(nums).forEach(t -> System.out.print(t + ","));
    }
}
