package com.leetcode.string;

/**
 * Created by charles on 3/7/17.
 * Given two non-negative integers num1 and num2 represented as string, return the sum of num1 and num2.

 Note:

 The length of both num1 and num2 is < 5100.
 Both num1 and num2 contains only digits 0-9.
 Both num1 and num2 does not contain any leading zero.
 You must not use any built-in BigInteger library or convert the inputs to integer directly.
 */
public class AddStrings_415 {
    public String addStrings(String num1, String num2) {
        int len1 = num1.length() - 1;
        int len2 = num2.length() - 1;

        int carry = 0;
        char[] n1Array = num1.toCharArray();
        char[] n2Array = num2.toCharArray();

        StringBuilder sb = new StringBuilder();
        int x = 0;
        int y = 0;
        int sum = 0;
        while (len1 >= 0 || len2 >= 0 || carry == 1) {
            x = len1 >= 0 ? (n1Array[len1] - '0') : 0;
            y = len2 >= 0 ? (n2Array[len2] - '0') : 0;
            sum = x + y + carry;
            sb.insert(0, sum % 10);
            carry = sum / 10;
            len1--;
            len2--;
        }
        return sb.toString();
    }

    public String addStringsII(String n1, String n2) {
        if (n1.length() > n2.length()) {
            return addStringsII(n2, n1); // suppose n1 is shorter
        }
        char[] arr1 = n1.toCharArray();
        char[] arr2 = n2.toCharArray();
        int l1 = arr1.length;
        int l2 = arr2.length;
        int carry = 0;

        int idx1 = 0;
        int idx2 = 0;
        for (int i = 0; i < l2; i++) {
            idx1 = l1 - i - 1;
            idx2 = l2 - i - 1;
            if (idx1 >= 0) {
                arr2[idx2] += (arr1[idx1] - '0' + carry);
            } else if (carry == 0) {
                break;
            } else {
                arr2[idx2] += carry;
            }

            if (arr2[idx2] > '9') {
                carry = 1;
                arr2[idx2] -= 10;
            } else {
                carry = 0;
            }
        }
        if (carry == 1) {
            return "1".concat(String.valueOf(arr2));
        } else {
            return String.valueOf(arr2);
        }
    }
}
