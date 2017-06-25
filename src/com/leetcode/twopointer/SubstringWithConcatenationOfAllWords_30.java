package com.leetcode.twopointer;

import java.util.*;

/**
 * Created by charles on 4/16/17.
 * You are given a string, s, and a list of words, words, that are all of the same length. Find all starting indices of substring(s) in s that is a concatenation of each word in words exactly once and without any intervening characters.

 For example, given:
 s: "barfoothefoobarman"
 words: ["foo", "bar"]

 You should return the indices: [0,9].
 (order does not matter).
 */
public class SubstringWithConcatenationOfAllWords_30 {
    /**
     * each word has same length. so total len = number of words * len of single word
     * first to add list of words into map, key is word, value is count of appearance
     * second to search each index as start of concatenated string
     */
    public List<Integer> findSubstring(String s, String[] words) {
        List<Integer> res = new ArrayList<>();

        Map<String, Integer> toFind = new HashMap<>();
        Map<String, Integer> found = new HashMap<>();
        // step one
        for (String word : words) {
            if (!toFind.containsKey(word)) {
                toFind.put(word, 1);
            } else {
                toFind.put(word, toFind.get(word) + 1);
            }
        }
        // step two
        int n = words[0].length();
        int m = words.length;
        int j = 0; // need to declare outside inner for loop, so as to compare with length of array.
        for (int i = 0; i <= s.length() - n * m; i++) {
            // init to clear previous map search result
            found.clear();
            // second pointer to search matching word
            for (j = 0; j < m; j++) {
                int k = i + j * n;
                String stub = s.substring(k, k + n);
                if (!toFind.containsKey(stub)) {
                    break;
                }
                if (!found.containsKey(stub)) {
                    found.put(stub, 1);
                } else {
                    found.put(stub, found.get(stub) + 1);
                }
                // compare appearance of stub in both maps
                if (found.get(stub) > toFind.get(stub)) {
                    break;
                }
            }
            // if find whole concatenated string in S
            if (j == m) {
                res.add(i);
            }
        }
        return res;
    }

    public List<Integer> findSubstringII(String s, String[] words) {
        List<Integer> res = new ArrayList<>();

        Set<Character> characterSet = buildSet(words);
        Map<String, Integer> countMap = buildCountMap(words);
        int m = words.length, n = words[0].length();
        int len = s.length();
        int window = m * n;
        String stub;
        for (int i = 0; i < len - window; i++) {
            if (!characterSet.contains(s.charAt(i))) {
                continue;
            }
            stub = s.substring(i, i + window);
            if (isConcatenated(stub, characterSet, countMap, n)) {
                res.add(i);
            }
        }
        return res;
    }
    private Set<Character> buildSet(String[] words) {
        Set<Character> set = new HashSet<>();
        for (String word : words) {
            if (word.length() == 0) {
                return set;
            }
            set.add(word.charAt(0));
        }
        return set;
    }
    private Map<String, Integer> buildCountMap(String[] words) {
        Map<String, Integer> map = new HashMap<>();
        for (String word : words) {
            map.put(word, map.getOrDefault(word, 0) + 1);
        }
        return map;
    }
    private boolean isConcatenated(String s, Set<Character> set, Map<String, Integer> map, int len) {
        String stub;
        Map<String, Integer> lookup = new HashMap<>();
        for (int i = 0; i < s.length(); i += len) {
            if (!set.contains(s.charAt(i))) {
                return false;
            }
            stub = s.substring(i, i + len);
            if (!map.containsKey(stub)) {
                return false;
            }
            if (lookup.containsKey(stub)) {
                if (lookup.get(stub) == map.get(stub)) {
                    return false;
                }
                lookup.put(stub, lookup.get(stub) + 1);
            } else {
                lookup.put(stub, 1);
            }
        }
        return true;
    }

    /** faster solution
     * sliding window template
     * https://discuss.leetcode.com/topic/71662/sliding-window-algorithm-template-to-solve-all-the-leetcode-substring-search-problem
     *
     * outer loop for i in (0 -> single word length)
     * is to determine start index
     * s = "barfoofoobarthefoobarman";
     words = new String[]{"bar","foo","the"};

     bar   foo   foo  bar  the  foo bar man
     arf   oof   oob  art  hef  oob .......
     rfo   ofo   oba  rth  efo  oba .......
     foo   foo   ... repeating first line
     * if we have 3 rows, and check word by word
     *
     * */
    public List<Integer> findSubstringIII(String s, String[] words) {
        List<Integer> res = new ArrayList<>();
        int len = s.length();
        int m = words.length, n = words[0].length();

        Map<String, Integer> map = buildCountMap(words);
        int window = n * m;
        int start = 0;
        Map<String, Integer> currMap = new HashMap<>();
        String stub;
        for (int i = 0; i < n; i++) {
            start = i;
            if (start + window > len) {
                return res;
            }
            slideWindow(s, start, n, len, m, map, res);
        }
        return res;
    }

    /** use left and right pointer to find slide window
     * right increment word length as interval
     * */
    private void slideWindow(String s, int start, int interval, int len, int target, Map<String, Integer> wordMap, List<Integer> res) {
        Map<String, Integer> map = new HashMap<>();
        String str, last;
        int count = 0, left = start;
        for (int right = start; right + interval <= len; right += interval) {
            str = s.substring(right, right + interval);
            if (wordMap.containsKey(str)) {
                // increment frequency
                map.put(str, map.getOrDefault(str, 0) + 1);
                // variable count is to record how many words found.
                if (map.get(str) <= wordMap.get(str)) {
                    count++;
                }
                // find redundant word, need to slide left side
                // until current str has same frequency in word map
                while (map.get(str) > wordMap.get(str)) {
                    last = s.substring(left, left + interval);
                    map.put(last, map.get(last) - 1);
                    left += interval;
                    if (map.get(last) < wordMap.get(last)) {
                        count--;
                    }
                }
                // found one result
                if (count == target) {
                    res.add(left);
                    // advance one word
                    last = s.substring(left, left + interval);
                    map.put(last, map.get(last) - 1);
                    left += interval;
                    count--;
                }
            } else {
                // current word is not valid in word array,
                // reset all variables
                map.clear();
                count = 0;
                left = right + interval;
            }
        }
    }
}
