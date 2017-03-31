package com.leetcode.stack;

import java.util.Collections;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by charles on 3/11/17.
 * Given an encoded string, return it's decoded string.
 The encoding rule is: k[encoded_string], where the encoded_string inside the square brackets is being repeated exactly k times. Note that k is guaranteed to be a positive integer.
 You may assume that the input string is always valid; No extra white spaces, square brackets are well-formed, etc.
 Furthermore, you may assume that the original data does not contain any digits and that digits are only for those repeat numbers, k. For example, there won't be input like 3a or 2[4].
 Examples:

 s = "3[a]2[bc]", return "aaabcbc".
 s = "3[a2[c]]", return "accaccacc".
 s = "2[abc]3[cd]ef", return "abcabccdcdcdef".
 */
public class DecodeString_394 {
    public String decodeString(String s) {
        char[] chars = s.toCharArray();
        String res = "";
        int timesOfRepeat = 0;

        Stack<Integer> cntStack = new Stack<>();
        Stack<StringBuilder> builderStack = new Stack<>();
        StringBuilder currBuilder = new StringBuilder();
        StringBuilder innerBuilder = null;

        for (char c : chars) {
            if (Character.isDigit(c)) {
                timesOfRepeat = timesOfRepeat * 10 + c - '0';
            } else if (c == '[') {
                cntStack.push(timesOfRepeat);
                builderStack.push(currBuilder);
                currBuilder = new StringBuilder(); // init new string builder
                timesOfRepeat = 0; // reset
            } else if (c == ']') {
                innerBuilder = currBuilder;
                currBuilder = builderStack.pop();

                for (timesOfRepeat = cntStack.pop(); timesOfRepeat > 0; timesOfRepeat--) {
                    currBuilder.append(innerBuilder);
                }
            } else {
                currBuilder.append(c);
            }
        }
        return currBuilder.toString();
    }

    public static void main(String[] args) {
        DecodeString_394 d = new DecodeString_394();
        String s1 = "2[abc]3[cd]ef";
        String s2 = "3[a2[c]]";
        System.out.println(d.decodeString(s1));
        System.out.println(d.decodeString(s2));

    }

    /**
     * By Regex,
     */
    public String decodeStringII(String s) {
        Pattern p = Pattern.compile("(\\d+)\\[([a-z]+)\\]");
        StringBuilder sb = new StringBuilder(s);

        for (Matcher m = p.matcher(sb); m.find(); m.reset()) {
            String repeated = String.join("", Collections.nCopies(Integer.parseInt(m.group(1)), m.group(2)));
            sb.replace(m.start(), m.end(), repeated);
        }
        return sb.toString();
    }
}
