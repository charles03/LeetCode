package com.leetcode.array;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by charles on 12/16/16.
 * Given an array of integers and an integer k,
 * find out whether there are two distinct indices i and j in the array such that nums[i] = nums[j]
 * and the difference between i and j is at most k.
 */
public class ContainsDuplicateII_219 {
    /**
     * Thought
     * Explanation: It iterates over the array using a sliding window.
     * The front of the window is at i, the rear of the window is k steps back.
     * The elements within that window are maintained using a set.
     * While adding new element to the set, if add() returns false,
     * it means the element already exists in the set.
     * At that point, we return true.
     * If the control reaches out of for loop,
     * it means that inner return true never executed,
     * meaning no such duplicate element was found.
     */

    public boolean containsNearbyDuplicate(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return false;
        }
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < nums.length ; i++) {
            // out of sliding window
            if (i > k) {
                set.remove(nums[i - k - 1]);
            }
            // when add return false, it means element already exist
            if (!set.add(nums[i])) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        ContainsDuplicateII_219 c = new ContainsDuplicateII_219();
        int[] nums = {1,2,3,1,4};
        System.out.println(c.containsNearbyDuplicate(nums, 2));
    }
}
