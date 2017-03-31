package com.leetcode.string;

/**
 * Created by charles on 3/16/17.
 * Given an arbitrary ransom note string and another string containing letters from all the magazines, write a function that will return true if the ransom note can be constructed from the magazines ; otherwise, it will return false.

 Each letter in the magazine string can only be used once in your ransom note.

 Note:
 You may assume that both strings contain only lowercase letters.

 canConstruct("a", "b") -> false
 canConstruct("aa", "ab") -> false
 canConstruct("aa", "aab") -> true
 */
public class RansomNote_383 {
    public boolean canConstruct2(String ransomNote, String magazine) {
        int[] table = new int[26];
        for (char c : magazine.toCharArray()) {
            table[c - 'a']++;
        }
        for (char c : ransomNote.toCharArray()) {
            table[c - 'a']--;
            if (table[c - 'a'] < 0) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        RansomNote_383 r = new RansomNote_383();
        System.out.println(r.canConstruct2("a", "b"));
    }
}
