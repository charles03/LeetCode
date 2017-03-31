package com.leetcode.array;

/**
 * Created by charles on 2/20/17.
 * Given an integer, write a function to determine if it is a power of three.

 Follow up:
 Could you do it without using any loop / recursion?
 */
public class PowerOfThree_326 {
    /**
     * Time complexity : O(log_b(n))O(log​b(n)). In our case that is O(log_3n)O(log3n). The number of divisions is given by that logarithm.
     Space complexity : O(1)O(1). We are not using any additional memory.
     */
    public boolean isPowerOfThree(int n) {
        if (n < 1) {
            return false;
        }
        while (n % 3 == 0) {
            n /= 3;
        }
        return n == 1;
    }

    /**
     * 3 ^ 19 is largest power of three less than Integer.MAX_VALUE (2147483647)
     */
    public boolean isPowerOfThreeII(int n) {
        return n > 0 && 1162261467 % n == 0;
    }
}
