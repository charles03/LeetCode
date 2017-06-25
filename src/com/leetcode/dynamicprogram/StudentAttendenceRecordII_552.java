package com.leetcode.dynamicprogram;

/**
 * Created by charles on 5/9/17.
 * Given a positive integer n, return the number of all possible attendance records with length n, which will be regarded as rewardable. The answer may be very large, return it after mod 109 + 7.

 A student attendance record is a string that only contains the following three characters:

 'A' : Absent.
 'L' : Late.
 'P' : Present.
 A record is regarded as rewardable if it doesn't contain more than one 'A' (absent) or more than two continuous 'L' (late).

 Example 1:
 Input: n = 2
 Output: 8
 Explanation:
 There are 8 records with length 2 will be regarded as rewardable:
 "PP" , "AP", "PA", "LP", "PL", "AL", "LA", "LL"
 Only "AA" won't be regarded as rewardable owing to more than one absent times.
 */
public class StudentAttendenceRecordII_552 {
    /**
     * A(x)L(y) denote number of strings contain (x) A's and ending with (y) L's
     * Condition: x < 2 and y < 3
     *
     * When n = 1 we have:

     A0L0: P
     A0L1: L
     A0L2:
     A1L0: A
     A1L1:
     A1L2:
     Done:
     When n = 2 we have:

     A0L0: LP, PP
     A0L1: PL
     A0L2: LL
     A1L0: AP, LA, PA
     A1L1: AL
     A1L2:
     Done: AA

     In general we have this transition table:

     -----+---------------
     INIT | A -- L -- P --
     -----+---------------
     A0L0 | A1L0 A0L1 A0L0
     A0L1 | A1L0 A0L2 A0L0
     A0L2 | A1L0 Done A0L0
     A1L0 | Done A1L1 A1L0
     A1L1 | Done A1L2 A1L0
     A1L2 | Done Done A1L0

     3 (A0L0) in right part of table;
     so next (A0L0) = prev (A0L0 + A0L1 + A0L2)
     6 (A1L0) in right part of table;
     next (A1L0) = prev (A0L0 + A0L1 + A0L2 + A1L0 + A1L1 + A1L2) => next(A0L0) + prev(A1L0, A1L1, A1L2)

     From the transition table we see that:
     A0L0 of n can result from A0L0 + A0L1 + A0L2 of n - 1 by appending P
     A0L1 of n can only result from A0L0 of n - 1 by appending L
     and so on...

     That's why in each iteration we update:
     dp[0] = dp[0] + dp[1] + dp[2]
     dp[1] = dp[0]
     and so on...

     */
    private static int MOD = 100000007;
    private static int MATRIX = 6;

    public int checkRecord(int n) {
        if (n == 1) {
            return 3;
        }
        int[] dp = {1,1,0,1,0,0}; // init table when n = 1;
        for (int i = 2; i <= n; i++) {
            dp = new int[] {sum(dp, 0, 2),
                    dp[0], dp[1], sum(dp, 0, 5), dp[3], dp[4]
            };
        }
        return sum(dp, 0, 5);
    }
    private int sum(int[] arr, int i, int j) {
        int res = 0;
        for (int k = i; k <= j; k++) {
            res = (res + arr[k]) % MOD;
        }
        return res;
    }
    /** improved, instead using dp array, use 6 vars to record state
     * denote A(x)_L(y) */
    public int checkRecordII(int n) {
        if (n == 1) {
            return 3;
        }
        long a0_l0 = 1, a0_l1 = 1, a0_l2 = 0, a1_l0 = 1, a1_l1 = 0, a1_l2 = 0;
        long next_a0_l0 = 0, next_a1_l0 = 0;
        for (int i = 1; i <= n; i++) {
            next_a0_l0 = (a0_l0 + a0_l1 + a0_l2) % MOD;
            // sequence of shift prev state to next is important
            // should use prev state before assign next state
            a0_l2 = a0_l1;
            a0_l1 = a0_l0;
            a0_l0 = next_a0_l0;
            next_a1_l0 = (next_a0_l0 + a1_l0 + a1_l1 + a1_l2) % MOD;
            a1_l2 = a1_l1;
            a1_l1 = a1_l0;
            a1_l0 = next_a1_l0;
        }
        return (int) a1_l0;
    }

    /** log(n) solution using Matrix multiplication
     *                              1 1 0 1 0 0
     *                              1 0 1 1 0 0
     * dp_n[0,1,2,3,4,5]      *     1 0 0 1 0 0     = dp_n+1[0,1,2,3,4,5]
     *                              0 0 0 1 1 0
     *                              0 0 0 1 0 1
     *                              0 0 0 1 0 1
     *
     f[i][0][0]   | 0 0 1 0 0 0 |   f[i-1][0][0]
     f[i][0][1]   | 1 0 1 0 0 0 |   f[i-1][0][1]
     f[i][0][2] = | 0 1 1 0 0 0 | * f[i-1][0][2]
     f[i][1][0]   | 0 0 1 0 0 1 |   f[i-1][1][0]
     f[i][1][1]   | 0 0 1 1 0 1 |   f[i-1][1][1]
     f[i][1][2]   | 0 0 1 0 1 1 |   f[i-1][1][2]

     Let A be the matrix above, then f[n][][] = A^n * f[0][][], where f[0][][] = [1 1 1 1 1 1].
     The point of this approach is that we can compute A^n using exponentiating by squaring (thanks to @StefanPochmann for the name correction),
     which will take O(6^3 * log n) = O(log n) time. Therefore, the runtime improves to O(log n), which suffices to handle the case for much larger n, say 10^18.
     Update: The final answer is f[n][1][2], which involves multiplying the last row of A^n and the column vector [1 1 1 1 1 1].
     Interestingly, it is also equal to A^(n+1)[5][2] as the third column of A is just that vector.
     */
    public int checkRecordIII(int n) {
        int[][] A = {
                {0, 0, 1, 0, 0, 0},
                {1, 0, 1, 0, 0, 0},
                {0, 1, 1, 0, 0, 0},
                {0, 0, 1, 0, 0, 1},
                {0, 0, 1, 1, 0, 1},
                {0, 0, 1, 0, 1, 1},
        };
        return pow(A, n+1)[5][2];
    }
    int[][] pow(int[][] A, int n) {
        int[][] res = new int[MATRIX][MATRIX];
        for (int i = 0; i < MATRIX; i++) {
            res[i][i] = 1;
        }
        while (n > 0) {
            if (n % 2 == 1) {
                res = multiply(res, A);
            }
            A = multiply(A, A);
            n /= 2;
        }
        return res;
    }
    int[][] multiply(int[][] A, int[][] B) {
        int[][] C = new int[MATRIX][MATRIX];
        for (int i = 0; i < MATRIX; i++) {
            for (int j = 0; j < MATRIX; j++) {
                for (int k = 0; k < MATRIX; k++) {
                    C[i][j] = (int)((C[i][j] + (long) A[i][k] * B[k][j]) % MOD);
                }
            }
        }
        return C;
    }

    public static void main(String[] args) {
        StudentAttendenceRecordII_552 s = new StudentAttendenceRecordII_552();
        System.out.println(s.checkRecordII(2));
    }
}
