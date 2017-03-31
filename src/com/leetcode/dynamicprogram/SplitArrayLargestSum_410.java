package com.leetcode.dynamicprogram;

/**
 * Created by charles on 2/4/17.
 * Given an array which consists of non-negative integers and an integer m, you can split the array into m non-empty continuous subarrays. Write an algorithm to minimize the largest sum among these m subarrays.

 Note:
 If n is the length of array, assume the following constraints are satisfied:

 1 ≤ n ≤ 1000
 1 ≤ m ≤ min(50, n)
 Examples:

 Input:
 nums = [7,2,5,10,8]
 m = 2

 Output:
 18

 Explanation:
 There are four ways to split nums into two subarrays.
 The best way is to split it into [7,2,5] and [10,8],
 where the largest sum among the two subarrays is only 18.
 */
public class SplitArrayLargestSum_410 {
    /**
     * Solution One, general skeleton
     * 1. run a binary search on various maxsplit candidates between max of array and sum of array
     * 2. for each candidate you find among the binary search, check if it can lead to valid split of array into <= m portions
     * 3. find first maxLimit candidates for which there's a valid array split.
     *
     * Use greedy to narrow down left and right boundaries in binary search.
     3.1 Cut the array from left.
     3.2 Try our best to make sure that the sum of numbers between each two cuts (inclusive) is large enough but still less than mid.
     3.3 We'll end up with two results: either we can divide the array into more than m subarrays or we cannot.
     If we can, it means that the mid value we pick is too small because we've already tried our best to make sure each part holds as many non-negative numbers as we can but we still have numbers left. So, it is impossible to cut the array into m parts and make sure each parts is no larger than mid. We should increase m. This leads to l = mid + 1;
     If we can't, it is either we successfully divide the array into m parts and the sum of each part is less than mid, or we used up all numbers before we reach m. Both of them mean that we should lower mid because we need to find the minimum one. This leads to r = mid - 1;
     */
    public int splitArray(int[] nums, int m) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        long sum = 0; // sum of array
        int max = 0; // max of array
        for (int num : nums) {
            sum += num;
            max = Math.max(num, max);
        }
        if (m == 1) {
            return (int) sum;
        }
        // binary search
        long left = max;
        long right = sum;
        long mid = 0l;
        // below code block failed in case ({1, 2147483647}, 2)
        /*while (left + 1 < right) {
            mid = left + (right - left) / 2;
            if (isValid(mid, nums, m)) {
                right = mid;
            } else {
                left = mid;
            }
            System.out.println(String.format("left : %d, right %d, mid %d", left, right, mid));
        }
        return (int)right;*/
        while (left <= right) {
            mid = left + (right - left) / 2;
            if (isValid(mid, nums, m)) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
            System.out.println(String.format("left : %d, right %d, mid %d", left, right, mid));
        }
        return (int)left;
    }

    private boolean isValid(long target, int[] nums, int m) {
        int count = 1;
        long total = 0l;
        for (int num : nums) {
            total += num;
            if (total > target) {
                total = num;;
                count++;
                if (count > m) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        SplitArrayLargestSum_410 s = new SplitArrayLargestSum_410();
        int[] n1 = {7,2,5,10,8};
        s.splitArray(n1, 2);
        int[] n2 = {1, 2147483647};
        s.splitArray(n2, 2);
    }
}
