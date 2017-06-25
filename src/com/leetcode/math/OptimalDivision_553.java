package com.leetcode.math;

import com.princeton.datastructure.Queue;

/**
 * Created by charles on 6/11/17.
 * Given a list of positive integers, the adjacent integers will perform the float division. For example, [2,3,4] -> 2 / 3 / 4.

 However, you can add any number of parenthesis at any position to change the priority of operations. You should find out how to add parenthesis to get the maximum result, and return the corresponding expression in string format. Your expression should NOT contain redundant parenthesis.

 Example:
 Input: [1000,100,10,2]
 Output: "1000/(100/10/2)"
 Explanation:
 1000/(100/10/2) = 1000/((100/10)/2) = 200
 However, the bold parenthesis in "1000/((100/10)/2)" are redundant,
 since they don't influence the operation priority. So you should return "1000/(100/10/2)".

 Other cases:
 1000/(100/10)/2 = 50
 1000/(100/(10/2)) = 50
 1000/100/10/2 = 0.5
 1000/100/(10/2) = 2
 Note:

 The length of the input array is [1, 10].
 Elements in the given array will be in range [2, 1000].
 There is only one optimal division for each test case.
 */
public class OptimalDivision_553 {
    /**
     * use Math
     * because all digits are positive integer.
     * in order to maximize fraction p/q, q as denominator should be minimized
     *
     * example: a/b/c/d, to minimize b/c/d
     * two possible combination b/(c/d) and (b/c)/d
     * b/(c/d) = (b*d)/c
     * (b/c)/d = b/(d*c)
     * when d > 1, always d/c > 1/(d*c);
     */
    public String optimalDivision(int[] nums) {
        int len = nums.length;
        if (len == 1) {
            return nums[0] + "";
        }
        if (len == 2) {
            return nums[0] + "/" + nums[1];
        }
        StringBuilder sb = new StringBuilder();
        sb.append(nums[0] + "/(" + nums[1]);

        for (int i = 2; i < len; i++) {
            sb.append("/").append(nums[i]);
        }
        sb.append(")");
        return sb.toString();
    }

    /**
     * the interesting part is use divide and conquer to brute force solve this problem
     * as well as apply memorization for optimization.
     *
     * Solution:
     * divide list into two parts left and right
     * iterate i from start to end so
     * left = (start, i), end = (i+1, end)
     * left and right parts return their max and min value and corresponding string
     *
     * minVal can be found by left.min/right.max
     * maxVal can be found by left.max/right.min
     * by default left most divide should be done first, we need not have to add paranthesis to the left part, but we must add parenthesis to the right part.
     * One more point, we also don't require parenthesis to right part when it contains single digit.
     *
     * Time complexity: O(N!) number of permutation of expression after applying brackets will be in O(N!)
     * where n is number of items in list
     * space complexity : O(n^2) depth of recursion tree will O(n) and reach node contains string of max length O(n);
     */
    public String optimalDivisionII(int[] nums) {
        T t = optimal(nums, 0, nums.length - 1, "");
        return t.maxStr;
    }
    private T optimal(int[] nums, int start, int end, String res) {
        T t = new T();
        if (start == end) {
            t.maxVal = nums[start];
            t.minVal = nums[start];
            t.minStr = "" + nums[start];
            t.maxStr = "" + nums[start];
            return t;
        }
        t.minVal = Float.MAX_VALUE;
        t.maxVal = Float.MIN_VALUE;
        t.minStr = "";
        t.maxStr = "";
        for (int i = start; i < end; i++) {
            T left = optimal(nums, start, i, "");
            T right = optimal(nums, i+1, end, "");
            if (t.minVal > left.minVal / right.maxVal) {
                t.minVal = left.minVal / right.maxVal;
                if (i+1 != end) { // not single digit
                    t.minStr = left.minStr + "/(" + right.maxStr + ")";
                } else {
                    t.minStr = left.minStr + "/" + right.maxStr;
                }
            }
            if (t.maxVal < left.maxVal / right.minVal) {
                t.maxVal = left.maxVal / right.minVal;
                if (i+1 != end) {
                    t.maxStr = left.maxStr + "/(" + right.minStr + ")";
                } else {
                    t.maxStr = left.maxStr + "/" + right.minStr;
                }
            }
        }
        return t;
    }

    /**
     * below is apply memorization for optimization
     * Time Complexity : O(n^3) memo array size n^2 is filled and filling of each cell of memo array take O(n) time
     * Space Complexity O(n^3) memo array size n^2 where each cell of array contains string length o(n)
     */
    public T optimal(int[] nums, int start, int end, String res, T[][] memo) {
        if (memo[start][end] != null) {
            return memo[start][end];
        }
        T t = new T();
        if (start == end) {
            t.maxVal = nums[start];
            t.minVal = nums[start];
            t.maxStr = "" + nums[start];
            t.minStr = "" + nums[start];
            memo[start][end] = t;
            return t;
        }
        t.minVal = Float.MAX_VALUE;
        t.maxVal = Float.MIN_VALUE;
        t.minStr = "";
        t.maxStr = "";
        for (int i = start; i < end; i++) {
            T left = optimal(nums, start, i, "", memo);
            T right = optimal(nums, i+1, end, "", memo);
            if (t.minVal > left.minVal / right.maxVal) {
                t.minVal = left.minVal / right.maxVal;
                if (i+1 != end) {
                    t.minStr = left.minStr + "/(" + right.minStr + ")";
                } else {
                    t.minStr = left.minStr + "/" + right.maxStr;
                }
            }
            if (t.maxVal < left.maxVal / right.minVal) {
                t.maxVal = left.maxVal / right.minVal;
                if (i+1 != end) {
                    t.maxStr = left.maxStr + "/(" + right.minStr + ")";
                } else {
                    t.maxStr = left.maxStr + "/" + right.minStr;
                }
            }
        }
        memo[start][end] = t;
        return t;
    }

    class T {
        float maxVal, minVal;
        String maxStr, minStr;
    }
}

