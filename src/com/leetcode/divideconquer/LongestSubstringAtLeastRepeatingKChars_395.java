package com.leetcode.divideconquer;

/**
 * Created by charles on 3/11/17.
 * Find the length of the longest substring T of a given string (consists of lowercase letters only) such that every character in T appears no less than k times.

 Example 1:
 Input:
 s = "aaabb", k = 3
 Output: 3
 The longest substring is "aaa", as 'a' is repeated 3 times.
 Example 2:
 Input:
 s = "ababbc", k = 2
 Output: 5
 The longest substring is "ababb", as 'a' is repeated 2 times and 'b' is repeated 3 times.
 */
public class LongestSubstringAtLeastRepeatingKChars_395 {
    /**
     * the idea is to find all letters that appear less than k times
     * and split the string at them by recursion
     */
    public int longestSubstring(String s, int k) {
        if (s.length() < k) {
            return 0;
        }
        String letterLessThanGivenOccur = toCountOccur(s, k);

        if (letterLessThanGivenOccur.length() == 0) {
            return s.length();
        }

        String[] segments = s.split("[" + letterLessThanGivenOccur + "]");
        int maxLen = 0;
        for (String subStr : segments) {
            maxLen = Math.max(maxLen, longestSubstring(subStr, k));
        }
        return maxLen;
    }
    String toCountOccur(String s, int k) {
        int[] count = new int[26];
        for (int i = 0; i < s.length(); i++) {
            count[s.charAt(i) - 'a']++;
        }
        StringBuilder sb =  new StringBuilder();
        for (int i = 0; i < count.length; i++) {
            if (count[i] > 0 && count[i] < k) {
                sb.append((char)('a' + i));
            }
        }
        return sb.toString();
    }

    /**
     * Two Pointer with Recursion
     */
    public int longestSubstringII(String s, int k) {
        int length = 0;
        for (int i = 1; i <= 26; i++) {
            // try 26 possibilities of unique letters
            length = Math.max(length, longestSubstringGivenUniqueCharOccur(s, k, i));
        }
        return length;
    }
    int longestSubstringGivenUniqueCharOccur(String s, int givenOccurs, int givenCountOfUnqieChars) {
        int[] map = new int[26];
        int countOfUnique = 0; // first pointer
        int countOfMoreThanGivenOccurs = 0; // second pointer

        int start = 0, end = 0; // position index
        int frontIdx = 0, tailIdx = 0; // index in letter map
        int dist = 0;
        int len = s.length();

        while (end < len) {
            tailIdx = s.charAt(end) - 'a';
            if (map[tailIdx] == 0) {
                countOfUnique++;
            }
            map[tailIdx]++; // increment after update num of unique char variable
            if (map[tailIdx] == givenOccurs) {
                countOfMoreThanGivenOccurs++;
            }
            end++; // move end to next index;

            while (countOfUnique > givenCountOfUnqieChars) {
                frontIdx = s.charAt(start) - 'a';
                if (map[frontIdx] == givenOccurs) {
                    countOfMoreThanGivenOccurs--;
                }
                map[frontIdx]--;
                if (map[frontIdx] == 0) {
                    countOfUnique--;
                }
                start++;
            }
            // if we found a string where the number of unique chars equals our target
            // and all those chars are repeated at least K times then update max
            if (countOfUnique == givenCountOfUnqieChars
                    && countOfUnique == countOfMoreThanGivenOccurs) {
                dist = Math.max(dist, end - start); // update new window
            }
        }
        return dist;
    }
    /** rewrite */
    public int longestSubStringIII(String s, int k) {
        int dist = 0;
        // add restriction of given fixed numbers of unique characters
        // so that loop through string to find max
        for (int uniqueCount = 1; uniqueCount <= 26; uniqueCount++) {
            dist = Math.max(dist, longestSubstrUnderUniqueChars(s, k, uniqueCount));
        }
        return dist;
    }
    private int longestSubstrUnderUniqueChars(String s, int k, int cnt) {
        // init vars
        int[] map = new int[128];
        int numUnique = 0; // counter of numbers of unique characters
        int numMoreThenK = 0; // counter of appearance of character more then K times
        int begin = 0, end = 0; // two pointers
        int dist = 0;

        while (end < s.length()) {
            if (map[s.charAt(end)]++ == 0) {
                numUnique++; // increment map[idx] after this statement;
            }
            if (map[s.charAt(end++)] == k) {
                numMoreThenK++; // increment end pointer after this statuement;
            }
            // slide begin pointer to cut elems at front of window
            while (numUnique > cnt) {
                if (map[s.charAt(begin)]-- == k) {
                    numMoreThenK--; // decrement map[idx] after this statement
                }
                if (map[s.charAt(begin++)] == 0) {
                    numUnique--; // move begin after this statement
                }
            }
            // if we found a substring where numbers of unique chars equals given count of unique char as target
            // and all these chars repeated at least K times
            // then update max
            if (numUnique == cnt && numUnique == numMoreThenK) {
                dist = Math.max(dist, end - begin);
            }
        }
        return dist;
    }
}
