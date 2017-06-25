package com.advanced.dynamicprogram;

/**
 * Created by charles on 11/12/16.
 * Give an integer array，find the longest increasing continuous subsequence in this array.
 An increasing continuous subsequence:

 Can be from right to left or from left to right.
 Indices of the integers in the subsequence should be continuous.

 Challenge O(n) time and O(1) extra space
 Example
 For [5, 4, 2, 1, 3], the LICS is [5, 4, 2, 1], return 4.
 For [5, 1, 2, 3, 4], the LICS is [1, 2, 3, 4], return 4.
 */
public class LongestIncreasingContinuousSubsequence {
    /** DP Thoughts
     * State: f[i], longest length when ending at index i
     * Function:
     *      if (a[i] > a[i-1]) f[i] = f[i-1] + 1
     *      if (a[i] < a[i-1]) f[i] = 1
     * initialization f[0] = 1
     * Answer: f]n];
     * From left to right, and then from right to left
     *
     * @param A an array of Integer
     * @return  an integer
     */
    public int longestIncreasingContinuousSubsequence(int[] A) {
        // Write your code here
        if (A == null || A.length == 0) {
            return 0;
        }
        int n = A.length;
        int len = 1; // just A[0] itself
        int max = 0;
        // from left to right
        for (int i = 1; i < n; i++) {
            if (A[i-1] < A[i]) {
                len++; // no need array
            } else {
                len = 1;
            }
            max = Math.max(len, max);
        }

        // from right to left
        len = 1;
        for (int j = n - 2; j >= 0; j--) {
            if (A[j] > A[j + 1]) {
                len++;
            } else {
                len = 1;
            }
            max = Math.max(len, max);
        }
        return max;
    }

    public static void main(String[] args) {
        LongestIncreasingContinuousSubsequence l = new LongestIncreasingContinuousSubsequence();
        int[] A = {5, 4, 2, 1, 3};
        System.out.println(l.longestIncreasingContinuousSubsequence(A));
    }
}
