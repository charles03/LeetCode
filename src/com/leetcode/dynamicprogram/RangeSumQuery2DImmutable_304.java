package com.leetcode.dynamicprogram;

import com.algorithm.tree.binaryIndexTree.RangeSumQuery_307;

/**
 * Created by charles on 2/26/17.
 * Given a 2D matrix matrix, find the sum of the elements inside the rectangle defined by its upper left corner (row1, col1) and lower right corner (row2, col2).

 Range Sum Query 2D
 The above rectangle (with the red border) is defined by (row1, col1) = (2, 1) and (row2, col2) = (4, 3), which contains sum = 8.

 Example:
 Given matrix = [
 [3, 0, 1, 4, 2],
 [5, 6, 3, 2, 1],
 [1, 2, 0, 1, 5],
 [4, 1, 0, 1, 7],
 [1, 0, 3, 0, 5]
 ]

 sumRegion(2, 1, 4, 3) -> 8
 sumRegion(1, 1, 2, 2) -> 11
 sumRegion(1, 2, 2, 4) -> 12
 */
public class RangeSumQuery2DImmutable_304 {
    private int[][] preSum;

    public RangeSumQuery2DImmutable_304(int[][] matrix) {
        if (matrix == null || matrix.length < 1 || matrix[0].length < 1) {
            return;
        }
        int row = matrix.length + 1;
        int col = matrix[0].length + 1;
        preSum = new int[row][col];

        for (int i = 1; i < row; i++) {
            for (int j = 1; j < col; j++) {
                preSum[i][j] = preSum[i][j-1] + matrix[i-1][j-1];
            }
        }
        for (int i = 1; i < row; i++) {
            for (int j = 1; j < col; j++) {
                preSum[i][j] += preSum[i-1][j];
            }
        }
    }

    /**
     * Divide Matrix as 4 parts
     *   A __|__ B
     *   C   |   D
     *   in order to get sub sum bottom right
     *   D/C/B/A is sum from bottom right point to same upper left (i,j)
     *   D - C - B + A
     */

    public int sumRegion(int r1, int c1, int r2, int c2) {
        if (preSum.length == 0 || preSum[0].length == 0) {
            return 0;
        }
        if (r1 == 0 && c1 == 0) {
            return preSum[r2+1][c2+1];
        }
        if (r1 == 0) {
            return preSum[r2+1][c2+1] - preSum[r2+1][c1];
        }
        if (c1 == 0) {
            return preSum[r2+1][c2+1] - preSum[r1][c2+1];
        }
        // whole rectangle - (upper) - (left) + (upper left) because upper left be deducted twice
        return preSum[r2+1][c2+1] - preSum[r1][c2 + 1] - preSum[r2 + 1][c1] + preSum[r1][c1];
    }
}
