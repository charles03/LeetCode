package com.leetcode.bitwise;

/**
 * Created by charles on 2/8/17.
 * The Hamming distance between two integers is the number of positions at which the corresponding bits are different.

 Given two integers x and y, calculate the Hamming distance.

 Note:
 0 ≤ x, y < 231.

 Example:

 Input: x = 1, y = 4

 Output: 2

 Explanation:
 1   (0 0 0 1)
 4   (0 1 0 0)
 ↑   ↑

 The above arrows point to positions where the corresponding bits are different
 */
public class HammingDistance_461 {
    /**
     * example
     * x : 001001
     * y : 101011
     *   : 100010 --> to count bit 1 left over after x XOR y
     */
    public int hammingDistanceByBuiltin(int x, int y) {
        return Integer.bitCount(x ^ y);
    }

    public int hammingDistance(int x, int y) {
        int count = 0;
        int diff = x ^ y;

        while (diff != 0) {
            /**
             * x &= x-1 each time remove digit '1' from lower to high
             */
            diff &= diff - 1;
            count++;
        }
        return count;
    }
}
