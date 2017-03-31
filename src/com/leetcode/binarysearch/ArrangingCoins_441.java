package com.leetcode.binarysearch;

/**
 * Created by charles on 3/20/17.
 * You have a total of n coins that you want to form in a staircase shape, where every k-th row must have exactly k coins.

 Given n, find the total number of full staircase rows that can be formed.

 n is a non-negative integer and fits within the range of a 32-bit signed integer.

 Example 1:

 n = 5

 The coins can form the following rows:
 ¤
 ¤ ¤
 ¤ ¤

 Because the 3rd row is incomplete, we return 2.
 Example 2:

 n = 8

 The coins can form the following rows:
 ¤
 ¤ ¤
 ¤ ¤ ¤
 ¤ ¤

 Because the 4th row is incomplete, we return 3.
 */
public class ArrangingCoins_441 {
    /** binary search */
    /** besides 0.5 * mid * mid + 0.5 * mid does not have overflow issue as the intermediate result is implicitly autoboxed to double data type.*/
    public int arrangeCoins(int n) {
        int start = 0;
        int end = n;
        int mid = 0;

        while (start <= end) {
            mid = start + (end - start) / 2;
            if ((0.5 * mid * mid + 0.5 * mid) <= n) {
                start = mid + 1;
            } else {
                end = mid - 1;
            }
        }
        return start - 1;
    }

    /** Math solution : quadratic equation. ax^2 +bx + c= 0
     * x = (-b +/- sqrt(b^2 - 4ac)) / 2a
     */
    public int arrangeCoinsII(int n) {
        return (int)((-1 + Math.sqrt(1 + 8 * (long)n)) / 2);
    }
}
