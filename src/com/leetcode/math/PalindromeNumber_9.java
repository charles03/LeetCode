package com.leetcode.math;

/**
 * Created by charles on 4/7/17.
 * Determine whether an integer is a palindrome. Do this without extra space.

 click to show spoilers.

 Some hints:
 Could negative integers be palindromes? (ie, -1)

 If you are thinking of converting the integer to string, note the restriction of using extra space.

 You could also try reversing an integer. However, if you have solved the problem "Reverse Integer", you know that the reversed integer might overflow. How would you handle such case?

 There is a more generic way of solving this problem.
 */
public class PalindromeNumber_9 {
    /**
     * For all negatives, return false
     *
     */
    public boolean isPalindrome(int x) {
        if (x < 0) {
            return false;
        }
        int reverse = 0;
        int y = x;
        while (y != 0) {
            reverse = reverse * 10 + y % 10;
            y /= 10;
        }
        return x == reverse;
    }
}
