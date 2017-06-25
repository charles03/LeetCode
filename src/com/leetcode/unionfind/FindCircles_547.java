package com.leetcode.unionfind;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Created by charles on 6/9/17.
 * {@link com.leetcode.dfs.FindCircles_547}
 *
 */
public class FindCircles_547 {
    private class UnionFind {
        private int count = 0;
        private int[] father;
        private int idx = 0;

        public UnionFind(int n) {
            count = n;
            father = new int[count];
            for (int i = 0; i < n; i++) {
                father[i] = i;
            }
            System.out.println(idx++ + "---" + print(father));
        }

        public int compressFind(int x) {
            int parent = x;
            while (parent != father[parent]) {
                parent = father[parent];
            }
            int original_parent = x;
            int tmp = -1;
            while (father[original_parent] != parent) {
                tmp = father[original_parent];
                father[original_parent] = parent;
                original_parent = tmp;
            }
            System.out.println(String.format("step %d : find %d, arr : %s", idx++, x, print(father)));
            return parent;
        }

        public void union(int p, int q) {
            int root_p = compressFind(p);
            int root_q = compressFind(q);
            if (root_p != root_q) {
                father[root_p] = root_q;
                count--;
            }
            System.out.println(String.format("step %d : union %d, %d, arr: %s", idx++, p, q, print(father)));
        }

        public int getCount() {
            return count;
        }
    }

    /**
     * The idea is to always attach smaller depth tree under the root of the deeper tree. This technique is called union by rank.
     * The term rank is preferred instead of height because if path compression technique (we have discussed it below) is used, then rank is not always equal to height.
     * Also, size (in place of height) of trees can also be used as rank. Using size as rank also yields worst case time complexity as O(Logn)
     */
    private class UnionFindII {
        private int count = 0;
        private int[] father;
        private int[] rank;
        private int idx = 0;
        /** init each cell has not connection */
        public UnionFindII(int n) {
            this.count = n;
            father = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) {
                father[i] = i;
            }
        }
        public int find(int x) {
            int p = x;
            while (p != father[p]) {
                father[p] = father[father[p]];
                p = father[p];
            }
            System.out.println(String.format("step %d : find %d with %d, arr : %s", idx++, x, p, print(father)));
            return p;
        }
        public void unionWithRank(int p, int q) {
            int rootP = find(p);
            int rootQ = find(q);
            if (rootP == rootQ) {
                return;
            }
            // apply rank into union so as to optimize time complexity in worse case
            if (rank[rootQ] > rank[rootP]) {
                father[rootP] = rootQ;
            } else {
                father[rootQ] = rootP;
                if (rank[rootP] == rank[rootQ]) {
                    rank[rootP]++;
                }
            }
            /** because we already union point p and q, original count should minus one */
            count--;
            System.out.println(String.format("step %d : union %d, %d, arr: %s", idx++, p, q, print(father)));
        }
        public int getCount() {
            return this.count;
        }
    }


    public int findCircleNum(int[][] M) {
        int n = M.length;
        UnionFind uf = new UnionFind(n);
        for (int i = 0; i < n - 1; i++) {
            for (int j = i+1; j < n; j++) {
                if (M[i][j] == 1) {
                    uf.union(i, j);
                }
            }
        }
        System.out.println("------Union with Rank to reduce path height--------");
        UnionFindII uf2 = new UnionFindII(n);
        for (int i = 0; i < n-1; i++) {
            for (int j = i+1; j < n; j++) {
                if (M[i][j] == 1) {
                    uf2.unionWithRank(i,j);
                }
            }
        }
        System.out.println(uf2.getCount());
        return uf.getCount();
    }

    public static void main(String[] args) {
        FindCircles_547 f = new FindCircles_547();
        int[][] m1 = {
                {1,0,1,0},
                {0,1,0,1},
                {1,0,1,1},
                {0,1,1,1}};
        System.out.println(f.findCircleNum(m1));
    }

    private String print(int[] a) {
        return Arrays.toString(a);
    }
}
