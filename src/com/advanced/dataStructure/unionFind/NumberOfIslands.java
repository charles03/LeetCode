package com.advanced.dataStructure.unionFind;

import java.util.Arrays;

/**
 * Created by charles on 9/22/16.
 *
 * Given a boolean 2D matrix, find the number of islands.
 Notice
 0 is represented as the sea, 1 is represented as the island.
 If two 1 is adjacent, we consider them in the same island. We only consider up/down/left/right adjacent.

 Example
 Given graph:

 [
 [1, 1, 0, 0, 0],
 [0, 1, 0, 0, 1],
 [0, 0, 0, 1, 1],
 [0, 0, 0, 0, 0],
 [0, 0, 0, 0, 1]
 ]
 return 3.
 */
public class NumberOfIslands {
    private int[] father; // 1D array for union find
    private int[] size; // array for weighted quick union find
    private int numberOfIslands;
    private int[] dx = {1, 0, -1, 0};
    private int[] dy = {0, 1, 0, -1};

    public int numOfIslands(char[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return 0;
        }
        // initialization for quick union find
        initWeightedQuickUnionFind(grid);
//        initUnionFind(grid);

        int row = grid.length;
        int col = grid[0].length;

        int id = 0;
        int nx = 0;
        int ny = 0;
        int nid = 0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                // skip when grid(i,j) not one
                if (grid[i][j] != '1') {
                    continue;
                }
                // four directions iteration
                for (int k = 0; k < 4; k++) {
                    nx = i + dx[k];
                    ny = j + dy[k];
                    if (0<=nx && nx<row && 0<=ny && ny<col && grid[nx][ny] == '1') {
                        id = i * col + j;
                        nid = nx * col + ny; // new index
                        System.out.println("id " + id + " " + nid);
                        weightedQuickUnion(id, nid);
//                        union(id, nid);
                    }
                }
            }
        }
        return numberOfIslands;
    }

    private void initWeightedQuickUnionFind(char[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int id = 0;
        numberOfIslands = 0; // reset
        father = new int[m * n + 1];
        size = new int[m * n + 1];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == '1') {
                    id = i * n + j;
                    father[id] = id;
                    // update numberOfIslands
                    numberOfIslands++;
                }
            }
        }
        Arrays.stream(father).forEach(t -> System.out.print(t + ", "));
        System.out.println();
        size[m * n] = m * n;
    }

    public void weightedQuickUnion(int p, int q) {
        int father_p = compressFind(p);
        int father_q = compressFind(q);

        if (father_p == father_q) {
            return;
        }
        if (size[father_p] < size[father_q]) {
            father[father_p] = father_q;
            size[father_q] += size[father_p];
        } else {
            father[father_q] = father_p;
            size[father_p] += size[father_q];
        }
        // after union, update number of islands
        numberOfIslands--;
    }

    public int compressFind(int p) {
        int root = p;
        while (father[root] != root) {
            root = father[root];
        }
        int path = p;
        int tmp = 0;
        while (father[path] != root) {
            tmp = father[path];
            father[path] = root;
            path = tmp;
        }
        return root;
    }

    private void initUnionFind(char[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int id = 0;
        father = new int[m * n];
        numberOfIslands = 0; // reset
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == '1') {
                    id = i * n + j;
                    father[id] = id;
                    numberOfIslands++;
                }
            }
        }
        Arrays.stream(father).forEach(System.out::print);
        System.out.println();
    }

    public void union(int p, int q) {
        int father_p = find(p);
        int father_q = find(q);
        if (father_p != father_q) {
            father[father_p] = father_q;
            numberOfIslands--;
//            System.out.println(father_p + ", " + father_q + " " + numberOfIslands);
        }
    }

    public int find(int p) {
        while (p != father[p]) {
            p = father[p];
        }
        return p;
    }
//    public int find(int p) {
//        if (p == father[p]) {
//            return p;
//        }
//        father[p] = find(father[p]);
//        return father[p];
//    }

    public static void main(String[] args) {
        NumberOfIslands n = new NumberOfIslands();
        char[][] islands = {{'1', '1', '0', '0', '0'}, {'0', '1', '0', '0', '1'}, {'0', '0', '0', '1', '1'}, {'0', '0', '0', '0', '0'}, {'0', '0', '0', '0', '1'}};
        System.out.println(n.numOfIslands(islands));
    }
}
