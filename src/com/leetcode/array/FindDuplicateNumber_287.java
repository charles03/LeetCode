package com.leetcode.array;

/**
 * Created by charles on 12/15/16.
 * Given an array nums containing n + 1 integers where each integer is between 1 and n (inclusive), prove that at least one duplicate number must exist. Assume that there is only one duplicate number, find the duplicate one.

 Note:
 You must not modify the array (assume the array is read only).
 You must use only constant, O(1) extra space.
 Your runtime complexity should be less than O(n2).
 There is only one duplicate number in the array, but it could be repeated more than once.
 */
public class FindDuplicateNumber_287 {
    /**
     * Thought:
     * similar to find entry node of recycle of linked list
     *
     * 4 -> 5 -> 6 -> 1 -> 2 -> 3
     *                |-<- 7 <-|
     * Define from start to join entry  -> len is l
     * Define from join entry to first met (slow and fast) -> len is x
     * Then from first met to join entry -> len is l
     * because fast go with 2(l + x), and from start to first met,
     * fast already go with l + 2x, then from first met to join entry is l
     * so reassign fast back to start, go one step by step.
     * then join entry is what we look for
     */
    public int findDuplicate(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return -1;
        }
        int slow = nums[0];
        int fast = nums[nums[0]];

        while (slow != fast) {
            System.out.println("slow " + slow + " -> " + nums[slow]);
            slow = nums[slow];
            System.out.println("fast " + fast + " -> " + nums[nums[fast]]);
            fast = nums[nums[fast]];
        }
        fast = 0;
        while (slow != fast) {
            System.out.println("slow " + slow + " -> " + nums[slow]);
            System.out.println("fast " + fast + " -> " + nums[fast]);
            slow = nums[slow];
            fast = nums[fast];
        }
        return slow;
    }

    public static void main(String[] args) {
        FindDuplicateNumber_287 f = new FindDuplicateNumber_287();
        int[] nums = {4,2,1,3,1,6,7,1,5};
        System.out.println(f.findDuplicate(nums));
    }
}
