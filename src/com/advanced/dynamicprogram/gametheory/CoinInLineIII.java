package com.advanced.dynamicprogram.gametheory;

/**
 * Created by charles on 11/15/16.
 * There are n coins in a line. Two players take turns to take a coin from one of the ends of the line until there are no more coins left.
 * The player with the larger amount of money wins.

 Could you please decide the first player will win or lose?
 Given array A = [3,2,2], return true.
 Given array A = [1,2,4], return true.
 Given array A = [1,20,4], return false

 Follow Up Question:

 If n is even. Is there any hacky algorithm that can decide
 whether first player will win or lose in O(1) memory and O(n) time?
 */

/**
 * This is Range DP, because dp is continuous after select one coin from either side
 * State : dp[i][j] is from index i to j, current player can get most value of coins
 * Function: dp[i][j] = sum[i][j] - Math.min(dp[i+1][j], dp[i][j-1])
 *
 * or Function: dp[i][j] = Math.max(
 *                      A[i] + Math.min(dp[i+2][j], dp[i+1][j-1])
 *                      A[j] + Math.min(dp[i+1][j-1], dp[i][j-2]))
 *  from small range to big range
 *  sum[i][j] is from index i to j, value sum of coins
 * Initialization: dp[i][i] = coin[i];
 * Answer dp[0][n-1]
 *
 * use memory search for optimization
 */
public class CoinInLineIII {
    public boolean firstWillWin(int[] A) {
        if (A == null || A.length == 0) {return false;}
        int n = A.length;
        if (n <= 2) {return true;}
        // range DP need 2-D matrix
        int[][] dp = new int[n + 2][n + 2];
        // assign MAX_VALUE /2 to all elem
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                dp[i][j] = Integer.MAX_VALUE / 2;
            }
        }
        for (int i = 1; i <= n; i++) {
            // init when range is 0
            dp[i][i] = A[i - 1];
        }
        int sum = 0;
        for (int i = n; i>= 1; i--) {
            sum += A[i - 1]; // to record sum
            for (int j = i + 1; j <= n; j++) {
                dp[i][j] = Math.max(A[i-1] + Math.min(dp[i+2][j], dp[i+1][j-1]),
                            A[j-1] + Math.min(dp[i+1][j-1], dp[i][j-2]));
            }
        }

        return dp[1][n] > sum - dp[1][n];
    }

    /**
     * Memorized search
     * State: for current player, most value can be picked
     *  split by 2 scenarios; pick index i or pick index j
     *      because opponent always look for max value as well
     *      thus, for same player, next round value should be
     *      min(dp[i+2][j], dp[i+1][j-1]) when pick index i coin
     *      vice versa for index j,
     * Function: dp[i][j] = Math.max(
     *                      A[i] + Math.min(dp[i+2][j], dp[i+1][j-1])
     *                      A[j] + Math.min(dp[i+1][j-1], dp[i][j-2]))
     *
     */
    public boolean firstWillWinByMemorySearchOne(int[] A) {
        if (A == null || A.length == 0) {return false;}
        int n = A.length;
        if (n <= 2) {return true;}
        int[][] dp = new int[n + 1][n + 1];
        boolean[][] visited = new boolean[n + 1][n + 1];

        int sum = 0;
        for (int num : A) {sum += num;}

        return sum < 2 * memorizedSearch(0, n-1, dp, visited, A);
    }

    private int memorizedSearch(int left, int right, int[][] dp, boolean[][] visited, int[] A) {
        if (visited[left][right]) {
            return dp[left][right];
        }
        visited[left][right] = true;
        int pick_left;
        int pick_right;
        if (left > right) {
            dp[left][right] = 0;
        } else if (left == right) {
            dp[left][right] = A[left];
        } else if ((left+1) == right) {
            dp[left][right] = Math.max(A[left], A[right]);
        } else {
            pick_left = A[left] + Math.min(memorizedSearch(left + 2, right, dp, visited, A),
                                memorizedSearch(left + 1, right - 1, dp, visited, A));
            pick_right = A[right] + Math.min(memorizedSearch(left, right - 2, dp, visited, A),
                                memorizedSearch(left + 1, right - 1, dp, visited, A));
            dp[left][right] = Math.max(pick_left, pick_right);
        }

        return dp[left][right];
    }

    /**
     * or use Function: dp[i][j] = Math.max(sum(i,j)-dp[i+1][j], sum(i,j)-dp[i][j-1])
     *     equal: sum(i,j) - Math.min(dp[i+1][j], dp[i][j-1]));
     */
    public boolean firstWillWinByMemorySearchTwo(int[] A) {
        if (A == null || A.length == 0) {return false;}
        int n = A.length;
        if (n <= 2) {return true;}
        int[][] dp = new int[n + 1][n + 1];
        boolean[][] visited = new boolean[n + 1][n + 1];
        int[][] sum = new int[n + 1][n + 1];
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                sum[i][j] = i == j ? A[i] : sum[i][j-1] + A[j];
            }
        }
        int totalSum = 0;
        for (int num: A) {totalSum += num;}

        return totalSum < 2 * memorizedSearchTwo(0, n, dp, visited, sum, A);
    }

    private int memorizedSearchTwo(int l, int r, int[][] dp, boolean[][] visited, int[][] sum, int[] A) {
        if (visited[l][r]) {
            return dp[l][r];
        }
        visited[l][r] = true;
        if (l > r) {
            dp[l][r] = 0;
        } else if (l == r) {
            dp[l][r] = A[l];
        } else if (l + 1 == r) {
            dp[l][r] = Math.max(A[l], A[r]);
        } else {
            dp[l][r] = sum[l][r] - Math.min(memorizedSearchTwo(l+1, r, dp, visited, sum, A),
                                            memorizedSearchTwo(l, r-1, dp, visited, sum, A));
        }
        return dp[l][r];
    }

    /**
     * Follow up Question
     * if n is even, is there any hacky algo that can decide whether first play will win
     * or lose in O(1) memory and O(n) time
     *
     * MIT open course,
     *
     * Thought, if sum of all even index > half of total or all odd index > half of total
     * then first play must win
     *
     * Why we can apply this strategy, is you can control your selection
     * regardless of your component's move.
     * you can always stick with even selection or odd selection from begin until end
     * unless coininline1 and 2, need to maximize the total money.
     *
     * above strategy only valid when n is even
     * https://www.youtube.com/watch?v=Tw1k46ywN6E&t=4165s
     */
    public boolean firstWillWinII(int[] A) {
        int onlyEven = 0;
        int onlyOdd = 0;
        for (int i = 0; i < A.length; i += 2) {
            onlyEven += A[i];
        }
        for (int j = 0; j < A.length; j += 2) {
            onlyOdd += A[j];
        }
        return onlyEven != onlyOdd;
    }


    public static void main(String[] args) {
        CoinInLineIII c = new CoinInLineIII();
        int[] A = {1, 20, 4};
        System.out.println(c.firstWillWin(A));
        System.out.println(c.firstWillWinByMemorySearchOne(A));

    }
}
