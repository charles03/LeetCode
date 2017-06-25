package com.algorithm.tree.segmentTree;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by charles on 11/20/16.
 *
 * Given an integer array (index from 0 to n-1, where n is the size of this array),and an query list.
 * Each query has two integers [start, end]. For each query, calculate the minimum number between index start and end in the given array, return the result list.
 * For array [1,2,7,8,5], and queries [(1,2),(0,4),(2,4)], return [2,1,5]
 *
 * Challenge : O(lgN) time for each query
 */
public class IntervalMinimumNumber {
    public ArrayList<Integer> intervalMinNumber(int[] A, ArrayList<Interval> queries) {
//        SegmentMinTreeNode expTreeNode = buildMinTree(0, A.length - 1, A);
        SegmentMinTreeNode root = buildMinTreeByJiuZhang(0, A.length - 1, A);
        ArrayList<Integer> res = new ArrayList<>();
        for (Interval query : queries) {
            res.add(query(root, query.start, query.end));
        }
        return res;
    }
    private SegmentMinTreeNode buildMinTree(int start, int end, int[] A) {
        if (start > end) {
            return null;
        }
        SegmentMinTreeNode root = new SegmentMinTreeNode(start, end, Integer.MAX_VALUE);
        if (start == end) {
            root.min = A[start];
            /**
             * must return expTreeNode, otherwise have stackOverFlow
             */
            return root;
        }
        int mid = start + (end - start) / 2;
        root.left = buildMinTree(start, mid, A);
        root.right = buildMinTree(mid + 1, end, A);
        if (root.left != null && root.left.min < root.min) {
            root.min = root.left.min;
        }
        if (root.right != null && root.right.min < root.min) {
            root.min = root.right.min;
        }
        return root;
    }

    private SegmentMinTreeNode buildMinTreeByJiuZhang(int start, int end, int[] A) {
        if (start > end) {
            return null;
        }
        SegmentMinTreeNode root = new SegmentMinTreeNode(start, end, Integer.MAX_VALUE);
        if (start != end) {
            int mid = start + (end - start) / 2;
            root.left = buildMinTreeByJiuZhang(start, mid, A);
            root.right = buildMinTreeByJiuZhang(mid + 1, end, A);

            root.min = Math.min(root.left.min, root.right.min);
        } else {
            root.min = A[start];
        }
        return root;
    }

    private int query(SegmentMinTreeNode root, int start, int end) {
        if (start == root.start && end == root.end) {
            return root.min;
        }
        int mid = root.start + (root.end - root.start) / 2;
        int leftMin = Integer.MAX_VALUE, rightMin = Integer.MAX_VALUE;
        // left child
        if (start <= mid) {
            leftMin = query(root.left, start, Math.min(mid, end));
        }
        // right child and make sure in intersection
        if (mid < end) {
            rightMin = query(root.right, Math.max(mid + 1, start), end);
        }
        // else no intersection
        return Math.min(leftMin, rightMin);
    }

    private class SegmentMinTreeNode {
        int start, end;
        int min;
        SegmentMinTreeNode left, right;
        SegmentMinTreeNode(int start, int end, int min) {
            this.start = start;
            this.end = end;
            this.min = min;
            this.left = null;
            this.right = null;
        }
    }

    public static void main(String[] args) {
        IntervalMinimumNumber im = new IntervalMinimumNumber();
        ArrayList<Interval> queries = new ArrayList<>();
        queries.add(new Interval(1,2));
        queries.add(new Interval(0,4));
        queries.add(new Interval(2,4));
        int[] A = {1,2,7,8,5};
        System.out.println(im.intervalMinNumber(A, queries).toString());
    }
}

class Interval {
    int start, end;
    Interval(int start, int end) {
        this.start = start;
        this.end = end;
    }
}
