package com.leetcode.string;

/**
 * Created by charles on 3/29/17.
 * Given a string and an integer k, you need to reverse the first k characters for every 2k characters counting from the start of the string. If there are less than k characters left, reverse all of them. If there are less than 2k but greater than or equal to k characters, then reverse the first k characters and left the other as original.
 Example:
 Input: s = "abcdefg", k = 2
 Output: "bacdfeg"
 Restrictions:
 The string consists of lower English letters only.
 Length of the given string and k will in the range [1, 10000]
 */
public class ReverseStringII_541 {
    /**
     * Thought,
     * swap char in char array
     */
    public String reverseStr(String s, int k) {
        char[] chars = s.toCharArray();
        int len = s.length();
        int start = 0;
        for (int i = start; i < len; i++) {
            if (start + k < len) {
                swap(chars, start, start + k - 1);
            } else if (start < len && start + k > len) {
                swap(chars, start, len-1);
            }
            start += 2 * k;
            i = start;
        }
        return new String(chars);
    }
    public String reverseStrII(String s, int k) {
        char[] chars = s.toCharArray();
        int n = chars.length;
        int i = 0;

        while (i < n) {
            int j = Math.min(i + k - 1, n - 1);
            swap(chars, i, j);
            i += 2 * k;
        }
        return String.valueOf(chars);
    }

    private void swap(char[] arr, int start, int end) {
        char tmp;
        while (start < end) {
            tmp = arr[start];
            arr[start++] = arr[end];
            arr[end--] = tmp;
        }
    }

    public static void main(String[] args) {
        ReverseStringII_541 r = new ReverseStringII_541();
        String s = "abcdefg";
        System.out.println(r.reverseStr(s, 2));
        System.out.println(r.reverseStr(s, 4));
    }
}
