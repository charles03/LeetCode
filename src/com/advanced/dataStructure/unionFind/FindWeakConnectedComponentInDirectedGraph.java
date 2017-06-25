package com.advanced.dataStructure.unionFind;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Created by charles on 9/25/16.
 *
 * Find the number Weakly Connected Component in the directed graph.
 * Each node in the graph contains a label and a list of its neighbors.
 * (a connected set of a directed graph is a subgraph in which any two vertices are connected by direct edge path.)
 *
 * Notice: Sort the element in the set in increasing order
 *
 * Example
 Given graph:

 A----->B  C
  \     |  |
   \    |  |
    \   |  |
     \  v  v
      ->D  E <- F
 Return {A,B,D}, {C,E,F}. Since there are two connected component which are {A,B,D} and {C,E,F}
 */

/**
 * what is weakly connected component in the directed graph?
 * A weakly connected component is one in which all components are connected by some path, ignoring direction.
 * So this entire graph would be a weakly connected component.
 *
 * What is strongly connected component in the directed graph?
 * is a sub-graph where there is a path from every node to every other node.
 */
/* Definition for Directed graph.
 * class DirectedGraphNode {
 *     int label;
 *     ArrayList<DirectedGraphNode> neighbors;
 *     DirectedGraphNode(int x) { label = x; neighbors = new ArrayList<DirectedGraphNode>(); }
 * };
 */
public class FindWeakConnectedComponentInDirectedGraph {
    /**
     * @param nodes a array of Directed graph node
     * @return a connected set of a directed graph
     */
    public List<List<Integer>> connectedSetAdvanced(ArrayList<DirectedGraphNode> nodes) {
        return null;
    }
}

class DirectedGraphNode {
    public int label;
    public ArrayList<DirectedGraphNode> neighbors;

    public DirectedGraphNode(int label) {
        this.label = label;
        neighbors = new ArrayList<>();
    }
}

class UnionFindInDirectedGraph {
    HashMap<Integer, Integer> father = new HashMap<>();

    public UnionFindInDirectedGraph(HashSet<Integer> hashSet) {
        for (Integer now : hashSet) {
            father.put(now, now);
        }
    }

    /*
    regular find which has time complexity O(n), unlike compressFind() O(1) for average
     */
    public int find(int x) {
        int parent = father.get(x);
        while (parent != father.get(parent)) {
            parent = father.get(parent);
        }
        return parent;
    }

    public int compressFind(int x) {
        int parent = father.get(x);
        while (parent != father.get(parent)) {
            parent = father.get(parent);
        }
        int temp = -1;
        int origFather = father.get(x);
        while (origFather != father.get(origFather)) {
            temp = father.get(origFather);
            father.put(origFather, parent);
            origFather = temp;
        }
        return parent;
    }
}




