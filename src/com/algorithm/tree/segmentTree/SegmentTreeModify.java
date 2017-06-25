package com.algorithm.tree.segmentTree;

/**
 * Created by charles on 11/21/16.
 *
 * For a Maximum Segment Tree, which each node has an extra value max to store the maximum value in this node's interval.
 Implement a modify function with three parameter expTreeNode,
 index and value to change the node's value with [start, end] = [index, index] to the new given value.
 Make sure after this change, every node in segment tree still has the max attribute with the correct value.

 For segment tree:
                        [1, 4, max=3]
                    /                \
        [1, 2, max=2]                [3, 4, max=3]
        /              \             /             \
 [1, 1, max=2], [2, 2, max=1], [3, 3, max=0], [4, 4, max=3]

 if call modify(expTreeNode, 2, 4), we can get:
                        [1, 4, max=4]
                    /                \
        [1, 2, max=4]                [3, 4, max=3]
        /              \             /             \
 [1, 1, max=2], [2, 2, max=4], [3, 3, max=0], [4, 4, max=3]

 or call modify(expTreeNode, 4, 0), we can get:
                        [1, 4, max=2]
                    /                \
        [1, 2, max=2]                [3, 4, max=0]
        /              \             /             \
 [1, 1, max=2], [2, 2, max=1], [3, 3, max=0], [4, 4, max=0]

 Challenge: Do it in O(h) time, h is height of segment tree
 */
public class SegmentTreeModify {
    public void modify(SegmentTreeNode root, int index, int value) {
        if (root.start == index &&  root.end == index) {
            // find node
            root.max = value;
            return;
        }
        // query
        int mid = root.start + (root.end - root.start) / 2;
        if (root.start <= index && index <= mid) {
            modify(root.left, index, value);
        }
        if (mid < index && index <= root.end) {
            modify(root.right, index, value);
        }
        // update, done in both ways

//        if (expTreeNode.left != null) {
//            expTreeNode.max = expTreeNode.left.max;
//        }
//        if (expTreeNode.right != null && expTreeNode.right.max > expTreeNode.max) {
//            expTreeNode.max = expTreeNode.right.max;
//        }
        root.max = Math.max(root.left.max, root.right.max);
    }
}
