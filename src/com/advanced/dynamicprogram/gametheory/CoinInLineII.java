package com.advanced.dynamicprogram.gametheory;

import java.util.Arrays;

/**
 * Created by charles on 11/13/16.
 * There are n coins with different value in a line.
 * // cannot use greedy, because consequently from left to right
 * Two players take turns to take one or two coins from left side until there are no more coins left.
 * The player who take the coins with the most value wins.
 * Could you please decide the first player will win or lose?
 *
 * Given values array A = [1,2,2], return true.
    Given A = [1,2,4], return false.
 */
public class CoinInLineII {
    /**
     * State: dp[i] from current index i to end, most value of coin which current player get
     * Function:
     *      sum[i] is value sum of from index i to end of coins
     *      dp[i] = sum[i] - min(dp[i-1], dp[i-2])
     * Initialization:
     *      dp[n] = 0;
     *      dp[n-1] = coin[i-1]
     *      dp[n-2] = coin[i-2] + coin[i-1]
     * Answer dp[n]
     */
    public boolean firstWillWin(int[] values) {
        if (values == null || values.length == 0) {
            return true;
        }
        if (values.length <= 2) {
            return true;
        }
        int n = values.length;
        int[] dp = new int[n + 1];
        dp[n] = 0;
        dp[n - 1] = values[n-1];
        dp[n - 2] = values[n-2] + values[n-1];

        int[] sum = new int[n + 1];
        int totalSum = values[n - 1];
        sum[n - 1] = values[n-1];
        for(int i = n - 2; i >= 0; i--) {
            sum[i] = sum[i+1] + values[i];
            totalSum += values[i];
        }
        System.out.println(Arrays.toString(sum));

        for (int i = n - 2; i >= 0; i--) {
            dp[i] = sum[i] - Math.min(dp[i+1], dp[i+2]);
        }
        System.out.println("  "+ dp[0]);
        if (dp[0] > totalSum/2) {
            return true;
        }
        return false;
    }

    public boolean firstWillWinByMemorySearch(int[] values) {
        if (values == null || values.length == 0) {
            return true;
        }
        int n = values.length;
        int[] dp = new int[n + 1];
        boolean[] flag = new boolean[n + 1];
        // sum array is sum value of remaining i numbers of coins
        int[] sum = new int[n + 1];
        sum[n-1] = values[n - 1];
        // at the same time need total sum of all coins
        int totalSum = values[n - 1];

        for(int i = n - 2; i >= 0; i--) {
            sum[i] = sum[i+1] + values[i];
            totalSum += values[i];
        }

        return totalSum/2 < memorizedSearch(dp, flag, values, sum, 0, n);
    }
    int memorizedSearch(int[] dp, boolean[] flag, int[] values, int[] sum, int i, int n) {
        if (flag[i] == true) {
            return dp[i];
        }
        flag[i] = true;
        if (i == n) {
            dp[n] = 0;
        } else if (i == n - 1) {
            dp[i] = values[i];
        } else if (i == n - 2) {
            dp[i] = values[i] + values[i + 1];
        } else {
            dp[i] = sum[i] - Math.min(memorizedSearch(dp, flag, values, sum, i+1, n),
                        memorizedSearch(dp, flag, values, sum, i+2, n));
        }
        return dp[i];
    }

    public static void main(String[] args) {
        CoinInLineII c = new CoinInLineII();
        int[] A = {1, 2, 2};
        int[] B = {1, 2, 4};

        System.out.println(c.firstWillWinByMemorySearch(A) == c.firstWillWin(A));
        System.out.println(c.firstWillWinByMemorySearch(B) == c.firstWillWin(B));
    }
}
