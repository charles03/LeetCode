package com.leetcode.twopointer;

import java.util.*;

/**
 * Created by charles on 4/16/17.
 * Given a string and a string dictionary, find the longest string in the dictionary that can be formed by deleting some characters of the given string. If there are more than one possible results, return the longest word with the smallest lexicographical order. If there is no possible result, return the empty string.

 Example 1:
 Input:
 s = "abpcplea", d = ["ale","apple","monkey","plea"]

 Output:
 "apple"
 Example 2:
 Input:
 s = "abpcplea", d = ["a","b","c"]

 Output:
 "a"
 Note:
 All the strings in the input will only contain lower-case letters.
 The size of the dictionary won't exceed 1,000.
 The length of all the strings in the input won't exceed 1,000.
 */
public class LongestWordInDictThruDeletion_524 {
    /**
     * Thought: pre-processing source string,
     * get count of each letter
     * and then sort given collection of String with lexicographical order
     * alloc int[128], count letter of each string in list
     *
     */
    public String findLongestWord(String s, List<String> d) {
        Collections.sort(d);
        String res = "";
        int max = Integer.MIN_VALUE; // max length of each string under premise of as part of original string
        char[] chars = s.toCharArray();
        for (String str : d) {
            if (isPart(chars, str)) {
                if (str.length() > max) {
                    res = str;
                    max = str.length();
                }
            }
        }
        return res;
    }

    private boolean isPart(char[] chars, String s) {
        char[] target = s.toCharArray();
        int i = 0, j = 0;
        int cLen = chars.length, tLen = target.length;
        while (j < tLen) {
            while (i < cLen && chars[i] != target[j]) {
                i++;
            }
            if (i >= cLen && j < tLen - 1) {
                return false;
            }
            i++;
            j++;
        }
        if (j == tLen) {
            return true;
        }
        return false;
    }

    /**
     * use indexOf api to get better performance
     */
    public String findLongestWordII(String s, List<String> d) {
        String res = "";
        for (String word : d) {
            if (isPrior(word, res) && isSubsequence(word, s)) {
                res = word;
            }
        }
        return res;
    }
    // to make sure a is longest length with smallest lexicographical order
    private boolean isPrior(String a, String b) {
        if (a.length() > b.length() ||
                (a.length() == b.length() && a.compareTo(b) < 0)) {
            return true;
        }
        return false;
    }
    private boolean isSubsequence(String a, String b) {
        int start = -1;
        for (int i = 0; i < a.length(); i++) {
            start = b.indexOf(a.charAt(i), start + 1);
            if (start < 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * {@link com.leetcode.string.LongestUncommonSubsequenceII_522 isSubsequence}
     */
    private boolean isSubsequenceII(String sub, String str) {
        // without use indexOf
        int i = 0, j = 0;
        int l1 = sub.length(), l2 = str.length();
        while (i < l1 && j < l2) {
            if (sub.charAt(i) == str.charAt(j)) {
                i++;
            }
            j++;
        }
        return i == l1;
    }

    public static void main(String[] args) {
        LongestWordInDictThruDeletion_524 l = new LongestWordInDictThruDeletion_524();
        List<String> d1 = Arrays.asList("ale","apple","monkey","plea");
        System.out.println(l.findLongestWord("abpcplea", d1));
    }
}
