package com.leetcode.string;

/**
 * Created by charles on 5/16/17.
 * Given two strings representing two complex numbers.

 You need to return a string representing their multiplication. Note i2 = -1 according to the definition.

 Example 1:
 Input: "1+1i", "1+1i"
 Output: "0+2i"
 Explanation: (1 + i) * (1 + i) = 1 + i2 + 2 * i = 2i, and you need convert it to the form of 0+2i.
 Example 2:
 Input: "1+-1i", "1+-1i"
 Output: "0+-2i"
 Explanation: (1 - i) * (1 - i) = 1 + i2 - 2 * i = -2i, and you need convert it to the form of 0+-2i.
 Note:

 The input strings will not have extra blank.
 The input strings will be given in the form of a+bi, where the integer a and b will both belong to the range of [-100, 100]. And the output should be also in this form.
 */
public class ComplexNumberMultiplication_537 {
    public String complexNumberMultiply(String a, String b) {
        String pattern = "\\+|i";
        String[] x = a.split(pattern);
        String[] y = b.split(pattern);

        int real1 = Integer.parseInt(x[0]);
        int img1 = Integer.parseInt(x[1]);
        int real2 = Integer.parseInt(y[0]);
        int img2 = Integer.parseInt(y[0]);

        int real = real1 * real2 - img1 * img2;
        int image = real1 * img2 + real2 * img1;

        return real + "+" + image + "i";
    }
}
