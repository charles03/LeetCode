package com.leetcode.backtrack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by charles on 1/20/17.
 * Given a collection of integers that might contain duplicates, nums, return all possible subsets.
 * If nums = [1,2,2], a solution is:

 [
 [2],
 [1],
 [1,2,2],
 [2,2],
 [1,2],
 []
 ]
 */
public class SubSetII_90 {
    public List<List<Integer>> subsetsWithDup(int[] nums) {

        List<List<Integer>> res = new ArrayList<>();
        if (nums == null || nums.length == 0) {
            return res;
        }
        // sort array first to resolve dup
        Arrays.sort(nums);
        dfsHelper(res, new ArrayList<Integer>(), 0, nums);
        return res;
    }

    private void dfsHelper(List<List<Integer>> res, List<Integer> path, int idx, int[] nums) {
        res.add(path);

        for (int i = idx; i < nums.length; i++) {
            if (i > idx && nums[i] == nums[i - 1]) {
                continue;
            }
            List<Integer> newPath = new ArrayList<>(path);
            newPath.add(nums[i]);
            dfsHelper(res, newPath, i + 1, nums);
        }
    }
    /** link to help understand why list should copy into new list before add */
    private void dfsHelperJiuzhang(List<List<Integer>> res, List<Integer> subset, int idx, int[] nums) {
        res.add(new ArrayList<>(subset));

        for (int i = idx; i < nums.length; i++) {
            if (i > idx && nums[i] == nums[i - 1]) { // only need i > idx
                continue;
            }
            subset.add(nums[i]); // add new for dfs
            dfsHelperJiuzhang(res, subset, i + 1, nums);
            subset.remove(subset.size() - 1); // when return from last call, should pop out result
        }
    }
    /** http://www.jiuzhang.com/qa/663/ */
    public void toUnderstandReferene() {
        List<List<Integer>> test = new ArrayList<>();
        List<Integer> l1 = new ArrayList<>();
        l1.add(1);
        test.add(l1);
        l1.add(2);
        test.add(l1);

        // we wish result should be [[1], [1,2]]
        // inverse, it it [[1,2], [1,2]]

        /** because as for Test list, var l1 is copy to be added into test
         * Test list doesn't care what specific data is in l1 list
         * if want Test know the change of l1
         * should make a copy of l1 and add into test
         */
    }

}
