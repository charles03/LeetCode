package com.leetcode.bitwise;

/**
 * Created by charles on 2/9/17.
 * Given an integer, write an algorithm to convert it to hexadecimal. For negative integer, two’s complement method is used.

 Note:

 All letters in hexadecimal (a-f) must be in lowercase.
 The hexadecimal string must not contain extra leading 0s. If the number is zero, it is represented by a single zero character '0'; otherwise, the first character in the hexadecimal string will not be the zero character.
 The given number is guaranteed to fit within the range of a 32-bit signed integer.
 You must not use any method provided by the library which converts/formats the number to hex directly.
 Example 1:

 Input:
 26

 Output:
 "1a"
 Example 2:

 Input:
 -1

 Output:
 "ffffffff"
 */
public class ConvertNumberToHexadecimal_405 {
    public String toHex(int num) {
        if (num < 10 && num >= 0) {
            return Integer.toString(num);
        }
        char[] hex = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};
        StringBuilder sb = new StringBuilder();

        while (num != 0) {
            sb.insert(0, hex[num & 15]);
            num >>>= 4; // should use right unsigned shift instead of signed shift
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        ConvertNumberToHexadecimal_405 c = new ConvertNumberToHexadecimal_405();
        System.out.println(c.toHex(-1));
    }
}
