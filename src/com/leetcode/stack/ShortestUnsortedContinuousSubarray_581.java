package com.leetcode.stack;

import sun.nio.cs.ext.MacHebrew;

import java.util.Stack;

/**
 * Created by charles on 5/14/17.
 * Given an integer array, you need to find one continuous subarray that if you only sort this subarray in ascending order, then the whole array will be sorted in ascending order, too.

 You need to find the shortest such subarray and output its length.

 Example 1:
 Input: [2, 6, 4, 8, 10, 9, 15]
 Output: 5
 Explanation: You need to sort [6, 4, 8, 10, 9] in ascending order to make the whole array sorted in ascending order.
 */
public class ShortestUnsortedContinuousSubarray_581 {
    /**
     * use stack to determine the correct position of minimum and maximum element in the unsorted subarray
     * to determine boundaries of required unsorted subarray.
     *
     * push elements' indices over the stack when elem in ascending order
     * as soon as encountering falling slope, need to pop from stack to help current number find correct position
     *
     * let's say popping step when index on stack top is k,
     * now num[j] has found its correct pos, it need to lie at index k + 1
     *
     * Similarly to find right boundary of unsorted subarray, we traverse over array backward.
     * keep pushing elem if see a falling slope.
     * as soon as see rising slope. need to trace forward now and determine large elem correct pos
     */
    public int findUnsortedSubarray(int[] nums) {
        Stack<Integer> stack = new Stack<>(); // store index in array
        int left = nums.length, right = 0;
        for (int i = 0; i < nums.length; i++) {
            while (!stack.isEmpty() && nums[stack.peek()] > nums[i]) {
                left = Math.min(left, stack.pop());
            }
            stack.push(i);
        }
        stack.clear();
        for (int i = nums.length - 1; i >= 0; i--) {
            while (!stack.isEmpty() && nums[stack.peek()] < nums[i]) {
                right = Math.max(right, stack.pop());
            }
            stack.push(i);
        }
        if (right - left > 0) {
            return right - left + 1;
        }
        return 0;
    }

    /**
     * without extra space.
     * find min in forwards in subarray only when array is in falling slope
     * whenever slope falls, knowing unsorted array has surely started
     * now to determine minimum element found till end of array
     * find max in backwards in subarray only when array is in rising slope
     * we need to find the first element which is just larger than minmin. Similarly,
     * for max's position, we need to find the first element which is just smaller than  searching in nums backwards.
     */
    public int findUnsortedSubarrayII(int[] nums) {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        boolean isDescending = false;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] < nums[i-1]) {
                isDescending = true;
            }
            if (isDescending) {
                min = Math.min(min, nums[i]);
            }
        }
        isDescending = false;
        for (int i = nums.length - 2; i >= 0; i--) {
            if (nums[i] > nums[i+1]) {
                isDescending = true;
            }
            if (isDescending) {
                max = Math.max(max, nums[i]);
            }
        }
        int left = 0, right = 0;
        for (left = 0; left < nums.length; left++) {
            if (min < nums[left]) {
                break;
            }
        }
        for (right = nums.length - 1; right >= 0; right--) {
            if (max > nums[right]) {
                break;
            }
        }
        if (right - left >= 0) {
            return right - left + 1;
        }
        return 0;
    }

}
