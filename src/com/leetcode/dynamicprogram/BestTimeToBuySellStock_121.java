package com.leetcode.dynamicprogram;

/**
 * Created by charles on 3/1/17.
 * Say you have an array for which the ith element is the price of a given stock on day i.

 If you were only permitted to complete at most one transaction (ie, buy one and sell one share of the stock), design an algorithm to find the maximum profit.

 Example 1:
 Input: [7, 1, 5, 3, 6, 4]
 Output: 5

 max. difference = 6-1 = 5 (not 7-1 = 6, as selling price needs to be larger than buying price)
 Example 2:
 Input: [7, 6, 4, 3, 1]
 Output: 0

 In this case, no transaction is done, i.e. max profit = 0.
 */
public class BestTimeToBuySellStock_121 {
    /**
     * No use brute force
     * The points of interest are peeks and valleys
     * Need to find largest peak following smallest valley,
     * can maintain two vars minprice and maxProfit
     */
    public int maxProfit(int[] prices) {
        if (prices == null || prices.length == 0) {
            return 0;
        }
        int profit = 0; // maximum profit
        int min = Integer.MAX_VALUE; // min price
        for (int i = 0; i < prices.length; i++) {
            if (prices[i] < min) {
                min = prices[i];
            }
            if ((prices[i] - min) > profit) {
                profit = prices[i] - min;
            }
        }
        return profit;
    }
}
