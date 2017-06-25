package com.leetcode.dfs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by charles on 6/15/17.
 * Given an integer array, your task is to find all the different possible increasing subsequences of the given array, and the length of an increasing subsequence should be at least 2 .

 Example:
 Input: [4, 6, 7, 7]
 Output: [[4, 6], [4, 7], [4, 6, 7], [4, 6, 7, 7], [6, 7], [6, 7, 7], [7,7], [4,7,7]]
 Note:
 The length of the given array will not exceed 15.
 The range of integer in the given array is [-100,100].
 The given array may contain duplicates, and two equal integers should also be considered as a special case of increasing sequence.

 */
public class IncreasingSubsequence_491 {

    /**
     * because result set should be no duplicate, so use Set<List<Integer>> as container
     * to store unique each case
     */
    public List<List<Integer>> findSubsequences(int[] nums) {
        Set<List<Integer>> set = new HashSet<>();
        if (nums == null || nums.length == 0) {
            return new ArrayList<>();
        }
        dfs(set, nums, new ArrayList<Integer>(), 0);
        // convert set to list
        return new ArrayList<>(set);
    }
    private void dfs(Set<List<Integer>> res, int[] nums, List<Integer> holder, int start) {
        if (holder.size() >= 2) {
            res.add(new ArrayList<>(holder));
        }
        for (int i = start; i < nums.length; i++) {
            // if matching condition, then add into holder
            if (holder.size() == 0
                    || holder.get(holder.size() - 1) <= nums[i]) {
                holder.add(nums[i]);
                // go to dfs search
                dfs(res, nums, holder, i+1);
                // remove last added value after return from recursion
                holder.remove(holder.size() - 1);
            }
        }
    }

    /**
     * use isDuplicate func to skip unnecessary recursion
     */
    public List<List<Integer>> findSubsequencesII(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> each = new ArrayList<>();
        dfsII(nums, res, each, 0);
        return res;
    }
    private void dfsII(int[] nums, List<List<Integer>> res, List<Integer> curr, int start) {
        if (curr.size() > 1) {
            res.add(new ArrayList<>(curr));
        }
        /**
         * thought:
         * in the same horizontal dfs tree level, same elem can only appear once
         * in the same vertical dfs tree branch, same elem can appear multiple times
         */
        for (int i = start; i < nums.length; i++) {
            // check same elem in horizontal dfs tree level
            if (i > start && isContainsDuplicate(nums, start, i)) {
                // if exist certain elem same as nums[i], in the range [start, i]
                // then skip current index i; move to next;
                continue;
            }
            if (curr.size() > 0 && nums[i] < curr.get(curr.size() - 1)) {
                // current holder is not empty, or current nums[i] small than last elem in current holder
                continue;
            }
            curr.add(nums[i]);
            dfsII(nums, res, curr, i+1);
            curr.remove(curr.size() - 1);
        }
    }
    private boolean isContainsDuplicate(int[] nums, int start, int index) {
        int target = nums[index];
        for (int i = start; i < index; i++) {
            if (nums[i] == target) {
                return true;
            }
        }
        return false;
    }
}
