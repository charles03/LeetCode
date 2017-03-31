package com.leetcode.bitwise;

/**
 * Created by charles on 2/8/17.
 * Write a function that takes an unsigned integer and returns the number of ’1' bits it has (also known as the Hamming weight).

 For example, the 32-bit integer ’11' has binary representation 00000000000000000000000000001011, so the function should return 3.
 */
public class NumberOf1Bits_191 {
    /**
     * remove lowest first one bit from number n
     * while n != 0 instead of n > 0 is because int n in java is signed
     * Let's use n = 00101100 as an example. This binary representation has three 1s.

     If n = 00101100, then n - 1 = 00101011, so n & (n - 1) = 00101100 & 00101011 = 00101000. Count = 1.

     If n = 00101000, then n - 1 = 00100111, so n & (n - 1) = 00101000 & 00100111 = 00100000. Count = 2.

     If n = 00100000, then n - 1 = 00011111, so n & (n - 1) = 00100000 & 00011111 = 00000000. Count = 3.

     n is now zero, so the while loop ends, and the final count (the numbers of set bits) is returned.
     */
    public int hammingWeight(int n) {
        int count = 0;
        while (n != 0) {
            n &= n - 1;
            count++;
        }
        return count;
    }

    /**
     * right shift original n
     * if last bit is 1 and & operation 1 is one,
     * then current bit in n is one, count plus one
     */
    public int hammingWeightII(int n) {
        int count = 0;
        while (n != 0) {
            count += n & 1;
            n >>>= 1;
        }
        return count;
    }

    // This is a naive implementation, shown for comparison, and to help in understanding the better functions.
// It uses 24 arithmetic operations (shift, add, and).
    int hammingWeightIII(int n)
    {
        n = (n & 0x55555555) + (n >>  1 & 0x55555555); // put count of each  2 bits into those  2 bits
        n = (n & 0x33333333) + (n >>  2 & 0x33333333); // put count of each  4 bits into those  4 bits
        n = (n & 0x0F0F0F0F) + (n >>  4 & 0x0F0F0F0F); // put count of each  8 bits into those  8 bits
        n = (n & 0x00FF00FF) + (n >>  8 & 0x00FF00FF); // put count of each 16 bits into those 16 bits
        n = (n & 0x0000FFFF) + (n >> 16 & 0x0000FFFF); // put count of each 32 bits into those 32 bits
        return n;
    }

    // This uses fewer arithmetic operations than any other known implementation on machines with slow multiplication.
// It uses 17 arithmetic operations.
    int hammingWeightIV(int n)
    {
        n -= (n >> 1) & 0x55555555; //put count of each 2 bits into those 2 bits
        n = (n & 0x33333333) + (n >> 2 & 0x33333333); //put count of each 4 bits into those 4 bits
        n = (n + (n >> 4)) & 0x0F0F0F0F; //put count of each 8 bits into those 8 bits
        n += n >> 8; // put count of each 16 bits into those 8 bits
        n += n >> 16; // put count of each 32 bits into those 8 bits
        return n & 0xFF;
    }

    // This uses fewer arithmetic operations than any other known implementation on machines with fast multiplication.
// It uses 12 arithmetic operations, one of which is a multiply.
    int hammingWeightV(int n)
    {
        n -= (n >> 1) & 0x55555555; // put count of each 2 bits into those 2 bits
        n = (n & 0x33333333) + (n >> 2 & 0x33333333); // put count of each 4 bits into those 4 bits
        n = (n + (n >> 4)) & 0x0F0F0F0F; // put count of each 8 bits into those 8 bits
        return n * 0x01010101 >> 24; // returns left 8 bits of x + (x<<8) + (x<<16) + (x<<24)
    }

}
