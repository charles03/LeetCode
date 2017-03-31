package com.leetcode.dynamicprogram;

/**
 * Created by charles on 1/25/17.
 * In the computer world, use restricted resource you have to generate maximum benefit is what we always want to pursue.
 For now, suppose you are a dominator of m 0s and n 1s respectively. On the other hand, there is an array with strings consisting of only 0s and 1s.
 Now your task is to find the maximum number of strings that you can form with given m 0s and n 1s. Each 0 and 1 can be used at most once.

 Note:
 The given numbers of 0s and 1s will both not exceed 100
 The size of given string array won't exceed 600.
 Example 1:
 Input: Array = {"10", "0001", "111001", "1", "0"}, m = 5, n = 3
 Output: 4

 Explanation: This are totally 4 strings can be formed by the using of 5 0s and 3 1s, which are “10,”0001”,”1”,”0”
 Example 2:
 Input: Array = {"10", "0", "1"}, m = 1, n = 1
 Output: 2

 Explanation: You could form "10", but then you'd have nothing left. Better form "0" and "1".
 */
public class OnesAndZeros_474 {
    /**
     * State: dp[i][j] is max count when using i numbers of 0 and j number 1
     * function: dp[i][j] = Math.max(1 + dp[i-count(0)][j - count(1)], dp[i][j])
     *      either choose current string or not,
     *      if choose current string, count numbers of 0 and 1,
     * init : dp[0][0] = 0;
     */

    public int findMaxForm(String[] strs, int m, int n) {
        if (strs == null || strs.length == 0) {
            return 0;
        }
        int[][] dp = new int[m + 1][n + 1];
        int[] digitCount = new int[2];
        for (String str : strs) {
            digitCount = countZeroAndOne(str);
            for (int i = m; i >= digitCount[0]; i--) {
                for (int j = n; j >= digitCount[1]; j--) {
                    dp[i][j] = Math.max(dp[i][j], 1 + dp[i-digitCount[0]][j-digitCount[1]]);
                }
            }
        }
        return dp[m][n];
    }

    private int[] countZeroAndOne(String s) {
        int[] res = new int[2];
        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == '0') {
                res[0]++;
            } else {
                res[1]++;
            }
        }
        return res;
    }

}
