package com.leetcode.dynamicprogram;

/**
 * Created by charles on 5/14/17.
 * Given two words word1 and word2, find the minimum number of steps required to make word1 and word2 the same, where in each step you can delete one character in either string.

 Example 1:
 Input: "sea", "eat"
 Output: 2
 Explanation: You need one step to make "sea" to "ea" and another step to make "eat" to "ea".

 */
public class DeleteOperationForTwoStrings_583 {
    /**
     * thought:
     * DP solution with LCS:
     * State : dp[i][j[ is longest common subsequence among s1 and s2 consider length
     * upto (i-1) index and (j-1) index;
     *
     * Function: dp[i][j]
     *      if (s1[i] == s2[j]) {
     *          dp[i][j] = 1 + dp[i-1][j-1]
     *      } else {
     *          dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1])
     *      }
     *
     * init, start from 0,
     * res : len(s1) + len(s2) - 2 * dp[s1][s2]
     */
    public int minDistance(String s1, String s2) {
        int len1 = s1.length();
        int len2 = s2.length();
        int[][] dp = new int[len1 + 1][len2 + 1];
        for (int i = 0; i <= len1; i++) {
            for (int j = 0; j <= len2; j++) {
                if (i == 0 || j == 0) {
                    continue;
                }
                if (s1.charAt(i-1) == s2.charAt(j-1)) {
                    dp[i][j] = 1 + dp[i-1][j-1];
                } else {
                    dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);
                }
            }
        }
        return len1 + len2 - 2 * dp[len1][len2];
    }

    /**
     * DP solution without LCS and space optimization
     * state: dp[i] refer to numbers of deletions that need to be made in order to equalize string s1 and s2.
     *      if length of string s1 upto (i-1) index
     *      and string s2 upto last to current index of s2;
     * function : if (s1[i-1] == s2[j-1]) {
     *     tmp[j] = dp[j-1];
     * } else {
     *     tmp[j] = 1 + Math.min(dp[j], tmp[j-1])
     * }
     */
    public int minDistanceII(String s1, String s2) {
        int len1 = s1.length();
        int len2 = s2.length();
        int[] dp = new int[len2 + 1];
        for (int i = 0; i <= len1; i++) {
            int[] tmp = new int[len2 + 1];
            for (int j = 0; j <= len2; j++) {
                if (i == 0 || j == 0) {
                    tmp[j] = i + j;
                } else if (s1.charAt(i) == s2.charAt(j)) {
                    tmp[j] = dp[j-1];
                } else {
                    // tmp[j-1] is dp[i][j-1], min deletions of s1(0, i-1) equal to s2(0, j-2)
                    // dp[j] is dp[i-1][j]
                    tmp[j] = 1 + Math.min(tmp[j-1], dp[j]);
                }
            }
            // copy whole tmp array which has been filled
            // to reflect new row entry
            dp = tmp;
        }
        return dp[len2];
    }
}
