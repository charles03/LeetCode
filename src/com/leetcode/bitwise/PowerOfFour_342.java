package com.leetcode.bitwise;

/**
 * Created by charles on 4/4/17.
 * Given an integer (signed 32 bits), write a function to check whether it is a power of 4.

 Example:
 Given num = 16, return true. Given num = 5, return false.

 Follow up: Could you solve it without loops/recursion?

 */
public class PowerOfFour_342 {
    /** Regular solution */
    public boolean isPowerOfFour(int num) {
        if (num <= 0) {
            return false;
        }
        while (num % 4 == 0) {
            num /= 4;
        }
        if (num != 1) {
            return false;
        }
        return true;
    }

    /** bit manipulation
     * n & (n-1) == 0 is to get all 2 base
     * (num - 1) % 3 is to get all 4 base
     * 4^x-1=(2^x-1)(2^x+1). And 2^x mod 3 is not 0. So either 2^x-1 or 2^x +1 must be 0.
     * */
    public boolean isPowerOfFourII(int num) {
        return (num & (num - 1)) == 0 && (num-1) % 3 == 0;
    }

    public boolean isPowerOfFourIII(int num) {
        //0x55555555 is to get rid of those power of 2 but not power of 4
        //so that the single 1 bit always appears at the odd position
        return num > 0 && (num & (num - 1)) == 0 && (num & 0x55555555) != 0;
    }
}
