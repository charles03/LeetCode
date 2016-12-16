package com.leetcode.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by charles on 12/12/16.
 * Given an array of integers where 1 ≤ a[i] ≤ n (n = size of array), some elements appear twice and others appear once.
 Find all the elements of [1, n] inclusive that do not appear in this array.
 Could you do it without extra space and in O(n) runtime? You may assume the returned list does not count as extra space.
 Example:
 Input:
 [4,3,2,7,8,2,3,1]

 Output:
 [5,6]
 */
public class FindAllNumbersDisappearedInAnArray_448 {
    public List<Integer> findDisappeardNumbers(int[] nums) {
        List<Integer> res = new ArrayList<>();
        int len = 0;
        if (nums == null || (len = nums.length) == 0) {
            return res;
        }

        // in-place swap
        int temp = 0;
        for (int i = 0; i < len; i++) {
            while (nums[i] != i + 1 && nums[i] != nums[nums[i] - 1]) {
                temp = nums[i];
                nums[i] = nums[temp - 1];
                nums[temp - 1] = temp;
            }
        }
        for (int i = 0; i < len; i++) {
            if (nums[i] != i + 1) {
                res.add(i + 1);
            }
        }
        return res;
    }

    public List<Integer> findDisappeared(int[] nums) {
        List<Integer> res = new ArrayList<>();
        int len = 0;
        if (nums == null || (len = nums.length) == 0) {
            return res;
        }
        System.out.println("before " + Arrays.toString(nums));
        for (int i = 0; i < len; i++) {
            nums[(nums[i] - 1) % len] += len;
        }
        System.out.println("after " + Arrays.toString(nums));
        for (int i = 0; i < len; i++) {
            if (nums[i] <= len) {
                res.add(i + 1);
            } else {
                // revert back to original num array
                nums[i] %= len;
            }
        }
        System.out.println("after revert " + Arrays.toString(nums));
        return res;
    }

    public static void main(String[] args) {
        FindAllNumbersDisappearedInAnArray_448 f = new FindAllNumbersDisappearedInAnArray_448();
        int[] nums = {4,3,2,7,8,2,3,1};
//        System.out.println(f.findDisappeardNumbers(nums).toString());
        System.out.println(f.findDisappeared(nums).toString());
    }
}
