package com.leetcode.tree;

import java.util.Stack;

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
        if (start > end) { // case when to end recursion
            return null;
        }
        int mid = start + (end - start) / 2;
        TreeNode root = new TreeNode(nums[mid]);
        // recursion
        root.left = bstHelper(nums, start, mid - 1);
        root.right = bstHelper(nums, mid + 1, end);
        return root;
    }

    /**
     * Iteration solution:
     * instead of using 3 stacks, wrap necessary info into data type
     * and then pre-order traversal of tree
     */
    public TreeNode sortedArrayToBSTII(int[] nums) {
        if (nums == null || nums.length == 0) {
            return null;
        }
        Stack<WrapperNode> stack = new Stack<>();
        int mid = (nums.length - 1 - 0) / 2;
        TreeNode node = new TreeNode(nums[mid]);
        stack.push(new WrapperNode(node, 0, nums.length - 1));

        int end = 0;
        int start = 0;
        while (!stack.isEmpty()) {
            WrapperNode curr = stack.pop();
            int currPos = curr.start + (curr.end - curr.start) / 2;
            /** left tree */
            start = curr.start;
            end = currPos - 1;
            if (start <= end) {
                mid = start + (end - start) / 2;
                node = new TreeNode(nums[mid]);
                stack.push(new WrapperNode(node, start, end));
                curr.node.left = node;
            }
            /** right tree */
            start = currPos + 1;
            end = curr.end;
            if (start <= end) {
                mid = start + (end - start) / 2;
                node = new TreeNode(nums[mid]);
                stack.push(new WrapperNode(node, start, end));
                curr.node.right = node;
            }
        }
        return node;
    }

    private class WrapperNode {
        TreeNode node;
        int start;
        int end;

        public WrapperNode(TreeNode node, int start, int end) {
            this.node = node;
            this.start = start;
            this.end = end;
        }
    }
}
