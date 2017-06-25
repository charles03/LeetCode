package com.leetcode.graph;

import java.util.*;

/**
 * Created by charles on 4/20/17.
 * Clone an undirected graph. Each node in the graph contains a label and a list of its neighbors.


 OJ's undirected graph serialization:
 Nodes are labeled uniquely.

 We use # as a separator for each node, and , as a separator for node label and each neighbor of the node.
 As an example, consider the serialized graph {0,1,2#1,2#2,2}.

 The graph has a total of three nodes, and therefore contains three parts as separated by #.

 First node is labeled as 0. Connect node 0 to both nodes 1 and 2.
 Second node is labeled as 1. Connect node 1 to node 2.
 Third node is labeled as 2. Connect node 2 to node 2 (itself), thus forming a self-cycle.
 Visually, the graph looks like the following:

    1
   / \
  /   \
 0 --- 2
      / \
      \_/
 */
public class CloneGraph_133 {
    /**
     * BFS + Queue
     * For this question, we implement customized queue instead of using java.util.Queue
     * so that we can move start and get next node instead of pop current node to lose info
     *
     * BFS iteration all nodes and relative neighbors
     */
    public UndirectedGraphNode cloneGraph(UndirectedGraphNode node) {
        List<UndirectedGraphNode> queue = new ArrayList<>();
        /** key is source node in graph, value is cloned node in clone graph */
        Map<UndirectedGraphNode, UndirectedGraphNode> map = new HashMap<>();
        int start = 0;
        queue.add(node);
        map.put(node, new UndirectedGraphNode(node.label));

        // clone nodes
        UndirectedGraphNode head = null;
        UndirectedGraphNode neighbor = null;
        while (start < queue.size()) {
            head = queue.get(start);
            start++;
            for (int i = 0; i < head.neighbors.size(); i++) {
                neighbor = head.neighbors.get(i);
                if (!map.containsKey(neighbor)) {
                    map.put(neighbor, new UndirectedGraphNode(neighbor.label));
                    queue.add(neighbor);
                }
            }
        }
        // clone neighbor edges
        head = null;
        UndirectedGraphNode clone = null;
        for (int i = 0; i < queue.size(); i++) {
            head = queue.get(i);
            clone = map.get(head);
            for (int j = 0; j < head.neighbors.size(); j++) {
                clone.neighbors.add(map.get(head.neighbors.get(j)));
            }
        }
        return map.get(node);
    }

    public UndirectedGraphNode cloneGraphII(UndirectedGraphNode node) {
        if (node == null) {
            return null;
        }
        Queue<UndirectedGraphNode> queue = new LinkedList<>();
        // use a map to save cloned nodes
        Map<UndirectedGraphNode, UndirectedGraphNode> map = new HashMap<>();
        queue.offer(node);
        map.put(node, new UndirectedGraphNode(node.label));

        UndirectedGraphNode head = null;
        while (!queue.isEmpty()) {
            head = queue.poll();
            // handle neighbor
            for (UndirectedGraphNode neighbor : head.neighbors) {
                // copy node
                if (!map.containsKey(neighbor)) {
                    queue.offer(neighbor);
                    map.put(neighbor, new UndirectedGraphNode(neighbor.label));
                }
                // copy edge
                map.get(head).neighbors.add(map.get(neighbor));
            }
        }
        return map.get(node);
    }
}
