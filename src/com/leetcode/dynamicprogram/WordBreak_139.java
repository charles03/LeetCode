package com.leetcode.dynamicprogram;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by charles on 1/25/17.
 * Given a non-empty string s and a dictionary wordDict containing a list of non-empty words, determine if s can be segmented into a space-separated sequence of one or more dictionary words. You may assume the dictionary does not contain duplicate words.

 For example, given
 s = "leetcode",
 dict = ["leet", "code"].

 Return true because "leetcode" can be segmented as "leet code".
 */
public class WordBreak_139 {
    public boolean wordBreak(String s, List<String> wordDict) {
        if (s == null || s.length() == 0) {
            return false;
        }
        if (wordDict == null || wordDict.size() == 0) {
            return false;
        }
        Set<String> dict = new HashSet<>();

        for (String word : wordDict) {
            dict.add(word);
        }
        return wordBreak(s, dict);
    }

    /**
     * State : dp[j] is with ending at pos j, true or false whether work can be broken by words in dict
     * function: for any pos j in (0, i)
     *      dp[i] = dp[j] && dict.contains(s.substring(j, i))
     * dp[0] = true;
     * return dp[s.length];
     */
    public boolean wordBreak(String s, Set<String> wordDict) {
        boolean[] dp = new boolean[s.length() + 1];
        dp[0] = true;

        for (int i = 1; i <= s.length(); i++) {
            for (int j = 0; j < i; j++) {
                if (dp[j] && wordDict.contains(s.substring(j, i))) {
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[s.length()];
    }

    /**
     * The diff is we get maxlength among whole word dict,
     * when looping for all possible solution to find wordDict contain substr
     * if diff(i,j) > max length, there is no need to continue
     */
    public boolean wordBreakJiuzhang(String s, Set<String> dict) {
        int maxLen = getMaxLength(dict);
        /**
         * should open len+1 memory, besides, this is segment question
         */
        boolean[] dp = new boolean[s.length() + 1];
        dp[0] = true;

        for (int i = 1; i <= s.length(); i++) {
            // j is last word length
            for (int j = 1; j<= maxLen && j <= i; j++) {
                if (!dp[i-j]) {
                    continue;
                }
                if (dict.contains(s.substring(i-j, i))) {
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[s.length()];
    }

    private int getMaxLength(Set<String> dict) {
        int maxLength = 0;
        for (String word : dict) {
            maxLength = Math.max(maxLength, word.length());
        }
        return maxLength;
    }
}
