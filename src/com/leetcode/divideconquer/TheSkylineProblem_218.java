package com.leetcode.divideconquer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by charles on 2/15/17.
 * A city's skyline is the outer contour of the silhouette formed by all the buildings in that city when viewed from a distance. Now suppose you are given the locations and height of all the buildings as shown on a cityscape photo (Figure A), write a program to output the skyline formed by these buildings collectively (Figure B).
 * The geometric information of each building is represented by a triplet of integers [Li, Ri, Hi], where Li and Ri are the x coordinates of the left and right edge of the ith building, respectively, and Hi is its height. It is guaranteed that 0 ≤ Li, Ri ≤ INT_MAX, 0 < Hi ≤ INT_MAX, and Ri - Li > 0. You may assume all buildings are perfect rectangles grounded on an absolutely flat surface at height 0.

 For instance, the dimensions of all buildings in Figure A are recorded as: [ [2 9 10], [3 7 15], [5 12 12], [15 20 10], [19 24 8] ] .
 The output is a list of "key points" (red dots in Figure B) in the format of [ [x1,y1], [x2, y2], [x3, y3], ... ] that uniquely defines a skyline. A key point is the left endpoint of a horizontal line segment. Note that the last key point, where the rightmost building ends, is merely used to mark the termination of the skyline, and always has zero height. Also, the ground in between any two adjacent buildings should be considered part of the skyline contour.
 For instance, the skyline in Figure B should be represented as:[ [2 10], [3 15], [7 12], [12 0], [15 10], [20 8], [24, 0] ].

 Notes:
 The number of buildings in any input list is guaranteed to be in the range [0, 10000].
 The input list is already sorted in ascending order by the left x position Li.
 The output list must be sorted by the x position.
 There must be no consecutive horizontal lines of equal height in the output skyline. For instance, [...[2 3], [4 5], [7 5], [11 5], [12 7]...] is not acceptable; the three lines of height 5 should be merged into one in the final output as such: [...[2 3], [4 5], [12 7], ...]
 */
public class TheSkylineProblem_218 {
    /**
     * Divide Conquer solution
     */
    public List<int[]> getSkyline(int[][] buildings) {
        List<int[]> res = new LinkedList<>();
        if (buildings.length == 0) {
            return res;
        }
        List<Skyline> nodes = mergeSort(buildings, 0, buildings.length - 1);

        for (Skyline node : nodes) {
            res.add(new int[]{node.x, node.y});
        }
        return res;
    }

    private LinkedList<Skyline> mergeSort(int[][] buildings, int start, int end) {
        LinkedList<Skyline> res = new LinkedList<>();
        if (start == end) {
            // current skyline, start point with height
            res.add(new Skyline(buildings[start][0], buildings[start][2]));
            // skyline node, end point with zero height
            res.add(new Skyline(buildings[start][1], 0));
            return res;
        }
        // devide
        int mid = start + (end - start) / 2;
        LinkedList<Skyline> left = mergeSort(buildings, start, mid);
        LinkedList<Skyline> right = mergeSort(buildings, mid + 1, end);

        mergeIntoResult(res, left, right);

        return res;
    }

    private void mergeIntoResult(LinkedList<Skyline> res, LinkedList<Skyline> left, LinkedList<Skyline> right) {
        long x1 = 0l, x2 =0l;
        int x = 0, y = 0;
        int leftY = 0, rightY = 0;
        Skyline current = null;
        while (!left.isEmpty() || !right.isEmpty()) {
            x1 = left.isEmpty() ? Long.MAX_VALUE : left.peekFirst().x;
            x2 = right.isEmpty() ? Long.MAX_VALUE : right.peekFirst().x;
            if (x1 < x2) {
                current = left.pollFirst();
                x = current.x;
                leftY = current.y;
            } else if (x1 > x2) {
                current = right.pollFirst();
                x = current.x;
                rightY = current.y;
            } else {
                x = left.peekFirst().x;
                leftY = left.pollFirst().y;
                rightY = right.pollFirst().y;
            }
            y = Math.max(leftY, rightY);
            if (res.isEmpty() || y != res.peekLast().y) {
                // if res has no node, or height of skyline changed
                res.add(new Skyline(x, y));
            }
        }
    }

    private static class Skyline {
        int x, y;
        Skyline(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static void main(String[] args) {
        TheSkylineProblem_218 t = new TheSkylineProblem_218();
        int[][] b1 = {{2, 9, 10}, {3, 7, 15}, {5, 12, 12}, {15, 20, 10}, {19, 24, 8}};
        output(t.getSkyline(b1));
    }

    private static void output(List<int[]> res) {
        for (int[] a : res) {
            System.out.println(a[0] + " " + a[1]);
        }
    }
}
