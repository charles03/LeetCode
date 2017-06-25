package com.leetcode.twopointer;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by charles on 4/19/17.
 * Given a string S and a string T, find the minimum window in S which will contain all the characters in T in complexity O(n).

 For example,
 S = "ADOBECODEBANC"
 T = "ABC"
 Minimum window is "BANC".

 Note:
 If there is no such window in S that covers all characters in T, return the empty string "".

 If there are multiple such windows, you are guaranteed that there will always be only one unique minimum window in S.
 */
public class MinimumWindowSubstring_76 {
    /**
     * maintain two pointers for window sliding,
     * one for exploiting new matched substring,
     * the other for expiring previous substring
     */
    private final String EMPTY = "";

    public String minWindow(String s, String t) {
        char[] sArr = s.toCharArray();
        char[] tArr = t.toCharArray();

        int[] map = new int[256]; // ASCII
        int start = 0, end = 0;
        int min = Integer.MAX_VALUE;

        // assemble chars of t into map
        for (int i = 0; i < tArr.length; i++) {
            map[tArr[i]]++;
        }

        int count = tArr.length;
        int window = 0;
        int minStart = 0;

        // for those Characters in s doesn't have in t, map[char] are most 0
        /** init: only valid char can force decrement count */
        while (end < sArr.length) {
            if (map[sArr[end]] > 0) { // t has this character
                count--; // find one out of tArr.length matching character
            }
            map[sArr[end]] --;
            while (count == 0) { // window matching
                window = end - start + 1;
                if (window < min) {
                    min = window;
                    minStart = start;
                }
                map[sArr[start]]++; // start to go left
                if (map[sArr[start]] > 0) { // to remove a character which t has in previous window
                    count++;
                }
                start++;
            }
            end++;
        }
        if (minStart + min > sArr.length) {
            return EMPTY;
        }
        return s.substring(minStart, minStart + min);
    }

    /** two pointer with map
     * store frequency of char from T
     * use count to find first window in S which contain T
     3 Checking from the leftmost index of the window and to see if it belongs to t. The reason we do so is that we want to shrink the size of the window.
     3-1) If the character at leftmost index does not belong to t, we can directly remove this leftmost value and update our window(its minLeft and minLen value)
     3-2) If the character indeed exists in t, we still remove it, but in the next step, we will increase the right pointer and expect the removed character. If find so, repeat step 3.
     */
    public String minWindowII(String s, String t) {
        Map<Character, Integer> map = new HashMap<>();
        for (char c : t.toCharArray()) {
            if (!map.containsKey(c)) {
                map.put(c, 1);
            } else {
                map.put(c, map.get(c) + 1);
            }
        }
        int left = 0, minLeft = 0, window = 0;
        int min = Integer.MAX_VALUE, count = 0;
        char ch;
        for (int right = 0; right < s.length(); right++) {
            ch = s.charAt(right);
            if (map.containsKey(ch)) { //identify if the first window is found by counting frequency of the characters of t showing up in S
                map.put(ch, map.get(ch) - 1);
                if (map.get(ch) > 0) {
                    count++;
                }
            }
            while (count == t.length()) {
                window = right - left + 1;
                if (window < min) {
                    min = window;
                    minLeft = left;
                }
                char l = s.charAt(left);
                //starting from the leftmost index of the window, we want to check if s[left] is in t.
                // If so, we will remove it from the window, and increase 1 time on its counter in hashmap which means we will expect the same character later by shifting right index.
                // At the same time, we need to reduce the size of the window due to the removal
                if (map.containsKey(l)) {
                    map.put(l, map.get(l) + 1);
                    if (map.get(l) > 0) {
                        count--;
                    }
                }
                left++;
            }
        }
        if (minLeft + min > s.length()) {
            return EMPTY;
        }
        return s.substring(minLeft, minLeft + min);
    }
}
