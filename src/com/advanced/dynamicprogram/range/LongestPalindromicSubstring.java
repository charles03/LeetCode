package com.advanced.dynamicprogram.range;

import java.util.Arrays;

/**
 * Created by charles on 11/13/16.
 *
 * Given a string S, find the longest palindromic substring in S.
 * You may assume that the maximum length of S is 1000, and there exists one unique longest palindromic substring.
 *
 * Given the string = "abcdzdcab", return "cdzdc".
 * Challenge: O(n) time, O(n^2) time is acceptable
 */
public class LongestPalindromicSubstring {
    public String toLongestPalindromicSubstring(String s) {
        if (s == null || s.length() == 0) {return "";}
        int len = s.length();
        int maxLen = 0;
        int range = 0;
        int start = 0;
        int end = 0;
        int idx = 0;
        char[] c = new char[2 * len + 1];
        // create string like #a#b#a#, so that no need to consider even/odd separately
        for (int i = 0; i < 2 * len; i = i+2) {
            c[i] = '#';
            c[i+1] = s.charAt(i/2);
        }
        c[2 * len] = '#';
        System.out.println(Arrays.toString(c));
        // extend original str so that iterate new char array lenght,
        // don't need to consider even/odd separately

        for (int i = 1; i < 2 * len; i++) {
            range = 0;
            while ((i-range)>=0 && (i+range)<=2*len && c[i-range] == c[i+range]) {
                range++;
            }
            range--;
            if (range > maxLen) {
                System.out.println("range " + range);
                maxLen = range;
                idx = i;
            }
        }
        start = (idx - maxLen) / 2;
        end = (idx + maxLen) / 2;

        return s.substring(start, end);
    }

    /** expand from center of each char
     * there are only 2n-1 such centers
     * because n of are each char as center
     * another n-1 of are between two letters.
     */
    public String longestPalindromeII(String s) {
        int start = 0, end = 0;
        int len1 = 0, len2 = 0;
        int len = 0;
        for (int i = 0; i < s.length(); i++) {
            len1 = expandAroundCenter(s, i, i);
            len2 = expandAroundCenter(s, i, i+1);
            len = Math.max(len1, len2);
            if (len > end - start) {
                start = i - (len - 1) / 2; // i is index center, where (len-1)/2 is half len of palindrome part
                end = i + len / 2;
            }
        }
        return s.substring(start, end + 1);
    }
    private int expandAroundCenter(String s, int left, int right) {
        int l = left, r = right;
        while (l >= 0 && r < s.length() && s.charAt(l) == s.charAt(r)) {
            l--;
            r++;
        }
        return r - l - 1;
    }

    /**
     * O(n) solution, Manachers Alogrithm
     *
     * To find in linear time a longest palindrome in a string, an algorithm may take advantage of the following characteristics or observations about a palindrome and a sub-palindrome:

     The left side of a palindrome is a mirror image of its right side.
     (Case 1) A third palindrome whose center is within the right side of a first palindrome will have exactly the same length as that of a second palindrome anchored at the mirror center on the left side, if the second palindrome is within the bounds of the first palindrome by at least one character.
     (Case 2) If the second palindrome meets or extends beyond the left bound of the first palindrome, then the third palindrome is guaranteed to have at least the length from its own center to the right outermost character of the first palindrome. This length is the same from the center of the second palindrome to the left outermost character of the first palindrome.
     To find the length of the third palindrome under Case 2, the next character after the right outermost character of the first palindrome would then be compared with its mirror character about the center of the third palindrome, until there is no match or no more characters to compare.
     (Case 3) Neither the first nor second palindrome provides information to help determine the palindromic length of a fourth palindrome whose center is outside the right side of the first palindrome.
     It is therefore desirable to have a palindrome as a reference (i.e., the role of the first palindrome) that possesses characters furtherest to the right in a string when determining from left to right the palindromic length of a substring in the string (and consequently, the third palindrome in Case 2 and the fourth palindrome in Case 3 could replace the first palindrome to become the new reference).
     Regarding the time complexity of palindromic length determination for each character in a string: there is no character comparison for Case 1, while for Cases 2 and 3 only the characters in the string beyond the right outermost character of the reference palindrome are candidates for comparison (and consequently Case 3 always results in a new reference palindrome while Case 2 does so only if the third palindrome is actually longer than its guaranteed minimum length).
     For even-length palindromes, the center is at the boundary of the two characters in the middle.
     */

