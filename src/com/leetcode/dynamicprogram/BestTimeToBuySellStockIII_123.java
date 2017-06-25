package com.leetcode.dynamicprogram;

/**
 * Created by charles on 5/9/17.
 * Say you have an array for which the ith element is the price of a given stock on day i.

 Design an algorithm to find the maximum profit. You may complete at most two transactions.
 Note:
 You may not engage in multiple transactions at the same time (ie, you must sell the stock before you buy again).
 */
public class BestTimeToBuySellStockIII_123 {

    /**
     * buy1 Minimum price to buy the stock, -price[i], if price[i] is small, then -price[i] is large
     * sell1 best profit so far, if sell stock not after day i (1st transaction)
     * buy2 best profit so far, if buy stock not after day i (2nd transaction)
     * sell2 final profit
     *
     * The logic between buy1 and sell1 is quite straight forward. What you need to do is simply find a minimum price to buy and sell it some days after.
     Of course, sell1 won't update if the profit is not greater than before even if you buy the stock at a lower price. Let's assume you sell the stock at Day a to get the greatest profit for the 1st transaction, which stores in sell1.

     Now comes the trick. Assume you find a better deal at Day b, sell1 get updated. So you have 2 choice for buy2:

     not update buy2, you still sell your stock at Day a. Nothing changed.
     update buy2 with new sell1, which means you sell the stock at Day b.
     buy2 = sell1 - price[i] means you sell you stock at Day b and buy it at Day i. And Day i is definitely not early than Day b, which is the hidden logic.
     */
    public int maxProfit(int[] prices) {
        int sell1 = 0, sell2 = 0;
        int buy1 = Integer.MIN_VALUE, buy2 = Integer.MIN_VALUE;
        for (int i = 0; i < prices.length; i++) {
            buy1 = Math.max(buy1, -prices[i]);
            sell1 = Math.max(sell1, buy1 + prices[i]);
            buy2 = Math.max(buy2, sell1 - prices[i]);
            sell2 = Math.max(sell2, buy2 + prices[i]);
        }
        return sell2;
    }
    /**
     * O(n) time complexity and O(n) space Solution
     * use two traversal
     * first traverse:
     * use dp[n] array to store max profit from front until current index
     * second traverse from end to front
     * as for each price[i]
     * if (currentMax[i] is ending at the price[i]),
     * at the same time if (price[i] is min(start) of second transaction) {
     *     then these two transactions can be merged as one.
     * }
     * else (currentMax[i] is not ending at the prices[i]) {
     *     then can be split as two transactions
     * }
     */
    public int maxProfitII(int[] prices) {
        int len = prices.length;
        if (len < 2) {
            return 0;
        }
        int[] dp = new int[len];
        int min = prices[0];
        // first traverse
        for (int i = 1; i < len; i++) {
            dp[i] = Math.max(dp[i-1], prices[i] - min);
            min = Math.min(min, prices[i]); // maintain min in the range from start to current index
        }
        // second traverse, from end to front
        int max = prices[len - 1];
        int profit = 0;
        for (int i = len - 2; i >= 0; i--) {
            max = Math.max(max, prices[i]); // maintain max in the range from end to current index
            profit = Math.max(profit, max - prices[i] + dp[i]);
        }
        return profit;
    }

    /**
     * similar with second solution,
     * first traverse to get max profit from left till that position
     * second traverse to get max profit from right till this position.
     * third traverse to store global profit after adding left[i] + right[i]
     */
    public int maxProfitIII(int[] prices) {
        if (prices == null || prices.length <= 1) {
            return 0;
        }
        int len = prices.length;
        int[] left = new int[len];
        int[] right = new int[len];
        // DP from left to right
        left[0] = 0;
        int min = prices[0];
        for (int i = 1; i < len; i++) {
            min = Math.min(min, prices[i]);
            left[i] = Math.max(left[i-1], prices[i] - min);
        }
        // DP from right to left
        right[len-1] = 0;
        int max = prices[len-1];
        for (int i = len-2; i >= 0; i--) {
            max = Math.max(max, prices[i]);
            right[i] = Math.max(right[i+1], max - prices[i]);
        }
        // get global profit
        int profit = 0;
        for (int i = 0; i < len; i++) {
            profit = Math.max(profit, left[i] + right[i]);
        }
        return profit;
    }

}
