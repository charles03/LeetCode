package com.leetcode.array;

/**
 * Created by charles on 4/7/17.
 * Given a sorted array, remove the duplicates in place such that each element appear only once and return the new length.

 Do not allocate extra space for another array, you must do this in place with constant memory.

 For example,
 Given input array nums = [1,1,2],

 Your function should return length = 2, with the first two elements of nums being 1 and 2 respectively. It doesn't matter what you leave beyond the new length.
 */
public class RemoveDuplicatesFromSortedArray_26 {
    /**
     * O(n) solution
     * use two pointer
     * one is to look for first next num differ from num at another pointer
     */
    public int removeDuplicates(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int i = 1, j = 1;
        int len = nums.length;
        int curr = nums[0];
        while (i < len) {
            while (i < len && curr == nums[i]) {
                i++;
            }
            if (i >= len) {
                return j;
            }
            swap(nums, i, j);
            curr = nums[j];
            i++;
            j++;
        }
        return j;
    }
    private void swap(int[] arr, int a, int b) {
        int tmp = arr[a];
        arr[a] = arr[b];
        arr[b] = tmp;
    }

    public static void main(String[] args) {
        int[] n1 = {1,1,2,2,2,3,4,4};
        RemoveDuplicatesFromSortedArray_26 r = new RemoveDuplicatesFromSortedArray_26();
        System.out.println(r.removeDuplicates(n1));
    }

    /** simple version */
    public int removeDuplicatsII(int[] nums) {
        if (nums == null) {
            return 0;
        }
        int idx = 1;
        int len = nums.length;
        if (len < 2) {
            return len;
        }
        for (int i = 1; i < len; i++) {
            if (nums[i] != nums[i-1]) {
                nums[idx] = nums[i];
                idx++;
            }
        }
        return idx;
    }
}
