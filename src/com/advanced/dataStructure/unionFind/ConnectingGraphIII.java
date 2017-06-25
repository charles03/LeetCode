package com.advanced.dataStructure.unionFind;

/**
 * Created by charles on 12/2/16.
 * Given n nodes in a graph labeled from 1 to n. There is no edges in the graph at beginning.
 You need to support the following method:
 1. connect(a, b), an edge to connect node a and node b
 2. query(), Returns the number of connected component in the graph
 Example
 5 // n = 5
 query() return 5
 connect(1, 2)
 query() return 4
 connect(2, 4)
 query() return 3
 connect(1, 4)
 query() return 3
 */
public class ConnectingGraphIII {
    private int[] father = null;
    private int count;

    public ConnectingGraphIII(int n) {
        father = new int[n + 1];
        count = n;
        for (int i = 1; i <= n; i++) {
            father[i] = 0;
        }
    }

    public void connect(int a, int b) {
        int root_a = find(a);
        int root_b = find(b);
        if (root_a != root_b) {
            father[root_a] = root_b;
            count--;
        }
    }

    public int query() {
        return count;
    }

    private int find(int x) {
        while (father[x] != 0) {
            x = father[x];
        }
        return x;
    }

    public static void main(String[] args) {
        ConnectingGraphIII c3 = new ConnectingGraphIII(5);
        System.out.println(c3.query());
        c3.connect(1, 2);
        System.out.println(c3.query());
        c3.connect(3, 4);
        System.out.println(c3.query());
        c3.connect(2, 3);
        System.out.println(c3.query());
    }
}
