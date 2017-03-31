package com.leetcode.dynamicprogram;

import java.util.Arrays;

/**
 * Created by charles on 2/25/17.
 * You are given coins of different denominations and a total amount of money amount. Write a function to compute the fewest number of coins that you need to make up that amount. If that amount of money cannot be made up by any combination of the coins, return -1.

 Example 1:
 coins = [1, 2, 5], amount = 11
 return 3 (11 = 5 + 5 + 1)

 Example 2:
 coins = [2], amount = 3
 return -1.

 Note:
 You may assume that you have an infinite number of each kind of coin.
 */
public class CoinChange_322 {
    /**
     * DP bottom up solution
     * State : dp[i] is min numbers of coins needs to make change for amount S using coin denominations(c0 .. cn);
     * function : if (coins[j] <= i) {
     *     dp[i] = min(dp[i], dp[i-coins[j]) + 1;
     * }
     * Init : dp[0] = 0;
     * ans : dp[amount]
     */
    public int coinChange(int[] coins, int amount) {
        int max = amount + 1;
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, max);
        dp[0] = 0;
        int len = coins.length;

        for (int i = 1; i <= amount; i++) {
            for (int j = 0; j < len; j++) {
                if (coins[j] <= i) {
                    /**
                     * dp[i] is not choose current coin[j]
                     * dp[i-coins[j]] is to choose current coin[j]
                     */
                    dp[i] = Math.min(dp[i], dp[i - coins[j]] + 1);
                }
            }
        }
        if (dp[amount] > amount) {
            return -1;
        } else {
            return dp[amount];
        }
    }

    /**
     * DP top down solution
     * F(s) = min F(s - c[i]) + 1 where i = 0..n-1
     * init: F(s) = 0, when s = 0;
     *      F(s) = -1, when n = 0
     * Use Backtrack and cut the partial solution in recursive tree,
     * this happends when we try to make a change of coin with value greater than the amount S
     * to improve time complexity we should store the solutions of already calculated suproblem in a table
     */
    public int coinChangeTopDown(int[] coins, int amount) {
        if (amount < 1) {
            return 0;
        }
        return coinChangeTopDown(coins, amount, new int[amount]);
    }

    private int coinChangeTopDown(int[] coins, int remain, int[] count) {
        if (remain < 0) {
            return -1;
        }
        if (remain == 0) {
            return 0;
        }
        if (count[remain - 1] != 0) {
            return count[remain - 1];
        }
        int min = Integer.MAX_VALUE;
        int res = 0;
        for (int coin : coins) {
            res = coinChangeTopDown(coins, remain - coin, count);
            if (res >= 0 && res < min) {
                min = 1 + res;
            }
        }
        if (min == Integer.MAX_VALUE) {
            count[remain - 1] = -1;
        } else {
            count[remain - 1] = min;
        }
        return count[remain - 1];
    }
}
