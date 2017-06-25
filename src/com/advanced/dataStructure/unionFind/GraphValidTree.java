package com.advanced.dataStructure.unionFind;

import java.util.HashMap;

/**
 * Created by charles on 9/24/16.
 * Reference http://www.geeksforgeeks.org/union-find/
 * Union-Find Algorithm | Set 1 (Detect Cycle in a an Undirected Graph)
 *
 * Given n nodes labeled from 0 to n - 1 and a list of undirected edges (each edge is a pair of nodes),
 * write a function to check whether these edges make up a valid tree.

 Notice
 You can assume that no duplicate edges will appear in edges.
 Since all edges are undirected, [0, 1] is the same as [1, 0] and thus will not appear together in edges.

 Example
 Given n = 5 and edges = [[0, 1], [0, 2], [0, 3], [1, 4]], return true.

 Given n = 5 and edges = [[0, 1], [1, 2], [2, 3], [1, 3], [1, 4]], return false.

 Tags: DFS / BFS / Union Find
 */
public class GraphValidTree {
    /**
     * @param n an integer
     * @param edges a list of undirected edges
     * @return true if it's a valid tree, or false
     */
    public boolean validTree(int n, int[][] edges) {
        // tree should have n nodes with n - 1 edges
        if (n - 1 != edges.length) {
            return false;
        }
        UnionFind uf = new UnionFind(n);
        for (int i = 0; i < edges.length; i++) {
            if (uf.compressFind(edges[i][0]) == uf.compressFind(edges[i][1])) {
                return false;
            }
            uf.union(edges[i][0], edges[i][1]);
        }

        return true;
    }

    public static void main(String[] args) {
        GraphValidTree valid = new GraphValidTree();
        int[][] case1 = {{0, 1}, {0, 2}, {0, 3}, {1, 4}};
        System.out.println(valid.validTree(5, case1));

        int[][] case2 = {{0, 1}, {1, 2}, {2, 3}, {1, 3}, {1, 4}};
        System.out.println(valid.validTree(5, case2));
    }
}

class UnionFind {
    HashMap<Integer, Integer> father = new HashMap<>();
    public UnionFind(int n) {
        for (int i = 0; i < n; i++) {
            father.put(i, i);
        }
    }

    public int compressFind(int x) {
        int parent = father.get(x);
        while (parent != father.get(parent)) {
            parent = father.get(parent);
        }
        int temp = -1;
        int origFather = father.get(x);
        while (origFather != father.get(origFather)) {
            // store the info
            temp = father.get(origFather);
            father.put(origFather, parent);
            // rolling to next one
            origFather = temp;
        }
        return parent;
    }

    public void union(int x, int y) {
        int fa_x = compressFind(x);
        int fa_y = compressFind(y);
        if (fa_x != fa_y) {
            father.put(fa_x, fa_y);
        }
    }
}
