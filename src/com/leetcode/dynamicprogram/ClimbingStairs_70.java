package com.leetcode.dynamicprogram;

/**
 * Created by charles on 4/1/17.
 * You are climbing a stair case. It takes n steps to reach to the top.

 Each time you can either climb 1 or 2 steps. In how many distinct ways can you climb to the top?

 Note: Given n will be a positive integer.
 */
public class ClimbingStairs_70 {
    /**
     * Thought: DP solution
     * state : f[i] is distinct ways to end at index i in array
     * function: f[i] = f[i-1] + f[i-2]
     * init : f[0] = 1, f[1] = 1;
     * explanation: how to jump to index i
     * is either from i-1 or from i-2
     * distinct ways at (i-1) and distinct ways at (i-2) is total distinct ways to (i)
     */
    public int climbStairs(int n) {
        int[] dp = new int[n+1];
        dp[0] = 1;
        dp[1] = 1;
        for (int i = 2; i <= n; i++) {
            dp[i] = dp[i-1] + dp[i-2];
        }
        return dp[n];
    }

    /** Space optimization
     * we can use two variables rollingly to store state of firstPrev and secondPrev
     */
    public int climbStairsII(int n) {
        if (n == 1) { // corner case
            return 1;
        }
        int res = 0;
        int firstPrev = 1;
        int secondPrev = 1;
        for (int i = 2; i <= n; i++) {
            res = firstPrev + secondPrev;
            secondPrev = firstPrev;
            firstPrev = res;
        }
        return res;
    }
    /** time complexity optimization
     * use Fibonacci Formula
     * Because pow method takes log(n) time
     */
    public int climbStairsIII(int n) {
        double sqrt5 = Math.sqrt(5);
        double fibN = Math.pow((1+sqrt5)/2, n+1) - Math.pow((1-sqrt5)/2, n+1);
        return (int)(fibN / sqrt5);
    }
    /** lg(n) time complexity
     * Binets Method, use matrix multiplication to obtain nth Fibonacci Number
     * MIT CTW cource mentioned
     */
    public int climbStairsIV(int n) {
        /*  [1 1]
            [1,0]
         */
        int[][] q = {{1,1},{1,0}};
        int[][] res = matrixPower(q, n);
        return res[0][0];
    }

    /** power of n
     * num ^ (n/2) * num ^ (n/2) = num ^ (n/2 + n/2) = num ^ n
     * Thus take log(n) time complexity to calculate power of n
     */
    private int[][] matrixPower(int[][] a, int n) {
        /*  [1,0]
            [0,1]
         */
        int[][] res = {{1,0}, {0,1}};
        while (n > 0) {
            if ((n & 1) == 1) {
                res = matrixMultiply(res, a);
            }
            n >>= 1;
            a = matrixMultiply(a, a);
        }
        return res;
    }
    /** only for size 2 matrix */
    private int[][] matrixMultiply(int[][] a, int[][] b) {
        int[][] c = new int[2][2];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                c[i][j] = a[i][0] * b[0][j] + a[i][1] * b[1][j];
            }
        }
        return c;
    }

}
