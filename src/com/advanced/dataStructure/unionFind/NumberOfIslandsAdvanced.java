package com.advanced.dataStructure.unionFind;

import com.advanced.dataStructure.disjointSet.NumberOfIslandsAdvancedDisjointSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by charles on 9/22/16.
 *
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
 This question also can be solved by DisjointSet data structure
 * {@link NumberOfIslandsAdvancedDisjointSet}
 */
public class NumberOfIslandsAdvanced {

    public List<Integer> numberOfIslandsAdvanced(int n, int m, Point[] operators) {
        List<Integer> res = new ArrayList<>();
        if (operators == null || operators.length == 0) {
            return res;
        }
        //down, left, up, right
        int[] dx = {0, -1, 0, 1 };
        int[] dy = {1, 0, -1, 0};
        int[][] islands = new int[n][m];

        UnionFindPoint uf = new UnionFindPoint(n, m);
        // need counter, current point & id, next point & id,
        int count = 0;
        int x = 0, y = 0;
        int nx = 0, ny = 0;
        int id = 0, nid = 0;
        int flag = 0; // to mark new record union into map

        for (int i = 0; i < operators.length; i++) {
            x = operators[i].x;
            y = operators[i].y;
            if (islands[x][y] == 1) {
                continue;
            }
            count++;
            islands[x][y] = 1;
            System.out.println("island " + x + " " + y + " " + islands[x][y]);
            id = UnionFindPoint.convertToId(x, y, m);
            // search in all directions
            for (int j = 0; j < 4; j++) {
                nx = x + dx[j];
                ny = y + dy[j];
                nid = UnionFindPoint.convertToId(nx, ny, m);

                if (0<=nx && nx<n && 0<=ny && ny<m && islands[nx][ny] == 1) {
                    flag = uf.unionWithReturn(id, nid);
                    System.out.println("cell " + i + " " + j + " " + flag);
                    // if union next into existing map, then count--
                    if (flag == 1) {
                        count--;
                    }
                }
            }
            res.add(count);
        }
        return res;
    }

    public static void main(String[] args) {
        NumberOfIslandsAdvanced numIsland = new NumberOfIslandsAdvanced();
        Point[] pair = new Point[4];
        pair[0] = new Point(0,0);
        pair[1] = new Point(0,1);
        pair[2] = new Point(2,2);
        pair[3] = new Point(2,1);

        List<Integer> res = numIsland.numberOfIslandsAdvanced(3, 3, pair);
        System.out.println(res);
    }

}
class UnionFindPoint {

    private Map<Integer, Integer> father = new HashMap<>();

    /**
     * this method should be careful, try to make id unique, consider i = 0
     */
    public static int convertToId(int i, int j, int k) {
        return i * k + j;
    }
    /**
     * constructor can initialize with two or multiple arguments
     * @param n
     */
    public UnionFindPoint(int n) {
        for (int i = 0; i < n; i++) {
            father.put(i, i);
        }
    }
    public UnionFindPoint(int n, int m) {
        int id = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                // need to convert to unique id, then add into father map
                id = convertToId(i, j, m);
                father.put(id, id);
            }
        }
    }
    public int compressFind(int x) {
        int parent = father.get(x);
        while (parent != father.get(parent)) {
            parent = father.get(parent);
        }
        int temp = -1;
        int origFather = x;
        while (origFather != father.get(origFather)) {
            temp = father.get(origFather);
            father.put(origFather, parent);
            origFather = temp;
        }
        return parent;
    }

    public int find(int x) {
        int parent = father.get(x);
        while(parent != father.get(parent)) {
            parent = father.get(parent);
        }
        return parent;
    }

    public void union(int x, int y) {
        int fatherX = compressFind(x);
        int fatherY = compressFind(y);
        if (fatherX != fatherY) {
            father.put(fatherX, fatherY);
        }
    }

    /**
     *Return 1 -> add fathers into father map
     *Return 0 -> already in the father map
     */
    public int unionWithReturn(int x, int y) {
        int fa_x = compressFind(x);
        int fa_y = compressFind(y);
        System.out.println("union " + x + " " + y);
        if (fa_x != fa_y) {
            father.put(fa_x, fa_y);
            return 1;
        }
        return 0;
    }
}



class Point {
    int x;
    int y;
    Point() {
        x = 0;
        y = 0;
    }
    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
