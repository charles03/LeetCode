package com.leetcode.dynamicprogram;

/**
 * Created by charles on 2/1/17.
 * Given a string s and a string t, check if s is subsequence of t.
 You may assume that there is only lower case English letters in both s and t. t is potentially a very long (length ~= 500,000) string, and s is a short string (<=100).
 A subsequence of a string is a new string which is formed from the original string by deleting some (can be none) of the characters without disturbing the relative positions of the remaining characters. (ie, "ace" is a subsequence of "abcde" while "aec" is not).

 Example 1:
 s = "abc", t = "ahbgdc"
 Return true.
 Example 2:
 s = "axc", t = "ahbgdc"
 Return false.
 Follow up:
 If there are lots of incoming S, say S1, S2, ... , Sk where k >= 1B, and you want to check one by one to see if T has its subsequence. In this scenario, how would you change your code?
 */
public class IsSubsequence_ {
    /**
     * DP solution
     * State dp[i] is length of sub of s which found from sub t [0, i)
     * Funtion if s[dp[i - 1]] == t[i] : dp[i] = dp[i - 1] + 1;
     *      else dp[i] = dp[i - 1]
     * init dp[0] = 1
     * answer dp[tLen]
     */
    public boolean isSubsequence(String s, String t) {
        if (s == null || t == null) {
            return false;
        }
        int sLen = s.length();
        int tLen = t.length();
        if ((sLen == 0 && tLen != 0 )|| (sLen == 0 && tLen == 0)) {
            return true;
        } else if (sLen == 0 || tLen == 0) {
            return false;
        }
        int[] dp = new int[tLen + 1];
        char[] ss = s.toCharArray();
        char[] tt = t.toCharArray();
        if (ss[0] == tt[0]) {
            dp[0] = 1;
        }
        for (int i = 1; i <= tLen; i++) {
            if (ss[dp[i]] == tt[i]) {
                dp[i] = dp[i - 1] + 1;
                if (dp[i] == sLen) {
                    dp[tLen] = sLen;
                    break;
                }
            } else {
                dp[i] = dp[i - 1];
            }
        }
        return dp[tLen] == sLen ? true : false;
    }
    
    public static void main(String[] args) {
        IsSubsequence_
    }
}
