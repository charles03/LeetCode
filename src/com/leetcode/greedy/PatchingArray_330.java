package com.leetcode.greedy;

/**
 * Created by charles on 2/25/17.
 * Given a sorted positive integer array nums and an integer n, add/patch elements to the array such that any number in range [1, n] inclusive can be formed by the sum of some elements in the array. Return the minimum number of patches required.

 Example 1:
 nums = [1, 3], n = 6
 Return 1.

 Combinations of nums are [1], [3], [1,3], which form possible sums of: 1, 3, 4.
 Now if we add/patch 2 to nums, the combinations are: [1], [2], [3], [1,3], [2,3], [1,2,3].
 Possible sums are 1, 2, 3, 4, 5, 6, which now covers the range [1, 6].
 So we only need 1 patch.

 Example 2:
 nums = [1, 5, 10], n = 20
 Return 2.
 The two patches can be [2, 4].

 Example 3:
 nums = [1, 2, 2], n = 5
 Return 0.
 */
public class PatchingArray_330 {
    /**
     * var canReachTo records the maximal value that can be formed by element in nums and patched numbers
     * if canReachTo is less than nums[i] - 1 which means we need to patch a new number,
     * we then patch next number of current canReachTo
     */
    public int minPatches(int[] nums, int n) {
        long canReachTo = 0;
        int cnt = 0;
        int  i = 0;
        int len = nums.length;
        while (canReachTo < n) {
            if (i >= len || canReachTo < nums[i] - 1) {
                // update canReachTo after add new patched number
                canReachTo += canReachTo + 1;
                cnt++;
            } else {
                canReachTo += nums[i];
                i++;
            }
        }
        return cnt;
    }

    public static void main(String[] args) {
        PatchingArray_330 p = new PatchingArray_330();
        int[] n1 = {1,5,10};
        System.out.println(p.minPatches(n1, 20));
    }
}
