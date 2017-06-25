package com.leetcode.string;

import java.util.Arrays;

/**
 * Created by charles on 4/11/17.
 * Implement strStr().

 Returns the index of the first occurrence of needle in haystack, or -1 if needle is not part of haystack.
 */
public class ImplementStrStr_28 {
    public int strStr(String haystack, String needle) {
        if (haystack == null || needle == null) {
            return -1;
        }
        char[] hay = haystack.toCharArray();
        char[] need = needle.toCharArray();

        int idx = -1;
        if (need.length == 0) {
            return 0; // for corner case when needle is "", otherwise need[0] will get exception
        }
        char c = need[0];
        for (int i = 0; i < hay.length; i++) {
            if (c == hay[i] && isContain(hay, need, i)) {
                return i;
            }
        }
        return idx;
    }
    private boolean isContain(char[] source, char[] part, int start) {
        int end = start + part.length;
        if (end > source.length) {
            return false;
        }

        int j = 0;
        for (int i = start; i < end; i++) {
            if (part[j++] != source[i]) {
                return false;
            }
        }
        return true;
    }

    /**
     * KMP solution,
     *
     * Detailed Reference in Chinese: http://blog.csdn.net/v_july_v/article/details/7041827
     * there are several implementations of KMP,
     * below is same as {@link RepeatedSubstringPattern_459}
     * */
    private int[] getKmpPrefixArray(char[] chars) {
        int len = chars.length;
        int[] next = new int[len];
        int i = 0, j = 1;
        while (i < len && j < len) {
            if (chars[i] == chars[j]) {
                next[j] = i + 1;
                i++;
                j++;
            } else {
                if (i == 0) {
                    next[j] = 0;
                    j++;
                } else {
                    // start from beginning of current matching pattern
                    i = next[i-1];
                }
            }
        }
        return next;
    }

    private int[] getKmpArr(char[] chars) {
        int len = chars.length;
        int[] next = new int[len];
        next[0] = -1;
        int k = -1;
        int j = 0;
        while (j < len - 1) {
            // next[k] is prefix, next[j] is postfix
            if (k == -1 || chars[j] == chars[k]) {
                k++;
                j++;
                next[j] = k;
            } else {
                k = next[k];
            }
        }
        return next;
    }
    private int[] getKmpArrII(char[] chars) {
        int len = chars.length;
        int[] next = new int[len];
        next[0] = -1;
        int k = -1;
        int j = 0;
        while (j < len - 1) {
            if (k == -1 || chars[j] == chars[k]) {
                k++;
                j++;
                if (chars[j] != chars[k]) {
                    next[j] = k;
                } else {
                    // cannot have case like chars[j] = chars[next[j],
                    // thus need to recursively find k = next[k] = next[next[k]]
                    next[j] = next[k];
                }
            } else {
                k = next[k];
            }
        }
        return next;
    }

    public int strKmpSearch(String haystack, String needle) {
        int[] next = getKmpArrII(needle.toCharArray());
        int len = haystack.length();
        int i = 0, j = 0;
        while (i < len && j < len) {
            if (j == -1 || haystack.charAt(i) == needle.charAt(j)) {
                i++;
                j++;
            } else {
                j = next[j];
            }
        }
        if (j < needle.length()) {
            return -1;
        } else {
            return i - needle.length();
        }
    }

    public static void main(String[] args) {
        ImplementStrStr_28 i = new ImplementStrStr_28();
        String s1 = "abcabdabcae";
        s1 = "abab";
        output(i.getKmpArr(s1.toCharArray()));
        output(i.getKmpArrII(s1.toCharArray()));
        output(i.getKmpPrefixArray(s1.toCharArray()));
    }
    private static void output(int[] arr) {
        Arrays.stream(arr).forEach(t->System.out.print(t + ","));
        System.out.println();
    }
}
