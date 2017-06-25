package com.advanced.dataStructure.unionFind;

import java.util.*;

/**
 * Created by charles on 9/25/16.
 *
 * Find the number connected component in the undirected graph.
 * Each node in the graph contains a label and a list of its neighbors.
 * (a connected component (or just component) of an undirected graph is a subgraph in which
 * any two vertices are connected to each other by paths, and which is connected to no additional vertices in the supergraph.)
 *
 * Notice: Each connected component should sort by label.
 *
 * Clarification
 Learn more about representation of graphs

 Example
 Given graph:

 A------B  C
  \     |  |
   \    |  |
    \   |  |
     \  |  |
       D   E
 Return {A,B,D}, {C,E}. Since there are two connected component which is {A,B,D}, {C,E}

 Can use Union Find and BFS
 */
public class FindConnectedComponentInUndirectedGraph {
    /**
     * @param nodes a array of Undirected graph node
     * @return a connected set of a Undirected graph
     */
    public List<List<Integer>> connectedSetByBFS(ArrayList<UndirectedGraphNode> nodes) {
        // Write your code here
        List<List<Integer>> res = new ArrayList<>();
        if (nodes == null || nodes.size() == 0) {
            return res;
        }
        // use map to mark visited and initialize
        Map<UndirectedGraphNode, Boolean> visited = new HashMap<>();
        for (UndirectedGraphNode node : nodes) {
            visited.put(node, false);
        }

        for (UndirectedGraphNode node : nodes) {
            if (visited.get(node) == false) {
                breadthFirstSearch(node, visited, res);
            }
        }
        return res;
    }

    private void breadthFirstSearch(UndirectedGraphNode node, Map<UndirectedGraphNode, Boolean> visited, List<List<Integer>> res) {
        List<Integer> set = new ArrayList<>();
        Queue<UndirectedGraphNode> queue = new LinkedList<>();
        visited.put(node, true);
        queue.offer(node);

        while (!queue.isEmpty()) {
            UndirectedGraphNode u = queue.poll();
            set.add(u.label);
            for (UndirectedGraphNode g : u.neighbors) {
                if (visited.get(g) == false) {
                    visited.put(g, true);
                    queue.offer(g);
                }
            }
        }
        Collections.sort(set);
        res.add(set);
    }
}

class UndirectedGraphNode {
    int label;
    ArrayList<UndirectedGraphNode> neighbors;

    public UndirectedGraphNode(int x) {
        this.label = x;
        neighbors = new ArrayList<>();
    }
}


