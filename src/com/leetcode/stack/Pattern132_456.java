package com.leetcode.stack;

/**
 * Created by charles on 3/19/17.
 * Given a sequence of n integers a1, a2, ..., an, a 132 pattern is a subsequence ai, aj, ak such that i < j < k and ai < ak < aj. Design an algorithm that takes a list of n numbers as input and checks whether there is a 132 pattern in the list.

 Note: n will be less than 15,000.

 Example 1:
 Input: [1, 2, 3, 4]

 Output: False

 Explanation: There is no 132 pattern in the sequence.
 Example 2:
 Input: [3, 1, 4, 2]

 Output: True

 Explanation: There is a 132 pattern in the sequence: [1, 4, 2].
 Example 3:
 Input: [-1, 3, 2, 0]

 Output: True

 Explanation: There are three 132 patterns in the sequence: [-1, 3, 2], [-1, 3, 0] and
 */
public class Pattern132_456 {
    /**
     * Naive O(n ^ 3) solution
     * to check every (i,j,k) combination to see if there is any 132 pattern
     */
    public boolean find132Pattern(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                for (int k = j + 1; k < nums.length; k++) {
                    if (nums[i] < nums[k] && nums[k] < nums[j]) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /** O(n^2) solution
     * we need to choose index i which will maximize the range (nums[i], nums[j])
     * Since upper bound nums[j] is fixed. this is equivalent to minimizing the lower bound nums[o]
     */
    public boolean find132PatternII(int[] nums) {
        int len = nums.length;
        for (int j = 0, min = Integer.MAX_VALUE; j < len; j++) {
            min = Math.min(min, nums[j]);
            if (min == nums[j]) {
                continue;
            }
            for (int k = len - 1; k > j; k--) {
                if (min < nums[k] && nums[k] < nums[j]) {
                    return true;
                }
            }
        }
        return false;
    }


}
