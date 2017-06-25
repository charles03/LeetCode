package com.algorithm.tree.segmentTree;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by charles on 11/21/16.
 *
 * Summarized Class for {@link SegmentTreeBuild} @link {@link SegmentTreeModify} {@link SegmentTreeQuery}
 * And {@link SegmentTreeBuildAdvanced} {@link IntervalMinimumNumber}
 * for max segment tree or min segement tree
 */
public class SegmentTree {
    /**
     * recursively build segment tree, include max/min/sum
     * @param A
     * @return
     */
    public SegmentTreeNode genericBuild(int[] A) {
        if (A == null || A.length == 0) {
            return null;
        }
        return buildSegmentTree(0, A.length - 1, A);
    }

    private SegmentTreeNode buildSegmentTree(int start, int end, int[] A) {
        if (start > end) {
            return null;
        }
        SegmentTreeNode root = new SegmentTreeNode(start, end);
        if (start == end) {
            root.max = A[start];
            root.min = A[start];
            root.sum = A[start];
            return root;
        }
        int mid = start + (end - start) / 2;
        root.left = buildSegmentTree(start, mid, A);
        root.right = buildSegmentTree(mid + 1, end, A);

        root.max = Math.max(root.left.max, root.right.max);
        root.min = Math.min(root.left.min, root.right.min);
        root.sum = root.left.sum + root.right.sum;

        return root;
    }

    /**
     * A serial of query method, separately query max/min/sum based on given inclusive/outclusive
     */
    public int queryMax(SegmentTreeNode root, int start, int end) {
        if (root == null) {
            return Integer.MIN_VALUE;
        }
        if (root.start == start && root.end == end) {
            return root.max;
        }
        int mid = root.start + (root.end - root.start) / 2;
        int leftMax = Integer.MIN_VALUE, rightMax = Integer.MIN_VALUE;

        if (start <= mid) {
            leftMax = queryMax(root.left, start, Math.min(end, mid));
        }
        if (mid < end) {
            rightMax = queryMax(root.right, Math.max(mid + 1, start), end);
        }
        return Math.max(leftMax, rightMax);
    }

    public int queryMin(SegmentTreeNode root, int start, int end) {
        if (root == null) {
            return Integer.MAX_VALUE;
        }
        if (root.start == start && root.end == end) {
            return root.min;
        }
        int mid = root.start + (root.end - root.start) / 2;
        int leftMin = Integer.MAX_VALUE, rightMin = Integer.MAX_VALUE;

        if (start <= mid) {
            leftMin = queryMin(root.left, start, Math.min(mid, end));
        }
        if (mid < end) {
            rightMin = queryMin(root.right, Math.max(mid + 1, start), end);
        }
        return Math.min(leftMin, rightMin);
    }

    public long querySum(SegmentTreeNode root, int start, int end) {
        if (root == null) {
            return 0;
        }
        if (start == root.start && root.end == end) { // use concise comparison
            return root.sum;
        }
        int mid = root.start + (root.end - root.start) / 2;
        long leftSum = 0, rightSum = 0;
        if (start <= mid) {
            leftSum = querySum(root.left, start, Math.min(mid, end));
        }
        if (mid < end) {
            rightSum = querySum(root.right, Math.max(mid + 1, start), end);
        }
        return leftSum + rightSum;
    }

    /**
     * modify method in segment tree, once value modified, should update max/min/sum correspondingly
     */
    public void modify(SegmentTreeNode root, int index, int value) {
        if (root == null) {
            return;
        }
        if (root.start == index && index == root.end) {
            root.sum = value;
            root.max = value;
            root.min = value;
            return;
        }
        int mid = root.start + (root.end - root.start) / 2;

        if (root.start <= index && index <= mid) {
            modify(root.left, index, value);
        }
        if (mid < index && index <= root.end) {
            modify(root.right, index, value);
        }
        root.max = Math.max(root.left.max, root.right.max);
        root.min = Math.min(root.left.min, root.right.min);
        root.sum = root.left.sum + root.right.sum;
    }

    /**
     * Simple method to collect all details of segment tree;
     * @param A
     * @param queries
     * @return
     */
    public List<List<Long>> collectAllDetails(int[] A, List<Interval> queries) {
        SegmentTreeNode root = genericBuild(A);
        return collectAllDetails(root, queries);
    }

    public List<List<Long>> collectAllDetails(SegmentTreeNode root, List<Interval> queries) {
        if (queries == null || queries.size() == 0) {
            return new ArrayList<>();
        }
        List<List<Long>> res = new ArrayList<>();

        List<Long> max = new ArrayList<>();
        List<Long> min = new ArrayList<>();
        List<Long> sum = new ArrayList<>();

        queries.stream().forEach(t -> {
            max.add((long)queryMax(root, t.start, t.end));
            min.add((long)queryMin(root, t.start, t.end));
            sum.add(querySum(root, t.start, t.end));
        });
        res.add(max);
        res.add(min);
        res.add(sum);

        return res;
    }


    public static void main(String[] args) {
        SegmentTree s = new SegmentTree();
        int[] A = {1, 4, 2, 3};
        ArrayList<Interval> queries = new ArrayList<>();
        queries.add(new Interval(1,2));
        queries.add(new Interval(0,3));
        queries.add(new Interval(2,3));

        SegmentTreeNode root = s.genericBuild(A);
        System.out.println(s.collectAllDetails(root, queries));

        s.modify(root, 1, 2);
        System.out.println(s.collectAllDetails(root, queries));
    }
}
