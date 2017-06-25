package com.leetcode.bfs;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by charles on 6/25/17.
 * {@link com.leetcode.dfs.Maze_490}
 */
public class Maze_490 {
    private int[] dx = {1,0,-1,0};
    private int[] dy = {0,-1,0,1};

    public boolean hasPath(int[][] maze, int[] start, int[] dest) {
        int m = maze.length;
        int n = maze[0].length;
        boolean[][] visited = new boolean[m][n];

        Queue<int[]> queue = new LinkedList<>();
        queue.offer(start);
        visited[start[0]][start[1]] = true;

        int[] curr = null;
        int nx, ny;
        while (!queue.isEmpty()) {
            curr = queue.poll();
            if (curr[0] == dest[0] && curr[1] == dest[1]) {
                return true;
            }
            for (int i = 0; i < 4; i++) {
                nx = curr[0];
                ny = curr[1];
                while (0<=nx && nx<m && 0<=ny && ny<n && maze[nx][ny] == 0) {
                    nx += dx[i];
                    ny += dy[i];
                }
                // back one step;
                nx -= dx[i];
                ny -= dy[i];
                if (!visited[nx][ny]) {
                    visited[nx][ny] = true;
                    queue.offer(new int[]{nx, ny});
                }
            }
        }
        return false;
    }
}
