package com.advanced.dynamicprogram.gametheory;

/**
 * As for game theory DP, recommend to use memorized search to start thinking question
 * Bring large state question into small state by searching
 *      Transit to small state and To search result for small state
 */

/**
 * Created by charles on 11/13/16.
 *
 * There are n coins in a line.
 * Two players take turns to take one or two coins from right side until there are no more coins left.
 * The player who take the last coin wins.

 Could you please decide the first play will win or lose?
 n = 1, return true.
 n = 2, return true.
 n = 3, return false.
 n = 4, return true.
 n = 5, return true.

 Challenge: O(n) time and O(1) memory
 */
public class CoinsInLine {
    /**
     * State: dp[i] is status of current player who pick coin from "i" amount
     * Function: dp[i] = (!dp[i-1] || !dp[i-2])
     *      dp[i-1] and dp[i-2] is status of opponent player after current player pick 1 or 2 coins
     *      only if in any 2 of cases status is lose for opponent player
     *      current player can win under i amount
     * Initialization: dp[0] = false, dp[1] = true; dp[2] = true;
     * Return dp[n]
     */

    public boolean firstWillWinByMemorySearch(int n) {
        boolean[] dp = new boolean[n + 1];
        boolean[] flag = new boolean[n + 1];

        return memorizedSearch(n, dp, flag);
    }

    private boolean memorizedSearch(int i, boolean[] dp, boolean[] flag) {
        if (flag[i] == true) {
            return dp[i];
        }
        if (i == 0) {
            dp[i] = false;
        } else if (i == 1) {
            dp[i] = true;
        } else if (i == 2) {
            dp[i] = true;
        } else {
            dp[i] = !memorizedSearch(i-1, dp, flag) || !memorizedSearch(i-2, dp, flag);
        }
        // update flag array
        flag[i] = true;
        return dp[i];
    }

    public static void main(String[] args) {
        CoinsInLine c = new CoinsInLine();
        System.out.println(c.firstWillWinByMemorySearch(7));
        System.out.println(c.firstWillWin(7));

        for (int i = 1; i < 10000; i++) {
            if (c.firstWillWin(i) != c.firstWillWinByMemorySearch(i)) {
                System.out.println(i);
            }
        }
    }

    /**
     * thought: conduct from 0 to n iteratively and in DP
     */
    public boolean firstWillWin(int n) {
        if (n == 0) {return false;}
        boolean[] dp = new boolean[3];
        dp[0] = false;
        dp[1] = true;
        int i = 2;
        while (i <= n) {
            dp[i%3] = !dp[(i-1)%3] || !dp[(i-2)%3];
            i++;
        }
        return dp[n%3];
    }

    /** greedy solution
     * at last two rounds, if there are 3 coins left for component,
     * then component must be lost because no matter how he choose, we always can find a way to select last coin
     * thus, current play will be lost only when he is in opponent position
     * which facing 3X coins.
     */
    public boolean firstWillWinIII(int n) {
        if (n % 3 == 0) {
            return false;
        }
        return true;
    }
}
