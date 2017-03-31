package com.leetcode.greedy;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Created by charles on 2/23/17.
 * Given a collection of intervals, find the minimum number of intervals you need to remove to make the rest of the intervals non-overlapping.

 Note:
 You may assume the interval's end point is always bigger than its start point.
 Intervals like [1,2] and [2,3] have borders "touching" but they don't overlap each other.
 Example 1:
 Input: [ [1,2], [2,3], [3,4], [1,3] ]

 Output: 1

 Explanation: [1,3] can be removed and the rest of intervals are non-overlapping.
 Example 2:
 Input: [ [1,2], [1,2], [1,2] ]

 Output: 2

 Explanation: You need to remove two [1,2] to make the rest of intervals non-overlapping.
 Example 3:
 Input: [ [1,2], [2,3] ]

 Output: 0

 Explanation: You don't need to remove any of the intervals since they're already non-overlappi
 */
public class NonOverlappingInterval_435 {
    /**
     * Greedy Solution based on starting points
     * 1. sort intervals by start point
     * three cases
     * case 1: no overlap between prev and curr; -> take later interval
     * case 2: prev completely cover curr; -> take later interval, need to remove one
     * case 3: prev has partial overlap with curr -> take prev interval, remove later
     */
    public int eraseOverlapIntervals(Interval[] intervals) {
        if (intervals.length == 0) {
            return 0;
        }
        Arrays.sort(intervals, new StartComparator());
        int end = intervals[0].end;
        int prev = 0;
        int removeCount = 0;
        for (int i = 1; i < intervals.length; i++) {
            if (intervals[prev].end > intervals[i].start) {
                if (intervals[prev].end > intervals[i].end) {
                    // case 2, take later interval, update prev to i
                    prev = i;
                }
                // case 2 and 3;
                removeCount++;
            } else {
                prev = i; // case 1, no remove, only update prev to i
            }
        }
        return removeCount;
    }

    /**
     * Greedy Solution based on ending point
     * case 1 : no overlap
     * case 2 : prev.start > curr.start -> remove later curr, update prev to i
     * case 3 : prev.end > curr.start -> remove later curr,
     */
    public int eraseOverlapIntervalII(Interval[] intervals) {
        if (intervals.length == 0) {
            return 0;
        }
        Arrays.sort(intervals, new EndComparator());
        int end = intervals[0].end;
        int remainCount = 1;
        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i].start >= end) {
                // case 1;
                end = intervals[i].end;
                remainCount++;
            }
            // else case 2 and 3
        }
        // final result is len of interval - count of remain unchanged
        return intervals.length - remainCount;
    }

    class StartComparator implements Comparator<Interval> {

        @Override
        public int compare(Interval o1, Interval o2) {
            return o1.start - o2.start;
        }
    }
    class EndComparator implements Comparator<Interval> {

        @Override
        public int compare(Interval o1, Interval o2) {
            return o1.end - o2.end;
        }
    }

}

class Interval {
    int start;
    int end;
    Interval() {
        this.start = 0;
        this.end = 0;
    }
    Interval(int start, int end) {
        this.start = start;
        this.end = end;
    }
}
