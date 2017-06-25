package com.amazon;

/**
 * Created by charles on 6/21/17.
 * Given an array nums of integers and an int k, partition the array (i.e move the elements in "nums") such that:

 All elements < k are moved to the left
 All elements >= k are moved to the right
 Return the partitioning index, i.e the first index i nums[i] >= k.
 */
public class PartitionArray {
    public int partition(int[] nums, int k) {
        int i = 0;
        int j = nums.length - 1;
        while (i <= j) {
            while (i <= j && nums[i] < k) {
                i++;
            }
            while (i <= j && nums[j] >= k) {
                j--;
            }
            if (i <= j) {
                swap(nums, i, j);
                i++;
                j--;
            }
        }
        return i;
    }
    private void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
}
