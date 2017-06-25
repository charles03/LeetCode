package com.leetcode.dfs;

import com.sun.javafx.css.CssError;
import com.sun.org.apache.xml.internal.utils.StringBufferPool;

import javax.xml.stream.events.Characters;

/**
 * Created by charles on 3/11/17.
 * {@link com.leetcode.stack.DecodeString_394}
 */
public class DecodeString_394 {
    /**
     * use DFS recursion
     */
    public String decodeString(String s) {
        StringBuilder sb = new StringBuilder();
        int len = s.length();
        int start = 0, end = 0;

        while (end < len) {
            start = end;
            if (Character.isDigit(s.charAt(start))) {
                int openIndex = getOpenIndex(s, start);
                int closeIndex = getCloseIndex(s, start);
                int count = Integer.parseInt(s.substring(start, openIndex));

                String encloseString = s.substring(openIndex+1, closeIndex);
                repeatingAppend(sb, decodeString(encloseString), count);
                end = closeIndex+1;
            } else {
                int curr = start;
                while (curr < len && !Character.isDigit(s.charAt(curr))) {
                    curr++;
                }
                repeatingAppend(sb, s.substring(start, curr), 1);
                end = curr;
            }
        }
        return sb.toString();
    }

    void repeatingAppend(StringBuilder sb, String str, int repeat) {
        for (int i = 0; i < repeat; i++) {
            sb.append(str);
        }
    }


    int getOpenIndex(String s, int start) {
        return s.indexOf('[', start);
    }
    int getCloseIndex(String s, int start) {
        int countOfOpen = 1;
        int currIdx = getOpenIndex(s, start) + 1;
        while (countOfOpen != 0) {
            switch (s.charAt(currIdx)) {
                case '[' :
                    countOfOpen++;
                    break;
                case ']' :
                    countOfOpen--;
                    break;
            }
            currIdx++;
        }
        return currIdx - 1;
    }


    /**
     * better clean solution
     *
     */
    public String decodeStringII(String s) {
        if (s == null || s.length() <= 1) {
            return s;
        }
        int[] index = new int[1]; // use array as object to store index while recursion
        return dfs(s, index);
    }
    private String dfs(String s, int[] index) {
        StringBuilder sb = new StringBuilder();
        int count = 0;
        String inner = null;
        while (index[0] < s.length() && s.charAt(index[0]) != ']') {
            char curr = s.charAt(index[0]);
            if (Character.isDigit(curr)) {
                count = count * 10 + (curr - '0');
            } else if (curr == '[') {
                index[0]++;
                inner = dfs(s, index);
            } else {
                sb.append(curr);
            }
            if (count != 0 && inner != null) {
                while (count > 0) {
                    sb.append(inner);
                    count--;
                }
                inner = null; // reset dfs result back to null state
                // otherwise will create incorrect res
                // example 3[a]2[bc] if without inner = null, will be aaaaa instead of aaabcbc
            }
            index[0]++;
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        DecodeString_394 d = new DecodeString_394();
        String s1 = "3[g2[ef6[d]]]2[bc]";
        System.out.println(d.decodeStringII(s1));
    }

}
