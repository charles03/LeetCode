package com.leetcode.string;

/**
 * Created by charles on 4/10/17.
 * Given a string s consists of upper/lower-case alphabets and empty space characters ' ', return the length of last word in the string.

 If the last word does not exist, return 0.

 Note: A word is defined as a character sequence consists of non-space characters only.

 For example,
 Given s = "Hello World",
 return 5.
 */
public class LengthLastWord_58 {
    public int lengthOfLastWord(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        int k = s.length() - 1;
        while (k >= 0 && s.charAt(k) == ' ') {
            k--;
        }
        if (k < 0) {
            return 0;
        }
        int len = 0;
        while (k >= 0 && s.charAt(k) != ' ') {
            k--;
            len++;
        }
        return len;
    }
    /** another easy solution using string api */
    public int lengthOfLastWordII(String s) {
        s = s.trim();
        int lastSpaceIdx = s.lastIndexOf(' ') + 1;
        return s.length() - lastSpaceIdx;
    }
}
