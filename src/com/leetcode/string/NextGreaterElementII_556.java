package com.leetcode.string;

/**
 * Created by charles on 6/10/17.
 * Given a positive 32-bit integer n, you need to find the smallest 32-bit integer which has exactly the same digits existing in the integer n and is greater in value than n. If no such positive 32-bit integer exists, you need to return -1.

 Example 1:
 Input: 12
 Output: 21
 Example 2:
 Input: 21
 Output: -1
 */
public class NextGreaterElementII_556 {
    public int nextGreaterElement(int n) {
        char[] digits = Integer.toString(n).toCharArray();
        int len = digits.length;
        // find first high digit breaking descending slope of low digits
        int highPos = len - 2;
        while (highPos >= 0 && digits[highPos] >= digits[highPos + 1]) { // highpos + 1 is lowPos
            highPos--; // from low to high, from right to left in array
        }
        if (highPos < 0) {
            return -1; // whole number is descending order in digit like 987654321
        }
        // find first low digit larger than high digit found at previous step
        int lowPos = len - 1;
        while (lowPos >= 0 && digits[highPos] >= digits[lowPos]) {
            lowPos--;
        }
        swap(digits, highPos, lowPos);
        // so far we having below premises
        /**
         * lowPos -> lowest digit are descending order
         * highPos -> lowPos are descending order as well as after swap
         * so in order to make this part of digits as smallest number
         * need to reverse this part
         */
        reverse(digits, highPos + 1);
        /**
         * use try -catch to handle the case like new number over Integer.MAX_VALUE
         * example : n = 1999999999
         * res : 9199999999 > 2147463647
         */
        try {
            return Integer.parseInt(new String(digits));
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    private void swap(char[] arr, int i, int j) {
        char tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
    private void reverse(char[] arr, int start) {
        int i = start;
        int j = arr.length - 1;
        while (i < j) {
            swap(arr, i, j);
            i++;
            j--;
        }
    }
}
