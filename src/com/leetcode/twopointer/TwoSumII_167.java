package com.leetcode.twopointer;

import java.util.Arrays;

/**
 * Created by charles on 3/26/17.
 * Given an array of integers that is already sorted in ascending order, find two numbers such that they add up to a specific target number.

 The function twoSum should return indices of the two numbers such that they add up to the target, where index1 must be less than index2. Please note that your returned answers (both index1 and index2) are not zero-based.

 You may assume that each input would have exactly one solution and you may not use the same element twice.

 Input: numbers={2, 7, 11, 15}, target=9
 Output: index1=1, index2=2

 */
public class TwoSumII_167 {
    /**
     * High level thought:
     * because of ascending sorted array,
     */
    public int[] twoSum(int[] numbers, int target) {
        int start = 0;
        int end = numbers.length - 1;
        int[] res = new int[2];
        while (start < end) {
            if (numbers[start] + numbers[end] < target) {
                start++;
            } else if (numbers[start] + numbers[end] > target) {
                end--;
            } else {
                res[0] = start + 1;
                res[1] = end + 1;
                break;
            }
        }
        return res;
    }
    /** better solution : Binary Search
     * if sum > target, the point is to find next end which makes
     * nums[start] + nums[end-1] < target <= num[start] + nums[end];
     *
     * if sum < target, to find next start which makes
     * nums[start] + nums[end] <= target < nums[start+1] + nums[end]
     *
     * if sum == target, return result
     * */
    public int[] TwoSumII(int[] nums, int target) {
        int start = 0;
        int end = nums.length - 1;
        int sum = 0;
        int[] res = new int[2];
        while (start < end) {
            sum = nums[start] + nums[end];
            if (sum == target) {
                res[0] = start;
                res[1] = end;
                break;
            } else if (sum > target) {
                /**
                 * BinarySearch method will return index of the search key, if it is contained in the array within the specified range;
                 * otherwise, return -(insertion point) - 1).  The point is defined as the point at which the
                 * key would be inserted into the array: the index of the first element in the range greater than the key,
                 * thus we need to mask returned result to get insertion point
                 */
                end = Arrays.binarySearch(nums, start, end, target-nums[start]);
                if (end < 0) {
                    end = ~end;
                }
                if (nums[start] + nums[end] != target) {
                    end--;
                }
            } else {
                start = Arrays.binarySearch(nums, start, end, target-nums[end]);
                if (start < 0) {
                    start = ~start;
                }
            }
        }
        return res;
    }


}
