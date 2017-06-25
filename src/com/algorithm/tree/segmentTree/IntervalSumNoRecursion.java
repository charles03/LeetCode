package com.algorithm.tree.segmentTree;

import java.util.Arrays;

/**
 * Created by charles on 11/21/16.
 * Given an integer array in the construct method, implement two methods query(start, end) and modify(index, value):
 For query(start, end), return the sum from index start to index end in the given array.
 For modify(index, value), modify the number in the given index to value

 Given array A = [1,2,7,8,5].

 query(0, 2), return 10.
 modify(0, 4), change A[0] from 1 to 4.
 query(0, 1), return 6.
 modify(2, 1), change A[2] from 7 to 1.
 query(2, 4), return 14.

 Reference: http://codeforces.com/blog/entry/18051
 */
public class IntervalSumNoRecursion {
    private static int N = 10000; // limit array size
    // better not to use instance field,
//    private int powerTwoCeil; // nearest ceil of power of two by input array size
//    private int powerTwoFloor; // nearest floor of power of two by input array size
    private int[] segment = new int[2 * N];
    private int len; // input array size

    /**
     * treat segment tree as complete binary tree
     */
    public void build(int[] A) {
        len = A.length;
        int powerTwoCeil = getPowerOfTwoCeil(len);
//        powerTwoFloor = getPowerOfTwoFloor(len);
        // initial bottom base
        for (int i = 0; i < len; i++) {
            // seg[i] = seg[2 * i] + seg[2 * i + 1]
            segment[i + powerTwoCeil] = A[i];
        }
        // build tree top
        for (int k = powerTwoCeil - 1; k > 0; k--) {
            segment[k] = segment[k << 1] + segment[k << 1 | 1];
        }
    }

    public void modify(int index, int value) { // set new value at position index
        int i = index + getPowerOfTwoCeil(len);
        // modify
        while (i > 1) {
            segment[i] = value;
            segment[i >> 1] = segment[i] + segment[i^1];
            i >>= 1;
        }
    }

    public int query(int start, int end) { // sum on interval [start, end)
        int res = 0;
        int offset = getPowerOfTwoCeil(len);
        System.out.println("power "+ offset);
        int l = start + offset;
        int r = end + offset;
        while (l < r) {
            if ((l&1) != 0) {
                System.out.println("l " + res + " " + l + " " + segment[l]);
                res += segment[l++];
            }
            if ((r&1) != 0) {
                System.out.println("r " + res + " " + r + " " + segment[r]);
                res += segment[--r];
            }
            l >>= 1;
            r >>= 1;
        }
        return res;
    }

    private int getPowerOfTwoCeil(int x) {
        int power = 2;
        while ((x >>= 1) > 0) {
            power <<= 1;
        }
        return power;
    }

    private int getPowerOfTwoFloor(int x) {
        int power = 1;
        while ((x >>= 1) > 0) {
            power <<= 1;
        }
        return power;
    }

    public static void main(String[] args) {
        IntervalSumNoRecursion sum = new IntervalSumNoRecursion();
        int[] A = {1,2,7,8,5};
        sum.build(A);
        System.out.println(Arrays.toString(sum.segment));
        // inclusive start , exclusive end
        System.out.println(sum.query(0, 2));
        System.out.println(sum.query(0, 3));
        System.out.println(sum.query(0, 1));
        System.out.println(sum.query(1, 4));

    }
}
