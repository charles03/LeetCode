package com.leetcode.array;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by charles on 12/17/16.
 * Given an array of integers, find if the array contains any duplicates.
 * Your function should return true if any value appears at least twice in the array, and it should return false if every element is distinct.
 */
public class ContainsDuplicate_217 {

    private Set<Integer> set = new HashSet<>();

    public boolean containsDuplicate(int[] nums) {
        if (nums == null || nums.length == 0) {
            return false;
        }
        int len = nums.length;
        for (int i = 0; i < len; i++) {
            if (!set.add(nums[i])) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        ContainsDuplicate_217 c = new ContainsDuplicate_217();
        int[] nums = {1,2,3,1};
        System.out.println(c.containsDuplicate(nums));
    }
}
