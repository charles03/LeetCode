package com.leetcode.array;

/**
 * Created by charles on 6/25/17.
 * There's a tree, a squirrel, and several nuts. Positions are represented by the cells in a 2D grid. Your goal is to find the minimal distance for the squirrel to collect all the nuts and put them under the tree one by one. The squirrel can only take at most one nut at one time and can move in four directions - up, down, left and right, to the adjacent cell. The distance is represented by the number of moves.
 Example 1:
 Input:
 Height : 5
 Width : 7
 Tree position : [2,2]
 Squirrel : [4,4]
 Nuts : [[3,0], [2,5]]
 Output: 12
 Explanation:

 Note:
 All given positions won't overlap.
 The squirrel can take at most one nut at one time.
 The given positions of nuts have no order.
 Height and width are positive integers. 3 <= height * width <= 10,000.
 The given positions contain at least one nut, only one tree and one squirrel.
 */
public class SquirrelSimulation_573 {
    /**
     * thought:
     * nut move to tree and squirrel go from tree to nut
     * total sum steps for all nuts are dist(nut, tree) * 2
     * one special case is first nut.
     * in order to get min total steps, we need to select first nut
     * having dist(nut, tree) - dist(nut, squirrel) is max
     * equal
     * diff (2 * dist(nut, tree) with (dist(nut, tree) + dist(nut, squirrel))
     */
    public int minDistance(int height, int widthkk, int[] tree, int[] squirrel, int[][] nuts) {
        int totalSteps = 0;
        int diff = Integer.MIN_VALUE;
        for (int[] nut : nuts) {
            totalSteps += (distance(nut, tree) * 2);
            diff = Math.max(diff, distance(nut, tree) - distance(nut, squirrel));
        }
        return totalSteps - diff;
    }
    private int distance(int[] a, int[] b) {
        return Math.abs(a[0]-b[0]) + Math.abs(a[1] - b[1]);
    }
}
