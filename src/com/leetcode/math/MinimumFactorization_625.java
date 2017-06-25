package com.leetcode.math;

/**
 * Created by charles on 6/25/17.
 * Given a positive integer a, find the smallest positive integer b whose multiplication of each digit equals to a.

 If there is no answer or the answer is not fit in 32-bit signed integer, then return 0.

 Example 1
 Input:

 48
 Output:
 68
 Example 2
 Input:

 15
 Output:
 35

 */
public class MinimumFactorization_625 {
    /** Thought
     * factor given input number, only use digit 9 to 2,
     * start from large digit, so that result digit will be always min
     * and then use min to record current minimum assembly number
     */
    public int smallestFactorization(int a) {
        if (a < 2) {
            return a;
        }
        long res = 0, base = 1;
        for (int i = 9; i >= 2; i--) {
            while (a % i == 0) { // small devisor will be in high pos
                a /= i;
                res = base * i + res;
                base *= 10;
            }
        }
        if (a < 2 && res <= Integer.MAX_VALUE) {
            return (int)res;
        }
        return 0;
    }
}
