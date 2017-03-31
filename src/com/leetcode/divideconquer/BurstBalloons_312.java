package com.leetcode.divideconquer;

/**
 * Created by charles on 2/19/17.
 * Given n balloons, indexed from 0 to n-1. Each balloon is painted with a number on it represented by array nums. You are asked to burst all the balloons. If the you burst balloon i you will get nums[left] * nums[i] * nums[right] coins. Here left and right are adjacent indices of i. After the burst, the left and right then becomes adjacent.

 Find the maximum coins you can collect by bursting the balloons wisely.

 Note:
 (1) You may imagine nums[-1] = nums[n] = 1. They are not real therefore you can not burst them.
 (2) 0 ≤ n ≤ 500, 0 ≤ nums[i] ≤ 100
 Example:
 Given [3, 1, 5, 8]

 Return 167

 nums = [3,1,5,8] --> [3,5,8] -->   [3,8]   -->  [8]  --> []
 coins =  3*1*5      +  3*5*8    +  1*3*8      + 1*8*1   = 167

 */
public class BurstBalloons_312 {
    /**
     * Divide and Conquer solution
     * when one ballon left, say it is num[i], at this moment the ballons at left
     * and at right are all bursted,
     * then merge condition is Math.max(left + right + arr[start-1] * arr[i] * arr[end+1], max)
     */
    public int maxCoins(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int n = nums.length;
        int[] arr = new int[n + 2];
        int[][] dp = new int[n + 2][n + 2];
        // add edge case into array to avoid exception
        arr[0] = 1;
        arr[n + 1] = 1;
        for (int i = 0; i < n; i++) {
            arr[i + 1] = nums[i]; // from 1 to n in new array is original array
        }
        return memorySearch(arr, dp, 1, n);
    }
    private int memorySearch(int[] arr, int[][] dp, int start, int end) {
        if (dp[start][end] != 0) { // if without add padding into original array
            // then should check start/end Index out of bounds exception
            return dp[start][end];
        }
        int max = 0;
        int leftMax = 0; // max of coin when poped all left numbers from index i-1 to start
        int rightMax = 0; // max of coins when poped all right numbers from index i+1 to end
        int curr = 0; // result when number at index i is poped out
        for (int i = start; i <= end; i++) {
            leftMax = memorySearch(arr, dp, start, i - 1);
            rightMax = memorySearch(arr, dp, i + 1, end);
            // only nums[i] remained,
            curr = arr[start - 1] * arr[i] * arr[end + 1];
            max = Math.max(leftMax + curr + rightMax, max);
        }
        dp[start][end] = max;
        return max;
    }

    private int getVal(int[] arr, int i) { // deal with nums[-1] and nums[nums.length]
        if (i < 0 || i >= arr.length) {
            return 1;
        }
        return arr[i];
    }

    /**
     * Range DP solution
     * https://discuss.leetcode.com/topic/37646/my-understanding-of-the-n-3-dp-solution-comments-explanation
     *
     * State : dp[i][j] to denote maximum gain from ballon range i tp j
     * we try out each ballon as last burst in this range.
     * Function: for each k in (i to j)
     * dp(i,j) = max[dp(i,j), (A[i-1]*A[k]*A[j+1] + dp(i,k-1) + dp(k+1,j))]
     * init : dp[i][j] = 0
     * Answer : dp[1][n];
     */
    public int maxCoinsDP(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int n = nums.length;
        int[][] dp = new int[n + 2][n + 2];
        int[] arr = new int[n + 2];
        // extend array with head and tail to handle case nums[-1] and nums[n]
        arr[0] = 1;
        arr[n + 1] = 1;
        for (int i = 0; i < n; i++) {
            arr[i+1] = nums[i];
        }
        // Range DP
        int end = 0;
        int curr = 0; // gain of coins when popped out number at index i
        for (int len = 1; len <= n; len++) { // outer range
            for (int start = 1; start <= n-len+1; start++) {
                end = start + len - 1;
                // k as last
                for (int k = start; k <= end; k++) {
                    curr = arr[start-1] * arr[k] * arr[end+1];
                    dp[start][end] = Math.max(dp[start][end], curr + dp[start][k-1] + dp[k+1][end]);
                }
            }
        }
        return dp[1][n];
    }


    public static void main(String[] args) {
        BurstBalloons_312 b = new BurstBalloons_312();
        int[] n1 = {3,1,5,8};
//        System.out.println(b.maxCoins(n1));
        System.out.println(b.maxCoinsDP(n1));
    }
}
