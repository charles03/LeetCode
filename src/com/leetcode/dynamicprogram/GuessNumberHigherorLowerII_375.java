package com.leetcode.dynamicprogram;

/**
 * Created by charles on 1/31/17.
 * We are playing the Guess Game. The game is as follows:
 I pick a number from 1 to n. You have to guess which number I picked.

 Every time you guess wrong, I'll tell you whether the number I picked is higher or lower.
 However, when you guess a particular number x, and you guess wrong, you pay $x. You win the game when you guess the number I picked.

 Example:
 n = 10, I pick 8.

 First round:  You guess 5, I tell you that it's higher. You pay $5.
 Second round: You guess 7, I tell you that it's higher. You pay $7.
 Third round:  You guess 9, I tell you that it's lower. You pay $9.

 Game over. 8 is the number I picked.

 You end up paying $5 + $7 + $9 = $21.
 Given a particular n ≥ 1, find out how much money you need to have to guarantee a win.

 Hint :
 The best strategy to play the game is to minimize the maximum loss you could possibly face. Another strategy is to minimize the expected loss. Here, we are interested in the first scenario.
 */
public class GuessNumberHigherorLowerII_375 {
    /**
     * For each number x in range[i, j]
     * when pick x = x + Max(dp[i, x - 1], dp[x+1, j]
     *   max menas whenever you choose a number, feedback is always worst and therefore leads you to worse branch
     *   dp[i, j] = min(xi, ... xj);
     *   above min is to make sure you are minimizing your cost.
     */
    public int getMoneyAmount(int n) {
        int[][] dp = new int[n + 1][n + 1];

        return memorizedSearch(dp, 1, n);
    }

    private int memorizedSearch(int[][] dp, int s, int e) {
        if (s >= e) {
            return 0;
        }
        if (dp[s][e] != 0) { // already visited
            return dp[s][e];
        }
        int res = Integer.MAX_VALUE;
        for (int i = s; i <= e; i++) {
            int tmp = i + Math.max(memorizedSearch(dp, s, i-1), memorizedSearch(dp, i+1, e));
            res = Math.min(res, tmp);
        }
        dp[s][e] = res;
        return res;
    }

    public int getMoneyAmountOpt(int n) {
        int[][] dp = new int[n + 1][n + 1];
        return helper(dp, 1, n);
    }
    private int helper(int[][] dp, int left, int right) {
        if (dp[left][right] != 0) { // already visited
            return dp[left][right];
        }
        if (left >= right) {
            return 0; // no means
        } else if (left >= right - 2) {
            return right - 1; // last right num
        }
        int mid = left + (right - left) / 2;
        int min = Integer.MAX_VALUE;
        int i = 0;
        int cost = 0;
        for (i = mid; i < right; i++) {
            cost = i + Math.max(helper(dp, left, i - 1), helper(dp, i + 1, right));
            min = Math.min(min, cost);
        }
        dp[left][right] = min;
        return min;
    }

}
