package com.leetcode.string;

import java.util.Arrays;

/**
 * Created by charles on 4/10/17.
 * Write a function to find the longest common prefix string amongst an array of strings.
 */
public class LongestCommonPrefix_14 {
    /** total 4 types of approaches
     * https://leetcode.com/articles/longest-common-prefix/#approach-4-binary-search
     */

    /**
     * Thought: vertically compare
     * if contain n numbers of strings,
     * pair comparison from first index of each string
     * if (all first index of each string equivalent)
     * then move to next index,
     * until in that level, some is not equivalent with others
     */
    public String longestCommonPrefix(String[] strs) {
        int size = strs.length;
        int idx = 0;
        if (size == 0) {
            return "";
        }
        if (size == 1) {
            return strs[0];
        }
        String base = strs[0];
        while (idx < base.length()) {
            for (int i = 1; i < size; i++) {
                if (idx < base.length()) {
                    if (idx >= strs[i].length() || base.charAt(idx) != strs[i].charAt(idx)) {
                        return base.substring(0, idx);
                    }
                }
            }
            idx++;
        }
        return base;
    }

    /** better solution
     * First is to sort string array,
     * then compare the first and last elem in sorted array
     */
    public String longestCommonPrefixII(String[] strs) {
        StringBuilder sb = new StringBuilder();
        if (strs == null || strs.length == 0) {
            return "";
        }
        Arrays.sort(strs);

        char[] a = strs[0].toCharArray();
        char[] b = strs[strs.length - 1].toCharArray();

        for (int i = 0; i < a.length; i++) {
            if (i < b.length && a[i] == b[i]) {
                sb.append(a[i]);
            } else {
                return sb.toString();
            }
        }
        return sb.toString();
    }
}
