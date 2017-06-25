package com.leetcode.dynamicprogram;

/**
 * Created by charles on 6/10/17.
 * Given a string s, find the longest palindromic subsequence's length in s. You may assume that the maximum length of s is 1000.

 Example 1:
 Input:

 "bbbab"
 Output:
 4
 One possible longest palindromic subsequence is "bbbb".
 Example 2:
 Input:

 "cbbd"
 Output:
 2
 One possible longest palindromic subsequence is "bb".
 */
public class LongestPalindromicSubsequence_516 {
    /**
     * Range DP solution;
     * state: dp[i][j] is max len of palindrome subsequence start at i, ending at j
     *        how to get range (i,j)
     *        given start i, to calculate j, j = i + dist
     *        dist is from 1 to n-1, i is from 0 to len-dist
     *
     * func : if (s.char(i) == s.char(j)) {
     *     dp[i][j] = dp[i+1][j] + 2;
     * } else {
     *     dp[i][j] = max(dp[i+1][j], dp[i][j-1])
     * }
     * init : dp[i][i] = 1; // for each single char, in diagonal line
     * return dp[0][len-1];
     */

    public int longestPalindromeSubseq(String s) {
        int len = s.length();
        if (len == 0) {
            return 0;
        }
        int[][] dp = new int[len][len];
        // init, diagonal line in dp matrix, means max len of subseq of each char in string is 1
        for (int i = 0; i < len; i++) {
            dp[i][i] = 1;
        }
        int j = 0;
        for (int d = 1; d < len-1; d++) {
            for (int i = 0; i < len - d; i++) {
                j = i + d;
                if (s.charAt(i) == s.charAt(j)) {
                    dp[i][j] = dp[i+1][j-1] + 2;
                } else {
                    dp[i][j] = Math.max(dp[i][j-1], dp[i+1][j]);
                }
            }
        }
        return dp[0][len-1];
    }
}
