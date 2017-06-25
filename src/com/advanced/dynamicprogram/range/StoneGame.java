package com.advanced.dynamicprogram.range;

/**
 * Created by charles on 11/18/16.
 * There is a stone game.At the beginning of the game the player picks n piles of stones in a line.
 The goal is to merge the stones in one pile observing the following rules:
 At each step of the game,the player can merge two adjacent piles to a new pile.
 The score is the number of stones in the new pile.
 You are to determine the minimum of the total score.

 For [4, 1, 1, 4], in the best solution, the total score is 18:
 1. Merge second and third piles => [4, 2, 4], score +2
 2. Merge the first two piles => [6, 4]，score +6
 3. Merge the last two piles => [10], score +10
 Other two examples:
 [1, 1, 1, 1] return 8
 [4, 4, 5, 9] return 43
 */
public class StoneGame {
    /**
     * memorized search is good at optimizing when searching from big to small
     * State: given section[i, j], min value of sum all stones
     * function:
     *      tips: pre-treat sum[i][j], is sum of all stones in given section [i, j]
     *      dp[i][j] = min(dp[i][j], dp[i][k] + dp[k+1][j] + sum[i,j])
     *          for any k in the section of [i, j]
     * Initialize: for each i, dp[i][i] = 0
     * Answer; dp[0][n-1];
     */
    public int stoneGameByMemorySearch(int[] A) {
        if (A == null || A.length == 0) {
            return 0;
        }
        int n = A.length;
        int[][] dp = new int[n][n];
        boolean[][] visited = new boolean[n][n];

        // preparation
        int[][] sum = new int[n][n];
        for (int i = 0; i< n; i++) {
            sum[i][i] = A[i];
            for (int j = i+1; j < n; j++) {
                sum[i][j] = sum[i][j-1] + A[j];
            }
        }

        return memorizedSearch(0, n - 1, dp, visited, sum);
    }

    private int memorizedSearch(int l, int r, int[][] dp, boolean[][] visited, int[][] sum) {
        if (visited[l][r]) {
            return dp[l][r];
        }
        if (l == r) {
            visited[l][r] = true;
            return dp[l][r];
        }
        dp[l][r] = Integer.MAX_VALUE;
        // iterate all k index to search all possible solutions
        for (int k = l; k < r; k++) {
            dp[l][r] = Math.min(dp[l][r], memorizedSearch(l, k, dp, visited, sum)
                            + memorizedSearch(k + 1, r, dp, visited, sum)
                            + sum[l][r]);
        }
        visited[l][r] = true;
        return dp[l][r];
    }

    /**
     * state: min value of sum of merge piles of sum of stones in given section [i, j]
     * function: dp[i][j] = min (dp[i][j], dp[i][k] + dp[k+1][j] + sum[i][j]])
     */
    public int stoneGame(int[] A) {
        if (A == null || A.length == 0) {
            return 0;
        }
        int n = A.length;
        int[][] dp = new int[n][n];
        int[][] sum = new int[n][n];
        // preparantion
        prepareSum(sum, A);

        int start, end;
        int min;
        for (int len = 2; len <= n; len++) {// section level, from small to large
            for (start = 0; start + len - 1 < n; start++) {
                end = start + len - 1;
                min = Integer.MAX_VALUE; // ass max_value of int to min initial
                // iterate all k to search solution
                for (int k = start; k < end; k++) {
                    min = Math.min(min, dp[start][k] + dp[k+1][end]);
                }
                dp[start][end] = min + sum[start][end];
            }
        }
        return dp[0][n-1];
    }

    private void prepareSum(int[][] sum, int[] A) {
        for (int i = 0; i < A.length; i++) {
            sum[i][i] = A[i];
            for (int j = i+1; j < A.length; j++) {
                sum[i][j] = sum[i][j-1] + A[j];
            }
        }
    }

    public static void main(String[] args) {
        StoneGame s = new StoneGame();
        int[] A = {4, 4, 5, 9};
        System.out.println(s.stoneGame(A));
        System.out.println(s.stoneGameByMemorySearch(A));
    }
}
