package com.advanced.dynamicprogram.range;

import javax.swing.plaf.metal.MetalTheme;

/**
 * Created by charles on 1/5/17.
 * There is a stone game.At the beginning of the game the player picks n piles of stones in a circle.
 The goal is to merge the stones in one pile observing the following rules:

 At each step of the game,the player can merge two adjacent piles to a new pile.
 The score is the number of stones in the new pile.
 You are to determine the minimum of the total score.

 For [1, 4, 4, 1], in the best solution, the total score is 18:

 1. Merge second and third piles => [2, 4, 4], score +2
 2. Merge the first two piles => [6, 4]，score +6
 3. Merge the last two piles => [10], score +10
 Other two examples:
 [1, 1, 1, 1] return 8
 [4, 4, 5, 9] return 43
 */
public class StoneGameII {

    /**
     * Range DP:
     * The difference between stone game and stone game II
     *  because piles locates as circle, start will be adjacent with end
     *  Thus need to duplicate same array append in the tail
     *  [1,2,3,4][1,2,3,4] -> so that head will be adjacent with tail
     *
     * State: dp[i][j] is min merge cost from pile i to pile j;
     * Function:
     *  pretreatment: sum[i,j] is sum from pile i to pile j
     *  dp[i][j] = min(dp[i][j], dp[i][k] + dp[k+1][j] + sum[i][j]), for all k in (i,j)
     * Initialize:
     *  for each i: dp[i][i] = 0;
     * Answer: for each dp[i][i+n-1] i in [0,n), return min()
     */
    public int stoneGameAdvanced(int[] A) {
        if (A == null || A.length == 0) {
            return 0;
        }
        int n = A.length; // original length of input array
        int m = 2 * n; // double up original length of input array due to circle shape of piles
        if (n <= 1) {
            return 0;
        }
        int[][] dp = new int[m][m];
        int[] sum = new int[m + 1];

        // pretreatment for sum
        for (int i = 1; i <= m; i++) {
            sum[i] = sum[i-1] + A[(i-1) % n];
        }
        // initialization
        for (int i = 0; i < m; i++) {
            dp[i][i] = 0; // init value of each pile is 0
        }
        // Range DP process, from small range to large range
        // i : start position of for loop in range
        // r : starting size of range
        int j = 0; // real end position of for loop in range
        int k = 0; // index for iteration of all results within range [i,j]
        for (int r = 2; r <= m; r++) {
            for (int i = 0; (i < m && i + r - 1 < m); i++) {
                j = i + r - 1;
                dp[i][j] = Integer.MAX_VALUE;
                for (k = i; k < j; k++) { // iteratively attempt k in (i,j) to find min merge cost
                    dp[i][j] = Math.min(dp[i][j],
                            dp[i][k] + dp[k+1][j] + sum[j+1] - sum[i]); // sum[j+1] - sum[i] is sum[i,j]
                }
            }
        }
        // iterate all valid fixed size (full length of input array) range from starding index p in (0, A.length);
        int ans = Integer.MAX_VALUE;
        for (int p = 0; p < n; p++) {
            ans = Math.min(ans, dp[p][p + n - 1]);
        }
        return ans;
    }

    public static void main(String[] args) {
        StoneGameII s = new StoneGameII();
        int[] a1 = {1,4,4,1};
        int[] a2 = {1,1,1,1};
        int[] a3 = {4,4,5,9};
        System.out.println(s.stoneGameAdvanced(a1) == 18);
        System.out.println(s.stoneGameAdvanced(a2) == 8);
        System.out.println(s.stoneGameAdvanced(a3) == 43);
    }
}
