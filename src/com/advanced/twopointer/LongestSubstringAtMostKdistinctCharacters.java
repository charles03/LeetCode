package com.advanced.twopointer;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by charles on 11/10/16.
 *
 * Given a string s, find the length of the longest substring T that contains at most k distinct characters.
 * Example
 For example, Given s = "eceba", k = 3,
 T is "eceb" which its length is 4.

 Challenge O(n) n is size of String s
 */
public class LongestSubstringAtMostKdistinctCharacters {
    public int lengthOfLongestSubstringKDistinct(String s, int k) {
        // write your code here
        if (s == null || s.length() == 0) {
            return 0;
        }
        // key: letter, value: number of occurrences
        Map<Character, Integer> map = new HashMap<Character, Integer>();
        char[] arr = s.toCharArray();
        int j = 0;
        char c;
        int maxLen = 0;
        for (int i = 0; i < arr.length; i++) {
            // loop to find max k distinct
            while (j < arr.length) {
                c = arr[j];
                if (map.containsKey(c)) {
                    map.put(c, map.get(c) + 1);
                } else if (map.size() == k) {
                    break;
                } else {
                    map.put(c, 1);
                }
                j++;
            }
            maxLen = Math.max(maxLen, j - i);
            c = arr[i];
            if (map.containsKey(c)) {
                int count = map.get(c);
                if (count > 1) {
                    map.put(c, count - 1);
                } else {
                    map.remove(c);
                }
            }
        }
        return maxLen;
    }

    public static void main(String[] args) {
        LongestSubstringAtMostKdistinctCharacters a = new LongestSubstringAtMostKdistinctCharacters();
        String s = "eceecfaeaveadavesfavs";
        System.out.println(a.lengthOfLongestSubstringKDistinct(s, 6));
        System.out.println(a.nonHashMap(s, 6));
    }

    public int nonHashMap(String s, int k) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        int[] set = new int[256];
        char[] crr = s.toCharArray();

        int j = 0;
        int count = 0;
        int maxLen = 0;
        for (int i = 0; i < crr.length; i++) {
            while (j < crr.length) {
                if (set[crr[j] - '0'] == 0) {
                    if (count == k) {
                        break;
                    }
                    count++;
                }
                set[crr[j] - '0']++;
                j++;
            }
            maxLen = Math.max(maxLen, j - i);

            if (set[crr[i] - '0'] != 0) {
                if (set[crr[i] - '0'] > 1) {
                    set[crr[i] - '0']--;
                } else {
                    set[crr[i] - '0'] = 0;
                    count--;
                }
            }
        }
        return maxLen;
    }
}
