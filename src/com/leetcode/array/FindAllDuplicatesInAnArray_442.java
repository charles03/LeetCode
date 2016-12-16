package com.leetcode.array;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by charles on 12/13/16.
 *Given an array of integers, 1 ≤ a[i] ≤ n (n = size of array), some elements appear twice and others appear once.
 Find all the elements that appear twice in this array.
 Could you do it without extra space and in O(n) runtime?

 Example:
 Input:
 [4,3,2,7,8,2,3,1]
 Output:
 [2,3]
 */
public class FindAllDuplicatesInAnArray_442 {
    public List<Integer> findDuplicates(int[] nums) {
        List<Integer> res = new ArrayList<>();
        if (nums == null || nums.length == 0) {
            return res;
        }
        int len = nums.length;

        for (int i = 0; i < len; i++) {
            nums[(nums[i] - 1) % len] += len;
        }
        for (int i = 0; i < len; i++) {
            if ((nums[i] - 1) / len >= 2) {
                res.add(i + 1);
            }
        }
        return res;
    }
    public static void main(String[] args) {
        FindAllDuplicatesInAnArray_442 f = new FindAllDuplicatesInAnArray_442();
        int[] nums = {4,3,2,7,8,2,3,1};
        System.out.println(f.findDuplicates(nums).toString());
    }
}
