package com.leetcode.array;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by charles on 12/15/16.
 * Given an integer array of size n, find all elements that appear more than ⌊ n/3 ⌋ times. The algorithm should run in linear time and in O(1) space.
 *
 * Boyer-Moore Majority Vote algorithm
 * https://gregable.com/2013/10/majority-vote-algorithm-find-majority.html
 *
 * it requires 2 passes over the input list
 * in first pass, generate candidate value which is majority value if there is a majority
 * second pass simply counts the frequency of value to confirm
 *
 */
public class MajorityElementII_229 {
    public List<Integer> majorityElement(int[] nums) {
        List<Integer> res = new ArrayList<>();
        if (nums == null || nums.length == 0) {
            return res;
        }
        int firstCandidate = 0, secondCandidate = 0; // two candidates
        int firstCount = 0, secondCount = 0; // count for candidate
        // first pass to find two candidates for majorities n/3
        for (int num : nums) {
            if (num == firstCandidate) {
                firstCount++;
            } else if (num == secondCandidate) {
                secondCount++;
            } else if (firstCount == 0) {
                firstCandidate = num;
                firstCount = 1; // reset first count or second count to 1
            } else if (secondCount == 0) {
                secondCandidate = num;
                secondCount = 1;
            } else {
                firstCount--;
                secondCount--;
            }
        }
        // second pass to verify candidates
        firstCount = 0;
        secondCount = 0;
        System.out.println(String.format("candidate 1 : %s, candidate 2 : %s", firstCandidate, secondCandidate));
        for (int num: nums) {
            if (num == firstCandidate) {
                firstCount++;
            } else if (num == secondCandidate) { // still need elseif instead if under case {0, 0, 0}
                secondCount++;
            }
        }
        if (firstCount > nums.length / 3) {
            res.add(firstCandidate);
        }
        if (secondCount > nums.length / 3) {
            res.add(secondCandidate);
        }
        return res;
    }

    public static void main(String[] args) {
        MajorityElementII_229 m = new MajorityElementII_229();
        int[] nums = {1,2,3,2,2,3,3,3,3,3,1};
        print(m.majorityElement(nums));
    }

    private static void print(List<Integer> list) {
        list.stream().forEach(t -> System.out.println(t + ","));
    }
}
