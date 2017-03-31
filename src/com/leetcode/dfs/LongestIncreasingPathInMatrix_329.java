package com.leetcode.dfs;

/**
 * Created by charles on 2/25/17.
 * Given an integer matrix, find the length of the longest increasing path.

 From each cell, you can either move to four directions: left, right, up or down. You may NOT move diagonally or move outside of the boundary (i.e. wrap-around is not allowed).

 Example 1:

 nums = [
 [9,9,4],
 [6,6,8],
 [2,1,1]
 ]
 Return 4
 The longest increasing path is [1, 2, 6, 9].

 Example 2:

 nums = [
 [3,4,5],
 [3,2,6],
 [2,2,1]
 ]
 Return 4
 The longest increasing path is [3, 4, 5, 6]. Moving diagonally is not allowed.
 */
public class LongestIncreasingPathInMatrix_329 {
    private int[] dx = {1, 0, -1, 0};
    private int[] dy = {0, 1, 0, -1};

    public int longestIncreasingPath(int[][] matrix) {
        if (matrix.length == 0) {
            return 0;
        }
        int rLen = matrix.length;
        int cLen = matrix[0].length;
        int ans = 0;
        int[][] dp = new int[rLen][cLen];
        int[][] flag = new int[rLen][cLen];

        for (int i = 0; i < rLen; i++) {
            for (int j = 0; j < cLen; j++) {
                dp[i][j] = memorizedSearch(matrix, dp, flag, i, j, rLen, cLen);
                ans = Math.max(ans, dp[i][j]);
            }
        }
        return ans;
    }

    public int memorizedSearch(int[][] matrix, int[][] dp, int[][] flag, int x, int y, int row, int col) {
        if (flag[x][y] != 0) { // already visited
            return dp[x][y];
        }
        int res = 1;
        int nx = 0, ny = 0; // new location index, nx and ny
        for (int k = 0; k < 4; k++) {
            nx = x + dx[k];
            ny = y + dy[k];
            // edge case check
            if (0<=nx && nx<row && 0<=ny && ny<col) {
                if (matrix[x][y] > matrix[nx][ny]) {
                    res = Math.max(res, memorizedSearch(matrix, dp, flag, nx, ny, row, col) + 1);
                }
            }
        }
        // update visited status
        flag[x][y] = 1;
        dp[x][y] = res;
        return res;
    }
}
