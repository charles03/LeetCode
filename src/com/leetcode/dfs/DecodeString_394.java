package com.leetcode.dfs;

import com.sun.javafx.css.CssError;

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

}
