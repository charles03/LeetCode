package com.algorithm.tree.segmentTree;

/**
 * Created by charles on 11/20/16.
 *For an integer array (index from 0 to n-1, where n is the size of this array),
 * in the corresponding SegmentTree, each node stores an extra attribute max to denote the maximum number in the interval of the array (index from start to end).

 Design a query method with three parameters expTreeNode, start and end, find the maximum number in the interval [start, end] by the given expTreeNode of segment tree.
 *For array [1, 4, 2, 3], the corresponding Segment Tree is:
                [0, 3, max=4]
                /             \
        [0,1,max=4]        [2,3,max=3]
        /         \        /         \
 [0,0,max=1] [1,1,max=4] [2,2,max=2], [3,3,max=3]

 query(expTreeNode, 1, 1), return 4
 query(expTreeNode, 1, 2), return 4
 query(expTreeNode, 2, 3), return 3
 query(expTreeNode, 0, 2), return 4
 */
public class SegmentTreeQuery {
    /**
     *@param root, start, end: The expTreeNode of segment tree and
     *                         an segment / interval
     *@return: The maximum number in the interval [start, end]
     */
    public int query(SegmentTreeNode root, int start, int end) {
        // write your code here
        if (root == null) {
            return Integer.MIN_VALUE;
        }
        if (root.start == start && root.end == end) {
            return root.max;
        }
        int mid = root.start + (root.end - root.start) / 2;
        int leftMax = Integer.MIN_VALUE, rightMax = Integer.MAX_VALUE;

        // left child
        if (start <= mid) {
            if (mid < end) { // split, go for left
                leftMax = query(root.left, start, mid);
            } else { // inclusive
                leftMax = query(root.left, start, end);
            }
            // leftMax = query(expTreeNode.left, start, Math.min(mid, end));
        }
        if (mid < end) {
            if (start <= mid) {
                rightMax = query(root.right, mid + 1, end);
            } else {
                rightMax = query(root.right, start, end);
            }
            // rightMax = query(expTreeNode.right, Math.max(mid + 1, start), end);
        }
        // else, no intersection
        return Math.max(leftMax, rightMax);
    }
}
