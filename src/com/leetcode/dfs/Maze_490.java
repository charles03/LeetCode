package com.leetcode.dfs;

/**
 * Created by charles on 6/25/17.
 *
 */
public class Maze_490 {
    /** thought
     * because ball can roll to four directions from current cell
     * then use dfs with while loop to search in single one direction
     * if block, then backward one step to try other directions until get destination
     */
    private int[] dx = {1,0,-1,0};
    private int[] dy = {0,-1,0,1};

    public boolean hasPath(int[][] maze, int[] start, int[] destination) {
        if (maze.length == 0 || maze[0].length == 0) {
            return false;
        }
        int m = maze.length;
        int n = maze[0].length;
        return dfs(maze, start, destination, new boolean[m][n], m, n);
    }
    private boolean dfs(int[][] maze, int[] curr, int[] dest, boolean[][] visited, int m, int n) {
        if (curr[0] == dest[0] && curr[1] == dest[1]) {
            return true;
        }
        int x = curr[0], y = curr[1];
        if (x < 0 || x > m || y < 0 || y > n || visited[x][y]) {
            return false;
        }
        visited[x][y] = true;
        int nx = 0, ny = 0;
        for (int i = 0; i < 4; i++) {
            nx = x;
            ny = y;
            while (0<=nx && nx<m && 0<=ny && ny<n && maze[nx][ny] == 0) {
                nx += dx[i];
                ny += dy[i];
            }
            // back one step
            nx -= dx[i];
            ny -= dy[i];
            if (dfs(maze, new int[]{nx,ny}, dest, visited, m, n)) {
                return true;
            }
        }
        return false;
    }
}
