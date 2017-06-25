package com.leetcode.dynamicprogram;

/**
 * Created by charles on 5/7/17.
 * There is an m by n grid with a ball. Given the start coordinate (i,j) of the ball, you can move the ball to adjacent cell or cross the grid boundary in four directions (up, down, left, right). However, you can at most move N times. Find out the number of paths to move the ball out of grid boundary. The answer may be very large, return it after mod 109 + 7.

 Example 1:
 Input:m = 2, n = 2, N = 2, i = 0, j = 0
 Output: 6
 Explanation:
 https://leetcode.com/static/images/problemset/out_of_boundary_paths_1.png

 Example 2:
 Input:m = 1, n = 3, N = 3, i = 0, j = 1
 Output: 12
 Explanation:
 https://leetcode.com/static/images/problemset/out_of_boundary_paths_2.png
 */
public class OutOfBoundaryPaths_576 {
    /**
     * DP solution.
     * dp[i][j][k] stands for how many possible ways to walk into cell j,k in step i;
     * dp[i][j][k] depends on dp[i-1][j][k], so can compress dimensional dp array to 2 dimensional.
     * dp[i][j]=dp[i−1][j]+dp[i+1][j]+dp[i][j−1]+dp[i][j+1] t
     * This happens because we can reach the index (i,j)(i,j) from any of the four adjacent positions and
     * the total number of ways of reaching the index (i,j)(i,j) in xx moves is the sum of the ways of reaching the adjacent positions in x-1x−1 moves.
     */
    public int findPaths(int m, int n, int N, int i, int j) {
        // m,n, length/width of array
        // N maximum allow steps
        // i,j, index of ball
        if (N <= 0) {
            return 0;
        }
        final int MOD = 1000000007; // when number is extreme large
        int[][] count = new int[m][n];
        count[i][j] = 1;
        int res = 0;

        int[] dx = {1,0,-1,0};
        int[] dy = {0,1,0,-1};

        int nx = 0;
        int ny = 0;
        for (int step = 0; step < N; step++) {
            int[][] temp = new int[m][n];
            for (int row = 0; row < m; row++) {
                for (int col = 0; col < n; col++) {
                    for (int k = 0; k < 4; k++) {
                        nx = row + dx[k];
                        ny = col + dy[k];
                        if (nx<0 || nx>=m || ny<0 || ny>= n) {
                            res = (res + count[row][col]) % MOD;
                        } else {
                            temp[nx][ny] = (temp[nx][ny] + count[row][col]) % MOD;
                        }
                    }
                }
            }
            count = temp;
        }
        return res;
    }

}
