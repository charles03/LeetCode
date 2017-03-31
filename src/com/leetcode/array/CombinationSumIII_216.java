package com.leetcode.array;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by charles on 12/17/16.
 * Find all possible combinations of k numbers that add up to a number n,
 * given that only numbers from 1 to 9 can be used and each combination should be a unique set of numbers
 */
public class CombinationSumIII_216 {
    /**
     * Thought:
     * use BackTracking (recursion) to find result
     */
    public List<List<Integer>> combinationSum3(int k, int n) {
        List<List<Integer>> res = new ArrayList<>();
        combine(res, new ArrayList<Integer>(), k, 1, n);
        return res;
    }

    private void combine(List<List<Integer>> res,
                         List<Integer> list,
                         int k,
                         int start,
                         int remainder) {
        if (list.size() == k && remainder == 0) {
            // must to wrap last result from recursion into new list after creating new instance
            // otherwise only add empty list
            res.add(new ArrayList<Integer>(list));
            return;
        }
        // traversing all possibility from start to 9
        for (int i = start; i <= 9; i++) {
            list.add(i);
            // recursively to try this new added number
            combine(res, list, k, i+1, remainder - i);
            // after recursion, must remove last added from temp list
            list.remove(list.size() - 1);
        }
    }

    public static void main(String[] args) {
        CombinationSumIII_216 c = new CombinationSumIII_216();
        print(c.combinationSum3(4, 10));
    }

    private static void print(List<List<Integer>> res) {
        for (List<Integer> l : res) {
            l.stream().forEach(t -> System.out.print(t + ","));
            System.out.println("");
        }
    }
}
