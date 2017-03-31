package com.leetcode.heap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;

/**
 * {@link com.leetcode.divideconquer.TheSkylineProblem_218}
 * Created by charles on 2/15/17.
 */
public class TheSkylineProblem_218 {
    /**
     * Priority Queue Solution
     * Use TreeMap as (PriorityQueue) because it has better O(log(n)) deletion time complexity
     */
    public List<int[]> getSkyline(int[][] buildings) {
        List<int[]> heights = new ArrayList<>();
        for (int[] b : buildings) {
            // left edge use negative height
            heights.add(new int[]{b[0], -b[2]});
            heights.add(new int[]{b[1], b[2]});
        }
        // sort by left edge and then right edge
        Collections.sort(heights, (a,b) -> (a[0] == b[0] ? a[1]-b[1] : a[0]-b[0]));
        // use treeMap to store all heights, from large to small
        List<int[]> skyline = new ArrayList<>();
        // key is height of current point, value is occurrence of current height
        TreeMap<Integer, Integer> priorityQueue = new TreeMap<>(Collections.reverseOrder());
        priorityQueue.put(0,0);
        // don't forget previous height
        int prevHeight = 0, startHeight = 0, currHeight = 0;
        int occurrence = 0;
        for (int[] point : heights) {
            // left edge, add height to map
            if (point[1] < 0) {
                startHeight = 0 - point[1];
                occurrence = priorityQueue.getOrDefault(startHeight, 0) + 1;
                priorityQueue.put(startHeight, occurrence);
            } else {
                // if occurrence of current height of point is once
                // then remove this key, else (occurrence decrease
                priorityQueue.computeIfPresent(point[1], (key, cnt) -> cnt == 1 ? null : cnt - 1);
            }
            currHeight = priorityQueue.firstKey();
            if (prevHeight != currHeight) {
                // add new skyline point into res
                skyline.add(new int[]{point[0], currHeight});
                prevHeight = currHeight;
            }
        }
        return skyline;
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
