package com.leetcode.dynamicprogram;

import java.util.*;

/**
 * Created by charles on 5/13/17.
 * Say you have an array for which the ith element is the price of a given stock on day i.

 Design an algorithm to find the maximum profit. You may complete at most k transactions.

 Note:
 You may not engage in multiple transactions at the same time (ie, you must sell the stock before you buy again).
 */
public class BestTimeToBuySellStockIV_188 {
    /**
     * state : mustsell[i][j] at most j transactions till i-th day, max profit when must sell at i-th day
     *         global[i][j] at most j transactions till i-th day, max profit when not need to sell at i-th day
     * function:
     *      mustsell[i][j] = Math.max(global[i-1][j-1] + gain_loss(at i-th day)
     *                              mustsell[i-1][j] + gain_loss(at i-th day))
     *      global[i][j] = Math.max(global[i-1][j], mustsell[i][j])
     *
     * init : mustsell[0][k] = global[0][k] = 0;
     */
    public int maxProfit(int k, int[] prices) {
        if (k == 0) {
            return 0;
        }
        if (k >= prices.length / 2) {
            return profitsByEverydayTransaction(prices);
        }
        int len = prices.length;
        int[][] mustsell = new int[len + 1][len + 1];
        int[][] globalprofit = new int[len + 1][len + 1];

        // init
        mustsell[0][0] = globalprofit[0][0] = 0;
        for (int i = 1; i <= k; i++) {
            mustsell[0][i] = globalprofit[0][i] = 0;
        }
        // DP
        int currDayGain = 0;
        for (int i = 1; i < len; i++) {
            currDayGain = prices[i] - prices[i-1];
            mustsell[i][0] = 0;
            for (int j = 1; j <= k; j++) {
                mustsell[i][j] = Math.max(globalprofit[i-1][j-1] + currDayGain,
                                            mustsell[i-1][j] + currDayGain);
                globalprofit[i][j] = Math.max(globalprofit[i-1][j], mustsell[i][j]);
            }
        }
        return globalprofit[len-1][k];
    }

    /** {@link com.leetcode.dynamicprogram.BestTimeToBuySellStockII_122}
     * as many transaction as we can, so use daily transaction, only sum positive daily gain
     */
    public int profitsByEverydayTransaction(int[] prices) {
        int profit = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > prices[i-1]) {
                profit += prices[i] - prices[i-1];
            }
        }
        return profit;
    }

    /**
     * reuse idea in {@link BestTimeToBuySellStockIII_123}
     * buy[i] : max profit at i-th transaction when buy stock with price of that day
     * sell[i] : max profit at i-th transaction when sell stock with price of that day;
     */
    public int maxProfitII(int k, int[] prices) {
        if (k >= prices.length / 2) {
            // handle this case independently so as to avoid TLE for large test case
            return profitsByEverydayTransaction(prices);
        }
        int[] buy = new int[k+1];
        int[] sell = new int[k+1];
        Arrays.fill(buy, Integer.MIN_VALUE);

        for (int price : prices) {
            for (int i = 1; i <= k; i++) {
                /**
                 * use i-1 transaction to get max profit at sell
                 * i-th transaction is to buy stock, so far max profit is sell[i-1] - price
                 * iterate every price to get max of buy when taking i-th transaction
                 */
                buy[i] = Math.max(buy[i], sell[i-1] - price);
                sell[i] = Math.max(sell[i], buy[i] + price);
            }
        }
        return sell[k];
    }

    /**
     * Time Complexity O(n + klg(n)) solution by using max heap and stack
     * thought: find all adjacent valley-peek pair so as to calculate profit easily
     * instead of accumulating all profits, we need highest k ones
     *
     * following is key point
     * two (valley-peek) pairs (v1,p1) (v2,p2)
     * when satisfying v1 <= v2 and p1 <= p2;
     * so that can merge these two transactions
     * trick is to treat (v1,p2) as first transaction. (v2,p1) as second.
     * then we can guarantee correct max profit for both situations
     * p2-v1 for single transaction, p2-v1+p1-v2 for two
     * extract k maximums from heap consumes another O(klgN)
     */
    public int maxProfitIII(int k, int[] prices) {
        if (k < 1 || prices == null || prices.length == 0) {
            return 0;
        }
        int res = 0;
        List<Integer> profits = new LinkedList<>();
        Stack<Point> stack = new Stack<>();
        int start = 0, end = 0; 
        int len = prices.length;
        while (end < len) {
            // find next valley & peek pair
            start = end;
            while (start+1 < len && prices[start] >= prices[start+1]) {
                start++;
            }
            end = start+1;
            while (end+1 < len && prices[end] <= prices[end+1]) {
                end++;
            }
            if (start < end && start < len && end < len) {
                // save profit of one transaction at the last valley & peek pair
                // if current valley is lower than last valley
                while (!stack.isEmpty() && prices[start] < prices[stack.peek().valley]) {
                    profits.add(prices[stack.peek().peek] - prices[stack.peek().valley]);
                    stack.pop();
                }
                // save profit difference between 1 transaction (last v and current p) and 2 transactions (last v/p + current v/p),
                // if current v is higher than last v and current p is higher than last p
                while (!stack.isEmpty() && prices[end] >= prices[stack.peek().peek]) {
                    profits.add(prices[stack.peek().peek] - prices[start]);
                    start = stack.peek().valley;
                    stack.pop();
                }
                stack.push(new Point(start, end));
            }
        }
        // save profits of remaining valley/peek pair
        while (!stack.isEmpty()) {
            profits.add(prices[stack.peek().peek] - prices[stack.peek().valley]);
            stack.pop();
        }
        PriorityQueue<Integer> heap = new PriorityQueue<>((a,b) -> b.compareTo(a));
        heap.addAll(profits);

        for (int i = 0; i < k && !heap.isEmpty(); i++) {
            res += heap.poll();
        }
        return res;
    }

}
class Point {
    int valley;
    int peek;
    public Point(int valley, int peek) {
        this.valley = valley;
        this.peek = peek;
    }
}
