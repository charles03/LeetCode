package com.leetcode.string;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by charles on 3/28/17.
 * Given two strings s and t, write a function to determine if t is an anagram of s.

 For example,
 s = "anagram", t = "nagaram", return true.
 s = "rat", t = "car", return false.

 Note:
 You may assume the string contains only lowercase alphabets.

 Follow up:
 What if the inputs contain unicode characters? How would you adapt your solution to such case?
 */
public class ValidAnagram_242 {
    /** if there is no unicode, only english letters */
    public boolean isAnagram(String s, String t) {
        int[] table = new int[26];
        if (s.length() != t.length()) {
            return false;
        }
        for (int i = 0; i < s.length(); i++) {
            table[s.charAt(i) - 'a']++;
            table[t.charAt(i) - 'a']--;
        }
        for (int num : table) {
            if (num != 0) {
                return false;
            }
        }
        return true;
    }

    public boolean isAnagramII(String s, String t) {
        Map<Character, Integer> map = new HashMap<>();
        if (s.length() != t.length()) {
            return false;
        }
        char sChar;
        char tChar;
        for (int i = 0; i < s.length(); i++) {
            sChar = s.charAt(i);
            tChar = t.charAt(i);
            if (map.containsKey(sChar)) {
                map.put(sChar, map.get(sChar) + 1);
            } else {
                map.put(sChar, 1);
            }
            if (map.containsKey(tChar)) {
                map.put(tChar, map.get(tChar) - 1);
            } else {
                map.put(tChar, -1);
            }
        }

        for (Character character : map.keySet()) {
            if (map.get(character) != 0) {
                return false;
            }
        }
        return true;
    }
}
