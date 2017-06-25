package com.leetcode.string;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by charles on 4/9/17.
 * Given a pattern and a string str, find if str follows the same pattern.

 Here follow means a full match, such that there is a bijection between a letter in pattern and a non-empty word in str.

 Examples:
 pattern = "abba", str = "dog cat cat dog" should return true.
 pattern = "abba", str = "dog cat cat fish" should return false.
 pattern = "aaaa", str = "dog cat cat dog" should return false.
 pattern = "abba", str = "dog dog dog dog" should return false.
 */
public class WordPattern_290 {
    public boolean wordPattern(String pattern, String str) {
        Map<String, Character> map = new HashMap<>();
        String[] words = str.split(" ");
        if (words.length != pattern.length()) {
            return false;
        }
        int len = words.length;
        char[] chars = new char[len];
        int count = 0;
        for (int i = 0; i < len; i++) {
            if (!map.containsKey(words[i])) {
                map.put(words[i], (char)('a' + count));
                chars[i] = map.get(words[i]);
                count++;
            } else {
                chars[i] = map.get(words[i]);
            }
        }
        System.out.println(String.valueOf(chars));
        return pattern.equals(String.valueOf(chars));
    }

    /** basic thought is still to find char-string mapping
     * at the same time need to make sure string-char is one-to-one
     */
    public boolean wordPatternII(String pattern, String str) {
        String[] words = str.split(" ");
        if (pattern.length() != words.length) {
            return false;
        }
        Map<Character, String> map = new HashMap<>();
        Map<String, Character> reverseMap = new HashMap<>();
        int len = pattern.length();
        char c;
        for (int i = 0; i < len; i++) {
            c = pattern.charAt(i);
            if (map.containsKey(c)) {
                if (!map.get(c).equals(words[i])) {
                    return false;
                }
            } else {
                // when new char doesn't have mapping word
                // whereas word already mapped to other char
                if (reverseMap.containsKey(words[i])) {
                    return false;
                } else {
                    map.put(c, words[i]);
                    reverseMap.put(words[i], c);
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        WordPattern_290 w = new WordPattern_290();
        String p1 = "abba";
        String s1 = "dog cat cat dog";
        w.wordPattern(p1, s1);

        String p2 = "e";
        String s2 = "eukera";
        w.wordPattern(p2, s2);
    }
}
