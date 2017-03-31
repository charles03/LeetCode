package com.leetcode.stack;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * Created by charles on 3/19/17.
 * You are given two arrays (without duplicates) nums1 and nums2 where nums1’s elements are subset of nums2. Find all the next greater numbers for nums1's elements in the corresponding places of nums2.

 The Next Greater Number of a number x in nums1 is the first greater number to its right in nums2. If it does not exist, output -1 for this number.

 Example 1:
 Input: nums1 = [4,1,2], nums2 = [1,3,4,2].
 Output: [-1,3,-1]
 Explanation:
 For number 4 in the first array, you cannot find the next greater number for it in the second array, so output -1.
 For number 1 in the first array, the next greater number for it in the second array is 3.
 For number 2 in the first array, there is no next greater number for it in the second array, so output -1.
 Example 2:
 Input: nums1 = [2,4], nums2 = [1,2,3,4].
 Output: [3,-1]
 Explanation:
 For number 2 in the first array, the next greater number for it in the second array is 3.
 For number 4 in the first array, there is no next greater number for it in the second array, so output -1.

 Noted
 All elements in nums1 and nums2 are unique.
 The length of both nums1 and nums2 would not exceed 1000.
 */
public class NextGreaterElement_496 {
    /**
     * high level thought
     * need container to store pair<Num itself, next greater>
     * use anther container to keep nums2 array relative decreasing order
     *
     * Container 1 is map, key is num in Num1, value is next greater in Num2
     * Container 2 is stack, LIFO. Two directions can use stack, from front to end or reverse
     * 1. Front -> End;
     *      when stack.peek < curr num in Num2
     *          map.put(stack.pop, curr Num)
     *      what remain in stack is decreasing order
     * 2. End -> Front;
     *      example : original :[6,2,4,9]
     *            end -> start :[9,4,2,6]
     *
     *      when stack.peek < curr num in Num2
     *          stack.pop
     *      map.put(curr num, stack.peek)
     *
     *      from 9 -> 2 is continuous decreasing,
     *      then, each time, when put into map,
     *      put new current num as key, value of peek in stack as value in map
     *
     * this way is to find greater elem from tail to end, with less set value action */
    public int[] nextGreaterElement(int[] findNums, int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        // saving memeory
        for (int num : findNums) {
            map.put(num, -1);
        }
        // use stack to find first great for decreasing order numbers
        Stack<Integer> stack = new Stack<>();
        for (int i = nums.length - 1; i >= 0; i--) {
            while (!stack.isEmpty() && nums[i] > stack.peek()) {
                stack.pop();
            }
            if (map.containsKey(nums[i]) && !stack.isEmpty()) {
                map.put(nums[i], stack.peek());
            }
            stack.push(nums[i]);
        }
        int[] res = new int[findNums.length];
        for (int i = 0; i < findNums.length; i++) {
            res[i] = map.get(findNums[i]);
        }
        return res;
    }

    /** below is way find greater elem from front to end, more action in put value into map */
    public int[] nextGreaterElementII(int[] findNums, int[] nums) {
        Map<Integer, Integer> map = new HashMap<>(); // map from x to next greater elem
        Stack<Integer> stack = new Stack<>();
        int[] res = new int[findNums.length];
        /**
         * for given decreasing sub-sequence
         * example [9,8,7,3,2,1,6] when 6 is greater than 1, then 2, then 3
         * we pop [1,2,3] whose next great element should be 6
         */
        for (int num : nums) {
            while (!stack.isEmpty() && stack.peek() < num) {
                map.put(stack.pop(), num);
            }
            stack.push(num);
        }

        for (int i = 0; i < findNums.length; i++) {
            res[i] = map.getOrDefault(findNums[i], -1);
        }
        return res;
    }
}
