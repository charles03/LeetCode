package com.leetcode.bitwise;

/**
 * Created by charles on 3/25/17.
 * Given a non-negative integer num, repeatedly add all its digits until the result has only one digit.

 For example:

 Given num = 38, the process is like: 3 + 8 = 11, 1 + 1 = 2. Since 2 has only one digit, return it.

 Follow up:
 Could you do it without any loop/recursion in O(1) runtime?
 */
public class AddDigits_258 {
    /**
     * Naive solution: while loop, follow process until %10 = 0
     */
    public int addDigits(int num) {
        int res = num;
        while (res >= 10) {
            int sum = 0;
            int digits = res;
            while (digits != 0) {
                sum += digits % 10;
                digits /= 10;
            }
            res = sum;
        }
        return res;
    }

    /**
     * Digit root problem, congruence formular
     * https://en.wikipedia.org/wiki/Digital_root#Congruence_formula
     * For base b (decimal case b = 10), the digit root of an integer is:

     dr(n) = 0 if n == 0
     dr(n) = (b-1) if n != 0 and n % (b-1) == 0
     dr(n) = n mod (b-1) if n % (b-1) != 0

     Note here, when n = 0, since (n - 1) % 9 = -1, the return value is zero (correct).
     From the formula, we can find that the result of this problem is immanently periodic, with period (b-1).
     Output sequence for decimals (b = 10):

     ~input: 0 1 2 3 4 ...
     output: 0 1 2 3 4 5 6 7 8 9 1 2 3 4 5 6 7 8 9 1 2 3 ....

     Henceforth, we can write the following code, whose time and space complexities are both O(1).
     */
    public int addDigitsII(int num) {
        return 1 + (num - 1) % 9;
    }

    public static void main(String[] args) {
        AddDigits_258 a = new AddDigits_258();
        System.out.println(a.addDigitsII(38));
    }
}
