package com.leetcode.string;

/**
 * Created by charles on 6/10/17.
 * Given a string, you need to reverse the order of characters in each word within a sentence while still preserving whitespace and initial word order.

 Example 1:
 Input: "Let's take LeetCode contest"
 Output: "s'teL ekat edoCteeL tsetnoc"
 Note: In the string, each word is separated by single space and there will not be any extra space in the string.
 */
public class ReverseWordsInStringIII_557 {
    private static final String SPACE = " ";
    public String reverseWords(String s) {
        String[] parts = s.split(SPACE);
        StringBuilder sb = new StringBuilder();
        for (String part : parts) {
            sb.append(new StringBuilder(part).reverse().toString())
                    .append(SPACE);
        }
        return sb.toString();
    }

    /**
     * without java api
     */
    public String reverseWordsII(String input) {
        final StringBuilder res = new StringBuilder();
        final StringBuilder word = new StringBuilder();
        for (char c : input.toCharArray()) {
            if (c != ' ') {
                word.append(c);
            } else {
                res.append(word.reverse())
                        .append(SPACE);
                word.setLength(0); // clear word stringbuilder
            }
        }
        res.append(word.reverse());
        return res.toString();
    }
    /** better speed, processing in char array */
    public String reverseWordIII(String s) {
        char[] chars = s.toCharArray();
        int i = 0;
        for (int j = 0; j < s.length(); j++) {
            if (chars[j] == ' ') {
                reverse(chars, i, j-1);
                i = j+1; // move pointer to next
            }
        }
        reverse(chars, i, s.length()-1); // for last word
        return new String(chars);
    }
    private void reverse(char[] chars, int start, int end) {
        char tmp;
        while (start < end) {
            tmp = chars[start];
            chars[start++] = chars[end];
            chars[end--] = tmp;
        }
    }
}
