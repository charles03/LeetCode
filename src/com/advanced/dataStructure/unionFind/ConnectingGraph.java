package com.advanced.dataStructure.unionFind;

/**
 * Created by charles on 12/1/16.
 *
 * Given n nodes in a graph labeled from 1 to n. There is no edges in the graph at beginning.
 You need to support the following method:
 1. connect(a, b), add an edge to connect node a and node b. 2.query(a, b)`, check if two nodes are connected
 Example
 5 // n = 5
 query(1, 2) return false
 connect(1, 2)
 query(1, 3) return false
 connect(2, 4)
 query(1, 4) return true
 */
public class ConnectingGraph {
    // no need private class union find and hash map as father array
    private int[] father = null;

    public ConnectingGraph(int n) {
        // initialize your data structure here.
        father = new int[n + 1];
        // initialize, start from i = 1
        for (int i = 1; i <= n; i++) {
            father[i] = 0;
        }
    }

    public void connect(int a, int b) {
        int root_a = find(a);
        int root_b = find(b);
        if (root_a != root_b) {
            father[root_a] = root_b;
        }
    }

    public boolean query(int a, int b) {
        int root_a = find(a);
        int root_b = find(b);
        return root_a == root_b;
    }

    private int find(int x) {
        while (father[x] != 0) {
            x = father[x];
        }
        return x;
    }

    public static void main(String[] args) {
        ConnectingGraph c = new ConnectingGraph(5);
        System.out.println(c.query(1, 2));
        c.connect(1, 2);
        System.out.println(c.query(1, 3));
        c.connect(2, 4);
        System.out.println(c.query(1, 4));
        System.out.println(c.query(1, 2));
        System.out.println(c.query(1, 3));
    }
}
