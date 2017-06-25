package com.leetcode.graph;

import java.util.*;

/**
 * Created by charles on 4/20/17.
 * For a undirected graph with tree characteristics, we can choose any node as the root. The result graph is then a rooted tree. Among all possible rooted trees, those with minimum height are called minimum height trees (MHTs). Given such a graph, write a function to find all the MHTs and return a list of their root labels.

 Format
 The graph contains n nodes which are labeled from 0 to n - 1. You will be given the number n and a list of undirected edges (each edge is a pair of labels).

 You can assume that no duplicate edges will appear in edges. Since all edges are undirected, [0, 1] is the same as [1, 0] and thus will not appear together in edges.

 Example 1:

 Given n = 4, edges = [[1, 0], [1, 2], [1, 3]]

   0
   |
   1
  / \
 2   3
 return [1]

 Example 2:

 Given n = 6, edges = [[0, 3], [1, 3], [2, 3], [4, 3], [5, 4]]

 0  1  2
  \ | /
    3
    |
    4
    |
    5
 return [3, 4]

 Note:

 (1) According to the definition of tree on Wikipedia: “a tree is an undirected graph in which any two vertices are connected by exactly one path. In other words, any connected graph without simple cycles is a tree.”

 (2) The height of a rooted tree is the number of edges on the longest downward path between the root and a leaf.
 */
public class MinimumHeightTrees_310 {
    /**
     * leaf node has one single edge,
     * find all leaf nodes in the graph and then remove from graph/tree
     * go into inner, repeat above process
     *
     * this is topological sorting, indegree = 1 --> leaf node has single edge
     */
    public List<Integer> findMinHeightTrees(int n, int[][] edges) {
        if (n == 1) {
            return Collections.singletonList(0);
        }
        List<Integer>[] graph = new ArrayList[n];
        constructGraph(graph, edges);

        List<Integer> leaves = new ArrayList<>();
        findLeaveInGraph(graph, leaves);

        // BFS to remove leaf level by level until at most two nodes leftover
        leaves = bfsRemoveLeaf(graph, leaves);
        return leaves;
    }
    private void constructGraph(List<Integer>[] graph, int[][] edges) {
        for (int i = 0; i < graph.length; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] edge : edges) {
            graph[edge[0]].add(edge[1]);
            graph[edge[1]].add(edge[0]);
        }
    }
    private void findLeaveInGraph(List<Integer>[] graph, List<Integer> leaves) {
        for (int i = 0; i < graph.length; i++) {
            if (graph[i].size() == 1) {
                leaves.add(i);
            }
        }
    }
    private List<Integer> bfsRemoveLeaf(List<Integer>[] graph, List<Integer> leaves) {
        int size = graph.length;
        List<Integer> newLeaves = null;
        int vertex = 0;
        while (size > 2) {
            size -= leaves.size();
            newLeaves = new ArrayList<>();
            for (int leaf : leaves) { // because leaf only has one vertex
                vertex = graph[leaf].get(0);
                // then decrement one degree for vertex by remove leaf
                graph[vertex].remove(new Integer(leaf));

                if (graph[vertex].size() == 1) {
                    newLeaves.add(vertex);
                }
            }
            leaves = newLeaves;
        }
        return leaves;
    }

    public static void main(String[] args) {
        MinimumHeightTrees_310 m = new MinimumHeightTrees_310();
        int[][] e1 = {{1,0}, {1,2}, {1,3}};
        m.findMinHeightTrees(4, e1);
    }
    /** because need to remove elem from container, so that use Set<Integer>
     * leaf node is vertex which has only one edge
     */
    public List<Integer> findMinHeightTreesII(int n, int[][] edges) {
        if (n == 1) {
            return Collections.singletonList(0);
        }
        List<Set<Integer>> graph = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            graph.add(new HashSet<>());
        }
        for (int[] edge : edges) {
            graph.get(edge[0]).add(edge[1]);
            graph.get(edge[1]).add(edge[0]);
        }
        List<Integer> leaves = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (graph.get(i).size() == 1) {
                leaves.add(i);
            }
        }
        List<Integer> tmpLeaves = null;
        int vertex = 0;
        while (n > 2) {
            n -= leaves.size();
            tmpLeaves = new ArrayList<>();
            /** for these leaf nodes, all set size should be 1 */
            for (int leaf : leaves) {
                vertex = graph.get(leaf).iterator().next(); // get corresponding vertex of leaf node
                graph.get(vertex).remove(leaf); // decrement leaf node from this vertex
                if (graph.get(vertex).size() == 1) { // if vertex become leaf after cut edge
                    tmpLeaves.add(vertex);
                }
            }
            leaves = tmpLeaves; // set new leaf list to prev leaf
        }
        return leaves;
    }
}
