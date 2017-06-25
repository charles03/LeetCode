package com.leetcode.math;

/**
 * Created by charles on 6/4/17.
 * Given an m * n matrix M initialized with all 0's and several update operations.

 Operations are represented by a 2D array, and each operation is represented by an array with two positive integers a and b, which means M[i][j] should be added by one for all 0 <= i < a and 0 <= j < b.

 You need to count and return the number of maximum integers in the matrix after performing all the operations.

 Example 1:
 Input:
 m = 3, n = 3
 operations = [[2,2],[3,3]]
 Output: 4
 Explanation:
 Initially, M =
 [[0, 0, 0],
 [0, 0, 0],
 [0, 0, 0]]

 After performing [2,2], M =
 [[1, 1, 0],
 [1, 1, 0],
 [0, 0, 0]]

 After performing [3,3], M =
 [[2, 2, 1],
 [2, 2, 1],
 [1, 1, 1]]

 So the maximum integer in M is 2, and there are four of it in M. So return 4.

 */
public class RangeAdditionII_598 {
    /**
     * each operation [i,j] will cover area top-left matrix[0,0] * matrix[i,j]
     * based on above opertion, oberserve maximum elems will be the ones which lie in
     * intersection region of rectangles
     * we don't need to perform operation which update value in matrix one by one,
     * but we need to determin bottom right corner of the intersecting region
     *
     * (row, col) => min(op[0][x], op[1][x]), min(op[0][y], op[1][y])
     */
    public int maxCount(int[][] ops, int m, int n) {
        for (int[] operation : ops) {
            m = Math.min(m, operation[0]);
            n = Math.min(n, operation[1]);
        }
        return m * n;
    }
}

