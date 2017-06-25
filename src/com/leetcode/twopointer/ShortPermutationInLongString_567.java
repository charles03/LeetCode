package com.leetcode.twopointer;

import java.util.Arrays;

/**
 * Created by charles on 5/17/17.
 * Given two strings s1 and s2, write a function to return true if s2 contains the permutation of s1. In other words, one of the first string's permutations is the substring of the second string.
 Example 1:
 Input:s1 = "ab" s2 = "eidbaooo"
 Output:True
 Explanation: s2 contains one permutation of s1 ("ba").
 Example 2:
 Input:s1= "ab" s2 = "eidboaoo"
 Output: False
 */
public class ShortPermutationInLongString_567 {
    /**
     * use simple array data structure to store frequencies.
     * get window size of len(s1) from current index of s2,
     * compare map1 and map2, if match, then return true
     * Time complexity : O(l1 + 26* l1 * (l2-l1)))
     */
    public boolean checkInclusion(String s1, String s2) {
        int len1 = s1.length();
        int len2 = s2.length();
        if (len1 > len2) {
            return false;
        }
        int[] table = new int[26];
        int window = 0;
        for (char c : s1.toCharArray()) {
            table[c - 'a']++;
        }

        for (int i = 0; i < len2 - len1; i++) {
            int[] tmp = new int[26];
            for (int j = 0; j < len1; j++) {
                tmp[s2.charAt(i+j) - 'a']++;
            }
            if (isMatch(table, tmp)) {
                return true;
            }
        }
        return false;
    }
    private boolean isMatch(int[] a1, int[] a2) {
        for (int i = 0; i < 26; i++) {
            if (a1[i] != a2[i]) {
                return false;
            }
        }
        return true;
    }

    /**
     * Solution 2:
     * we keep a track of the number of elements which were already matching in the earlier hashmap
     * and update just the count of matching elements when we shift the window towards the right.
     * we maintain a count variable, which stores the number of chars (out of 26 alphabets)
     * which have same frequency
     * if the deduction of the last element and the addition of the new element leads to a new frequency match of any of the characters, we increment the countcount by 1
     * If not, we keep the countcount intact.
     * But, if a character whose frequency was the same earlier(prior to addition and removal) is added, it now leads to a frequency mismatch which is taken into account by decrementing the same countcount variable
     */
    public boolean checkInclusionII(String s1, String s2) {
        int len1 = s1.length();
        int len2 = s2.length();
        if (len1 > len2) {
            return false;
        }
        int[] table1 = new int[26];
        int[] table2 = new int[26];
        /** in window (size = len(s1)), how many chars having same count between s1 and s2 */
        int match = findExistingMatch(s1, s2, table1, table2);

        // start sliding window
        // window size is length of string 1
        char leftChar, rightChar;
        int left, right;
        for (int i = 0; i < len2 - len1; i++) {
            rightChar = s2.charAt(i + len1);
            leftChar = s2.charAt(i);
            if (match == 26) {
                return true;
            }
            right = rightChar - 'a';
            left = leftChar - 'a';
            table2[right]++; // add char at right side of window
            if (table2[right] == table1[right]) {
                match++;
            } else if (table2[right] == table1[right] + 1) {
                match--; // new add char is more than needed
            }
            table2[left]--;// remove char at left side of window
            if (table2[left] == table1[left]) {
                match++;
            } else if (table2[left] == table1[left] - 1) {
                match--; // removed char is needed actually
            }
        }
        return match == 26;
    }

    private int findExistingMatch(String s1, String s2, int[] table1, int[] table2) {
        int len1 = s1.length();
        for (int i = 0; i < len1; i++) {
            table1[s1.charAt(i) - 'a']++;
            table2[s2.charAt(i) - 'a']++;
        }
        int match = 0;
        /** at first size len1 window, check total count of matching frequency of 26 alphabet
         * as well as those alphabet not appear in window of s1 or s2,*/
        for (int i = 0; i < 26; i++) {
            if (table1[i] == table2[i]) {
                match++;
            }
        }
        return match;
    }

    public static void main(String[] args) {
        ShortPermutationInLongString_567 s = new ShortPermutationInLongString_567();
        s.checkInclusion("adc", "dcda");
    }
}
