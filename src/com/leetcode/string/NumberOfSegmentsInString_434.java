package com.leetcode.string;

/**
 * Created by charles on 3/2/17.
 * Count the number of segments in a string, where a segment is defined to be a contiguous sequence of non-space characters.

 Please note that the string does not contain any non-printable characters.

 Example:

 Input: "Hello, my name is John"
 Output: 5
 */
public class NumberOfSegmentsInString_434 {
    public int countSegments(String s) {
        int res = 0;
        int len = s.length();
        for (int i = 0; i < len; i++) {
            if (s.charAt(i) != ' ' && (i == 0 || s.charAt(i-1) == ' ')) {
                res++;
            }
        }
        return res;
    }

    public int countSegmentsII(String s) {
        s = s.trim();
        if (s.length() == 0) {
            return 0;
        }
        return s.split("\\s+").length;
    }
}
