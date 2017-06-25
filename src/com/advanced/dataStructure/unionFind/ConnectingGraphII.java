package com.advanced.dataStructure.unionFind;

/**
 * Created by charles on 12/1/16.
 * Given n nodes in a graph labeled from 1 to n. There is no edges in the graph at beginning.
 You need to support the following method:
 1. connect(a, b), an edge to connect node a and node b
 2. query(a), Returns the number of connected component nodes which include node a.
 Example
 5 // n = 5
 query(1) return 1
 connect(1, 2)
 query(1) return 2
 connect(2, 4)
 query(1) return 3
 connect(1, 4)
 query(1) return 3
 */
public class ConnectingGraphII {
    private int[] father = null;
    private int[] size = null; // weighted union find to record size

    public ConnectingGraphII(int n) {
        // initialize your data structure here.
        father = new int[n + 1];
        size = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            father[i] = 0;
            size[i] = 1;
        }
    }

    public void connect(int a, int b) {
        int root_a = find(a);
        int root_b = find(b);
        if (root_a != root_b) {
            father[root_a] = root_b;
            size[root_b] += size[root_a]; // root_b as father
        }
    }

    public int query(int a) {
        int root_a = find(a);
        return size[root_a];
    }

    private int find(int x) {
        while (father[x] != 0) {
            x = father[x];
        }
        return x;
    }

    public static void main(String[] args) {
        ConnectingGraphII c2 = new ConnectingGraphII(5);
        System.out.println(c2.query(1));
        c2.connect(1, 2);
        System.out.println(c2.query(1) + "," + c2.query(2));

        c2.connect(2, 4);
        System.out.println(c2.query(1) + "," + c2.query(2) + "," + c2.query(4));
        System.out.println(c2.query(3) + "," + c2.query(5));
    }
}
