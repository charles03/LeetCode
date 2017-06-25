package com.leetcode.string;

import javax.lang.model.element.NestingKind;

/**
 * Created by charles on 4/10/17.
 * Given two binary strings, return their sum (also a binary string).

 For example,
 a = "11"
 b = "1"
 Return "100".
 */
public class AddBinary_67 {
    public String addBinary(String a, String b) {
        int i = a.length() - 1, j = b.length() - 1;
        int carry = 0;
        StringBuilder sb = new StringBuilder();
        int val = 0;
        int aVal = 0, bVal = 0;
        while (i >= 0 || j >= 0) {
            if (i >= 0) {
                aVal = a.charAt(i) - '0';
            } else {
                aVal = 0;
            }
            if (j >= 0) {
                bVal = b.charAt(j) - '0';
            } else {
                bVal = 0;
            }
            val =  aVal ^ bVal;
            sb.insert(0, (val ^ carry));
            carry = (aVal + bVal + carry) / 2;
            i--;
            j--;
        }
        if (carry != 0) {
            sb.insert(0, carry);
        }
        return sb.toString();
    }

    public String addBinaryII(String a, String b) {
        int i = a.length() - 1, j = b.length() - 1;
        int carry = 0;
        int aVal = 0, bVal = 0;
        StringBuilder sb = new StringBuilder();

        while (i >= 0 || j >= 0) {
            aVal = i>=0 ? a.charAt(i) - '0' : 0;
            bVal = j>=0 ? b.charAt(j) - '0' : 0;
            sb.insert(0, aVal ^ bVal ^ carry);
            carry = (aVal + bVal + carry) >> 1;
            i--;
            j--;
        }
        if (carry != 0) {
            sb.insert(0, carry);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        AddBinary_67 a = new AddBinary_67();
        String s1 = "111";
        String s2 = "1";
        System.out.println(a.addBinary(s1, s2));
    }
}
