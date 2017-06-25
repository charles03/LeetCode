package com.leetcode.dynamicprogram;

import java.util.*;

/**
 * Created by charles on 6/14/17.
 * Given a non-empty string s and a dictionary wordDict containing a list of non-empty words, add spaces in s to construct a sentence where each word is a valid dictionary word. You may assume the dictionary does not contain duplicate words.

 Return all such possible sentences.

 For example, given
 s = "catsanddog",
 dict = ["cat", "cats", "and", "sand", "dog"].

 A solution is ["cats and dog", "cat sand dog"].
 */
public class WordBreakII_140 {
    /**
     * Map<Int, List> memo help to store key of starting index in string
     * and value is possibility sentences consisted of words in dict
     */
    public List<String> wordBreak(String s, List<String> wordDict) {
        int maxLen = getMaxLen(wordDict);
        Set<String> dicts = new HashSet<>(wordDict);
        Map<Integer, List<String>> memo = new HashMap<>();
        return memorizedDfs(s, dicts, 0, maxLen, memo);
    }

    /**
     * memorizedDfs return all possible sentences can be split by words in dict
     * and starting index is start in the raw string till end of string
     */
    private List<String> memorizedDfs(String s, Set<String> dict, int start,
                                      int maxLen, Map<Integer, List<String>> memo) {
        List<String> res = new ArrayList<>();
        if (start == s.length()) {
            res.add("");
            return res;
        }
        List<String> dfsRes = null;
        for (int idx = start+1; idx <= maxLen + start && idx <= s.length(); idx++) {
            String word = s.substring(start, idx);
            if (!dict.contains(word)) {
                continue;
            }
            if (memo.containsKey(idx)) {
                dfsRes = memo.get(idx);
            } else {
                dfsRes = memorizedDfs(s, dict, idx, maxLen, memo);
            }
            System.out.print("dfs ");
            System.out.println(dfsRes);
            // append word into each sentence found by memorized dfs process
            for (String sentence : dfsRes) {
                if (!sentence.equals("")) {
                    res.add(word + " " + sentence);
                } else {
                    res.add(word);
                }
            }
        }
        // add into memo
        memo.put(start, res);
        return res;
    }


    private int getMaxLen(List<String> dict) {
        int len = -1;
        for (String word : dict) {
            len = Math.max(len, word.length());
        }
        return len;
    }

    public static void main(String[] args) {
        WordBreakII_140 w = new WordBreakII_140();
        List<String> l1 = Arrays.asList("cat","cats","and","sand","dog");
        System.out.println(w.wordBreak("catsanddog", l1));
    }

    /**
     * combined solution in {@link WordBreak_139}
     * and use word len range (min, max) in dictionary to improve performance.
     */
    public List<String> wordBreakII(String s, List<String> wordDict) {
        List<String> res = new ArrayList<>();
        if (wordDict == null || wordDict.size() == 0) {
            return res;
        }
        Set<String> dict = new HashSet<>(wordDict);
        int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
        for (String word : wordDict) {
            min = Math.min(min, word.length());
            max = Math.max(max, word.length());
        }
        if (!canBeSegment(s, dict, min, max)) {
            return res;
        }
        // use dfs to find all possible cases;
        backtrack(res, s, "", dict, min, max);
        return res;
    }
    /** this method is same as in wordbreak 1, use dp */
    private boolean canBeSegment(String s, Set<String> dict, int min, int max) {
        boolean[] dp = new boolean[s.length() + 1];
        dp[0] = true;
        for (int i = 1; i <= s.length(); i++) {
            for (int len = min; len <= max && i - len >= 0; len++) {
                if (!dp[i-len]) {
                    continue;
                }
                dp[i] = dict.contains(s.substring(i - len, i));
                if (dp[i]) {
                    break; // already find one valid case to segment substr(0, i);
                }
            }
        }
        return dp[s.length()];
    }
    private void backtrack(List<String> res, String str, String sentence, Set<String> dict, int min, int max) {
        if (str.length() == 0) {
            res.add(sentence);
            return;
        }
        for (int len = min; len <= max && len <= str.length(); len++) {
            String word = str.substring(0, len);
            if (!dict.contains(word)) {
                continue;
            }
            String newSentence = null;
            if (sentence.equals("")) {
                newSentence = word;
            } else {
                newSentence = sentence + " " + word;
            }
            backtrack(res, str.substring(len), newSentence, dict, min, max);
        }
    }
}
