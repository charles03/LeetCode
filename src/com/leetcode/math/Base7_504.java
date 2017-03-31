package com.leetcode.math;

/**
 * Created by charles on 3/28/17.
 * same as {@link ExcelSheetColumnNumber_171}
 * Given an integer, return its base 7 string representation.

 Example 1:
 Input: 100
 Output: "202"
 Example 2:
 Input: -7
 Output: "-10"
 */
public class Base7_504 {
    /**
     * 100 % 7 -> 0 -> lowest digit
     * 100 / 7 -> 14 -> high digits
     * 14 % 7 -> 0 ->lower digit
     * 14 / 7 -> 2 -> higher digit
     */
    public String convertToBase7(int num) {
        StringBuilder sb = new StringBuilder();
        if (num == 0) {
            return "0";
        }
        int abs = Math.abs(num);
        while (abs != 0) {
            sb.insert(0, abs % 7);
            abs /= 7;
        }
        if (num < 0) {
            sb.insert(0, "-");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        Base7_504 b = new Base7_504();
        int n1 = -8;
        System.out.println(b.convertToBase7(n1).equals(Integer.toString(n1, 7)));
        System.out.println(b.convertToBase7(-7));
    }
}
