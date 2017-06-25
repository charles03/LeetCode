package com.algorithm.tree.segmentTree;

/**
 * Created by charles on 11/20/16.
 */
public class SegmentTreeNode {
    public int start, end;
    public int max;
    public int min;
    public long sum;
    public SegmentTreeNode left, right;
    // constructor
    public SegmentTreeNode(int start, int end) {
        this(start, end, Integer.MIN_VALUE, Integer.MAX_VALUE, 0);
    }

    public SegmentTreeNode(int start, int end, int max) {
        this(start, end, max, Integer.MAX_VALUE, 0);
    }
    public SegmentTreeNode(int start, int end, int max, int min) {
        this(start, end, max, min, 0);
    }
    /**
     *
     * @param start
     * @param end
     * @param max max value in given segment
     * @param min min in given segement [start, end) inclusive, outclusive
     * @param sum sum in given segement
     */
    public SegmentTreeNode(int start, int end, int max, int min, long sum) {
        this.start = start;
        this.end = end;
        this.max = max;
        this.min = min;
        this.sum = sum;

        this.left = null;
        this.right = null;
    }
}
