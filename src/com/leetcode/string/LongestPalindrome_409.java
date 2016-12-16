package com.leetcode.string;

/**
 * Created by charles on 11/18/16.
 * Given a string which consists of lowercase or uppercase letters,
 * find the length of the longest palindromes that can be built with those letters.
 This is case sensitive, for example "Aa" is not considered a palindrome here.

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
     * solution thought:
     * if count of char has even number of appearances, add into answer
     * if count is odd number of appearance, appearance minus one
     * and finally we can put an unused character in the middle of palindrome (if there is any)
     */

    public int longestPalindrome(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        // lower case and upper case
        // keep times of appearance of each char in the array
        int[] charStatArray = new int[26 + 26];
        charStatArray = countAppearance(s.toCharArray());

        int maxCount = 0;
        int oddCount = 0;
        for (int cnt: charStatArray) {
            if (cnt == 0) {
                continue;
            }
            // odd
            if (cnt % 2 == 1) {
                maxCount += cnt - 1;
                oddCount++;
            } else {
                maxCount += cnt;
            }
        }
        return (oddCount > 0) ? 1 + maxCount: maxCount;
    }

    private int[] countAppearance(char[] arr) {
        int[] res = new int[52];
        for (char c : arr) {
            if (c >= 97) {
                res[26 + c - 'a']++;
            } else {
                res[c - 'A']++;
            }
        }
        return res;
    }

    public static void main(String[] args) {
        LongestPalindrome_409 l = new LongestPalindrome_409();
        System.out.println(l.longestPalindrome("AAAabccccdd"));
    }
}
