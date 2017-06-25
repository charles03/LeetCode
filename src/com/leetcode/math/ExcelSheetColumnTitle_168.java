package com.leetcode.math;

/**
 * Created by charles on 4/12/17.
 * Given a positive integer, return its corresponding column title as appear in an Excel sheet.

 For example:

 1 -> A
 2 -> B
 3 -> C
 ...
 26 -> Z
 27 -> AA
 28 -> AB
 */
public class ExcelSheetColumnTitle_168 {
    private char[] letters = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};

    public String convertToTitle(int n) {
        if (n < 1) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        while (n > 0) {
            n -= 1;
            sb.insert(0, letters[n % 26]);
            n /= 26;
        }
        return sb.toString();
    }
    public static void main(String[] args) {
        ExcelSheetColumnTitle_168 e = new ExcelSheetColumnTitle_168();
        System.out.println(e.convertToTitle(677));
        System.out.println(e.convertToTitle(28));
    }
}
