package com.leetcode.stack;

import java.util.Arrays;
import java.util.Stack;

/**
 * Created by charles on 3/19/17.
 * Given a circular array (the next element of the last element is the first element of the array), print the Next Greater Number for every element. The Next Greater Number of a number x is the first greater number to its traversing-order next in the array, which means you could search circularly to find its next greater number. If it doesn't exist, output -1 for this number.

 Example 1:
 Input: [1,2,1]
 Output: [2,-1,2]
 Explanation: The first 1's next greater number is 2;
 The number 2 can't find next greater number;
 The second 1's next greater number needs to search circularly, which is also 2.

 */
public class NextGreaterElementII_503 {
    /**
     * Naive solution : extend original array to twice length
     * 2nd half has same element as first half.
     */
    public int[] nextGreaterElements(int[] nums) {
        int max = Integer.MIN_VALUE;
        for (int num : nums) {
            max = Math.max(max, num);
        }
        int len = nums.length;
        int[] res = new int[len];
        int[] temp = new int[len * 2];

        for (int i = 0; i < len * 2; i++) {
            temp[i] = nums[i % len];
        }
        Arrays.fill(res, -1);
        for (int i = 0; i < len; i++) {
            if (nums[i] == max) {
                continue;
            }
            for (int j = i + 1; j < len * 2; j++) {
                if (temp[j] > nums[i]) {
                    res[i] = temp[j];
                    break;
                }
            }
        }
        return res;
    }

    /**
     * Better solution: use Stack to facilitate lookup
     * First we put all indexes into stack. smaller index on the top
     */
    public int[] nextGreaterElementsII(int[] nums) {
        int len = nums.length;
        int[] res = new int[len];
        Arrays.fill(res, -1);

        Stack<Integer> stack = new Stack<>(); // store index of array in stack
        for (int i = 0; i < len * 2; i++) {
            int num = nums[i % len];

            while (!stack.isEmpty() && nums[stack.peek()] < num) {
                res[stack.pop()] = num;
            }
            if (i < len) {
                stack.push(i);
            }
        }
        return res;
    }
    /**
     * Optimized version, traver from tail to front
     * so as to reduce action of assign value into result array
     */
    public int[] nextGreaterElementsIII(int[] nums) {
        int len = nums.length;
        int[] res = new int[len];
        Arrays.fill(res, -1);
        Stack<Integer> stack = new Stack<>();
        /** traver from tail to front */
        for (int i = len * 2 - 1; i >= 0; i--) {
            int idx = i % len;
            int num = nums[idx];
            while (!stack.isEmpty() && nums[stack.peek()] <= num) {
                stack.pop();
            }
            if (!stack.isEmpty()) {
                res[idx] = nums[stack.peek()];
            }
            stack.push(idx);
        }
        return res;
    }

}
