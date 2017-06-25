package com.leetcode.math;

import java.util.Arrays;

/**
 * Created by charles on 6/5/17.
 * Given the coordinates of four points in 2D space, return whether the four points could construct a square.

 The coordinate (x,y) of a point is represented by an integer array with two integers.

 Example:
 Input: p1 = [0,0], p2 = [1,1], p3 = [1,0], p4 = [0,1]
 Output: True
 */
public class ValidSquare_593 {
    /**
     * check four sides are equal and diagonals formed between corners of quadrilateral are equal.
     * for given 4 points [p0, p1, p2, p3] there are total of 4! ways in which these points
     * can be arranged to be considered as square's boundaries.
     *
     * Naive solution is to generate every possible permutation and check if any leads to valid square
     *
     * better: to sort given set of points based on their x-coordinate values. in the case of tie, based on their y-coordinate value
     * {#img valid-sqare-593} illustrate 3 cases total for generating square
     * conclude summary: p0p1, p0p2, p1p3, p3p2 form four sides of any valid square
     * p0p3 and p1p2 form diagonals of the square
     *
     * best : list all 24 arrangements after permutation, showed as figure
     * {#img valid-square-593-2}
     */
    public boolean validSquare(int[] p1, int[] p2, int[] p3, int[] p4) {
        int[][] p = {p1, p2, p3, p4};
        Arrays.sort(p, (a,b) -> a[0] == b[0] ? a[1] - b[1] : a[0] - b[0]);
        return dist(p[0], p[1]) != 0
                && dist(p[0],p[1]) == dist(p[1],p[3])
                && dist(p[1],p[3]) == dist(p[3],p[2])
                && dist(p[3],p[2]) == dist(p[2],p[0])
                && dist(p[0],p[3]) == dist(p[1],p[2]); // diagonal
    }
    public double dist(int[] p1, int[] p2) {
        return (p2[1] - p1[1]) * (p2[1] - p1[1]) + (p2[0] - p1[0]) * (p2[0] - p1[0]);
    }

    public boolean validSquareII(int[] p1, int[]p2, int[] p3, int[] p4) {
        return check(p1,p2,p3,p4)
                || check(p1,p3,p2,p4)
                || check(p1,p2,p4,p3);
    }
    public boolean check(int[] p1, int[] p2, int[] p3, int[] p4) {
        return dist(p1,p2) > 0
                && dist(p1,p2) == dist(p2,p3)
                && dist(p2,p3) == dist(p3,p4)
                && dist(p3,p4) == dist(p4,p1)
                && dist(p1,p3) == dist(p2,p4);

    }
}
