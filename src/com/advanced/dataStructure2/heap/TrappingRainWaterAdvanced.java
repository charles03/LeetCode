package com.advanced.dataStructure2.heap;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Created by charles on 10/29/16.
 *
 * Given n x m non-negative integers representing an elevation map 2d where the area of each cell is 1 x 1,
 * compute how much water it is able to trap after raining.
 *
 * Example
 Given 5*4 matrix

 [12,13,0,12]
 [13,4,13,12]
 [13,8,10,12]
 [12,13,12,12]
 [13,13,13,13]
 return 14.

 Basic question {@link com.advanced.dataStructure2.twoPointers.TrappingRainWater} by two pointer
 Tag: Heap

 Advantage of using heap is to maintain the Max or Min value among set of data
 Thought: from outer bound to inner.
    offer all data in four edges into Min Heap.
    pop min value from current heap, search into four directions (when matching edges condition)
    maintain 2D array to record indexes have been iterated
    1. looking for outer bound, from outer to inner to trap water
    2. looking for min value of pillar in current Heap -> use Heap to maintain min Heap
    3. tag visited index and use four directions to search unvisited cell
    4. update current pillar if it's original val smaller than the value popped from min heap

 */
public class TrappingRainWaterAdvanced {
    // define assistant direction array {right, down, left, up}
    private int[] dx = {1, 0, -1, 0};
    private int[] dy = {0, 1, 0, -1};

    public int trapRainWater(int[][] heights) {
        int res = 0;
        if (heights == null || heights.length == 0) {
            return res;
        }
//        PriorityQueue<Pillar> heap= new PriorityQueue<>(1, new PillarComparator());
        PriorityQueue<Pillar> heap = new PriorityQueue<>(1, (o1, o2) -> o1.z - o2.z);
        // add four edges into heap
        int n = heights.length;
        int m = heights[0].length;
        int[][] visited = new int[n][m];

        for (int i = 0; i < n; i++) {
            heap.offer(new Pillar(i, 0, heights[i][0]));
            heap.offer(new Pillar(i, m - 1, heights[i][m - 1]));
            // update visited array
            visited[i][0] = visited[i][m - 1] = 1;
        }
        for (int j = 0; j < m; j++) {
            heap.offer(new Pillar(0, j, heights[0][j]));
            heap.offer(new Pillar(n - 1, j, heights[n - 1][j]));
            visited[0][j] = visited[n - 1][j] = 1;
        }

        // start to trap water from outer to inner
        int nx = 0, ny = 0;
        while (!heap.isEmpty()) {
            // get current min from heap
            Pillar now = heap.poll();
            for (int i = 0; i < 4; i++) {
                nx = now.x + dx[i];
                ny = now.y + dy[i];
                // check edge conditions and look for unvisited cell
                if (0<=nx && nx <n && 0<=ny && ny<m && visited[nx][ny] == 0) {
                    visited[nx][ny] = 1;
                    // insert into heap for new cell
                    heap.offer(new Pillar(nx, ny, Math.max(now.z, heights[nx][ny])));
                    // update result
                    res += Math.max(0, now.z - heights[nx][ny]);
                }
            }
        }

        return res;
    }

    public static void main(String[] args) {
        TrappingRainWaterAdvanced water = new TrappingRainWaterAdvanced();
        int[][] heights = {{12,13,0,12}, {13,4,13,12}, {13,8,10,12}, {12,13,12,12}, {13,13,13,13}};
        System.out.println(water.trapRainWater(heights));
    }
}
// need object to store location index (x, y) and height z
class Pillar {
    public int x, y, z;
    public Pillar(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
// need to have comparator for priority Queue to create Min Heap
// also can write comparator in lamda expression
class PillarComparator implements Comparator<Pillar> {
    @Override
    public int compare(Pillar a, Pillar b) {
        if (a.z < b.z) {
            return  -1;
        } else if (a.z == b.z) {
            return 0;
        } else {
            return -1;
        }
    }
}
