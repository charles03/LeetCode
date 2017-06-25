package com.leetcode.twopointer;

/**
 * Created by charles on 4/16/17.
 * Given an array with n objects colored red, white or blue, sort them so that objects of the same color are adjacent, with the colors in the order red, white and blue.

 Here, we will use the integers 0, 1, and 2 to represent the color red, white, and blue respectively.
 Note:
 You are not suppose to use the library's sort function for this problem.

 click to show follow up.

 Follow up:
 A rather straight forward solution is a two-pass algorithm using counting sort.
 First, iterate the array counting number of 0's, 1's, and 2's, then overwrite array with total number of 0's, then 1's and followed by 2's.

 Could you come up with an one-pass algorithm using only constant space?
 */
public class SortColors_75 {
    /** two-pass solution */
    public void sortColors(int[] nums) {
        if (nums == null || nums.length == 0) {
            return;
        }
        int c0 = 0, c1 = 0, c2 = 0;

        for (int i : nums) {
            switch (i) {
                case 0: c0++;
                    break;
                case 1: c1++;
                    break;
                case 2: c2++;
                    break;
            }
        }
        for (int i = 0; i < nums.length; i++) {
            if (i < c0) {
                nums[i] = 0;
            } else if (i < c0 + c1) {
                nums[i] = 1;
            } else {
                nums[i] = 2;
            }
        }
    }
    /** one pass solution
     *  thought: i0 for index of 0, i2 for index of 2
     *  loop array,
     *  nums[i] == 0, then swap with current index of 0(from front);
     *      if (i0 == i) {
     *          increment both
     *      }
     *  nums[i] == 2, then swap with current index of 2(from tail);
     *      if (i == i2) {
     *          increment i
     *          decrement i2
     *      }
     *  else when nums[i] == 1: increment i only
     * */
    public void sortColorsII(int[] nums) {
        int len = nums.length;
        int i0 = 0, i2 = len - 1;
        int i = 0;
        while (i < len) {
            if (nums[i] == 0) {
                if (i0 != i) {
                    swap(nums, i0, i);
                    i0++;
                } else {
                    i++;
                    i0++;
                }
            } else if (nums[i] == 2) {
                if (i < i2) {
                    swap(nums, i, i2);
                    i2--;
                } else {
                    i2--;
                    i++;
                }
            } else {
                i++;
            }
        }
    }
    private void swap(int[] nums, int a, int b) {
        int tmp = nums[a];
        nums[a] = nums[b];
        nums[b] = tmp;
    }

    /**
     * one-pass simpler solution
     * move point with index,
     * when nums[index] == 0, swap value between index and index of 0
     * copy nums[p0] into nums[index] no matter whether index == p0
     * and then set nums[p0] = 0;
     * increment p0;
     *
     * as for index of 2 (start from tail)
     * when nums[index] == 2, swap value between index and index of 2
     * besides decrement p2, we need decrement index as well;
     * because outside of if block, we increment index unitedly.
     */
    public void sortColorsIII(int[] nums) {
        int p0 = 0, p2 = nums.length - 1, index = 0;
        while (index < p2) {
            if (nums[index] == 0) {
                nums[index] = nums[p0];
                nums[p0] = 0;
                p0++;
            }
            if (nums[index] == 2) {
                nums[index] = nums[p2];
                nums[p2] = 2;
                p2--;
                index--; // in order to simplify if condition;
            }
            index++;
        }
    }

}
