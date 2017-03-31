package com.leetcode.divideconquer;

/**
 * Created by charles on 2/12/17.
 * Find the contiguous subarray within an array (containing at least one number) which has the largest sum.

 For example, given the array [-2,1,-3,4,-1,2,1,-5,4],
 the contiguous subarray [4,-1,2,1] has the largest sum = 6
 More practice:
 If you have figured out the O(n) solution, try coding another solution using the divide and conquer approach, which is more subtle.
 */
public class MaximumSubarray_53 {
    /**
     * DP version
     * state, ending at i, dp[i] is current max sub sum
     * function : dp[i] = Math.max(dp[i-1], dp[i-1] + A[i])
     */
    public int maxSubArray(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
        dp[0] = nums[0];
        int max = nums[0]; // at least include on element

        for (int i = 1; i < n; i++) {
            if (dp[i-1] > 0) {
                dp[i] = nums[i] + dp[i-1];
            } else {
                dp[i] = nums[i];
            }
            max = Math.max(max, dp[i]);
        }
        return max;
    }

    /**
     * DP space optimization Greedy
     */
    public int maxSubArrayOptimize(int[] nums) {
        int res = Integer.MIN_VALUE;
        int subSum = 0;

        for (int i = 0; i < nums.length; i++) {
            subSum = Math.max(subSum, 0) + nums[i];
            res = Math.max(res, subSum);
        }
        return res;
    }

    /**
     * Divide Conquer
     */
    public int maxSubArraySumDivideConquer(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        return maxSubArraySumDivideConquer(nums, 0, nums.length - 1);
    }
    public int maxSubArraySumDivideConquer(int[] nums, int left, int right) {
        // Base case : only one element
        if (left == right) {
            return nums[left];
        }
        int mid = left + (right - left) / 2;
        /**
         * return maximum of following three possible cases
         * 1) max subarray sum in left half
         * 2) max subarray sum in right half
         * 3) max subarray sum that subarray cross midpoint
         */
        int leftMax = maxSubArraySumDivideConquer(nums, left, mid);
        int rightMax = maxSubArraySumDivideConquer(nums, mid + 1, right);
        int crossMax = maxCrossingSum(nums, left, right, mid);

        return Math.max(crossMax, Math.max(leftMax, rightMax));
    }

    public int maxCrossingSum(int[] nums, int l, int r, int m) {
        int sum = 0;
        int leftSum = Integer.MIN_VALUE;
        // include elements on left of mid
        for (int i = m; i >= l; i--) {
            sum += nums[i];
            if (sum > leftSum) {
                leftSum = sum;
            }
        }
        // include element on right of mid
        sum = 0; // reset sum before go to right side
        int rightSum = Integer.MIN_VALUE;
        for (int i = m+1; i <= r; i++) {
            sum += nums[i];
            if (sum > rightSum) {
                rightSum = sum;
            }
        }
        // return sum of elem of left and right of mid
        return leftSum + rightSum;
    }

    public static void main(String[] args) {
        MaximumSubarray_53 m = new MaximumSubarray_53();
        int[] arr = {-2,1,-3,4,-1,2,1,-5,4};

        System.out.println(m.maxSubArrayOptimize(arr) == m.maxSubArraySumDivideConquer(arr));
    }

}
