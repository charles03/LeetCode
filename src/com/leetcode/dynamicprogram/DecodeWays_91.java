package com.leetcode.dynamicprogram;

/**
 * Created by charles on 1/28/17.
 * A message containing letters from A-Z is being encoded to numbers using the following mapping:
 'A' -> 1
 'B' -> 2
 ...
 'Z' -> 26
 Given an encoded message containing digits, determine the total number of ways to decode it.
 For example,
 Given encoded message "12", it could be decoded as "AB" (1 2) or "L" (12).
 The number of ways decoding "12" is 2.
 */
public class DecodeWays_91 {
    /**
     * State: dp[i] is total number of ways substring [0, i]
     * Function : dp[i]
     *      if (digit[i-1] != '0') dp[i] = dp[i-1];
     *      if (10 <= digit[i-1][i] <= 26) dp[i] += dp[i-2]
     * init dp[0] = 1, dp[1] = digit[0] != '0' > 1 : 0
     * return dp[n]
     */
    public int numDecodings(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        char[] digits = s.toCharArray();
        int len = digits.length;
        int[] dp = new int[len + 1];

        // init;
        dp[0] = 1;
        dp[1] = digits[0] == '0' ? 1 : 0;

        int twoDigits = 0; // used inner loop
        for (int i = 2; i <= len; i++) {
            if (digits[i - 1] != '0') {
                dp[i] = dp[i - 1];
            }
            twoDigits = (digits[i-2] - '0') * 10 + (digits[i-1] - '0');
            if (twoDigits >= 10 && twoDigits <= 26) {
                dp[i] += dp[i - 2];
            }
        }
        return dp[len];
    }

    public static void main(String[] args) {
        DecodeWays_91 d = new DecodeWays_91();
        System.out.println(d.numDecodings("1034405"));
    }
}
