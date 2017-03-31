package com.leetcode.greedy;

/**
 * Created by charles on 3/28/17.
 * Say you have an array for which the ith element is the price of a given stock on day i.

 Design an algorithm to find the maximum profit.
 You may complete as many transactions as you like (ie, buy one and sell one share of the stock multiple times). However,
 you may not engage in multiple transactions at the same time (ie, you must sell the stock before you buy again).
 */
public class BestTimeToBuySellStockII_122 {
    /**
     * Thought,
     * Greedy solution,
     * sum is final result, curr = prices[0]
     * if (next > curr) {
     *     sum += next - curr
     * }
     * always look for ascending subsequence
     */
    public int maxProfit(int[] prices) {
        if (prices == null || prices.length == 0) {
            return 0;
        }
        int curr = prices[0];
        int sum = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > curr) {
                sum += prices[i] - curr;
            }
            curr = prices[i];
        }
        return sum;
    }
}
