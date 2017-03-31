package com.leetcode.bitwise;

/**
 * Created by charles on 2/25/17.
 * Given a non negative integer number num. For every numbers i in the range 0 ≤ i ≤ num calculate the number of 1's in their binary representation and return them as an array.

 Example:
 For num = 5 you should return [0,1,1,2,1,2].

 Follow up:

 It is very easy to come up with a solution with run time O(n*sizeof(integer)). But can you do it in linear time O(n) /possibly in a single pass?
 Space complexity should be O(n).
 Can you do it like a boss? Do it without using any builtin function like __builtin_popcount in c++ or in any other language.
 Hint:

 You should make use of what you have produced already.
 Divide the numbers in ranges like [2-3], [4-7], [8-15] and so on. And try to generate new range from previous.
 Or does the odd/even status of the number help you in calculating the number of 1s?
 */
public class CountingBits_338 {
    public int[] countBits(int num) {
        int[] res = new int[num + 1];
        res[0] = 0;
        int pow = 1;
        int j = 0;
        for (int i = 1; i <= num; i++) {
            /**
             * change range based on power of two
             * [8~15] -> 8 + [0~7]
             * use bit count[8 ~ 15] = 1 + count[0~7]
             */
            if (i == pow) {
                pow *= 2;
                j = 0;
            }
            res[i] = res[j] + 1;
        }
        return res;
    }
    /** simplified version by bitwise operation */
    public int[] countBitsII(int nums) {
        int[] res = new int[nums + 1];
        res[0] = 0;
        for (int i = 1; i <= nums; i++) {
            /**
             * i & i-1 is to find diff of bit count of "1" is one
             * example
             *   1100
             * & 1011
             *   1000 -> ans[8] + 1 is bit count of ans[12]
             */
//            System.out.println(i & i-1);
            res[i] = res[i & i-1] + 1;
        }
        return res;
    }

    public static void main(String[] args) {
        CountingBits_338 c = new CountingBits_338();
        c.countBitsII(8);
    }
}
