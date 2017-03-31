package com.leetcode.bitwise;

/**
 * Created by charles on 3/23/17.
 * Given a positive integer, output its complement number. The complement strategy is to flip the bits of its binary representation.

 Note:
 The given integer is guaranteed to fit within the range of a 32-bit signed integer.
 You could assume no leading zero bit in the integer’s binary representation.
 Example 1:
 Input: 5
 Output: 2
 Explanation: The binary representation of 5 is 101 (no leading zero bits), and its complement is 010. So you need to output 2.
 Example 2:
 Input: 1
 Output: 0
 Explanation: The binary representation of 1 is 1 (no leading zero bits), and its complement is
 */
public class NumberComplement_476 {
    /**
     * High level: current binary expression plus complement -> all 1's which closest to num
     * 101 --- num
     * 010 --- complement
     * 111 --- sum (until highest 1's in num)
     */
    public int findComplement(int num) {
        /** revert to how to generate all 1's binary until high digit of num */
        int n = 0;
        while (n < num) {
            n = n << 1 | 1; // left shift 1 and also add tail 1
        }
        return n - num;
    }

    public int findComplementII(int num) {
        /** mask of num 101 -> ~num give ....1111010, when only care about rightmost 3 bits
         * then add 1000 to erase 1's before 010;
         */
        return ~num + (Integer.highestOneBit(num) << 1);
    }
}
