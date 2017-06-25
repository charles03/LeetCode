package com.leetcode.bitwise;

/**
 * Created by charles on 5/31/17.
 * Given a positive integer n, find the number of non-negative integers less than or equal to n, whose binary representations do NOT contain consecutive ones.

 Example 1:
 Input: 5
 Output: 5
 Explanation:
 Here are the non-negative integers <= 5 with their corresponding binary representations:
 0 : 0
 1 : 1
 2 : 10
 3 : 11
 4 : 100
 5 : 101
 Among them, only integer 3 disobeys the rule (two consecutive ones) and the other 5 satisfy the rule.

 */
public class NonNegativeIntWithoutConsecutiveOnes_600 {
    /**
     * Brute force solution:
     * for range [0.. num]
     * check each bit of current number in the range
     * if there is mismatching requirement where exists consecutive ones
     * then skip num;
     *
     * time complexity O(31 * n), n refers to given number
     */
    public int findIntegers(int num) {
        int count = 0;
        for (int i = 0; i <= num; i++) {
            if (!isContainConsecutiveOne(i)) {
                count++;
            }
        }
        return count;
    }
    private boolean isContainConsecutiveOne(int n) {
        int i = 31;
        while (i > 0) {
            if ((n & (1 << i)) != 0
                    && (n & (1 << (i-1))) != 0) {
                return true;
            }
            i--;
        }
        return false;
    }

    /**
     * DP solution:
     * suppose f[i] gives the count of such binary numbers with i bits
     * in order to determine the value of f[n]
     * function : f[n] = f[n-1] + f[n-2]
     * f[n-1] is append 0 at tail
     * f[n-2] is append 01 at tail
     * in order to meet requirement where no consecutive 1's
     *
     * And then, take below two examples, number in binary format
     * 1. num = 1010100
     * start off with highest non-zero digit MSB of nums (most significant bit)
     * if we fill 0 at the MSB position,
     * find out count of 6 bit num with no two consecutive 1's
     * 6-bit numbers will sit in the range 0000000 -> 0111111 <= f[6]
     * and then we fill MSB with 1 so as to cover large number beyond 1000000
     *
     * in order not to exceed given number.
     * the range can only be in 1000000 -> 1010100,
     * thus we can't fix 1 at MSB and consider all 6-bit number at the LSB (least significant bit)
     *
     * for covering pending range, we fix 1 at the MSB and move to proceed with second digit
     * Because second digit at given number is 0,
     * we cannot substitute 1 at second pos.
     * thus new numbers will fall in range (10)00000-> (10)11111
     * but again we observe that considering number leads to exceed required range
     * so cannot use f[5] directly to cover all 5-bit
     * thus we have to fill 0 at the third pos and proceed further
     * the find out count of 4-bit without consecutive 1's
     * now number in the range (100)0000 -> (100)1111 <= f[4]
     * and then substitute 1 at third pos to look for 3-bit range
     * (10101)00 -> (10101)11
     *
     * based on above procedure, numbers in the range
     * 0000000 -> 0111111, 1000000-> 1001111, 101000->1010011 have been considered
     * now only 1010100 is pending, so add 1 to f[6] + f[4] + f[2];
     *
     */

    /**
     * 2. num = 1011010
     * when number contain some consecutive 1's
     * start with MSB, find first 1 from left
     * initially fix 0 at MSB to consider range 0000000-> 0111111 <= f[6]
     * then fix 1 at MSB, move on to second bit,
     * it's 0 at given number, so no choice but to fix 0 at this pos
     * to proceed 3rd pos, it is 1, so fix 0 at this pos
     * so as to consider range (100)0000 -> (100)1111 <= f[4]
     * then fix 1 at 3rd pos, to proceed 4th pos
     * because 4th bit in given number is 1 (also it is consecutive 1 with previous 3rd pos)
     * now we initially fix 0 at the 4th pos,
     * to consider range (1010)000 -> (1010)111 <= f[3]
     *
     * for now we consider below range
     * 0000000->0111111, 1000000->1001111, 1010000 -> 1010111,
     * but if we try to consider any number larger than 1010111, like 1011000,
     * it leads to presence of two consecutive 1's in new number
     * Thus valid number upto given number have been consider in the range till 1010111
     * a resultant count is f[6] + f[4] + f[3]
     */
    public int findIntegerII(int num) {
        int[] f = new int[32];
        f[0] = 1;
        f[1] = 2;
        // to get dp count array f[i] is count when index from len(f) till i.
        for (int i = 2; i < 32; i++) {
            f[i] = f[i-1] + f[i-2];
        }
        // MSB is 31, so index init with 30
        int k = 30;
        int count = 0;
        int prev_bit = 0;

        while (k >= 0) {
            // when MSB is 1 at k+1 pos from tail, to get count of valid number in the range less than 1 with k numbers of 0's
            if ((num & (1 << k)) != 0) {
                count += f[k];
                if (prev_bit == 1) {
                    count--; // for convenience, without separate handling
                    break; // termiate loop directly when consecutive 1's
                }
                prev_bit = 1; // set 1 at k+1 index from tail.
            } else {
                prev_bit = 0;
            }
            k--; // move to next bit;
        }
        return count + 1; // for both without and with consecutive 1's
    }
}
