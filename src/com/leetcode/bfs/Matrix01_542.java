package com.leetcode.bfs;


import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by charles on 5/24/17.
 * {@link com.leetcode.dynamicprogram.Matrix01_542}
 */
public class Matrix01_542 {
    /**
     * use Queue<int[]> as auxiliary memory for bfs
     */
    public int[][] updateMatrix(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;

        Queue<int[]> queue = new LinkedList<>();
        // find and put into Queue, all zeros in original matrix
        // in order for BFS search from each cell when value is 0
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 0) {
                    queue.offer(new int[]{i,j});
                } else {
                    matrix[i][j] = Integer.MAX_VALUE;
                }
            }
        }
        int[] dx = {1,0,-1,0};
        int[] dy = {0,-1,0,1};

        int x = 0, y = 0;
        int nx = 0, ny = 0;
        while (!queue.isEmpty()) {
            int[] cell = queue.poll();
            x = cell[0];
            y = cell[1];
            for (int k = 0; k < 4; k++) {
                nx = x + dx[k];
                ny = y + dy[k];
                if (nx<0 || nx>=m || ny<0 || ny>=n
                        || matrix[nx][ny] <= matrix[x][y] + 1) {
                    continue;
                }
                queue.add(new int[]{nx, ny});
                matrix[nx][ny] = matrix[x][y] + 1;
            }
        }
        return matrix;
    }
}
