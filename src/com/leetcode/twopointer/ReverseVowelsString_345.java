package com.leetcode.twopointer;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by charles on 4/4/17.
 * Write a function that takes a string as input and reverse only the vowels of a string.

 Example 1:
 Given s = "hello", return "holle".

 Example 2:
 Given s = "leetcode", return "leotcede".

 Note:
 The vowels does not include the letter "y".
 */
public class ReverseVowelsString_345 {
    /**
     * Thought: two pointers, from left to find first vowels, from end to find another vowels
     * when left < end
     * Then swap, and then continue until left >= end;
     *
     * Besides, use HashSet to determine vowels
     */
    public String reverseVowels(String s) {
        List<Character> list = Arrays.asList(new Character[]{'a','e','i','o','u','A','E','I','O','U'});
        Set<Character> vowels = new HashSet<>(list);

        char[] chars = s.toLowerCase().toCharArray();
        int len = chars.length;
        int i = 0;
        int j = len - 1;

        while (i < j) {
            while (i < len && !vowels.contains(chars[i])) {
                i++;
            }
            while ( j >= 0 && !vowels.contains(chars[j])) {
                j--;
            }
            if (i < j) {
                swap(chars, i, j);
            }
            i++;
            j--;
        }
        return new String(chars);
    }
    private void swap(char[] chars, int i, int j) {
        char c = chars[i];
        chars[i] = chars[j];
        chars[j] = c;
    }

    public static void main(String[] args) {
        ReverseVowelsString_345 r = new ReverseVowelsString_345();
        System.out.println(r.reverseVowels("hello"));

        System.out.println(r.reverseVowels(".,"));
    }
}
