package com.algorithm.tree.segmentTree;

import java.util.ArrayList;

/**
 * Created by charles on 11/21/16.
 *
 * Given an integer array (index from 0 to n-1, where n is the size of this array),
 * and an query list. Each query has two integers [start, end].
 * For each query, calculate the sum number between index start and end in the given array, return the result list.
 *
 * For array [1,2,7,8,5], and queries [(0,4),(1,2),(2,4)], return [23,9,20]
 */
public class IntervalSum {
    /**
     *@param A, queries: Given an integer array and an query list
     *@return: The result list
     */
    public ArrayList<Long> intervalSum(int[] A, ArrayList<Interval> queries) {
        if (A == null || A.length == 0 || queries == null || queries.size() == 0) {
            return new ArrayList<Long>();
        }
        ArrayList<Long> res = new ArrayList<>();

        SegmentTree s = new SegmentTree();
        SegmentTreeNode root = s.genericBuild(A);

        for (Interval query : queries) {
            res.add(s.querySum(root, query.start, query.end));
        }
        return res;
    }

    public static void main(String[] args) {
        IntervalSum sum = new IntervalSum();
        ArrayList<Interval> queries = new ArrayList<>();
        queries.add(new Interval(0, 4));
        queries.add(new Interval(1, 2));
        queries.add(new Interval(2, 4));
        int[] A = {1,2,7,8,5};

        System.out.println(sum.intervalSum(A, queries));
    }
}
