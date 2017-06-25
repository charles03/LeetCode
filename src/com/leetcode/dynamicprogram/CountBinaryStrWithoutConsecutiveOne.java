package com.leetcode.dynamicprogram;

/**
 * Created by charles on 5/31/17.
 * Given a positive integer N,
 * count all possible distinct binary strings of length N such that there are no consecutive 1’s.
 */
public class CountBinaryStrWithoutConsecutiveOne {
    /**
     * DP solution:
     * State: a[i] is count of binary strings until index i
     * which do not contain any two consecutive 1's and also ending with 0's
     *
     * b[i] is count of binary string until index i
     * which do not contain any two consecutive 1's and ending with 1's
     *
     * function: a[i] = a[i-1] + b[i-1]
     *          b[i] = a[i-1]
     *
     * init: a[0] = b[0] = 1
     *
     * result : total count = a[n-1] + b[n-1] ; n is given input length of binary string
     */
    public int countBinaryStrings(int n) {
        int[] a = new int[n];
        int[] b = new int[n];

        a[0] = b[0] = 1;
        for (int i = 1; i < n; i++) {
            a[i] = a[i-1] + b[i-1];
            b[i] = a[i-1];
        }
        return a[n-1] + b[n-1];
    }

    /**
     * deeper analysis, it is Fibonacci pattern
     * which can count result in O(log N ) time use method 5 in
     * link : http://www.geeksforgeeks.org/program-for-nth-fibonacci-number/
     */
}
