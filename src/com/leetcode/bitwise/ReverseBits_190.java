package com.leetcode.bitwise;

/**
 * Created by charles on 2/8/17.
 * Reverse bits of a given 32 bits unsigned integer.

 For example, given input 43261596 (represented in binary as 00000010100101000001111010011100), return 964176192 (represented in binary as 00111001011110000010100101000000).

 Follow up:
 If this function is called many times, how would you optimize it?
 */
public class ReverseBits_190 {
    public int reverseBits(int n) {
        int res = 0;
        // 32 bit int
        for (int i = 0; i < 32; i++) {
            res += n & 1; // add bit one from lower
            n >>>= 1; // right unsigned shift for origin
            if (i < 31) {
                res <<= 1; // not shift when hit last digit
            }
        }
        return res;
    }

    /**
     * how to optimize when call function multiple times
     * divide int into 4 bytes, reverse each byte then combine into int
     * for each byte, we can use cahce to improve performance
     */
    private int[] reverseHex = new int[] {0,8,4,12,2,10,6,14,1,9,5,13,3,11,7,15};

    public int reverseBitsII(int n) {
        int res = 0;
        int mask = 0xF;
        int currIdx = 0;
        for (int i = 0; i < 8; i++) {
            res <<= 4;
            currIdx = n & mask;
            res |= reverseHex[currIdx];
            n >>>= 4;
        }
        return res;
    }

    public int reverseBitsIII(int n) {
        int res = n;
        res = res >>> 16 | res << 16;
        res = (res & 0xFF00FF00) >>> 8 | (res & 0x00FF00FF) << 8;
        res = (res & 0xF0F0F0F0) >>> 4 | (res & 0x0F0F0F0F) << 4;
        res = (res & 0xCCCCCCCC) >>> 2 | (res & 0x33333333) << 2;
        res = (res & 0xAAAAAAAA) >>> 1 | (res & 0x55555555) << 1;
        return res;
    }

    public int reverseBitsIV(int n) {
        return Integer.reverse(n);
    }
}
