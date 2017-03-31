package com.leetcode.dynamicprogram;

/**
 * Created by charles on 2/5/17.
 * Given a 2D binary matrix filled with 0's and 1's, find the largest square containing only 1's and return its area.

 For example, given the following matrix:

 1 0 1 0 0
 1 0 1 1 1
 1 1 1 1 1
 1 0 0 1 0
 Return 4.

 */
public class MaximalSquare_221 {
    /**
     * State dp[i][j] is from topleft ending at current [i][j], max edge length of square
     * Function
     *      if (a[i-1][j-1] == '1') {
     *          dp[i][j] = 1 + Math.min(dp[i-1[j], dp[i-1][j-1], dp[i][j-1])
     *          max = max(dp[i][j], max);
     *      }
     * init dp[0][0] = 0;
     * answer dp[m][n];
     */
    public int maxiamlSquare(char[][] a) {
        if (a.length == 0) {
            return 0;
        }
        int m = a.length;
        int n = a[0].length;
        int max = 0;
        int[][] dp = new int[m+1][n+1];

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (a[i-1][j-1] == '1') {
                    // be awared, dp function should add one
                    dp[i][j] = 1 + Math.min(Math.min(dp[i-1][j-1], dp[i-1][j]), dp[i][j-1]);
                    max = Math.max(dp[i][j], max);
                }
            }
        }
        return max * max;
    }

    /**
     * Space Optimization O(2*n)
     */
    public int maxSquare(char[][] matrix) {
        int ans = 0;
        if (matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }
        int m = matrix.length;
        int n = matrix[0].length;
        int[][] dp = new int[2][n];

        for (int i = 0; i < m; i++) {
            dp[i % 2][0] = matrix[i][0];
            ans = Math.max(dp[i % 2][0], ans);

            for (int j = 1; j < n; j++) {
                if (i > 0) {
                    if (matrix[i][j] == '1') {
                        dp[i % 2][j] = 1 + Math.min(Math.min(dp[(i-1)%2][j-1], dp[(i-1)%2][j]), dp[i%2][j-1]);
                    } else {
                        dp[i % 2][j] = 0;
                    }
                } else {
                    dp[i % 2][j] = matrix[i % 2][j];
                }
                ans = Math.max(dp[i % 2][j], ans);
            }
        }
        return ans * ans;
    }

    /**
     * So far faster solution
     * State : dp[i][j] is maximal side len of square ending at i, j
     * Function: if (A(i,j) == 0) dp[i][j] = 0
     *  else if dp[i][j-1] != dp[i-1][j]
     *      dp[i][j] = math.max(above two) + 1;
     *   else
     *      dp[i][j] = dp[i-s1][j-s2] == 1 ? s1 + 1 : s1;
     */
    public int maximalSquare(char[][] matrix) {
        int m = matrix.length;
        if (m <= 0) return 0;
        int n = matrix[0].length;
        int[][] dp = new int[m][n];

        for (int i = 0; i < m; i++) {
            dp[i][0] = matrix[i][0] - '0';
        }
        for (int j = 0; j < n; j++) {
            dp[0][j] = matrix[0][j] - '0';
        }
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (matrix[i][j] == '0') {
                    dp[i][j] = 0;
                } else {
                    toFindMaxSideLengthUntilCurrentPos(dp, matrix, i, j);
                }
            }
        }
        int max = 0;
        for (int[] row : dp) {
            for (int col : row) {
                max = Math.max(max, col);
            }
        }
        return max * max;
    }

    /** dp[i-1][j] is from i,j extending left to max square
     *  dp[i][j-1] is from i,j extending up to max square
     *  if topleft corner A[i-dp(i,j-1)][j-dp(i-1,j)] == 1
     *  then dp[i][j] max square is dp(i,j-1) + 1; else is dp(i,j-1)
     */
    private void toFindMaxSideLengthUntilCurrentPos(int[][] dp, char[][] A, int i, int j) {
        int l1 = dp[i][j - 1];
        int l2 = dp[i-1][j];
        if (l1 != l2) {
            dp[i][j] = Math.min(l1, l2) + 1;
        } else {
            if (A[i - l1][j - l2] == '1') {
                dp[i][j] = l1 + 1;
            } else {
                dp[i][j] = l1;
            }
        }
    }
}
