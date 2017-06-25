package com.advanced.dynamicprogram;

/**
 * Created by charles on 11/12/16.
 * Given a 2D binary matrix filled with 0's and 1's,
 * find the largest square containing all 1's and return its area.
 *Example
 For example, given the following matrix:

 1 0 1 0 0
 1 0 1 1 1
 1 1 1 1 1
 1 0 0 1 0
 Return 4.
 */
public class MaximalSquare {
    /** DP Thought (without Space Optimization)
     *  State: f[i][j] : max edge length from bottom-right to upper left
     *  Fuction: f[i][j] =
     *      if matrix[i][j] == 1
     *          Min(f[i-1][j-1], f[i-1][j], f[i][j-1]) + 1
     *      if matrix[i][j] == 0
     *         f[i][j] = 0;
     *  Initialization
     *      f[i][0] = matrix[i][0];
     *      f[0][j] = matrix[0][j];
     *  Answer: max[f[i][j]]
     */

    /**Theory: current row only has relationship and dependency on last row
     * DP with space optimization
     * State: same
     * Function:
     * if matrix[i][j] = 1
     *      f[i%2][j] = min(f[i-1%2][j], f[i%2][j-1], f[i-1%2][j-1]) + 1
     * if matrix[i][j] = 0
     *      f[i%2][j] = 0;
     *
     * Initiate:
     *      f[i%2][0] = matrix[i%2][0]
     *      f[0][j] = matrix[0][j]
     * Answer
     *      max[f[i%2][j]
     */
    public int maxSquareArea(int[][] matrix) {
        if (matrix == null || matrix.length == 0) {return 0;}
        int n = matrix.length;
        int m = matrix[0].length;
        // rolling array for space optimization
        int[][] dp = new int[2][m];
        int maxEdgeLen = 0;

        for (int i = 0; i < n; i++) {
            // init inside for loop, because of rolling array
            dp[i % 2][0] = matrix[i][0];
            maxEdgeLen = Math.max(maxEdgeLen, dp[i%2][0]);
            for (int j = 1; j < m; j++) {
                if (i == 0) {
                    dp[i%2][j] = matrix[i%2][j];
                } else {
                    if (matrix[i][j] > 0) {
                        // add one for current point
                        dp[i%2][j] = 1 + Math.min(dp[(i-1)%2][j],
                                Math.min(dp[(i-1)%2][j-1], dp[i%2][j-1]));
                    } else {
                        dp[i%2][j] = 0;
                    }
                }
                maxEdgeLen = Math.max(dp[i%2][j], maxEdgeLen);
            }
        }
        return maxEdgeLen * maxEdgeLen;
    }

    public static void main(String[] args) {
        MaximalSquare m = new MaximalSquare();
        int[][] matrix = {{1,0,1,0,0},{1,0,1,1,1},{1,1,1,1,1},{1,0,0,1,0}};
        System.out.println(m.maxSquareArea(matrix));
        System.out.println(m.maxSquareNoRollingArray(matrix));
    }

    public int maxSquareNoRollingArray(int[][] matrix) {
        if (matrix == null || matrix.length == 0) {return 0;}
        int n = matrix.length;
        int m = matrix[0].length;
        int[][] dp = new int[n][m];
        int maxEdgeLen = 0;
        // init
        for (int i = 0; i < n; i++) {
            dp[i][0] = matrix[i][0];
        }
        for (int j = 0; j < m; j++) {
            dp[0][j] = matrix[0][j];
        }

        for (int i = 1; i < n; i++) {
            maxEdgeLen = Math.max(dp[i][0], maxEdgeLen);
            for (int j = 1; j < m; j++) {
                if (matrix[i][j] > 0) {
                    dp[i][j] = 1 + Math.min(dp[(i-1)%2][j],
                            Math.min(dp[(i-1)%2][j-1], dp[(i)%2][j-1]));
                } else {
                    dp[i][j] = 0;
                }
                maxEdgeLen = Math.max(dp[i][j], maxEdgeLen);
            }
        }
        return maxEdgeLen * maxEdgeLen;
    }
}
