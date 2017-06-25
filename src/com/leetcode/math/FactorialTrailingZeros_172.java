package com.leetcode.math;

/**
 * Created by charles on 4/6/17.
 * Given an integer n, return the number of trailing zeroes in n!.

 Note: Your solution should be in logarithmic time complexity.
 */
public class FactorialTrailingZeros_172 {
    /**
     * 0 is from tail 5 * 2/ 2 of multiple
     * there are a bunch more 2's (each even number will contribute at least one 2), we only need to count the number of 5's.
     * simply the multiples of 5, like 5, 10, 15, 20, 25, 35, .... So is the result simply n / 5? Well, not that easy.
     * Notice that some numbers may contribute more than one 5, like 25 = 5 * 5
     * which is n / 5 + n / 25 + n / 125 + .... The idea behind this expression is: all the multiples of 5 will contribute one 5,
     * the multiples of 25 will contribute one more 5 and the multiples of 125 will contribute another one more 5... and so on
     */
    public int trailingZeros(int n) {
        int count = 0;
        while (n > 0) {
            n /= 5;
            count += n;
        }
        return count;
    }
}
