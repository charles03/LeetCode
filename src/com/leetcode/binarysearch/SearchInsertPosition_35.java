package com.leetcode.binarysearch;

/**
 * Created by charles on 3/22/17.
 * Given a sorted array and a target value, return the index if the target is found. If not, return the index where it would be if it were inserted in order.

 You may assume no duplicates in the array.

 Here are few examples.
 [1,3,5,6], 5 → 2
 [1,3,5,6], 2 → 1
 [1,3,5,6], 7 → 4
 [1,3,5,6], 0 → 0
 */
public class SearchInsertPosition_35 {
    /**
     * High level: find index of new number in sorted array
     * naive: find num[i] <= target <= num[i+1]
     */
    public int searchInsert(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        if (target <= nums[0]) {
            return 0;
        }
        int len = nums.length;
        if (target > nums[len - 1]) {
            return len;
        }
        int start = 0, end = len - 1;
        int mid = 0;
        while (start < end) {
            mid = start + (end - start) / 2;
            if (nums[mid] < target) {
                start = mid + 1;
            } else if (nums[mid] == target) {
                return mid;
            } else {
                end = mid;
            }
        }
        return end;
    }

    public static void main(String[] args) {
        SearchInsertPosition_35 s = new SearchInsertPosition_35();
        int[] n1 = {1,3,5,6};
        System.out.println(s.searchInsert(n1, 5));
        System.out.println(s.searchInsert(n1, 2));
    }
}
