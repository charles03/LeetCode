package com.leetcode.math;

import java.util.Random;

/**
 * Created by charles on 3/16/17.
 * Shuffle a set of numbers without duplicates.

 Example:

 // Init an array with set 1, 2, and 3.
 int[] nums = {1,2,3};
 Solution solution = new Solution(nums);

 // Shuffle the array [1,2,3] and return its result. Any permutation of [1,2,3] must equally likely to be returned.
 solution.shuffle();

 // Resets the array back to its original configuration [1,2,3].
 solution.reset();

 // Returns the random shuffling of array [1,2,3].
 solution.shuffle();
 */
public class ShuffleArray_384 {
    private int[] nums;
    private int[] copy;

    public ShuffleArray_384(int[] nums) {
        this.nums = nums;
        this.copy = nums.clone();
    }
    public int[] reset() {
        return copy;
    }
    /** use Fisher-Yates Shuffle */
    public int[] shuffle() {
        Random random = new Random();
        for (int i = nums.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            swap(nums, i, j);
        }
        return nums;
    }

    private void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
}
