package com.leetcode.dynamicprogram;

import java.util.Arrays;

/**
 * Created by charles on 5/20/17.
 * Given several boxes with different colors represented by different positive numbers.
 You may experience several rounds to remove boxes until there is no box left. Each time you can choose some continuous boxes with the same color (composed of k boxes, k >= 1), remove them and get k*k points.
 Find the maximum points you can get.

 Example 1:
 Input:

 [1, 3, 2, 2, 2, 3, 4, 3, 1]
 Output:
 23
 Explanation:
 [1, 3, 2, 2, 2, 3, 4, 3, 1]
 ----> [1, 3, 3, 4, 3, 1] (3*3=9 points)
 ----> [1, 3, 3, 3, 1] (1*1=1 points)
 ----> [1, 1] (3*3=9 points)
 ----> [] (2*2=4 points)
 */
public class RemoveBoxes_546 {
    /**
     * Range DP
     * Each range as segment,
     * premise: single segment (if having same color and remove all together)
     * will having higher score then remove elem in segment part by part
     *
     * let i is index of segment in this whole process.
     * let color[i] is color of i-th segment
     * let len[i] is length of i-th segment
     *
     * above vars (color and len array need preprocess)
     *
     * state: dp[i,j,k] is max score
     *    1. when process from i-th segment to j-th segment
     *  & 2. having k numbers of boxes which is same color as box in j-th segment.
     *
     * function :
     *  1. when j-th segment merge together with afterward k boxes with same color
     *      dp[i,j,k] = dp[i,j-1,0] + (len[j] + k) ^ 2;
     *  2. when j-th segment merge one segment at index 't' in (i,j) other than k boxes
     *      dp[i,j,k] = max( dp[i,j,k], (dp[i,t,k+len[j]] + dp[t+1, j-1,0]));
     *     equivalent: (remove [t+1, j-1] segments first)
     *
     *  Then use memory-dp recursion to implement
     */
    private int[] color;
    private int[] len;

    public int removeBoxes(int[] boxes) {
        int cnt = preprocess(boxes); // quantity of segment
//        output(color);
//        output(len);
        int n = boxes.length;
        int[][][] dp = new int[cnt+1][cnt+1][n+1];

        return memorizedDp(0, cnt, 0, dp);
    }
    private int memorizedDp(int i, int j, int k, int[][][] dp) {
        if (i > j) {
            return 0;
        }
        if (dp[i][j][k] > 0) {
            return dp[i][j][k];
        }
        if (i == j) {
            dp[i][j][k] = (k + len[j]) * (k + len[j]);
            return dp[i][j][k];
        }
        // function 1. remove with k boxes after j-th segment
        dp[i][j][k] = memorizedDp(i, j-1, 0, dp) + (k+len[j]) * (k+len[j]);
        // function 2. first remove certain segments at index range [t+1, j-1];
        // then merge jth segment with segment at index t to remove boxes same as jth color
        for (int t = i; t < j; t++) {
            if (color[t] == color[j]) {
                dp[i][j][k] = Math.max(dp[i][j][k],
                        memorizedDp(i, t, k + len[j], dp) +
                        memorizedDp(t+1, j-1, 0, dp));
            }
        }
        return dp[i][j][k];
    }
    // pre-process to get init details of segments before removing
    /**
     * example: [1, 3, 2, 2, 2, 3, 4, 3, 1]
     * will having total 7 segments (2..2) is one,
     * color[] -> [1,3,2,3,4,3,1]
     * len[] -> [1,1,3,1,1,1,1]
     *
     * return final number of segments in given box array
     */
    private int preprocess(int[] boxes) {
        int length = boxes.length;
        color = new int[length];
        len = new int[length];
        color[0] = boxes[0];
        len[0] = 1;
        int idx = 0; // index of segment
        for (int i = 1; i < length; i++) {
            if (boxes[i] == boxes[i-1]) {
                len[idx]++;
            } else {
                idx++;
                color[idx] = boxes[i];
                len[idx] = 1;
            }
        }
        return idx;
    }

    public static void main(String[] args) {
        RemoveBoxes_546 r = new RemoveBoxes_546();
        int[] b1 = {1,3,2,2,2,3,4,3,1};
        r.removeBoxes(b1);
        int[] b2 = {1,1,1};
        System.out.println(r.removeBoxes(b2));

        int[] b3 = {1,3,2,2,2,3,4,3,1};
        System.out.println(r.removeBoxes(b3));
    }

    private static void output(int[] arr) {
        Arrays.stream(arr).forEach(t-> System.out.print(t+","));
        System.out.println();
    }
}
