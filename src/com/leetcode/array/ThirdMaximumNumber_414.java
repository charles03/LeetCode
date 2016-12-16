package com.leetcode.array;

/**
 * Created by charles on 12/13/16.
 * Given a non-empty array of integers, return the third maximum number in this array.
 * If it does not exist, return the maximum number. The time complexity must be in O(n).
 *
 Example 1:
 Input: [3, 2, 1]
 Output: 1
 Explanation: The third maximum is 1.

 Example 2:
 Input: [1, 2]
 Output: 2
 Explanation: The third maximum does not exist, so the maximum (2) is returned instead.

 Example 3:
 Input: [2, 2, 3, 1]
 Output: 1
 Explanation: Note that the third maximum here means the third maximum distinct number.
 Both numbers with value 2 are both considered as second maximum.
 */
public class ThirdMaximumNumber_414 {
    public int thirdMaxNotRecommend(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int m1 = Integer.MIN_VALUE; // most maximum
        int m2 = Integer.MIN_VALUE; // 2nd maximum
        int m3 = Integer.MIN_VALUE; // 3rd maximum
        int min = Integer.MAX_VALUE; // for edge case
        int len = nums.length;
        if (len == 1) {
            return nums[0];
        }

        for (int i = 0; i < len; i++) {
            if (nums[i] > m1) {
                m3 = m2;
                m2 = m1;
                m1 = nums[i];
            } else if (m2 < nums[i] && nums[i] < m1) {
                m3 = m2;
                m2 = nums[i];
            } else if (m3 < nums[i] && nums[i] < m2) {
                m3 = nums[i];
            }
            min = Math.min(nums[i], min);
        }
        System.out.println(String.format("%s, %s, %s, %s", m1, m2, m3, min));
        if (len == 2) {
            return m1;
        }
        if ((m3 == min || m3 != Integer.MIN_VALUE) && m2 != min) {
            return m3;
        }
        return m1;
    }

    public int thirdMax(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int max = Integer.MIN_VALUE; // first max
        int mid = Integer.MIN_VALUE; // second max
        int small = Integer.MIN_VALUE; // third max
        int count = 0; // to count top unique element in array;

        for (int i = 0; i < nums.length; i++) {
            // skip loop if max or mid are duplicate, to avoid right shift
            if (nums[i] == max || nums[i] == mid) {
                continue;
            }
            // right shift
            if (nums[i] > max) {
                small = mid;
                mid = max;
                max = nums[i];
                count++;
            } else if (nums[i] > mid) {
                small = mid;
                mid = nums[i];
                count++;
            } else if (nums[i] >= small) {
                // if small duplicated, no issue in right shift, but need count++;
                small = nums[i];
                count++;
            }
        }
        if (count >= 3) {
            return small;
        }
        return max;
    }

    public static void main(String[] args) {
        ThirdMaximumNumber_414 t = new ThirdMaximumNumber_414();
        int[] num1 = {2,2,3,1};
        int[] num2 = {1,1,2};
        int[] num3 = {1,2,-2147483648};
        int[] num4 = {-2147483648,1,1};
        int[] num5 = {1,2,2,5,3,5};
        int[] num6 = {1,1};
        int[] num7 = {1,2};
        System.out.println(t.thirdMax(num1) == 1);
        System.out.println(t.thirdMax(num2) == 2);
        System.out.println(t.thirdMax(num3) == -2147483648);
        System.out.println(t.thirdMax(num4) == 1);
        System.out.println(t.thirdMax(num5) == 2);
        System.out.println(t.thirdMax(num6) == 1);
        System.out.println(t.thirdMax(num7) == 2);
        System.out.println(String.valueOf(-123));
    }
}
