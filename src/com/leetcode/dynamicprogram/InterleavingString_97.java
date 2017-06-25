package com.leetcode.dynamicprogram;

/**
 * Created by charles on 6/11/17.
 * Given s1, s2, s3, find whether s3 is formed by the interleaving of s1 and s2.

 For example,
 Given:
 s1 = "aabcc",
 s2 = "dbbca",

 When s3 = "aadbbcbcac", return true.
 When s3 = "aadbbbaccc", return false.
 */
public class InterleavingString_97 {
    /**
     * state : dp[i][j],
     *      char sequence of [0,i] of s1 and [0,j] of s2 can form [0,i+j] of s3 or not
     * func : dp[i][j] = dp[i-1][j]  && s1[i-1] == s3[i+j-1]
     *                  || dp[i][j-1] && s2[j-1] == s3[i+j-1]
     * init : f[i][0] = s1[0..i-1] == s3[0..i-1]
     *        f[0][j] = s2[0..j-1] == s3[0..j-1]
     * res : dp[len(s1)][len(s2)]
     */
    public boolean isInterleave(String s1, String s2, String s3) {
        if (s1.length() + s2.length() != s3.length()) {
            return false;
        }
        int l1 = s1.length();
        int l2 = s2.length();
        int l3 = s3.length();
        // dp for char sequence always allocate memory size = length of string + 1
        boolean[][] dp = new boolean[l1+1][l2+1];
        dp[0][0] = true;

        // init dp[i][0]
        for (int i = 1; i <= l1; i++) {
            if (s3.charAt(i-1) == s1.charAt(i-1) && dp[i-1][0]) {
                dp[i][0] = true;
            }
        }
        // init dp[0][i]
        for (int j = 1; j <= l2; j++) {
            if (s3.charAt(j-1) == s2.charAt(j-1) && dp[0][j-1]) {
                dp[0][j] = true;
            }
        }
        // function dp[i][j] = dp[i-1,j] && islastcharmatched
        // or dp[i,j-1] && islastcharmatched
        char s3char;
        for (int i = 1; i <= l1; i++) {
            for (int j = 1; j <= l2; j++) {
                s3char = s3.charAt(i+j-1);
                if (s3char == s1.charAt(i-1) && dp[i-1][j]
                        || s3char == s2.charAt(j-1) && dp[i][j-1]) {
                    dp[i][j] = true;
                }
            }
        }
        return dp[l1][l2];
    }

    /**
     * 1-D array for space optimization
     * use 1-D array to store prefix strings processed so far
     * because update only depends on previous value [i-1][j] or [i][j-1]
     */
    public boolean isInterleavedII(String s1, String s2, String s3) {
        if (s1.length() > s2.length()) {
            return isInterleavedII(s2, s1, s3);
        }
        if (s3.length() != s1.length() + s2.length()) {
            return false;
        }
        // assume s2 length is longer than s1
        boolean[] dp = new boolean[s2.length() + 1];
        char s3Char;
        boolean isMatchAtS2 = false;
        boolean isMatchAtS1 = false;
        for (int i = 0; i <= s1.length(); i++) {
            for (int j = 0; j <= s2.length(); j++) {
                if (i == 0 && j == 0) {
                    dp[0] = true;
                    continue;
                }
                s3Char = s3.charAt(i+j-1);
                if (j != 0) {
                    isMatchAtS2 = dp[j-1] && s2.charAt(j-1) == s3Char;
                }
                if (i != 0) {
                    isMatchAtS1 = dp[j] && s1.charAt(i-1) == s3Char;
                }

                if (i == 0) {
                    // no s1, only s2
                    dp[j] = isMatchAtS2;
                } else if (j == 0) {
                    // no s2, only s1
                    dp[j] = isMatchAtS1;
                } else {
                    dp[j] = isMatchAtS2 || isMatchAtS1;
                }
            }
        }
        return dp[s2.length()];
    }
}
