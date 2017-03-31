package com.leetcode.array;

/**
 * Created by charles on 3/25/17.
 * You are given a map in form of a two-dimensional integer grid where 1 represents land and 0 represents water. Grid cells are connected horizontally/vertically (not diagonally). The grid is completely surrounded by water, and there is exactly one island (i.e., one or more connected land cells). The island doesn't have "lakes" (water inside that isn't connected to the water around the island). One cell is a square with side length 1. The grid is rectangular, width and height don't exceed 100. Determine the perimeter of the island.

 Example:

 [[0,1,0,0],
 [1,1,1,0],
 [0,1,0,0],
 [1,1,0,0]]

 Answer: 16
 */
public class IslandPerimeter_463 {
    /**
     * High level thought,
     * loop whole grid, for each cell,
     *      if sigle 1, no neighbor, then perimeter is 4
     * if add one neighbor, then current perimeter will minus one
     *
     * Then use DFS to for loop four directions of each cell
     */
    public int islandPerimeter(int[][] grid) {
        int res = 0;
        int m = grid.length;
        int n = grid[0].length;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    res += 4 - getPreimeterForCurrentCell(grid, i, j, m, n);
                }
            }
        }
        return res;
    }
    private int[] dx = {1,0,-1,0};
    private int[] dy = {0,1,0,-1};

    private int getPreimeterForCurrentCell(int[][] grid, int x, int y, int row, int col) {
        int nx = 0, ny = 0;
        int cnt = 0;
        for (int k = 0; k < 4; k++) {
            nx = x + dx[k];
            ny = y + dy[k];
            if (0<=nx && nx<row && 0<=ny && ny<col && grid[nx][ny] == 1) {
                cnt++;
            }
        }
        return cnt;
    }

    public static void main(String[] args) {
        int[][] g1 = {{0,1,0,0},
                {1,1,1,0},
                {0,1,0,0},
                {1,1,0,0}};
        IslandPerimeter_463 i = new IslandPerimeter_463();
        System.out.println(i.islandPerimeter(g1));
    }

    /**
     * Math trick solution
     * the pattern is islands * 4 - neighbours * 2, since every adjacent islands made two sides disappeared
     * +--+     +--+                   +--+--+
       |  |  +  |  |          ->       |     |
       +--+     +--+                   +--+--+
       will verify pattern
     */
    public int islandPerimeterII(int[][] grid) {
        int islandCnt = 0;
        int neighborCnt = 0;
        int m = grid.length;
        int n = grid[0].length;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    islandCnt++; // to count islands
                    /**
                     * only need to count if has any right or down neighbor
                     */
                    if (i < m-1 && grid[i+1][j] == 1) { // right neighbor
                        neighborCnt++;
                    }
                    if (j < n-1 && grid[i][j+1] == 1) {
                        neighborCnt++;
                    }
                }
            }
        }
        return islandCnt * 4 - neighborCnt * 2;
    }

    public int islandPerimeterIII(int[][] grid) {
        int perimeter = 0;
        for(int i=0; i<grid.length; i++){
            for(int j=0; j<grid[0].length; j++){
                if(grid[i][j]==1){
                    perimeter += 4;
                    if(j-1>=0 && grid[i][j-1]==1)  perimeter -= 2;
                    if(i-1>=0 && grid[i-1][j]==1)  perimeter -= 2;
                }
            }
        }
        return perimeter;
    }
}
