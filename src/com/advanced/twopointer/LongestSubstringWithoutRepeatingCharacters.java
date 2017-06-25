package com.advanced.twopointer;

/**
 * Created by charles on 11/10/16.
 * Given a string, find the length of the longest substring without repeating characters.
 *Example
 For example, the longest substring without repeating letters for "abcabcbb" is "abc", which the length is 3.
 For "bbbbb" the longest substring is "b", with the length of 1.
 */
public class LongestSubstringWithoutRepeatingCharacters {
    public int lengthOfLongestSubstring(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        int j = 0;
        int maxLen = 0;
        int[] map = new int[256];
        for (int i = 0; i < s.length(); i++) {
            while (j < s.length() && map[s.charAt(j)] == 0) {
                map[s.charAt(j)] = 1;
                // update maxLen
                maxLen = Math.max(maxLen, j - i + 1);
                j++;
            }
            // when encounter dup, set value of left pointer is zero
            // to remove from map
            map[s.charAt(i)] = 0;
        }

        return maxLen;
    }

    public static void main(String[] args) {
        LongestSubstringWithoutRepeatingCharacters a = new LongestSubstringWithoutRepeatingCharacters();
        String s = "abcabcbbavseaveadc";
        System.out.println(a.lengthOfLongestSubstring(s));
        System.out.println(a.updatedSolution(s));
    }

    public int updatedSolution(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        int j = 0;
        int[] map = new int[256];
        int maxLen = 0;
        for (int i = 0; i < s.length(); i++) {
            while (j < s.length() && map[s.charAt(j)] == 0) {
                map[s.charAt(j)] = 1;
                j++;
            }
            maxLen = Math.max(maxLen, j - i);
            // remove left most point
            map[s.charAt(i)] = 0;
        }
        return maxLen;
    }
}
