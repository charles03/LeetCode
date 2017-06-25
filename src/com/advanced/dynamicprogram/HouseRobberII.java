package com.advanced.dynamicprogram;

/**
 * Created by charles on 11/12/16.
 * After robbing those houses on that street, the thief has found himself a new place for his thievery
 * so that he will not get too much attention. This time, all houses at this place are arranged in a circle.
 * That means the first house is the neighbor of the last one.
 * Meanwhile, the security system for these houses remain the same as for those in the previous street.

 Given a list of non-negative integers representing the amount of money of each house,
 determine the maximum amount of money you can rob tonight without alerting the police.
 *
 * extension {@link HouseRobber}
 *
 * Two Tips for resolving this type of questions --> head connect tail, as cycle
 * solution one: find a way to split
 * for example
 * [3,6,4] -->[3,6] when not choose head
 *        |-->[6,4] when not choose tail
 * Solution two: append duplicate after tail of origin
 */
public class HouseRobberII {
    public long houseRobbing(int[] A) {
        if (A == null || A.length == 0) {return 0;}
        if (A.length == 1) {return A[0];}
        // split two subarray to separately addressing
        return Math.max(robbing(A, 0, A.length - 2), robbing(A, 1, A.length - 1));
    }
    // use example method in @{link HouseRobber}
    public long robbing(int[] A, int l, int r) {
        // rolling array for space optimization
        long[] dp = new long[2];
        if (l == r) {return A[r];} // init
        if (l + 1 == r) {return Math.max(A[l], A[r]);} // adjacent
        dp[l%2] = A[l];
        dp[(l+1)%2] = Math.max(A[l], A[l+1]);

        for (int i = l+2; i <= r; i++) {
            dp[i%2] = Math.max(dp[(i-1)%2], dp[(i-2)%2] + A[i]);
        }
        return dp[r%2];
    }

    public static void main(String[] args) {
        HouseRobberII h = new HouseRobberII();
        int[] A = {3,6,4};
        System.out.println(h.houseRobbing(A));
    }
}
