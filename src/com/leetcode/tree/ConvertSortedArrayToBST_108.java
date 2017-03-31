package com.leetcode.tree;

/**
 * Created by charles on 3/30/17.
 * Given an array where elements are sorted in ascending order, convert it to a height balanced BST.

 */
public class ConvertSortedArrayToBST_108 {
    /**
     * Thought:
     * BST, left < mid < right
     * because looking for balance height BST,
     * then split array from center, the number at mid index as root of tree
     */
    public TreeNode sortedArrayToBST(int[] nums) {
        if (nums == null || nums.length == 0) {
            return null;
        }
        return bstHelper(nums, 0, nums.length - 1);
    }

    private TreeNode bstHelper(int[] nums, int start, int end) {
        TreeNode node = null;
        int mid = 0;
        if (start != end) {
            mid = start + (end - start) / 2;
            node = new TreeNode(nums[mid]);
            node.left = bstHelper(nums, 0, mid);
            node.right = bstHelper(nums, mid + 1, end);
        } else {
            node = new TreeNode(nums[start]);
            node.left = null;
            node.right = null;
        }
        return node;
    }
}
