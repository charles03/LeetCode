package com.leetcode.dynamicprogram;

/**
 * Created by charles on 1/26/17.
 * Consider the string s to be the infinite wraparound string of "abcdefghijklmnopqrstuvwxyz", so s will look like this: "...zabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcd....".
 Now we have another string p. Your job is to find out how many unique non-empty substrings of p are present in s. In particular, your input is the string p and you need to output the number of different non-empty substrings of p in the string s.

 Note: p consists of only lowercase English letters and the size of p might be over 10000.

 Example 1:
 Input: "a"
 Output: 1

 Explanation: Only the substring "a" of string "a" is in the string s.
 Example 2:
 Input: "cac"
 Output: 2
 Explanation: There are two substrings "a", "c" of string "cac" in the string
 */
public class UniqueSubstringsInWrapString_467 {
    /**
     * State : dp[j] is maximum unique substring end with i-th letter
     * // 0-'a', 1-'b',... 25-'z'
     * // store longest contiguous substring ends at current position
     * function : if (p[i] - p[i-1] == 1 || p[i-1] - p[i] == 25) {}
     *              dp[i]++;
     *            else dp[i] = 1;
     * init : dp[0] = 1;
     * return sum(dp[0] + dp[1] + ... + dp[25])
     */
    public int findSubStringInWraproundString(String p) {
        if (p == null || p.length() == 0) {
            return 0;
        }
        int[] dp = new int[26]; // only need size 26 for alphabets
        dp[p.charAt(0) - 'a'] = 1;

        int curr = 0;
        int prev = 0;
        // to store longest contiguous sub string ends at current position
        int len = 1; // record len change end at i-th letter

        for (int i = 1; i < p.length(); i++) {
            curr = p.charAt(i) - 'a';
            prev = p.charAt(i -1) - 'a';

            if ((prev + 1) % 26 == curr) {
                len++;
            } else {
                len = 1;
            }
            dp[curr] = Math.max(dp[curr], len);
        }
        int sum = 0;
        for (int i : dp){
            sum += dp[i];
        }
        return sum;

    }
}
