package com.leetcode.queue;

import com.algorithm.tree.segmentTree.IntervalSum;

import java.util.*;

/**
 * Created by charles on 11/22/16.
 * Given two 1d vectors, implement an iterator to return their elements alternately.

 For example, given two 1d vectors:
 v1 = [1, 2]
 v2 = [3, 4, 5, 6]
 By calling next repeatedly until hasNext returns false, the order of elements returned by next should be: [1, 3, 2, 4, 5, 6].

 Follow up: What if you are given k 1d vectors? How well can your code be extended to such cases?

 Clarification for the follow up question - Update (2015-09-18):
 The "Zigzag" order is not clearly defined and is ambiguous for k > 2 cases.
 If "Zigzag" does not look right to you, replace "Zigzag" with "Cyclic". For example, given the following input:

 [1,2,3]
 [4,5,6,7]
 [8,9]
 It should return [1,4,8,2,5,9,3,6,7].

 3 Solutions : http://likesky3.iteye.com/blog/2247236

 As for K list followup, it is delicate to use Queue.
 */
public class ZigzagIterator_281 {
    private LinkedList<Iterator<Integer>> queue = new LinkedList<>();

    public ZigzagIterator_281(List<Integer> v1, List<Integer> v2, List<Integer> v3) {
        if (v1.size() > 0) {
            queue.offer(v1.iterator());
        }
        if (v2.size() > 0) {
            queue.offer(v2.iterator());
        }
        if (v3.size() > 0) {
            queue.offer(v3.iterator());
        }
    }

    public int next() {
        // poll one iterator from queue
        // then get next integer
        Iterator<Integer> active = queue.poll();
        Integer res = active.next();
        // after work, put iterator back to queue if hasnext
        if (active.hasNext()) {
            queue.offer(active);
        }
        return res;
    }

    public boolean hasNext() {
        if (!queue.isEmpty()) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        List<Integer> v1 = Arrays.asList(1,2,3);
        List<Integer> v2 = Arrays.asList(4,5,6,7);
        List<Integer> v3 = Arrays.asList(8,9);

        ZigzagIterator_281 zig = new ZigzagIterator_281(v1, v2, v3);
        while (zig.hasNext()) {
            System.out.print(zig.next() + ", ");
        }
    }
}

// for follow up, use array list to keep iterator of each list
class ZigzagIterator_solution2 {
    private int total = 2;
    private int active = 0;
    private List<Iterator<Integer>> iters;
    ZigzagIterator_solution2(List<Integer> v1, List<Integer> v2) {
        iters = new ArrayList<>();
        iters.add(v1.iterator());
        iters.add(v2.iterator());
    }

    int next() {
        int curr = active;
        active++;
        if (active == total) {
            active = 0;
        }
        return iters.get(curr).next();
    }

    public boolean hasNext() {
        if (iters.get(active).hasNext()) {
            return true;
        } else {
            for (int i = 1; i <= total; i++) {
                int next = (active + i) % total;
                if (iters.get(next).hasNext()) {
                    active = next;
                    return  true;
                }
            }
            return false;
        }
    }
}
