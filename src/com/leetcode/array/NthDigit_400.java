package com.leetcode.array;

/**
 * Created by charles on 3/9/17.
 * Find the nth digit of the infinite integer sequence 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, ...

 Note:
 n is positive and will fit within the range of a 32-bit signed integer (n < 231).

 Example 1:

 Input:
 3

 Output:
 3
 Example 2:

 Input:
 11

 Output:
 0

 Explanation:
 The 11th digit of the sequence 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, ... is a 0, which is part of the number 10.

 */
public class NthDigit_400 {
    public int findNthDigit(int n) {
        int k = 1;
        long base = 9;
        int start = 1;
        while (n > k * base) {
            n -= k * base;
            base *= 10;
            start *= 10;
            k++;
        }
        n--;
        start += n / k;
        // start is the final number which contain corresponding nth digit
        // n % k is the index of that digit in final number
        return Integer.toString(start).charAt(n % k) - '0';
    }

    public int findNthDigitII(int n) {
        if (n <= 9) {
            return n;
        }
        int base = 1;
        // to Determine Range
        // 10,11,...99: 90 * 2 digits in total base = 2
        // 101,102...999: 900 * 2 digits in total base = 3

        double range = 9 * Math.pow(10, base-1) * base;
        while (n > range) {
            n -= (long)range;
            base++;
            range = 9 * Math.pow(10, base-1) * base;
        }

        // Now we should find out which number the answer follows,
        // if the input is 15, the answer should follow on number "12", that's the variable number for.
        int number = (int) Math.pow(10, base-1) + (n-1) / base;
        // Then we should find out which specific in the number "12". that's what index for, for input 15, index = 0
        int index = (n - 1) % base;

        // The answer is the index-th digit of the variable number
        return Integer.toString(number).charAt(index) - '0';
    }
}
