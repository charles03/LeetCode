package com.leetcode.divideconquer;

import javax.lang.model.element.NestingKind;

/**
 * Created by charles on 2/12/17.
 * Given an array of size n, find the majority element. The majority element is the element that appears more than ⌊ n/2 ⌋ times.

 You may assume that the array is non-empty and the majority element always exist in the array
 */
public class MajorityELement_169 {
    /**
     * use candidate var to record res
     * use count to filter candidate
     */
    public int majorityElement(int[] nums) {
        int count = 0;
        int candidate = -1;
        for (int i = 0; i < nums.length; i++) {
            if (count == 0) {
                candidate = nums[i];
                count = 1;
            } else if (candidate == nums[i]) {
                count++;
            } else {
                count--;
            }
        }
        return candidate;
    }

    /**
     * Bit manipulation
     */
    public int majorityElementByBit(int[] nums) {
        int[] bit = new int[32];

        for (int num : nums) {
            for (int i = 0; i < 32; i++) {
                if ((num >> (31-i) & 1) == 1) {
                    bit[i]++;
                }
            }
        }
        int res = 0;
        int majorityCount = nums.length / 2;
        for (int i = 0; i < 32; i++) {
            if (bit[i] > majorityCount) {
                bit[i] = 1;
            } else {
                bit[i] = 0;
            }
            res |= bit[i] << (31-i);
        }
        return res;
    }

    /**
     * Divide Conquer
     */
    public int majorityElementByDivideConquer(int[] nums) {
        if (nums.length == 0) {
            return nums[0];
        }
        return majorityElementByDivideConquer(nums, 0, nums.length - 1);
    }

    public int majorityElementByDivideConquer(int[] nums, int l, int r) {
        // base case
        if (l == r) {
            return nums[l];
        }
        int mid = l + (r-l) / 2;
        int leftMajority = majorityElementByDivideConquer(nums, l, mid);
        int rightMajority = majorityElementByDivideConquer(nums, mid + 1, r);
        if (leftMajority == rightMajority) {
            return leftMajority;
        }
        int half = (r - l + 1) / 2;
        if (countFreq(nums, l, r, leftMajority) > half) {
            return leftMajority;
        }
        return rightMajority;
    }

    private int countFreq(int[] nums, int l, int r, int target) {
        int freq = 0;
        for (int i = l; i <= r; i++) {
            if (nums[i] == target) {
                freq++;
            }
        }
        return freq;
    }

    public static void main(String[] args) {
        MajorityELement_169 m = new MajorityELement_169();
        int[] a1 = {1,-1,1,-2,-1,-1};
        System.out.println(m.majorityElement(a1) == m.majorityElementByBit(a1));
        System.out.println(m.majorityElement(a1) == m.majorityElementByDivideConquer(a1));
    }
}
