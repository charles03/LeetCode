package com.leetcode.binarysearch;

import com.leetcode.greedy.PatchingArray_330;

import java.util.*;

/**
 * Created by charles on 3/8/17.
 * Given a set of intervals, for each of the interval i, check if there exists an interval j whose start point is bigger than or equal to the end point of the interval i, which can be called that j is on the "right" of i.

 For any interval i, you need to store the minimum interval j's index, which means that the interval j has the minimum start point to build the "right" relationship for interval i. If the interval j doesn't exist, store -1 for the interval i. Finally, you need output the stored value of each interval as an array.

 Note:
 You may assume the interval's end point is always bigger than its start point.
 You may assume none of these intervals have the same start point.
 Example 1:
 Input: [ [1,2] ]

 Output: [-1]

 Explanation: There is only one interval in the collection, so it outputs -1.
 Example 2:
 Input: [ [3,4], [2,3], [1,2] ]

 Output: [-1, 0, 1]

 Explanation: There is no satisfied "right" interval for [3,4].
 For [2,3], the interval [3,4] has minimum-"right" start point;
 For [1,2], the interval [2,3] has minimum-"right" start point.
 Example 3:
 Input: [ [1,4], [2,3], [3,4] ]

 Output: [-1, 2, -1]

 Explanation: There is no satisfied "right" interval for [1,4] and [3,4].
 For [2,3], the interval [3,4] has minimum-"right" start point.

 */
public class FindRightInterval_436 {
    /**
     * Binary Search Solution without TreeMap
     * sort starts,
     * For each end, find rightmost start using binary search
     * to get original index, we need a map
     */
    public int[] findRightInterval(Interval[] intervals) {
        Map<Integer, Integer> map = new HashMap<>();
        List<Integer> starts = new ArrayList<>();
        int len = intervals.length;
        for (int i = 0; i < len; i++) {
            map.put(intervals[i].start, i); // to keep original index of start
            starts.add(intervals[i].start);
        }
        Collections.sort(starts);
        int[] res = new int[len];

        for (int i = 0; i < len; i++) {
            int end = intervals[i].end;
            int start = binarySearch(starts, end);
            if (start < end) {
                res[i] = -1;
            } else {
                res[i] = map.get(start);
            }
        }
        return res;
    }

    int binarySearch(List<Integer> list,  int x) {
        int left = 0;
        int right = list.size() - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (list.get(mid) < x) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return list.get(left);
    }

    /**
     * TreeMap solution
     */
    public int[] findRightIntervalII(Interval[] intervals) {
        if (intervals == null) {
            return null;
        }
        int len = intervals.length;
        int[] res = new int[len];

        TreeMap<Integer, Integer> treeMap = new TreeMap<>();
        for (int i = 0; i < len; i++) {
            treeMap.put(intervals[i].start, i);
        }
        for (int i = 0; i < len; i++) {
            Integer right = treeMap.ceilingKey(intervals[i].end);
            if (right != null) {
                res[i] = treeMap.get(right);
            } else {
                res[i] = -1;
            }
        }
        return res;
    }
}

class Interval {
    int start, end;
    Interval() {
        start = 0;
        end = 0;
    }
    Interval(int start, int end) {
        this.start = start;
        this.end = end;
    }
}
