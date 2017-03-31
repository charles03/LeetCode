package com.leetcode.dynamicprogram;

import com.advanced.dataStructure.trie.AddAndSearchWord;

/**
 * Created by charles on 1/31/17.
 * Find the contiguous subarray within an array (containing at least one number) which has the largest product.

 For example, given the array [2,3,-2,4],
 the contiguous subarray [2,3] has the largest product = 6.
 */
public class MaximumProductSubarray_152 {
    /**
     * State : max[i] means max product that can be achieved ending with i
     *         min[i] means min product that can be achieved ending with i
     * function: max[i] = max(max[i-1] * A[i], min[i-1]*A[i], A[i])
     *          min[i] = min(min[i-1] * A[i], max[i-1]*A[i], A[i])
     *          res = max(res, max[i])
     * int max[0] = min[0] = A[0]
     */
    public int maxProduct(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int len = nums.length;
        int[] max = new int[len];
        int[] min = new int[len];
        // init
        max[0] = nums[0];
        min[0] = nums[0];
        int res = nums[0];
        for (int i = 1; i < len; i++) {
            max[i] = Math.max(nums[i], Math.max(max[i-1]*nums[i], min[i-1]*nums[i]));
            min[i] = Math.min(nums[i], Math.min(max[i-1]*nums[i], min[i-1]*nums[i]));
            res = Math.max(res, max[i]);
        }
        return res;
    }

    public int maxProductOptimization(int[] nums) {
        // store result that is max which have found so far
        int res = nums[0];
        int i = 1;
        int len = nums.length;
        // use two vars max & min to store product of subarray
        // end with current number nums[i]
        int max = nums[0];
        int min = nums[0];
        int tmp = 0; // for swap
        while (i < len) {
            /**
             * multiply by negative makes max -> min, or make min -> max
             * so redefine extreme by swapping
             */
            if (nums[i] < 0) {
                tmp = max;
                max = min;
                min = tmp;
            }
            // compare current nums[i] with product of all
            // to caculate max/min, either current number itself
            // or max/min by previous subarray and multiply current one
            max = Math.max(nums[i], max * nums[i]);
            min = Math.min(nums[i], min * nums[i]);
            // the candidate for result
            res = Math.max(res, max);
            // move to next
            i++;
        }
        return res;
    }
}
