package com.algorithm.tree.segmentTree;

/**
 * Created by charles on 11/22/16.
 * Given an integer array in the construct method, implement two methods query(start, end) and modify(index, value):
 For query(start, end), return the sum from index start to index end in the given array.
 For modify(index, value), modify the number in the given index to value

 Given array A = [1,2,7,8,5].

 query(0, 2), return 10.
 modify(0, 4), change A[0] from 1 to 4.
 query(0, 1), return 6.
 modify(2, 1), change A[2] from 7 to 1.
 query(2, 4), return 14.

 {@link SegmentTree} and {@link SegmentTreeNode}
 */
public class IntervalSumFollowup {
    private SegmentTreeNode root;
    private SegmentTree segment;

    public IntervalSumFollowup(int[] A) {
        segment = new SegmentTree();
        root = segment.genericBuild(A);
    }

    public long query(int start, int end) {
        return segment.querySum(root, start, end);
    }

    public void modify(int index, int value) {
        segment.modify(root, index, value);
    }

    public static void main(String[] args) {
        int[] A = {1,2,7,8,5};
        IntervalSumFollowup sum = new IntervalSumFollowup(A);

        System.out.println(sum.query(0, 2));
        sum.modify(0, 4);
        System.out.println(sum.query(0, 2));
    }
}
