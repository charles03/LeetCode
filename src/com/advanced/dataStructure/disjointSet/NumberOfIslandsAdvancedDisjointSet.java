package com.advanced.dataStructure.disjointSet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by charles on 10/23/16.
 * Given a n,m which means the row and column of the 2D matrix and an array of pair A( size k).
 * Originally, the 2D matrix is all 0 which means there is only sea in the matrix.
 * The list pair has k operator and
 * each operator has two integer A[i].x, A[i].y means that you can change the grid matrix[A[i].x][A[i].y] from sea to island.
 * Return how many island are there in the matrix after each operator.
 *
 *  Notice
 0 is represented as the sea, 1 is represented as the island.
 If two 1 is adjacent, we consider them in the same island. We only consider up/down/left/right adjacent.

 Example
 Given n = 3, m = 3, array of pair A = [(0,0),(0,1),(2,2),(2,1)].

 return [1,1,2,2].
 */
public class NumberOfIslandsAdvancedDisjointSet {
    /**
     use disjoint set to union https://en.wikipedia.org/wiki/Disjoint-set_data_structure
     * @param positions // not list of points, p[0] is x, p[1] = y
     * @return
     */
    public List<Integer> numberIslandsDisjointSet(int n, int m, int[][] positions) {
        List<Integer> res = new ArrayList<>();
        boolean[][] visited = new boolean[n][m];
        int[] fathers = new int[n * m]; // compression find
        int[][] dir = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};// direction for left, down, right, up
        int island = 0;
        // initialization
        for (int i = 0; i < fathers.length; i++) {
            fathers[i] = i;
        }

        int x = 0, y = 0;
        int self = 0; // each iteration, self father value without join
        int father = 0; // final father value for each point after through disjoint set
        for (int i = 0; i < positions.length; i++) {
            island++; // assume point is disjointed
            visited[positions[i][0]][positions[i][1]] = true;
            self = positions[i][0] * m + positions[i][1];
            for (int k = 0; k < dir.length; k++) {
                x = positions[i][0] + dir[k][0];
                y = positions[i][1] + dir[k][1];
                // edge cases
                if (0<=x && x<n && 0<=y && y<m && visited[x][y]) {
                    father = getFather(fathers, x * m + y);
                    if (father != self) {
                        fathers[father] = self;
                        island--;
                    }
                }
            }
            res.add(island);
        }
        return res;
    }
    // disjoint set and path compression
    public int getFather(int[] fa, int i) {
        if (fa[i] == i) {
            return i;
        }
        fa[i] = getFather(fa, fa[i]); // path compression here
        return fa[i];
    }

    public static void main(String[] args) {
        NumberOfIslandsAdvancedDisjointSet disjointSet = new NumberOfIslandsAdvancedDisjointSet();
        int[][] positions = {{0,0}, {0,1}, {2,2}, {2,1}};
        List<Integer> res = disjointSet.numberIslandsDisjointSet(3, 3, positions);
        System.out.println(res);
    }
}
