package com.leetcode.array;

/**
 * Created by charles on 3/7/17.
 * Given a string which consists of lowercase or uppercase letters, find the length of the longest palindromes that can be built with those letters.

 This is case sensitive, for example "Aa" is not considered a palindrome here.

 Note:
 Assume the length of given string will not exceed 1,010.

 Example:

 Input:
 "abccccdd"

 Output:
 7

 Explanation:
 One longest palindrome that can be built is "dccaccd", whose length is 7.
 */
public class LongestPalindrome_409 {
    /**
     * for any odd or even number
     * closet even number is ( (num / 2) * 2)
     */
    public int longestPalindrome(String s) {
        int[] lowercase = new int[26];
        int[] uppercase = new int[26];
        int len = 0;

        char letter = ' ';
        for (int i = 0; i < s.length(); i++) {
            letter = s.charAt(i);
            if (letter >= 97) {
                lowercase[letter - 'a']++;
            } else {
                uppercase[letter - 'A']++;
            }
        }
        for (int i = 0; i < 26; i++) {
            len += (lowercase[i] / 2) * 2;
            len += (uppercase[i] / 2) * 2;
        }
        if (len == s.length()) {
            return len;
        } else {
            return len + 1;
        }
    }
}
