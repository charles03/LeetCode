package com.algorithm.tree.segmentTree;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by charles on 11/21/16.
 */
public class SegmentTreeQueryCount {
    public int queryCount(SegmentNode root, int start, int end) {
        if (root == null) {
            return 0;
        }
        if (start <= root.start && root.end <= end) { // be careful
            // should use inclusive [start, end] include [expTreeNode.start, expTreeNode.end]
            /**
             * instead of if (start == expTreeNode.start && expTreeNode.end == end)
             */
            return root.count;
        }
        int mid = root.start + (root.end - root.start) / 2;
        int leftCount = 0, rightCount = 0;
        if (start <= mid) {
            leftCount = queryCount(root.left, start, Math.min(mid, end));
        }
        if (mid < end) {
            rightCount = queryCount(root.right, Math.max(mid + 1, start), end);
        }
        return leftCount + rightCount;
    }

    public int queryCountJiuzhang(SegmentNode root, int start, int end) {
        if (start > end || root == null) {
            return 0;
        }
        if (start <= root.start && root.end <= end) {
            return root.count;
        }
        int mid = root.start + (root.end - root.start) / 2;
        int leftCount = 0, rightCount = 0;

        // left child
        if (start <= mid) {
            if (mid < end) { // split
                leftCount = queryCount(root.left, start, mid);
            } else { // inclusive
                leftCount = queryCount(root.left, start, end);
            }
        }
        // right child
        if (mid < end) {
            if (start <= mid) { // split
                rightCount = queryCount(root.right, mid + 1, end);
            } else { // inclusive
                rightCount = queryCount(root.right, start, end);
            }
        }
        // else no intersection
        return leftCount + rightCount;
    }

    private class SegmentNode {
        int start;
        int end;
        int count;
        SegmentNode left;
        SegmentNode right;

        SegmentNode(int start, int end, int count) {
            this.start = start;
            this.end = end;
            this.count = count;
            this.left = null;
            this.right = null;
        }
    }

}
