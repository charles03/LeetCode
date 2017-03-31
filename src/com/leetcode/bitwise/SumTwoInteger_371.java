package com.leetcode.bitwise;

/**
 * Created by charles on 3/25/17.
 * Calculate the sum of two integers a and b, but you are not allowed to use the operator + and -.

 Example:
 Given a = 1 and b = 2, return 3.
 */
public class SumTwoInteger_371 {
    /**
     * High level thought, use bit manipulation instead of operators
     *     10011
     *  +  10110
     *  ----------
     *    101001
     * if don't consider carry
     * sum of a,b -> use ^ operator to get base
     *
     *      10011 (a)
     *   ^  10110
     *  ----------
     *      00101 (a) new
     *
     *      10011
     *   &  10110
     *   ---------
     *      10010
     *   <<------- 1 to get carry,
     *     100100 (b) new
     *
     */

    public int getSum(int a, int b) {
        int c = 0;
        while (b != 0) {
            c = a;
            a ^= b;
            /** below two steps to get carry */
            b &= c;
            b <<= 1;
        }
        return a;
    }


}
