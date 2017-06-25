package com.leetcode.graph;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by charles on 4/20/17.
 */
public class UndirectedGraphNode {
    int label;
    List<UndirectedGraphNode> neighbors;
    UndirectedGraphNode(int x) {
        this.label = x;
        this.neighbors = new ArrayList<>();
    }
}
