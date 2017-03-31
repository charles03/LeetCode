package com.leetcode.dynamicprogram;

import java.util.Arrays;

/**
 * Created by charles on 1/28/17.
 * Given a positive integer n, find the least number of perfect square numbers (for example, 1, 4, 9, 16, ...) which sum to n.

 For example, given n = 12, return 3 because 12 = 4 + 4 + 4; given n = 13, return 2 because 13 = 4 + 9.
 */
public class PerfectSquares_279 {
    /**
     * State : dp[i] is for least number of perfect square numbers for sum i
     * function: dp[i + j*j] = min(dp[i] + 1, dp[i + j*j]
     * init : dp[j*j] = 1; else dp[] = max_value
     * return dp[n];
     */
    public int numSquares(int n) {
        int[] dp = new int[n + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        // init
        for (int i = 0; i * i <= n; i++) {
            dp[i*i] = 1;
        }
        // dp
        for (int i = 0; i <= n; i++) {
            for (int j = 0; i + j*j <= n; j++) {
                dp[i + j*j] = Math.min(dp[i] + 1, dp[i + j*j]);
            }
        }
        return dp[n];
    }

    /**
     * Version 2 : Math,
     * Pythagoras’ theorem a^2 + b^2 = c^2
     * And
     * Based on Lagrange's Four Square theorem, there
     * are only 4 possible results: 1, 2, 3, 4.
     * https://en.wikipedia.org/wiki/Lagrange's_four-square_theorem
     *
     */
    public int numSquares_v2(int n) {
        // if n is perfect square
        if (is_square(n)) {
            return 1;
        }

        // The result is 4 if and only if n can be written in the
        // form of 4^k*(8*m + 7). Please refer to
        // Legendre's three-square theorem.
        while ((n & 3) == 0) { // n % 4 == 0
            n >>= 2; // n /= 4
        }
        if ((n & 7) == 7) { // n % 8 == 7
            return 4;
        }
        // check whether 2 is result
        int sqrt_n = (int)(Math.sqrt(n));
        for (int i = 1; i <= sqrt_n; i++) {
            if (is_square(n - i * i)) {
                return 2;
            }
        }
        // else cases
        return 3;
    }

    private boolean is_square(int n) {
        int sqrt_n = (int)(Math.sqrt(n));
        return (sqrt_n * sqrt_n == n);
    }
}
