package com.leetcode.dfs;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by charles on 3/6/17.
 * Given an m x n matrix of non-negative integers representing the height of each unit cell in a continent, the "Pacific ocean" touches the left and top edges of the matrix and the "Atlantic ocean" touches the right and bottom edges.

 Water can only flow in four directions (up, down, left, or right) from a cell to another one with height equal or lower.

 Find the list of grid coordinates where water can flow to both the Pacific and Atlantic ocean.

 Note:
 The order of returned grid coordinates does not matter.
 Both m and n are less than 150.
 Example:

 Given the following 5x5 matrix:

 Pacific ~   ~   ~   ~   ~
 ~  1   2   2   3  (5) *
 ~  3   2   3  (4) (4) *
 ~  2   4  (5)  3   1  *
 ~ (6) (7)  1   4   5  *
 ~ (5)  1   1   2   4  *
 *   *   *   *   * Atlantic

 Return:

 [[0, 4], [1, 3], [1, 4], [2, 2], [3, 0], [3, 1], [4, 0]] (positions with parentheses in
 */
public class PacificAtlanticWaterFlow_417 {
    /**
     * DFS version
     * by using two auxiliary matrix as visited to get bool of water can flow to pacific / atlantic separately
     * Then go through these two matrix to get final result
     */
    private int[] dx = {1, 0, -1, 0};
    private int[] dy = {0, 1, 0, -1};

    public List<int[]> pacificAtlantic(int[][] matrix) {
        List<int[]> res = new LinkedList<>();
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return res;
        }
        int row = matrix.length;
        int col = matrix[0].length;

        boolean[][] pacific = new boolean[row][col];
        boolean[][] atlantic = new boolean[row][col];

        for (int i = 0; i < row; i++) {
            dfs(matrix, pacific, Integer.MIN_VALUE, i, 0, row, col);
            dfs(matrix, atlantic, Integer.MIN_VALUE, i, col-1, row, col);
        }
        for (int i = 0; i < col; i++) {
            dfs(matrix, pacific, Integer.MIN_VALUE, 0, i, row, col);
            dfs(matrix, atlantic, Integer.MIN_VALUE, row-1, i, row, col);
        }
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (pacific[i][j] && atlantic[i][j]) {
                    res.add(new int[]{i, j});
                }
            }
        }
        return res;
    }

    void dfs(int[][] matrix, boolean[][] visited, int height, int x, int y, int row, int col) {
        if (0<=x && x<row && 0<=y && y<col && !visited[x][y] && matrix[x][y] > height) {
            visited[x][y] = true;
            for (int k = 0; k < 4; k++) {
                dfs(matrix, visited, matrix[x][y], x+dx[k], y+dy[k], row, col);
            }
        }
    }

    /**
     * BFS
     */
    public List<int[]> pacificAtlanticII(int[][] matrix) {
        List<int[]> res = new LinkedList<>();
        int row = matrix.length;
        int col = matrix[0].length;

        boolean[][] pacific = new boolean[row][col];
        boolean[][] atlantic = new boolean[row][col];

        Queue<int[]> queue1 = new LinkedList<>();
        Queue<int[]> queue2 = new LinkedList<>();
        // vertical boarder
        for (int i = 0; i < row; i++) {
            queue1.offer(new int[]{i, 0});
            queue2.offer(new int[]{i, col-1});
            pacific[i][0] = true;
            atlantic[i][col-1] = true;
        }
        // horizontal boarder
        for (int i = 0; i < col; i++) {
            queue1.offer(new int[]{0, i});
            queue2.offer(new int[]{row-1, i});
            pacific[0][i] = true;
            atlantic[row-1][i] = true;
        }

        bfs(matrix, queue1, pacific);
        bfs(matrix, queue2, atlantic);

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (pacific[i][j] && atlantic[i][j]) {
                    res.add(new int[]{i, j});
                }
            }
        }
        return res;
    }

    void bfs(int[][] matrix, Queue<int[]> queue, boolean[][] visited) {
        int m = matrix.length;
        int n = matrix[0].length;
        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            for (int k = 0; k < 4; k++) {
                int nx = curr[0] + dx[k];
                int ny = curr[1] + dy[k];
                if (0<=nx && nx<m && 0<=ny && ny<n && !visited[nx][ny] && matrix[nx][ny] > matrix[curr[0]][curr[1]]) {
                    visited[nx][ny] = true;
                    queue.offer(new int[]{nx, ny});
                }
            }
        }
    }
}
