package com.leetcode.string;

/**
 * Created by charles on 4/7/17.
 * The count-and-say sequence is the sequence of integers beginning as follows:
 1, 11, 21, 1211, 111221, ...

 1 is read off as "one 1" or 11.
 11 is read off as "two 1s" or 21.
 21 is read off as "one 2, then one 1" or 1211.
 Given an integer n, generate the nth sequence.
 */
public class CountAndSay_38 {
    /** n is counter, each time call function to count and say */
    public String countAndSay(int n) {
        String prev = "1";
        char[] arr = null;
        while (n > 1) {
            arr = prev.toCharArray();
            prev = toCount(arr);
            n--;
        }
        return prev;
    }
    private String toCount(char[] chars) {
        StringBuilder sb = new StringBuilder();
        int len = chars.length;
        for (int i = 0; i < len; i++) {
            int count = 1;
            while (i + 1 < len && chars[i] == chars[i+1]) {
                count++;
                i++;
            }
            sb.append(count).append(chars[i]);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        CountAndSay_38 c = new CountAndSay_38();
        System.out.println(c.countAndSay(4));
        System.out.println(c.countAndSay(6));
    }
}
