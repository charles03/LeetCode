package com.leetcode.dynamicprogram;

import javax.swing.plaf.metal.MetalTheme;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Created by charles on 2/23/17.
 * {@link com.leetcode.greedy.NonOverlappingInterval_435}
 */
public class NonOverlappingInterval_435 {
    /**
     * DP based on starting point
     * State : max number of valid intervals that can be included ending at index i
     * function : dp[i] = max(dp[j]) + 1;
     *      such that jth interval and ith don't overlap for all j <= i;
     * Init dp[o] = 1;
     * Answer, final result will be total number of intervals given less the result just obtained (interval.len - ans)
     */
    public int eraseOverlapIntervals(Interval[] intervals) {
        if (intervals.length == 0) {
            return 0;
        }
        Arrays.sort(intervals, new StartComparator());
        int len = intervals.length;
        int[] dp = new int[len];
        dp[0] = 1;
        int ans = 1;

        for (int i = 1; i < len; i++) {
            int max = 0;
            // loop back from current interval to prev intervals
            for (int j = i - 1; j >= 0; j--) {
                if (!isOverlapping(intervals[j], intervals[i])) {
                    max = Math.max(max, dp[j]);
                }
            }
            dp[i] = max + 1;
            ans = Math.max(ans, dp[i]);
        }
        return len - ans;
    }

    public boolean isOverlapping(Interval left, Interval right) {
        return left.end > right.start;
    }

    class StartComparator implements Comparator<Interval> {
        @Override
        public int compare(Interval o1, Interval o2) {
            return o1.start - o2.start;
        }
    }

    /**
     * Approach based on ending point
     * State, similar to above
     * Function: case 1 : when current interval i+1 need to be included
     *          dp[i] = Math.max(max, dp[j]) + 1
     *          need not go back till the starting index everytime, we can stop traversal as soon as hit first non-overlapping
     *          interval and use dp[j] + 1 to fill dp[i+1[
     *
     *          Case 2 : when current interval i+1 need to be removed
     *          dp[i] = dp[i-1[
     *
     *          dp[i] = Math.max(case1 , case2)
     * init : dp[o] = 1;
     * answer : math.max(ans, dp[i])
     */
    public int eraseOverlapIntervalsII(Interval[] intervals) {
        if (intervals.length == 0) {
            return 0;
        }
        Arrays.sort(intervals, new EndComparator());
        int len = intervals.length;
        int[] dp = new int[len];
        dp[0] = 1;
        int ans = 1;

        for (int i = 1; i < len; i++) {
            int max = 0;
            for (int j = i - 1; j >= 0; j--) {
                if (!isOverlapping(intervals[j], intervals[i])) {
                    max = Math.max(max, dp[j]);
                    break; // what if without break?
                }
            }
            dp[i] = Math.max(max + 1, dp[i-1]);
            ans = Math.max(ans, dp[i]);
        }
        return len - ans;
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
        start = 0;
        end = 0;
    }
    Interval(int start, int end) {
        this.start = start;
        this.end = end;
    }
}