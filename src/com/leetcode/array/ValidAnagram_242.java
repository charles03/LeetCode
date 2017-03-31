package com.leetcode.array;

import java.util.Arrays;

/**
 * Created by charles on 2/27/17.
 */
public class ValidAnagram_242 {
    /**
     * naive solution O(nlgn)
     */
    public boolean isAnagram(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }
        char[] s1 = s.toCharArray();
        char[] t1 = t.toCharArray();

        Arrays.sort(s1);
        Arrays.sort(t1);

        return Arrays.equals(s1, t1);
    }

    /**
     * Auxilury array as hash table
     */
    public boolean isAnagramII(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }
        int[] unicode = new int[256];
        char[] s1 = s.toCharArray();
        char[] t1 = t.toCharArray();

        for (char c : s1) {
            unicode[c]++;
        }
        for (char c : t1) {
            if (unicode[c] > 0) {
                unicode[c]--;
            } else {
                return false;
            }
        }
        return true;
    }
}
