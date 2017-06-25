package com.advanced.dynamicprogram.memorysearch;

/**
 * Difference of DP regular search and memorized search
 * example: {@link memory-search-template.png} and {@link com.advanced.dynamicprogram.LongestIncreasingContinuousSubsequence}
 *
 * When to use memorized search in DP
 *  1. when transit function is hard to formula, or without sequential (hard to complete with for loops)
 *  2. when hard to define initialization (hard to find appropriate start point)
 *  3. from small state to large state
 *
 * Advantagies
 *  1. keep record after search, e.g, if previously already finish search for f[i][j]
 *  2. if next time reach f[i][j] again, memory can return result directly without redo search
 *
 * Time complexity, because there is flag array and dp array,
 * each elem least or most be iterated at once. so O(n * m) time complexity
 *
 * Limitation of memorized search
 *  1. no way for space and time optimization, cannot use rolling array
 */

/**
 * Created by charles on 11/13/16.
 * Give you an integer matrix (with row size n, column size m)，
 * find the longest increasing continuous subsequence in this matrix.
 * (The definition of the longest increasing continuous subsequence here can start at any row or column
 * and go up/down/right/left any direction).
 */
public class LongestIncreasingContinuousSubsequenceII {
    private int[][] dp;
    private int[][] flag; // 0 is not visited, 1 is visited
    private int rowLen;
    private int colLen;
    // assisting arrays to define /right/down/left/up
    private int[] dx = {1, 0, -1, 0};
    private int[] dy = {0, 1, 0, -1};

    public int longestIncreaseContinuousSubsequence(int[][] A) {
        if (A == null || A.length == 0) {return 0;}
        rowLen = A.length;
        colLen = A[0].length;
        // init
        int ans = 0;
        dp = new int[rowLen][colLen];
        flag = new int[rowLen][colLen];
        // search ans memory, use dp ans flag array to memory
        for (int i = 0; i < rowLen; i++) {
            for (int j = 0; j < colLen; j++) {
                dp[i][j] = memorizedSearch(A, i, j);
                ans = Math.max(ans, dp[i][j]);
            }
        }
        return ans;
    }

    private int memorizedSearch(int[][] A, int x, int y) {
        // if already searched, then return result from dp array directly
        if (flag[x][y] > 0) {
            return dp[x][y];
        }
        // search
        int ans = 1;
        int nx, ny; // new index in search represents previous state in dp

        for (int i = 0; i < 4; i++) {
            nx = x + dx[i];
            ny = y + dy[i];
            // edge conditions
            if (0<=nx && nx<rowLen && 0<=ny && ny<colLen) {
                if (A[nx][ny] < A[x][y]) {
                    // from last state to current state
                    // recursive search, trace back for ending at new index
                    ans = Math.max(ans, memorizedSearch(A, nx, ny) + 1);
                }
            }
        }
        // update flag and dp
        flag[x][y] = 1;
        dp[x][y] = ans;
        return ans;
    }

    public static void main(String[] args) {
        LongestIncreasingContinuousSubsequenceII l = new LongestIncreasingContinuousSubsequenceII();
        int[][] A =    {{1 ,2 ,3 ,4 ,5},
                        {16,17,24,23,6},
                        {15,18,25,22,7},
                        {14,19,20,21,8},
                        {13,12,11,10,9}};
        System.out.println(l.longestIncreaseContinuousSubsequence(A));
    }
}
