package com.leetcode.binarysearch;

/**
 * Created by charles on 4/4/17.
 * Given a positive integer num, write a function which returns True if num is a perfect square else False.

 Note: Do not use any built-in library function such as sqrt.

 Example 1:

 Input: 16
 Returns: True
 Example 2:

 Input: 14
 Returns: False
 */
public class ValidPerfectSquare_367 {
    /**
     * Thought: binary search, each time pick mid num
     * if (mid * mid > num) {
     *     end = mid
     * } else {
     *     start = mid;
     * }
     */
    public boolean isPerfectSquare(int num) {
        int start = 0;
        int end = num;
        long mid = 0; // should use long type, otherwise due to missing decision, calculation is wrong
        long multi = 0;
        while (start + 1 < end) {
            mid = start + (end - start) / 2;
            multi = mid * mid;
            if (multi == num) {
                return true;
            } else if (multi < num) {
                start = (int)mid;
            } else {
                end = (int)mid;
            }
        }
        return false;
    }

    /** Math solution 1+3+5+7.. + 2n+1 = n^2 */
    public boolean isPerfectSquareII(int num) {
        int i = 1;
        while (num > 0) {
            num -= i;
            i += 2;
        }
        return num == 0;
    }
    /** Newton method */
    public boolean isPerfectSquareIII(int num) {
        long x = num;
        while (x * x > num) {
            x = (x + num / x) >> 1;
        }
        return x * x == num;
    }

    public static void main(String[] args) {
        ValidPerfectSquare_367 v = new ValidPerfectSquare_367();
        System.out.println(v.isPerfectSquare(808201));
    }
}
