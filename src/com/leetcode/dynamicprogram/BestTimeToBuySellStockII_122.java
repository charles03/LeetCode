package com.leetcode.dynamicprogram;

/**
 * Created by charles on 3/2/17.
 * Say you have an array for which the ith element is the price of a given stock on day i.

 Design an algorithm to find the maximum profit. You may complete as many transactions as you like (ie, buy one and sell one share of the stock multiple times). However, you may not engage in multiple transactions at the same time (ie, you must sell the stock before you buy again).
 */
public class BestTimeToBuySellStockII_122 {
    /**
     * Instead of looking for every peak following a valley, we can simply go on crawling over the slope
     * and keep on adding profit obtained from every consecutive transacction.
     * In the end, we will be using peaks and valleys effectively.
     */
    public int maxProfit(int[] prices) {
        if (prices == null || prices.length == 0) {
            return 0;
        }
        int profit = 0;
        int min = prices[0];
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > prices[i-1]) {
                profit += prices[i] - prices[i-1];
            }
        }
        return profit;
    }

    /**
     * Peak Valley Approach
     * Mathematically speaking: TotalProfit = Sum(height(peak(i)) - height(valley(i))))
     */
    public int maxProfitII(int[] prices) {
        int i = 0;
        int valley = prices[0];
        int peak = prices[0];
        int maxProfit = 0;

        while (i < prices.length - 1) {
            // look for next valley
            while (i < prices.length - 1 && prices[i] >= prices[i+1]) {
                i++;
            }
            valley = prices[i];
            // look for next peak
            while (i < prices.length - 1 && prices[i] <= prices[i+1]) {
                i++;
            }
            peak = prices[i];
            maxProfit += peak - valley;
        }
        return maxProfit;
    }

    /**
     * Brute force solution, TLE
     */
    public int maxProfitIII(int[] prices) {
        return calculate(prices, 0);
    }
    int calculate(int[] nums, int len) {
        if (len >= nums.length) {
            return 0;
        }
        int max = 0;
        for (int s = len; s < nums.length; s++) { // start index
            int maxProfit = 0;
            for (int i = s + 1; i < nums.length; i++) {
                if (nums[s] < nums[i]) {
                    int profit = calculate(nums, i + 1) + nums[i] - nums[s];
                    if (profit > maxProfit) {
                        maxProfit = profit;
                    }
                }
            }
            if (maxProfit > max) {
                max = maxProfit;
            }
        }
        return max;
    }

}
