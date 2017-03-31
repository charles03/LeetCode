package com.leetcode.dynamicprogram;

/**
 * Created by charles on 2/5/17.
 * A sequence of numbers is called a wiggle sequence if the differences between successive numbers strictly alternate between positive and negative. The first difference (if one exists) may be either positive or negative. A sequence with fewer than two elements is trivially a wiggle sequence.

 For example, [1,7,4,9,2,5] is a wiggle sequence because the differences (6,-3,5,-7,3) are alternately positive and negative. In contrast, [1,4,7,2,5] and [1,7,4,5,5] are not wiggle sequences, the first because its first two differences are positive and the second because its last difference is zero.

 Given a sequence of integers, return the length of the longest subsequence that is a wiggle sequence. A subsequence is obtained by deleting some number of elements (eventually, also zero) from the original sequence, leaving the remaining elements in their original order.

 Examples:
 Input: [1,7,4,9,2,5]
 Output: 6
 The entire sequence is a wiggle sequence.

 Input: [1,17,5,10,13,15,10,5,16,8]
 Output: 7
 There are several subsequences that achieve this length. One is [1,17,10,13,10,16,8].

 Input: [1,2,3,4,5,6,7,8,9]
 Output: 2
 Follow up:
 Can you do it in O(n) time?
 */
public class WiggleSequence_376 {
    /**
     * DP solution
     * State: dp[i] is longest wiggle sequence ending at index i
     * Function: if (a[i] - a[i-1]) * (a[i-1] - a[i-2]) < 0
     *              dp[i] = dp[i - 1] + 1
     *           else
     *              dp[i] = Math.max(dp[i-1], dp[i-2])
     *
     */
    public int wiggleMaxLengthDp(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        int len = nums.length;
        int[] up = new int[len];
        int[] down = new int[len];

        up[0] = 1;
        down[0] = 1;

        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > nums[i - 1]) {
                up[i] = down[i-1] + 1;
                down[i] = down[i-1];
            } else if (nums[i] < nums[i - 1]) {
                down[i] = up[i-1] + 1;
                up[i] = up[i-1];
            } else {
                down[i] = down[i-1];
                up[i] = up[i-1];
            }
        }
        return Math.max(up[len - 1], down[len - 1]);
    }

    /**
     * Greedy solution,
     * need to consider test case like [3,3,3,2,5]
     */
    public int wiggleMaxLength(int[] nums) {
        if (nums.length <= 1) {
            return nums.length;
        }
        int k = 1;
        int maxLen = 2;
        int arrLen = nums.length;
        while (k < arrLen && nums[k] == nums[k - 1]) {
            k++;
        }
        if (k == arrLen) {
            return 1;
        }
        boolean isAscending = nums[k] > nums[k-1];
        // greedy
        for (int i = k + 1; i < arrLen; i++) {
            if ((isAscending && nums[i] < nums[i - 1]) //prev is asc and curr is desc
                    || (!isAscending && nums[i] > nums[i - 1])) { // prev is desc and curr is asc
                isAscending = !isAscending; // switch state
                maxLen++;
            }
        }
        return maxLen;
    }

    /**
     * Greedy Simplified
     * only use two vars count of ascending, and count of descending
     */
    public int wiggleMaxLengthUlti(int[] nums) {
        int upCount = 1;
        int downCount = 1;
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int len = nums.length;
        for (int i = 1; i < len; i++) {
            if (nums[i] > nums[i-1]) {
                upCount = downCount + 1;
            } else if (nums[i] < nums[i-1]) {
                downCount = upCount + 1;
            }
        }
        return Math.max(upCount, downCount);
    }

}
