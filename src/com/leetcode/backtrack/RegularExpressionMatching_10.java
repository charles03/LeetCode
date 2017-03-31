package com.leetcode.backtrack;

/**
 * Created by charles on 1/19/17.
 * Implement regular expression matching with support for '.' and '*'.

 '.' Matches any single character.
 '*' Matches zero or more of the preceding element.

 The matching should cover the entire input string (not partial).

 The function prototype should be:
 bool isMatch(const char *s, const char *p)

 Some examples:
 isMatch("aa","a") → false
 isMatch("aa","aa") → true
 isMatch("aaa","aa") → false
 isMatch("aa", "a*") → true
 isMatch("aa", ".*") → true
 isMatch("ab", ".*") → true
 isMatch("aab", "c*a*b") → true
 */
public class RegularExpressionMatching_10 {
    /**
     * dp[i][j] is current index i as end of String S
     * and current index j as end of String P
     * whether is matching or not.
     *
     * 1. if p[j] == s[i] : dp[i][j] = dp[i-1][j-1];
     * 2. if p[j] == '.' : dp[i][j] = dp[i-1][j-1];
     * 3. if p[j] == '#' :
     *      if p[j-1] != s[i] dp[i][j] = dp[i][j-2];
     *      if p[i-1] == s[i-1] || p[i-1] == '.'
     *          dp[i][j] = dp[i-1][j] // multiple
     *          dp[i][j] = dp[i][j-1] // single
     *          dp[i][j] = dp[i][j-2] // empty
     */
    public boolean isMatchByDP(String s, String p) {

        if (s == null || p == null) {
            return false;
        }
        char[] sc = s.toCharArray();
        char[] pc = p.toCharArray();

        // as for String DP question need to allocate 1 more space
        boolean[][] dp = new boolean[sc.length + 1][pc.length + 1];
        // initialization
        dp[0][0] = true;
        for (int i = 0; i < pc.length; i++) {
            if (pc[i] == '*' && dp[0][i - 1]) {
                dp[0][i + 1] = true;
            }
        }

        for (int i = 0; i < sc.length; i++) {
            for (int j = 0; j < pc.length; j++) {
                if (pc[j] == '.') {
                    dp[i + 1][j + 1] = dp[i][j];
                }
                if (pc[j] == sc[i]) {
                    dp[i + 1][j + 1] = dp[i][j];
                }
                if (pc[j] == '*') {
                    if (pc[j - 1] != sc[i] && pc[j - 1] != '.') {
                        dp[i + 1][j + 1] = dp[i + 1][j - 1];
                    } else {
                        dp[i + 1][j + 1] = (dp[i + 1][j] || dp[i][j + 1] || dp[i + 1][j - 1]);
                    }
                }
            }
        }
        return dp[sc.length][pc.length];

    }

    public boolean isMatch(String s, String p) {
        if (s == null || p == null) {
            return false;
        }
        char[] sc = s.toCharArray();
        char[] pc = p.toCharArray();
        return helper(0,0,sc,pc);
    }

    private boolean helper(int sp, int pp, char[] sc, char[] pc) {
        // edge case
        if (sp == sc.length || pp == pc.length) {
            if (sp == sc.length && pp == pc.length) {
                return true;
            }
            // following is to check 'aaaa', 'aaa*a**'
            if (sp == sc.length && pp < pc.length - 1 && pc[pp + 1] == '*') {
                return helper(sp, pp+2, sc, pc);
            }
            return false;
        }

        // not match at current position, if no '*' then match failed
        // otherwise we can skip current * in regex and continue to next match

        if (sc[sp] != pc[pp] && pc[pp] != '.') {
            // no '*' match failed
            if (pp == pc.length - 1 || pc[pp + 1] != '*') {
                return false;
            }
            // skip '*'
            return helper(sp, pp + 2, sc, pc);
        }

        // match at current position, if not '*' then continue to match next position both
        // otherwise try to match one more chars and provide states for backtrack
        if (pp == pc.length - 1 || pc[pp + 1] != '*') {
            return helper(sp + 1, pp + 1, sc, pc);
        }

        return helper(sp + 1, pp, sc, pc) || helper(sp, pp + 2, sc, pc);
    }
}
