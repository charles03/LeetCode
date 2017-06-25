package com.leetcode.math;

/**
 * Created by charles on 4/1/17.
 * Write a program to check whether a given number is an ugly number.

 Ugly numbers are positive numbers whose prime factors only include 2, 3, 5. For example, 6, 8 are ugly while 14 is not ugly since it includes another prime factor 7.

 Note that 1 is typically treated as an ugly number.
 */
public class UglyNumber_263 {
    /**
     * Thought:
     * define divisor array [2,3,5]
     * two level while loops
     * inner while (num % i == 0) {
     *     num /= i;
     * }
     * if (num can be divided by divisor[i]) {
     *     need to check quotient (divident / divisor)
     * }
     * need to if final quotient == 1, then
     * original dividend only contain divisors, are ugly number
     *
     * Given algorithm
     * Time complexity: lg(n)
     * for 3 loops
     * each loops has lg(n)/ log3(n) / log5(n)
     * max is lg(n);
     */
    public boolean isUgly(int num) {
        if (num <= 0) {
            return false;
        }
        int[] divisor = {2,3,5};
        for (int d : divisor) {
            while (num % d == 0) {
                num /= d;
            }
        }
        return num == 1;
    }

}
