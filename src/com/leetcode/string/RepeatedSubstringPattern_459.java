package com.leetcode.string;

/**
 * Created by charles on 4/3/17.
 * Given a non-empty string check if it can be constructed by taking a substring of it and appending multiple copies of the substring together. You may assume the given string consists of lowercase English letters only and its length will not exceed 10000.

 Example 1:
 Input: "abab"

 Output: True

 Explanation: It's the substring "ab" twice.
 Example 2:
 Input: "aba"

 Output: False
 Example 3:
 Input: "abcabcabcabc"

 Output: True

 Explanation: It's the substring "abc" four times. (And the substring "abcabc" twice.)

 */
public class RepeatedSubstringPattern_459 {
    /**
     * Thought:
     * try every possible substring, suppose there are ith number of repeated substring
     * first, total length of whole string should be divided by ith completely, no remaining
     * As long as find one spliting solution, which all substrings are identical
     * then return true,
     * if still not find, return false;
     */
    public boolean repeatedSubstringPattern(String str) {
        int len = str.length();
        if (len < 2) {
            return false;
        }
        for (int k = 2; k <= len; k++) {
            // k is times of repeating
            if (len % k != 0) {
                continue;
            }
            if (isRepeating(str, k)) {
                return true;
            }
        }
        return false;
    }
    private boolean isRepeating(String str, int repeat) {
        int len = str.length();
        int subLen = len / repeat;
        String candidate = str.substring(0, subLen);
        for (int i = 0; i < len; i++) {
            if (str.charAt(i) != candidate.charAt(i % subLen)) {
                return false;
            }
        }
        return true;
    }

    /** optimized by strict top bound to sqrt of given length of string
     * need to check either repeating ith or repeating (len / i)th*/
    public boolean repeatedSubstringPatternIA(String str) {
        int len = str.length();
        if (len < 2) {
            return false;
        }
        int range = (int)Math.sqrt(len);
        for (int i = 2; i <= range; i++) {
            if (len % i != 0) {
                continue;
            }
            if (isRepeating(str, i)) {
                return true;
            }
            if (isRepeating(str, len / i)) {
                return true;
            }
        }
        // at last, check possibility of each char
        if (isRepeating(str, len)) {
            return true;
        }
        return false;
    }

    /** Thought: use Regex solution */
    public boolean repeatedSubstringPatternII(String str) {
        return str.matches("^([a-z]+)\\1+$");
    }
    /** simplified version*/
    public boolean repeatedSubstringPatternIII(String str) {
        return str.matches("(.+)\\1+");
    }

    /**
     * KMP solution:
     * TODO
     * Reference http://jakeboxer.com/blog/2009/12/13/the-knuth-morris-pratt-algorithm-in-my-own-words/
     */
    public boolean repeatedSubstringPatternIV(String str) {
        int[] prefix = getKmpPrefixArray(str);
        int n = str.length();
        int len = prefix[n - 1];
        return (len > 0 && n % (n - len) == 0);
    }
    private int[] getKmpPrefixArray(String str) {
        int len = str.length();
        int[] res = new int[len];
        char[] chars = str.toCharArray();
        int i = 0, j = 1;
        res[0] = 0;
        while (i < len && j < len) {
            if(chars[i] == chars[j]) {
                res[j] = i + 1;
                i++;
                j++;
            } else {
                if (i == 0) {
                    res[j] = 0;
                    j++;
                } else {
                    // start from beginning of current matching pattern
                    i = res[i-1];
                }
            }
        }
        return res;
    }
}
