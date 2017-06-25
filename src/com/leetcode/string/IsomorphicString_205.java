package com.leetcode.string;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by charles on 4/9/17.
 * Given two strings s and t, determine if they are isomorphic.

 Two strings are isomorphic if the characters in s can be replaced to get t.

 All occurrences of a character must be replaced with another character while preserving the order of characters. No two characters may map to the same character but a character may map to itself.

 For example,
 Given "egg", "add", return true.

 Given "foo", "bar", return false.

 Given "paper", "title", return true.
 */
public class IsomorphicString_205 {
    /**
     * be careful of sequence of characters
     * Thus, should have map <Char, Char>
     * find first mapping from char in s to char in t
     */
    public boolean isIsomorphic(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }
        int[] map = new int[128]; // mapping from char in s to char in t
        int[] count = new int[128];
        for (int i = 0; i < s.length(); i++) {
            int sVal = (int) s.charAt(i);
            int tVal = (int) t.charAt(i);

            if (map[sVal] == 0 && count[tVal] == 0) {
                // if conditioned, then this is valid mapping from Char in s to char in t
                // else either char in s or t is used
                map[sVal] = tVal;
                count[tVal] = 1;
            } else if (map[sVal] != tVal) {
                // if char in t is used
                return false;
            }
        }
        return true;
    }

    public boolean isIsomorphicII(String s, String t) {
        Map<Character, Character> map = new HashMap<>();
        Map<Character, Character> reverseMap = new HashMap<>();

        for (int i = 0; i < s.length(); i++) {
            char a = s.charAt(i);
            char b = t.charAt(i);
            if (map.containsKey(a)) {
                if (map.get(a).equals(b)) {
                    continue;
                } else {
                    return false;
                }
            } else {
                if (!reverseMap.containsKey(b)) {
                    map.put(a,b);
                    reverseMap.put(b,a);
                } else {
                    return false;
                }
            }
        }
        return true;
    }
}