    /**
     Implementation[edit]
     Let:

     s be a string of N characters
     s2 be a derived string of s, comprising N * 2 + 1 elements, with each element corresponding to one of the following: the N characters in s, the N-1 boundaries among characters, and the boundaries before and after the first and last character respectively
     A boundary in s2 is equal to any other boundary in s2 with respect to element matching in palindromic length determination
     p be an array of palindromic span for each element in s2, from center to either outermost element, where each boundary is counted towards the length of a palindrome (e.g. a palindrome that is three elements long has a palindromic span of 1)
     c be the position of the center of the palindrome currently known to include a boundary closest to the right end of s2 (i.e., the length of the palindrome = p[c]*2+1)
     r be the position of the right-most boundary of this palindrome (i.e., r = c + p[c])
     i be the position of an element (i.e., a character or boundary) in s2 whose palindromic span is being determined, with i always to the right of c
     i2 be the mirrored position of i around c (e.g., {i, i2} = {6, 4}, {7, 3}, {8, 2},… when c = 5 (i.e., i2 = c * 2 - i)
     */
    public String findLongestParlindrome(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char[] ch2 = addBoundaries(s.toCharArray());
        System.out.println(Arrays.toString(ch2));
        // record from current index i, max distance in single direction, either left or right, because it is mirror
        int[] distance = new int[ch2.length];
        int center = 0; // center index
        int rightMost = 0; // right most index
        int left = 0, right = 0; // walking indices to compare if two elem are the same

        for (int curIndex = 1; curIndex < ch2.length; curIndex++) {
            if (curIndex > rightMost) { // re-start
                distance[curIndex] = 0;
                left = curIndex - 1; // initial leftward
                right = curIndex + 1; // initial rightward
            } else {
                int rightIndex = center * 2 - curIndex;
                if (distance[rightIndex] < (rightMost - curIndex)) {
                    distance[curIndex] = distance[rightIndex];
                    left = -1; // this signals bypassing the while loop below
                } else {
                    distance[curIndex] = rightMost - curIndex;
                    right = rightMost + 1;
                    left = curIndex * 2 - right;
                }
            }
            while (left >= 0 && right < ch2.length && ch2[left] == ch2[right]) {
                distance[curIndex]++;
                left--;
                right++;
            }
            if ((curIndex + distance[curIndex]) > rightMost) {
                center = curIndex;
                rightMost = curIndex + distance[curIndex];
            }
        }
        int maxLen = 0;
        center = 0;
        for (int i = 1; i < ch2.length; i++) {
            if (maxLen < distance[i]) {
                maxLen = distance[i];
                center = i;
            }
        }
        char[] res = Arrays.copyOfRange(ch2, center-maxLen, center+maxLen+1);
        return String.valueOf(removeBoundaries(res));
    }

    // add splitter to handle even or odd length in easy way
    public char[] addBoundaries(char[] ch) {
        if (ch == null || ch.length == 0) {
            return "||".toCharArray();
        }
        char[] ch2 = new char[ch.length * 2 + 1];
        for (int i = 0; i < ch2.length - 1; i += 2) {
            ch2[i] = '|';
            ch2[i+1] = ch[i/2];
        }
        ch2[ch2.length - 1] = '|';
        return ch2;
    }

    public char[] removeBoundaries(char[] ch) {
        if (ch == null || ch.length < 3) {
            return "".toCharArray();
        }
        char[] ch2 = new char[(ch.length-1)/2];
        for (int i = 0; i < ch2.length; i++) {
            ch2[i] = ch[i * 2 + 1];
        }
        return ch2;
    }

    public static void main(String[] args) {
        LongestPalindromicSubstring l = new LongestPalindromicSubstring();
        System.out.println(l.toLongestPalindromicSubstring("abacdca"));
        System.out.println(l.findLongestParlindrome("abacdca"));
    }
}
