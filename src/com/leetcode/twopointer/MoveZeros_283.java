package com.leetcode.twopointer;

/**
 * Created by charles on 3/25/17.
 * Given an array nums, write a function to move all 0's to the end of it while maintaining the relative order of the non-zero elements.

 For example, given nums = [0, 1, 0, 3, 12], after calling your function, nums should be [1, 3, 12, 0, 0].

 Note:
 You must do this in-place without making a copy of the array.
 Minimize the total number of operations.
 */
public class MoveZeros_283 {
    /**
     * Thought: two pointers
     * from front of array, consecutively find non-zero number
     * use second pointer from start of array to update number into array by index
     */
    public void moveZeros(int[] nums) {
        int firstNonZeroIdx = 0;
        int idx = 0;
        int len = nums.length;
        while (firstNonZeroIdx < len) {
            while (firstNonZeroIdx < len && nums[firstNonZeroIdx] == 0) {
                firstNonZeroIdx++;
            }
            if (firstNonZeroIdx < len && idx != firstNonZeroIdx) {
                nums[idx] = nums[firstNonZeroIdx];
                nums[firstNonZeroIdx] = 0;
            }
            idx++;
            firstNonZeroIdx++;
        }
    }

    /** better version, use single pointer
     * if num in array != 0, then update from start of array
     */
    public void moveZerosII(int[] nums) {
        if (nums == null || nums.length == 0) {
            return;
        }
        int insertPos = 0;
        for (int num : nums) {
            if (num != 0) {
                nums[insertPos++] = num;
            }
        }
        while (insertPos < nums.length) {
            nums[insertPos++] = 0;
        }
    }
}
