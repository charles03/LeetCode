package com.advanced.dynamicprogram.range;

/**
 * Created by charles on 11/19/16.
 *
 * Given a string s1, we may represent it as a binary tree by partitioning it to two non-empty substrings recursively.
 Below is one possible representation of s1 = "great"
 To scramble the string, we may choose any non-leaf node and swap its two children.

 For example, if we choose the node "gr" and swap its two children, it produces a scrambled string "rgeat"
 We say that "rgeat" is a scrambled string of "great".

 Similarly, if we continue to swap the children of nodes "eat" and "at", it produces a scrambled string "rgtae"
 We say that "rgtae" is a scrambled string of "great".
 Given two strings s1 and s2 of the same length, determine if s2 is a scrambled string of s1.
 */
public class ScrambleString {
    /**
     * use recursive method to traverse every possible situation, if without reduce branch
     * we will run into TLE
     * There are other factors we should make full use of to reduce traversing range
     *      limited characters
     *      scramble string is containing just exactly the same set of chars as the original string
     * used to prune almost al invalid traversing branches
     */
    private static int SIZE = 256;
    private char BASE = ' ';

    public boolean isScramble(String s1, String s2) {
        if (s1 == null || s2 == null || s1.length() != s2.length()) {
            return false;
        }
        // s1 length = s2.length;
        int len = s1.length();
        char[] c1 = s1.toCharArray();
        char[] c2 = s2.toCharArray();
        return isScrambleByRecursion(c1, c2, 0, 0, len);
    }

    public boolean isScrambleByRecursion(char[] c1, char[] c2, int l1, int l2, int len) {
        // statics
        boolean equal = true;
        int idx = 0;
        while (equal && idx < len) {
            equal &= c1[idx + l1] == c2[idx + l2];
            idx++;
        }
        if (equal) {
            System.out.println(" " + l1 + " " + l2 + " " + len);
            return true;
        }

        int[] count = new int[SIZE];
        for (int i = 0; i < len; i++) {
            count[c1[i + l1] - BASE]++;
            count[c2[i + l2] - BASE]--;
        }

        for (int i = 0; i < SIZE; i++) {
            if (count[i] != 0) {
                System.out.println("in count " + i + " " + l1 + " " + l2 + " " + len);
                return false;
            }
        }
        // select splitter index k to traverse left and right
        for (int k = 1; k < len; k++) {
            if (isScrambleByRecursion(c1, c2, l1, l2, k) // without swap
                    && isScrambleByRecursion(c1, c2, l1+k, l2+k, len-k)) {
                return true;
            }
            if (isScrambleByRecursion(c1, c2, l1, l2+len-k, k) // with swap
                    && isScrambleByRecursion(c1, c2, l1+k, l2, len-k)) {
                return true;
            }
        }
        return false;
    }

    /**
     * use 3-dimension array to store state dp[x][y][k]
     * State: s1 start at x index, s2 start at y index, length is k
     *        substr(s1) is substr(s2) scramble string
     * Function:
     */

    public static void main(String[] args) {
        ScrambleString s = new ScrambleString();
        char[] c1 = {'a', 'b'};
        char[] c2 = {'a', 'b'};
//        System.out.println(c1.equals(c2));
        System.out.println(s.isScramble("rgeat", "great"));
        System.out.println(s.isScramble("acb", "abc"));
    }
}
