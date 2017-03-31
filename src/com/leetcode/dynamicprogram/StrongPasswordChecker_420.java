package com.leetcode.dynamicprogram;

/**
 * Created by charles on 3/3/17.
 * A password is considered strong if below conditions are all met:

 It has at least 6 characters and at most 20 characters.
 It must contain at least one lowercase letter, at least one uppercase letter, and at least one digit.
 It must NOT contain three repeating characters in a row ("...aaa..." is weak, but "...aa...a..." is strong, assuming other conditions are met).
 Write a function strongPasswordChecker(s), that takes a string s as input, and return the MINIMUM change required to make s a strong password. If s is already strong, return 0.

 Insertion, deletion or replace of any one character are all considered as one change.
 */
public class StrongPasswordChecker_420 {
    /**
     * Define 3 sub-problems
     * 1. length, if len < 6: insert, or len >20, delete
     * 2. missing char or digit : can be done with replacing or inserting
     * 3. repeating over 3, most efficient way is replacing,
     *
     * The goal is to solve as many problem as possible at the same time by insert, delete and replace
     */
    public int strongPasswordChecker(String s) {
        int res = 0;
        int lower = 1, upper = 1, digit = 1; //  init, '1' is missing

        char[] chars = s.toCharArray();
        int len = chars.length;
        int[] repeatCounts = new int[len];

        int i = 0;
        int j = 0; // pointer to go through all repeated chars
        while (i < len) {
            if (Character.isLowerCase(chars[i])) {
                lower = 0; // '0' is meeting condition
            }
            if (Character.isUpperCase(chars[i])) {
                upper = 0;
            }
            if (Character.isDigit(chars[i])) {
                digit = 0;
            }
            // at the same to record down the 3 repeating chars in string
            j = i;
            while (i < len && chars[i] == chars[j]) {
                i++;
            }
            repeatCounts[j] = i - j;
        }

        int missing = lower + upper + digit;
        if (len < 6) {
            /** len < 6, can be covered by first two sub problems */
            res += missing + Math.max(0, 6 - (len + missing));
        } else {
            res = handleRepeatOverLength(repeatCounts, len, missing);
        }
        return res;
    }

    private int handleRepeatOverLength(int[] repeat, int len, int missing) {
        int cnt = 0;
        int overLen = Math.max(len - 20, 0);
        int leftOver = 0;

        /** anyway should shrink char less than 20 */
        cnt += overLen;
        /** and then to see when those removed chars can resolve repeating problem */
        /**
         * To delete char in repeating until len is (3*m + 2)
         * How we get (3*m + 2), if origin repeating len is k, can be %3 = (i-1) when i = 1
         * then len after is k - i
         * if origin repeating len is k, can be %3 = (i-1) when i = 2
         * then len after is k - i;
         */
        for (int i = 1; i < 3; i++) {
            for (int j = 0; j < len && overLen > 0; j++) {
                if (repeat[j] < 3 || repeat[j] % 3 != (i-1)) {
                    continue;
                }
                repeat[j] -= i;
                overLen -= i; // over len should be decreased correspondingly
            }
        }
        /**
         * if overLen > 0, continue remove chars, each time remove 3m, only keep 2 chars
         */
        for (int i = 0; i < len; i++) {
            if (repeat[i] >= 3 && overLen > 0) {
                int remove = repeat[i] - 2; // only keep 2 chars
                repeat[i] -= overLen;
                overLen -= remove;
            }
            /** if still having repeating chars, then apply replacing,
             * the count should repeat[i] / 3, and then add into left over
             */
            if (repeat[i] >= 3) {
                leftOver += repeat[i] / 3;
            }
        }
        /** need to compare leftover with missing condition, and then add into return result */
        cnt += Math.max(missing, leftOver);
        return cnt;
    }
}
