package com.leetcode.dynamicprogram;

/**
 * Created by charles on 5/31/17.
 * The Fibonacci numbers are the numbers in the following integer sequence.

 0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, ……..

 In mathematical terms, the sequence Fn of Fibonacci numbers is defined by the recurrence relation

 Fn = Fn-1 + Fn-2
 with seed values

 F0 = 0 and F1 = 1.
 */
public class Fibonacci {
    /**
     * space optimized DP solution
     */
    public int fib(int n) {
        int a = 0;
        int b = 1;
        int c = 0;
        if (n == 0) {
            return a;
        }
        for (int i = 2; i <= n; i++) {
            c = a + b;
            a = b;
            b = c;
        }
        return b;
    }
    /**
     * time optimized matrix solution
     *
     * use recursive multiplication to get Power(M, n)
     * where M is matrix {{1,1},{1,0}}
     * then we get the (n+1)th Fibonacci number as the element at row and column (0, 0) in the resultant matrix.
     *
     * |1 1|^n    |Fn+1  Fn|
     * |1 0|   =  |Fn  Fn-1|
     */
    public int fibII(int n) {
        int[][] F = new int[][]{{1,1},{1,0}};
        if (n == 0) {
            return 0;
        }
        power(F, n-1);
        return F[0][0];
    }
    private void power(int[][] f, int n) {
        if (n == 0 || n == 1) {
            return;
        }
        power(f, n/2);
        multiply(f, f);

        int[][] m = new int[][]{{1,1},{1,0}};
        if (n % 2 != 0) {
            multiply(f, m);
        }
    }
    private void multiply(int[][] a, int[][] b) {
        // because two matrix are square matrix and also both length are 2
        int[][] c = new int[2][2];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                c[i][j] += a[i][j] * b[j][i];
            }
        }
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                a[i][j] = c[i][j];// assign back to a
            }
        }
    }



    /**
     * Taking determinant on both sides, we get
     (-1)n = Fn+1Fn-1 – Fn2
     Moreover, since AnAm = An+m for any square matrix A, the following identities can be derived (they are obtained form two different coefficients of the matrix product)

     FmFn + Fm-1Fn-1 = Fm+n-1

     By putting n = n+1,

     FmFn+1 + Fm-1Fn = Fm+n

     Putting m = n

     F2n-1 = Fn^2 + Fn-1^2

     F2n = (Fn-1 + Fn+1)Fn = (2Fn-1 + Fn)Fn (Source: Wiki)

     To get the formula to be proved, we simply need to do following
     If n is even, we can put k = n/2
     If n is odd, we can put k = (n+1)/2
     */
    private int[] memo; // create an array for memorization
    public int fibIII(int n) {
        if (n == 0) {
            return 0;
        }
        if (n == 1 || n == 2) {
            return n;
        }
        if (memo == null) {
            memo = new int[n];
        }
        int k = 0;
        if (memo[n] > 0) { // already computed
            return memo[n];
        }
        if (n / 2 != 0) { // n is odd
            k = (n+1) / 2;
            memo[n] = fibIII(k) * fibIII(k) + fibIII(k-1) * fibIII(k-1);
        } else {
            k = n/2;
            memo[n] = (2*fibIII(k-1) + fibIII(k)) * fibIII(k);
        }
        return memo[n];
    }
}
