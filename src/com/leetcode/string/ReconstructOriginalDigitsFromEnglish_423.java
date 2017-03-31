package com.leetcode.string;

import java.util.Arrays;

/**
 * Created by charles on 3/2/17.
 * Given a non-empty string containing an out-of-order English representation of digits 0-9, output the digits in ascending order.

 Note:
 Input contains only lowercase English letters.
 Input is guaranteed to be valid and can be transformed to its original digits. That means invalid inputs such as "abc" or "zerone" are not permitted.
 Input length is less than 50,000.
 Example 1:
 Input: "owoztneoer"

 Output: "012"
 Example 2:
 Input: "fviefuro"

 Output: "45"
 */
public class ReconstructOriginalDigitsFromEnglish_423 {

    /**
     * only need to count the unique letter of each word, because input is always valid
     */
    public String originalDigits(String s) {
        int[] count = new int[10];
        int len = s.length();
        for (int i = 0; i < len; i++) {
            identifyDigitByUniqueChar(s.charAt(i), count);
        }
        output(count);
        count[7] -= count[6];
        count[5] -= count[4];
        count[3] -= count[8];
        count[9] = count[9] - count[8] - count[6] - count[5];
        count[1] = count[1] - count[0] - count[2] - count[4];

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i <= 9; i++) {
            for (int j = 0; j < count[i]; j++) {
                sb.append(i);
            }
        }
        return sb.toString();
    }

    private void identifyDigitByUniqueChar(char c, int[] count) {
        switch (c) {
            case 'z' : count[0]++; // zero -> z is unique
                break;
            case 'w' : count[2]++; // two -> w is unique
                break;
            case 'u' : count[4]++; // four
                break;
            case 'x' : count[6]++; // six
                break;
            case 'g' : count[8]++; // eight
                break;
            case 's' : count[7]++; // seven & six, thus real count of 7 is current cnt[7] - cnt[6]
                break;
            case 'f' : count[5]++; // five & four
                break;
            case 'h' : count[3]++; // three & eight
                break;
            case 'i' : count[9]++; // nine & eight & six & five
                break;
            case 'o' : count[1]++; // one & two & four & zero
                break;
        }
    }

    private void output(int[] nums) {
        for (int n : nums) {
            System.out.print(n + ",");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        ReconstructOriginalDigitsFromEnglish_423 r = new ReconstructOriginalDigitsFromEnglish_423();
        System.out.println(r.originalDigits("owoztneoer"));
        System.out.println(r.originalDigitsII("owoztneoer"));
    }

    private static String[] digits = {"zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};
    private static char[] ids = {'z', 'o', 'w', 'h', 'u', 'f', 'x', 's', 'g', 'i'};
    private static int[] unique = {0,2,4,6,8};
    private static int[] composite = {1,3,5,7,9};

    public String originalDigitsII(String s) {
        int[] digitCnt = new int[10];
        int[] letterCnt = new int[26];
        // get corresponding occurrence of each letter
        for (char c : s.toCharArray()) {
            letterCnt[c - 'a']++;
        }
        for (int idx : unique) {
            digitCnt[idx] = letterCnt[ids[idx] - 'a'];
            for (char c : digits[idx].toCharArray()) {
                letterCnt[c - 'a'] -= digitCnt[idx];
            }
        }

        for (int idx : composite) {
            digitCnt[idx] = letterCnt[ids[idx] - 'a'];
            for (char c : digits[idx].toCharArray()) {
                letterCnt[c - 'a'] -= digitCnt[idx];
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < digitCnt[i]; j++) {
                sb.append(i);
            }
        }
        return sb.toString();
    }
}
