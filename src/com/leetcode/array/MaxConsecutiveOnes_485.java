package com.leetcode.array;

/**
 * Created by charles on 1/28/17.
 * Given a binary array, find the maximum number of consecutive 1s in this array.
 Example 1:
 Input: [1,1,0,1,1,1]
 Output: 3
 Explanation: The first two digits or the last three digits are consecutive 1s.
 The maximum number of consecutive 1s is 3.
 Note:

 The input array will only contain 0 and 1.
 The length of input array is a positive integer and will not exceed 10,000
 */
public class MaxConsecutiveOnes_485 {
    public int findMaxConsectiveOnes(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int count = 0;
        int max = 0;

        for (int n : nums) {
            if (n == 0) {
                count = 0;
            } else {
                count++;
            }
            max = Math.max(max, count);
        }
        return max;
    }

    public int findMaxConsectiveOnes_v2(int[] nums) {
        int count = 0;
        int max = 0;
        for (int num : nums) {
            if (num == 1) {
                count++;
            } else {
                max = Math.max(max, count);
                count = 0; // reset
            }
        }
        /**
         * noted, below return is for the case when no zero in array
         * [1], thus, should return max(count, max)
         */
        return Math.max(max, count);
    }

    public static void main(String[] args) {
        MaxConsecutiveOnes_485 m = new MaxConsecutiveOnes_485();
        int[] n1 = {1};
        System.out.println(m.findMaxConsectiveOnes_v2(n1) == 1);
    }
}
