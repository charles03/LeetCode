package com.leetcode.twopointer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by charles on 4/12/17.
 * Given an array of integers and an integer k, you need to find the number of unique k-diff pairs in the array. Here a k-diff pair is defined as an integer pair (i, j), where i and j are both numbers in the array and their absolute difference is k.

 Example 1:
 Input: [3, 1, 4, 1, 5], k = 2
 Output: 2
 Explanation: There are two 2-diff pairs in the array, (1, 3) and (3, 5).
 Although we have two 1s in the input, we should only return the number of unique pairs.
 Example 2:
 Input:[1, 2, 3, 4, 5], k = 1
 Output: 4
 Explanation: There are four 1-diff pairs in the array, (1, 2), (2, 3), (3, 4) and (4, 5).
 Example 3:
 Input: [1, 3, 1, 5, 4], k = 0
 Output: 1
 Explanation: There is one 0-diff pair in the array, (1, 1).
 Note:
 The pairs (i, j) and (j, i) count as the same pair.
 The length of the array won't exceed 10,000.
 All the integers in the given input belong to the range: [-1e7, 1e7].

 */
public class KdiffPairsInArray_532 {
    /**
     * naive solution:
     * Sort array first, from small to large
     * then use two pointers,
     */
    public int findPairs(int[] nums, int k) {
        if (nums == null || nums.length < 2) {
            return 0;
        }
        Arrays.sort(nums);
        int start = 0;
        int end = 1;
        int len = nums.length;
        int count = 0;
        int diff = 0;
        while (start < len && end < len) {
            diff = nums[end] - nums[start];
            if (diff == k) {
                count++;
                // skip all dup at right pointer
                end = end + 1;
                while (end < len && nums[end] == nums[end - 1]) {
                    end++;
                }
            } else if (diff < k) {
                end++;
            } else {
                // skip all dup at left pointer
                start = start + 1;
                while (start < len && nums[start] == nums[start - 1]) {
                    start++;
                }
                end = start + 1;
            }
        }
        return count;
    }

    public int findPairsII(int[] nums, int k) {
        if (nums == null || nums.length < 2 || k < 0) {
            return 0;
        }
        // key: unique num in array, val: count of appearance.
        Map<Integer, Integer> map = new HashMap<>();
        int count = 0;
        for (int i : nums) {
            map.put(i, map.getOrDefault(i, 0) + 1);
        }

        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (k == 0) {
                // if element in array appear equal or more than twice
                if (entry.getValue() >= 2) {
                    count++;
                }
            } else {
                if (map.containsKey(entry.getKey() + k)) {
                    count++;
                }
            }
        }
        return count;
    }

    public static void main(String[] args) {
        int[] n1 = {3,1,4,1,5};
        KdiffPairsInArray_532 k = new KdiffPairsInArray_532();
        System.out.println(k.findPairs(n1, 2));
    }
}
