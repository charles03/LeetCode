package com.amazon;

import java.util.Arrays;

/**
 * Created by charles on 6/21/17.
 * Given an input string, reverse the string word by word.

 For example,
 Given s = "the sky is blue",
 return "blue is sky the".

 Have you met this question in a real interview? Yes
 Clarification
 What constitutes a word?
 A sequence of non-space characters constitutes a word.
 Could the input string contain leading or trailing spaces?
 Yes. However, your reversed string should not contain leading or trailing spaces.
 How about multiple spaces between two words?
 Reduce them to a single space in the reversed string.
 */
public class ReverseWordsInString {
    public String reverse(String str) {
        // need to check corner case when str is ""
        if (str == null || str.length() == 0) {
            return str;
        }
        String[] arr = str.split(" ");
        StringBuilder sb = new StringBuilder();
        for (int i = arr.length - 1; i >= 0; i--) {
            if (arr[i] == null || arr[i].equals("")) {
                continue;
            }
            sb.append(arr[i]).append(" ");
        }
        if (sb.length() == 0) {
            return "";
        }
        return sb.substring(0, sb.length() - 1);
    }

    /** because String is immutable object in java, will cause heavy garbage collection
     * use below in-place method to reverse sentence.
     * step 1. from front to tail, reverse each single word by in-place swap
     * step 2. directly in-place swap for whole char array.
     */
    public String reverse(char[] chars) {
        int l = 0;
        for (int r = 0; r < chars.length; r++) {
            if (chars[r] == ' ') {
                swapInRange(chars, l, r);
                l = r + 1;
            }
        }
        // handle if last word doesn't have space afterward
        swapInRange(chars, l, chars.length - 1);
        // and then all single word is reversed, but word order is still
        // thus swap in whole sentence range, will get final result
        StringBuilder sb = new StringBuilder();
        int i = chars.length - 1;
        int j = i;
        while (j >= 0) {
            while (i >= 0 && chars[i] == ' ') {
                i--;
            }
            j = i;
            while (j >= 0 && chars[j] != ' ') {
                sb.append(chars[j]);
                j--;
            }
            sb.append(' ');
            i = j;
        }
        return sb.substring(0, sb.length() - 1);
    }
    private void swapInRange(char[] ch, int i, int j) {
        char tmp;
        while (i < j) {
            tmp = ch[i];
            ch[i] = ch[j];
            ch[j] = tmp;
            i++;
            j--;
        }
    }

    public static void main(String[] args) {
        ReverseWordsInString r = new ReverseWordsInString();
        String s1 = "the sky is blue";
        print(s1.split(" "));
        System.out.println(r.reverse(s1));
        System.out.println(r.reverse(s1.toCharArray()));

        s1 = "  the    sky is    blue  ";
        print(s1.split(" "));
        System.out.println(r.reverse(s1));
        System.out.println(r.reverse(s1.toCharArray()));

        String s2 = "    ";
        System.out.println(r.reverse(s2.toCharArray()));
    }
    private static void print(String[] arr) {
        Arrays.stream(arr).forEach(t -> System.out.print(t + ","));
        System.out.println();
    }
}
