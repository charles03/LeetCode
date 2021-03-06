package com.leetcode.math;

/**
 * Created by charles on 4/12/17.
 * Reverse digits of an integer.

 Example1: x = 123, return 321
 Example2: x = -123, return -321

 click to show spoilers.

 Have you thought about this?
 Here are some good questions to ask before coding. Bonus points for you if you have already thought through this!

 If the integer's last digit is 0, what should the output be? ie, cases such as 10, 100.

 Did you notice that the reversed integer might overflow? Assume the input is a 32-bit integer, then the reverse of 1000000003 overflows. How should you handle such cases?

 For the purpose of this problem, assume that your function returns 0 when the reversed integer overflows.


 */
public class ReverseInteger_7 {
    public int reverse(int x) {
        long n = 0;
        int a = x;
        while (a >= 10 || a <= -10) {
            n += a % 10;
            n *= 10;
            a /= 10;
        }
        n += a % 10;
        if (n > Integer.MAX_VALUE || n < Integer.MIN_VALUE) {
            return 0;
        }
        return (int)n;
    }

    public int reverseII(int x) {
        long res = 0;
        while (x != 0) {
            res = res * 10 + x % 10;
            if (res > Integer.MAX_VALUE || res < Integer.MIN_VALUE) {
                return 0;
            }
            x /= 10;
        }
        return (int)res;
    }

    public static void main(String[] args) {
        ReverseInteger_7 r = new ReverseInteger_7();
        System.out.println(r.reverse(-123));
        System.out.println(r.reverse(123));
    }
}
