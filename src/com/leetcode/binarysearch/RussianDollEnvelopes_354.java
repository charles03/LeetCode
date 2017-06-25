package com.leetcode.binarysearch;

import java.util.Arrays;

/**
 * Created by charles on 4/22/17.
 * You have a number of envelopes with widths and heights given as a pair of integers (w, h). One envelope can fit into another if and only if both the width and height of one envelope is greater than the width and height of the other envelope.

 What is the maximum number of envelopes can you Russian doll? (put one inside other)

 Example:
 Given envelopes = [[5,4],[6,4],[6,7],[2,3]], the maximum number of envelopes you can Russian doll is 3 ([2,3] => [5,4] => [6,7]).
 */
public class RussianDollEnvelopes_354 {
    /**
     * Sort array in order (width Asc and height Desc if width is same)
     * Find {@link com.leetcode.dynamicprogram.LongestIncreasingSubsequence_300} based on height
     * by DP or Binary search
     *
     * Since the width is increasing, only need to consider height
     * [3,4] cannot contain [3,3], so that need to put [3,4] before [3,3] when sorting
     * otherwise it will be counted as an increasing number if order is [3,3],[3,4]
     */
    public int maxEnvelope(int[][] envelopes) {
        if (envelopes == null || envelopes.length == 0
                || envelopes[0] == null || envelopes[0].length != 2) {
            return 0;
        }
        /**
         * comparator ascend sort, is to return a.val - b.val (imposed)
         * descend sort is to return b.val - a.val
         */
        Arrays.sort(envelopes, (a,b) -> {return a[0] == b[0] ? b[1] - a[1] : a[0] - b[0];});
        // look for longest increasing subsequence by height
        int[] dp = new int[envelopes.length];
        int res = 0;
        int insertIdx = 0;
        for (int[] envelope : envelopes) {
            insertIdx = Arrays.binarySearch(dp, 0, res, envelope[1]);
            if (insertIdx < 0) {
                insertIdx = -(insertIdx + 1);
            }
            // if afterward num is smaller than previous
            // afterward num will replace with current one
            dp[insertIdx] = envelope[1];
            if (insertIdx == res) { // increase length because already find one more valid case
                res++;
            }
        }
        return res;
    }
}
