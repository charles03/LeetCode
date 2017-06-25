package com.leetcode.array;

/**
 * Created by charles on 4/5/17.
 * Given a non-negative integer represented as a non-empty array of digits, plus one to the integer.

 You may assume the integer do not contain any leading zero, except the number 0 itself.

 The digits are stored such that the most significant digit is at the head of the list.
 */
public class PlusOne_66 {
    /**
     * from left to right, left is high digits, right is lower digits
     * like 199 + 1=> 200
     * or 9 + 1 => 10
     *
     * Thought: from right (lowest digits) {}
     * if (digit < 9) { digit++, then break;}
     * else set current digit as 0
     * and then to next digit, until reach at index 0
     * if (digit[0] == 0) {
     *     need to create new array, size + 1, and assign arr[0] = 1
     * }
     *
     */
    public int[] plusOne(int[] digits) {
        int len = digits.length;
        for (int i = len - 1; i >= 0; i--) {
            if (digits[i] < 9) {
                digits[i]++;
                break;// return directly
            } else {
                digits[i] = 0;
            }
        }
        // like case 99 + 1 -> 100 or 9 + 1 -> 10
        if (digits[0] == 0) {
            int[] res = new int[len + 1];
            res[0] = 1;
            return res;
        }
        return digits;
    }
}
