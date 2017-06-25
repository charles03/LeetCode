package com.leetcode.twopointer;

import java.util.Arrays;

/**
 * Created by charles on 4/16/17.
 * Follow up for {@link com.leetcode.array.RemoveDuplicatesFromSortedArray_26}:
 What if duplicates are allowed at most twice?

 For example,
 Given sorted array nums = [1,1,1,2,2,3],

 Your function should return length = 5, with the first five elements of nums being 1, 1, 2, 2 and 3. It doesn't matter what you leave beyond the new length.
 */
public class RemoveDuplicatesFromSortedArrayII_80 {
    /**
     * can allow at most twice.
     * should modify array, so that final array as [11223] instead of only return len;
     *
     */
    public int removeDuplicates(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int j = 1;
        int len = nums.length;
        if (len < 2) {
            return 1;
        }
        int idx = 0;
        for (int i = 0; i < len; i++) {
            while (j < len && nums[i] == nums[j]) {
                j++;
            }
            if (j - i >= 2) {
                nums[idx] = nums[i];
                nums[idx + 1] = nums[i];
                idx += 2;
            } else {
                nums[idx] = nums[i];
                idx++;
            }
            i = j-1;
        }
        return idx;
    }

    /** more general solution for allowing duplicates to appear k times
     * we need a count variable to keep how many times the duplicated element appears,
     * if we encounter a different element, just set counter to 1, if we encounter a duplicated one, we need to check this count, if it is already k,
     * then we need to skip it, otherwise, we can keep this element.*/
    public int removeDuplicatesII(int[] nums, int n, int k) {
        if (n < k) {
            return n;
        }
        int i = 1, j = 1;
        int cnt = 1;
        while (j < n) {
            if (nums[j] != nums[j-1]) {
                cnt = 1;
                nums[i++] = nums[j];
            } else {
                if (cnt < k) {
                    nums[i++] = nums[j];
                    cnt++;
                }
            }
            j++;
        }
        return i;
    }

    public static void main(String[] args) {
        RemoveDuplicatesFromSortedArrayII_80 r = new RemoveDuplicatesFromSortedArrayII_80();
        int[] a1 = {1,1,1,2,2,3};
        System.out.println(r.removeDuplicates(a1));
        output(a1);
    }
    private static void output(int[] arr) {
        Arrays.stream(arr).forEach(t->System.out.print(t + ","));
        System.out.println();
    }
}
