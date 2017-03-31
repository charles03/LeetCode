package com.leetcode.heap;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Created by charles on 1/12/17.
 * Given an m x n matrix of positive integers representing the height of each unit cell in a 2D elevation map, compute the volume of water it is able to trap after raining.
 Note:
 Both m and n are less than 110. The height of each unit cell is greater than 0 and is less than 20,000.
 Example:
 Given the following 3x6 height map:

 [1,4,3,1,3,2],
 [3,2,1,3,2,4],
 [2,3,3,2,3,1]
 Return 4.
 */
public class TrappingRainWaterII_407 {
    private int[] dx = {1,0,-1,0};
    private int[] dy = {0,1,0,-1};

    /**
     * Maintain a heap which contains the current walls, the boundary of the water pool.
     Every time we pick the lowest wall as a bar, then recursively travel from this wall to its neighbors to find if they can trap the water.
     If we meet a position which is lower than the bar, we trap some water, else we meet a new wall and put it into the heap.
     We can use a 2-D array to memorize the visited position and a member variable to records the water we have trapped.
     */
    public int trapRainWater(int[][] heights) {
        int res = 0;
        if (heights == null || heights.length == 0 || heights[0].length == 0) {
            return res;
        }
        int row = heights.length;
        int col = heights[0].length;
        boolean[][] vistied = new boolean[row][col];
        PriorityQueue<Pillar> heap = new PriorityQueue<>(new PillarComparator());
        // add four edge into heap
        for (int i = 0; i < row; i++) {
            heap.offer(new Pillar(i, 0, heights[i][0]));
            heap.offer(new Pillar(i, col-1, heights[i][col-1]));
        }
        for (int i = 0; i < col; i++) {
            heap.offer(new Pillar(0, i, heights[0][i]));
            heap.offer(new Pillar(row-1, i, heights[row-1][i]));
        }
        int nx = 0, ny = 0;

        while (!heap.isEmpty()) {
            Pillar now = heap.poll();
            for (int k = 0; k < 4; k++) {
                nx = now.x + dx[k];
                ny = now.y + dy[k];
                if (0<=nx && nx<row && 0<=ny && ny<col && !vistied[nx][ny]) {
                    vistied[nx][ny] = true;
                    // add new wall into heap
                    heap.offer(new Pillar(nx, ny, Math.max(now.z, heights[nx][ny])));
                    // find trapping water
                    res += Math.max(0, now.z - heights[nx][ny]);
                }
            }
        }
        return res;
    }
}

class Pillar {
    int x, y, z;
    Pillar(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}

class PillarComparator implements Comparator<Pillar> {

    @Override
    public int compare(Pillar o1, Pillar o2) {
        if (o1.z < o2.z) {
            return -1;
        } else if (o1.z == o2.z) {
            return 0;
        } else {
            return 1;
        }
    }
}
