package com.leetcode.dynamicprogram;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by charles on 2/2/17.
 * A sequence of numbers is called arithmetic if it consists of at least three elements and if the difference between any two consecutive elements is the same.
 For example, these are arithmetic sequences:

 1, 3, 5, 7, 9
 7, 7, 7, 7
 3, -1, -5, -9
 The following sequence is not arithmetic.

 1, 1, 2, 5, 7
 A zero-indexed array A consisting of N numbers is given. A subsequence slice of that array is any sequence of integers (P0, P1, ..., Pk) such that 0 ≤ P0 < P1 < ... < Pk < N.
 A subsequence slice (P0, P1, ..., Pk) of array A is called arithmetic if the sequence A[P0], A[P1], ..., A[Pk-1], A[Pk] is arithmetic. In particular, this means that k ≥ 2.
 The function should return the number of arithmetic subsequence slices in the array A.
 The input contains N integers. Every integer is in the range of -2^31 and 2^31-1 and 0 ≤ N ≤ 1000. The output is guaranteed to be less than 2^31-1.

 Example:
 Input: [2, 4, 6, 8, 10]
 Output: 7
 Explanation:
 All arithmetic subsequence slices are:
 [2,4,6]
 [4,6,8]
 [6,8,10]
 [2,4,6,8]
 [4,6,8,10]
 [2,4,6,8,10]
 [2,6,10]

 2      4       6       8       10
      2 -> 1  4 -> 1  6 -> 1   8 -> 1
              2 -> 2  4 -> 1   6 -> 1
                      2 -> 3   4 -> 2
                                2 -> 4
 how to use above table,
 2 -> 1 means for diff is 2, it is so far once at index 1 when value is 4
 when diff (6,4) is 2, it appears twice, then add previous value 1 from map into result
 and update when key is 2,
 each column is a map which contains corresponding key value pair
 */
public class ArithmeticSlicesII_446 {
    public int numberOfArithmticeSlices(int[] A) {
        int res = 0;
        // assign map to each elem in array
        Map<Integer, Integer>[] dp = new Map[A.length];
        long diff = 0l;
        int aDiff = 0;
        int countsWithSameDiff = 0;
        int n = A.length;
        for (int i = 0; i < n; i++) {
            dp[i] = new HashMap<>();
            for (int j = 0; j < i; j++) {
                diff = (long)A[i] - (long)A[j];
                if (diff > Integer.MAX_VALUE || diff < Integer.MIN_VALUE) {
                    continue;
                }
                aDiff = (int)diff;
                dp[i].putIfAbsent(aDiff, 1); // init if no this key yet

                // in the search from [0,j][j, i]
                /**
                 * only when current diff as key
                 * appear in both dp[j] and dp[i]
                 * then add value of dp[j] into count result
                 * the new added count dp[i][diff] + dp[j][diff] will be stored in dp[i][diff]
                 * this new count value will add into result when next diff appear
                 */
                if (dp[j].containsKey(aDiff)) {
                    countsWithSameDiff = dp[i].get(aDiff) + dp[j].get(aDiff);
                    dp[i].put(aDiff, countsWithSameDiff);
                    res += dp[j].get(aDiff);
                }
            }
        }
        return res;
    }

    public int numberOfArithmeticSlicesII(int[] A) {
        int res = 0;
        Map<Integer, Integer>[] maps = new Map[A.length];
        long lDiff = 0l;
        int diff = 0;

        int freqEndAtCurrI = 0;
        int freqEndAtPrevJ = 0;
        int updateFreq = 0;
        for (int i = 0; i < A.length; i++) {
            maps[i] = new HashMap<>();
            for (int j = 0; j < i; j++) {
                /**
                 * iteratve all possibilities among [0, i)
                 * at any index j, matching below situation
                 */
                lDiff = (long)A[i] - (long)A[j];
                if (lDiff <= Integer.MIN_VALUE || lDiff > Integer.MAX_VALUE) {
                    continue;
                }
                diff = (int) lDiff;
                freqEndAtPrevJ = maps[j].getOrDefault(diff, 0);
                freqEndAtCurrI = maps[i].getOrDefault(diff, 0);
                // 1 is default value for init when two-elements
                updateFreq = freqEndAtCurrI + freqEndAtPrevJ + 1;
                res += freqEndAtPrevJ;
                maps[i].put(diff, updateFreq);
            }
        }
        return res;
    }

    public static void main(String[] args) {
        ArithmeticSlicesII_446 a = new ArithmeticSlicesII_446();
        int[] A = {1,1,2,5,7};
        int[] B = {3, -1, -5, 9};
        int[] C = {2,4,6,8,10};
        System.out.println(a.numberOfArithmeticSlicesII(A) == a.numberOfArithmticeSlices(A));
        System.out.println(a.numberOfArithmeticSlicesII(B) == a.numberOfArithmticeSlices(B));
        System.out.println(a.numberOfArithmeticSlicesII(C) == a.numberOfArithmticeSlices(C));
    }
}
