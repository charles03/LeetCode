package com.leetcode.twopointer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by charles on 4/16/17.
 * Given a string, find the length of the longest substring without repeating characters.

 Examples:

 Given "abcabcbb", the answer is "abc", which the length is 3.

 Given "bbbbb", the answer is "b", with the length of 1.

 Given "pwwkew", the answer is "wke", with the length of 3. Note that the answer must be a substring, "pwke" is a subsequence and not a substring.
 */
public class LongestSubstringWithoutRepeatingChar_3 {
    /**
     * start from each char in string to find max substring
     */
    public int lengthOfLongestSubstring(String s) {
        int j = 0, len = s.length();
        int[] map = new int[256];
        int maxLen = 0;
        for (int i = 0; i < len; i++) {
            while (j < len && map[s.charAt(j) - ' '] == 0) {
                map[s.charAt(j) - ' '] = 1;
                j++;
            }
            maxLen = Math.max(maxLen, j - i);
            /**
             * j won't move until map[s.charAt(j)] == 0
             * for below example "pwwkep"
             * j will be move until i reach j and remove left most [0,i)
             */
            // remove left most
            map[s.charAt(i) - ' '] = 0;
        }
        return maxLen;
    }

    /** HashMap solution
     * key : char in string, value : position index
     * keep two pointers which define the max substring.
     * move right pointer to scan thru the string. meanwhile update hashmap
     * if map.contain(char) {
     *     move left pointer to right of same char last found
     * }
     */
    public int lengthOfLongestSubstringII(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        Map<Character, Integer> map = new HashMap<>();
        int max = 0;
        int j = 0;
        for (int i = 0; i < s.length(); i++) {
            if (map.containsKey(s.charAt(i))) {
                /**
                 * to always pick max index after removing dup
                 * like example "abba"
                 * when b is repeated, j is 2
                 * next a is repeated, map(a) = 1 < 2 (current j);
                 * thus should use j instead of next index of a.
                 */
                j = Math.max(j, map.get(s.charAt(i)) + 1);
            }
            map.put(s.charAt(i), i);
            max = Math.max(max, i-j+1);
        }
        return max;
    }

    public int lengthOfLongestSubstringIII(String s) {
        int[] map = new int[256];
        Arrays.fill(map, -1);
        int maxLen = 0;
        int startIdx = 0;

        for (int i = 0; i < s.length(); i++) {
            startIdx = Math.max(map[s.charAt(i) - ' '], startIdx);
            map[s.charAt(i) - ' '] = i;
            maxLen = Math.max(maxLen, i - startIdx + 1);
        }
        return maxLen;
    }

    public static void main(String[] args) {
        LongestSubstringWithoutRepeatingChar_3 l = new LongestSubstringWithoutRepeatingChar_3();
        String s1 = "abba";
        l.lengthOfLongestSubstringII(s1);
        String s = "pwwkep";
        System.out.println(l.lengthOfLongestSubstring(s));

    }
}
