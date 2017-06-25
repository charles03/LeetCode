package com.leetcode.dynamicprogram;

import java.util.Arrays;

/**
 * Created by charles on 5/21/17.
 * Given a matrix consists of 0 and 1, find the distance of the nearest 0 for each cell.

 The distance between two adjacent cells is 1.
 Example 1:
 Input:

 0 0 0
 0 1 0
 0 0 0
 Output:
 0 0 0
 0 1 0
 0 0 0
 Example 2:
 Input:

 0 0 0
 0 1 0
 1 1 1
 Output:
 0 0 0
 0 1 0
 1 2 1
 */
public class Matrix01_542 {
    /**
     * use DP thought;
     * split into two traverse processes
     * one is from top-left to bottom right
     * the other is reverse order
     *
     * at first traverse:
     * for current cell, path from left or top
     * then dp[i][j] = min(dp[i][j], dp[i-1][j] + 1, dp[i][j-1] +1)
     * if (current cell is 0) then dp[i][j] = 0;
     *
     * at second traverse:
     * path from bottom and right;
     * if (current cell is 0) then dp[i][j] = 0;
     * else dp[i][j] = min(dp[i][j], dp[i+1][j]+1, dp[i][j+1] +1);
     */
    public int[][] updateMatrix(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;

        int[][] ans = new int[m][n];
        for (int[] row : ans) {
            Arrays.fill(row, m+n);
        }
        // top-left to bottom-right
        for (int row = 0; row < m; row++) {
            for (int col = 0; col < n; col++) {
                if (matrix[row][col] == 0) {
                    ans[row][col] = 0;
                } else {
                    if (row > 0) {
                        ans[row][col] = Math.min(ans[row][col],
                                1 + ans[row-1][col]);
                    }
                    if (col > 0) {
                        ans[row][col] = Math.min(ans[row][col],
                                1 + ans[row][col-1]);
                    }
                }

            }
        }
        // bottom-right to top-left
        for (int row = m-1; row >= 0; row--) {
            for (int col = n-1; col >= 0; col--) {
                if (matrix[row][col] == 0) {
                    ans[row][col] = 0;
                } else {
                    if (row < m-1) {
                        ans[row][col] = Math.min(ans[row][col],
                                1 + ans[row+1][col]);
                    }
                    if (col < n-1) {
                        ans[row][col] = Math.min(ans[row][col],
                                1 + ans[row][col+1]);
                    }
                }
            }
        }

        return ans;
    }
}
