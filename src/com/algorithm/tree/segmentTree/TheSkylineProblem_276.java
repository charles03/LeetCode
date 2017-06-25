package com.algorithm.tree.segmentTree;

import sun.tools.jstat.Literal;

import javax.xml.soap.Node;
import java.util.*;

/**
 * {@link com.leetcode.divideconquer.TheSkylineProblem_218}
 * Created by charles on 2/15/17.
 * Apply with Segment Tree solution
 *
 * https://discuss.leetcode.com/topic/49110/java-segment-tree-solution-47-ms
 */
public class TheSkylineProblem_276 {

    private static class SegmentNode {
        int start, end;
        SegmentNode left, right;
        int height;
        SegmentNode(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

    private SegmentNode build(int start, int end) {
        if (start > end) {
            return null;
        }
        SegmentNode curr = new SegmentNode(start, end);
        if (start + 1 < end) {
            int mid = start + (end - start) / 2;
            curr.left = build(start, mid);
            curr.right = build(mid, end);
        }
        return curr;
    }
    private void add(SegmentNode node, int start, int end, int height) {
        if (node == null || start >= node.end || end <= node.start || height < node.height) {
            return;
        }
        if (node.left == null && node.right == null) {
            node.height = Math.max(node.height, height);
        } else {
            add(node.left, start, end, height);
            add(node.right, start, end, height);
            node.height = Math.min(node.left.height, node.right.height);
        }
    }

    private void explore(List<int[]> res, List<Integer> endpointList, SegmentNode node) {
        if (node == null) {
            return;
        }
        if (node.left == null && node.right == null
                && (res.size() == 0 || res.get(res.size() - 1)[1] != node.height)) {
            res.add(new int[] {endpointList.get(node.start), node.height});
        } else {
            explore(res, endpointList, node.left);
            explore(res, endpointList, node.right);
        }
    }

    /**
     * use Segment Tree to query the interval
     */
    public List<int[]> getSkyline(int[][] buildings) {
        Set<Integer> endpointSet = new HashSet<>();
        for (int[] building : buildings) {
            endpointSet.add(building[0]);
            endpointSet.add(building[1]);
        }
        List<Integer> endpointList = new ArrayList<>(endpointSet);
        Collections.sort(endpointList);
        HashMap<Integer, Integer> endpointMap = new HashMap<>();

        for (int i = 0; i < endpointList.size(); i++) {
            endpointMap.put(endpointList.get(i), i);
        }
        SegmentNode root = build(0, endpointList.size() - 1);
        for (int[] building : buildings) {
            add(root, endpointMap.get(building[0]), endpointMap.get(building[1]), building[2]);
        }
        List<int[]> res = new ArrayList<>();
        explore(res, endpointList, root);

        if (endpointList.size() > 0) {
            res.add(new int[]{endpointList.get(endpointList.size() - 1), 0});
        }
        return res;
    }
}
