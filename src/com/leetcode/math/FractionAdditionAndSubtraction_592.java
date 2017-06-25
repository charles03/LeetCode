package com.leetcode.math;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by charles on 6/8/17.
 * Given a string representing an expression of fraction addition and subtraction, you need to return the calculation result in string format. The final result should be irreducible fraction. If your final result is an integer, say 2, you need to change it to the format of fraction that has denominator 1. So in this case, 2 should be converted to 2/1.

 Example 1:
 Input:"-1/2+1/2"
 Output: "0/1"
 Example 2:
 Input:"-1/2+1/2+1/3"
 Output: "1/3"
 Example 3:
 Input:"1/3-1/2"
 Output: "-1/6"
 Example 4:
 Input:"5/3+1/3"
 Output: "2/1"
 Note:
 The input string only contains '0' to '9', '/', '+' and '-'. So does the output.
 Each fraction (input and output) has format ±numerator/denominator. If the first input fraction or the output is positive, then '+' will be omitted.
 The input only contains valid irreducible fractions, where the numerator and denominator of each fraction will always be in the range [1,10]. If the denominator is 1, it means this fraction is actually an integer in a fraction format defined above.
 The number of given fractions will be in the range [1,10].
 The numerator and denominator of the final result are guaranteed to be valid and in the range of 32-bit int.
 */
public class FractionAdditionAndSubtraction_592 {
    /**
     * 1. save all signs in sequence
     * 2. split by signs, then by slash
     * 3. use gcd (greatest common divisor)
     */
    public String fractionAddition(String expression) {
        List<Character> signs = new ArrayList<>();
        if (expression.charAt(0) != '-') {
            signs.add('+');
        }
        for (int i = 0; i < expression.length(); i++) {
            if (expression.charAt(i) == '+' || expression.charAt(i) == '-') {
                signs.add(expression.charAt(i));
            }
        }

        /**
         * a/b + c/d
         * b = gcd(b,d) * e, d = gcd(b,d) * f
         *
         * af/bf + ce/de = (af + ce)/ gcd(b,d) * ef
         */
        int a = 0, b = 1;
        int c = 0, d = 0;
        int gcd = 0;
        int i = 0;
        for (String sub : expression.split("(\\+)|(-)")) {
            if (sub.length() == 0) {
                continue;
            }
            String[] fraction = sub.split("/");
            c = Integer.parseInt(fraction[0]);
            d = Integer.parseInt(fraction[1]);
            gcd = Math.abs(greatestCommonDivisor(b,d));

            a = getNumerator(a * d / gcd, c * b / gcd, signs.get(i++));
            b = b * d / gcd;
            // reduce fraction by divide gcd()
            gcd = Math.abs(greatestCommonDivisor(a,b));
            a /= gcd;
            b /= gcd;
        }
        return a + "/" + b;
    }
    private int greatestCommonDivisor(int a, int b) {
        int tmp = 0;
        while (b != 0) {
            tmp = b;
            b = a % b;
            a = tmp;
        }
        return tmp;
    }

    private int getNumerator(int a, int b, char sign) {
        if (sign == '+') {
            return a + b;
        } else {
            return a - b;
        }
    }
}
