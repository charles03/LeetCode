package com.leetcode.hash;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by charles on 5/18/17.
 * Given a binary array, find the maximum length of a contiguous subarray with equal number of 0 and 1.

 Example 1:
 Input: [0,1]
 Output: 2
 Explanation: [0, 1] is the longest contiguous subarray with equal number of 0 and 1.
 Example 2:
 Input: [0,1,0]
 Output: 2
 Explanation: [0, 1] (or [1, 0]) is a longest contiguous subarray with equal number of 0 and 1.

 */
public class ContiguousArray_525 {
    /**
     * if there are same counts of 1's and 0's in sub-array a[i:j],
     * then the 1-0 count difference up to index i-1 must be same as up to index j
     * use map to store (key : count diff, value: first index)
     * count diff can be sum of {1,-1} when having {1,0};
     */
    public int findMaxLength(int[] nums) {
        // use -1, 1 to achieve count matching when number of (1's) = number of (0's)
        int[] offset = {-1, 1};
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, -1);
        int maxLen = 0;
        int preSum = 0;

        for (int i = 0; i < nums.length; i++) {
            preSum = preSum + offset[nums[i]];
            if (map.containsKey(preSum)) {
                maxLen = Math.max(maxLen, i - map.get(preSum));
            } else {
                map.put(preSum, i);
            }
        }
        return maxLen;
    }
    /**
     * instead of map, use extra array to keep track of offset sum.
     * still use {-1, 1} for (0,1), so sum can range between -n to n,
     * thus we need 2n+1 size of array.
     * besides, init array with -2 default autofill value
     * as there is no 0's or 1's at the beginning.
     */
    public int findMaxLengthII(int[] nums) {
        int len = nums.length;
        int[] track = new int[2 * len + 1];
        Arrays.fill(track, -2);
        track[len] = -1;
        /**
         * as for track array,
         * start offset from len() because it is in the center of [0, 2n];
         * index is preSum of (1's and 0's) + len(){as offset}
         * value is index of origin num array
         */
        int max = 0, offset = len;
        int[] diff = {-1, 1};
        for (int i = 0; i < len; i++) {
            offset += diff[nums[i]];
            if (track[offset] >= -1) { // when elem in track array already existed
                max = Math.max(max, i - track[offset]);
            } else {
                track[offset] = i; // update index of given nums array
            }
        }
        return max;
    }
}
