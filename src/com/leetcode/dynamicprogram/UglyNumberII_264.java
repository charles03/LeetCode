package com.leetcode.dynamicprogram;

/**
 * Created by charles on 11/22/16.
 * Write a program to find the n-th ugly number.
 Ugly numbers are positive numbers whose prime factors only include 2, 3, 5. For example, 1, 2, 3, 4, 5, 6, 8, 9, 10, 12 is the sequence of the first 10 ugly numbers.
 Note that 1 is typically treated as an ugly number.

 Thought: brute force not efficient, next ugly number only derived from previous ugly number
 should only focus on ugly num, which non ugly can be ignored

 State: U[k] is kth ugly number. next2 is first ugly num larger than U[k] by prev ugly time 2
 likewise, next3 and next5
 Funciton: dp[k+1] = min(next2, next3, next5)
 initial next2 = 2, next3 = 3, next5 = 5

 Optimization: for base 2, record the position index of previous ugly number where first larger than U[k];
 update position index everytime when calculate new ugly num
 same as for base 3 and base 5
 */
public class UglyNumberII_264 {
    public int nthUglyNumber(int n) {
        // use array to record
        int[] uglyNum = new int[n];
        // init
        uglyNum[0] = 1;
        // position indexes to record under case
        // it must exist ugly number U2 under base 2, have position p2 in ugly num array
        // in uglynum array, any index before p2, ugly[~p2] * 2  must be smaller than current ugly num U[k]
        // any index after p2, ugly[p2~] * 2 must be larger than U[k]
        int p2 = 1, p3 = 1, p5 = 1;
        // next ugly num larger then current by different bases
        int next2 = 2, next3 = 3, next5 = 5;

        for (int i = 1; i < n; i++) {
            uglyNum[i] = Math.min(Math.min(next2, next3), next5);

            if (uglyNum[i] == next2) {
                // update pos index as well as generate new ugly num
                next2 = uglyNum[p2++] * 2;
            }
            if (uglyNum[i] == next3) {
                next3 = uglyNum[p3++] * 3;
            }
            if (uglyNum[i] == next5) {
                next5 = uglyNum[p5++] * 5;
            }
        }
        return uglyNum[n - 1];
    }

    public static void main(String[] args) {
        UglyNumberII_264 u = new UglyNumberII_264();
        System.out.println(u.nthUglyNumber(10));
    }
}
