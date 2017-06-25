package com.leetcode.tree;

import java.util.Stack;

/**
 * Created by charles on 4/9/17.
 * Given a binary tree and a sum, determine if the tree has a root-to-leaf path such that adding up all the values along the path equals the given sum.

 For example:
 Given the below binary tree and sum = 22,
 5
 / \
 4   8
 /   / \
 11  13  4
 /  \      \
 7    2      1
 return true, as there exist a root-to-leaf path 5->4->11->2 which sum is 22.
 */
public class PathSum_112 {
    /**
     * Because premise is to find sum from root to leaf
     * if (current node is leaf) {
     *     when root.left == null && root.right == null
     *     return sum == root.val
     * }
     * backtrack
     * bool  sum of left tree || bool of sum of right tree
     * where target sum is prev sum - root.val
     */
    public boolean hasPathSum(TreeNode root, int sum) {
        if (root == null) {
            return false;
        }
        return helper(root, sum);
    }
    private boolean helper(TreeNode root, int target) {
        if (root == null) {
            return false;
        }
        if (root.left == null && root.right == null) {
            return target == root.val;
        }
        return helper(root.left, target - root.val) || helper(root.right, target - root.val);
    }

    /** iteration solution
     * use two Stacks, one is to simulate to keep track of tree node
     * the other is to record sub sum of each path until leaf */
    public boolean hashPathSumII(TreeNode root, int sum) {
        if (root == null) {
            return false;
        }
        Stack<TreeNode> path = new Stack<>();
        Stack<Integer> sums = new Stack<>();
        path.push(root);
        sums.push(root.val);

        TreeNode node = null;
        int sub = 0;
        while (!path.isEmpty()) {
            node = path.pop();
            sub = sums.pop();
            // check if node is leaf node in the tree
            if (node.left == null && node.right == null) {
                // then check current sub sum is target sum
                if (sub == sum) {
                    return true;
                }
            } else {
                if (node.left != null) {
                    path.push(node.left);
                    sums.push(node.left.val + sub);
                }
                if (node.right != null) {
                    path.push(node.right);
                    sums.push(node.right.val + sub);
                }
            }
        }
        return false;
    }

    /** better solution, use backtrack thought in stack of value sum */
    public boolean hasPathSumIII(TreeNode root, int sum) {
        if (root == null) {
            return false;
        }
        Stack<TreeNode> stack = new Stack<>();
        Stack<Integer> sums = new Stack<>();

        stack.push(root);
        sums.push(sum); // push target sum at the beginning
        int target = 0;
        TreeNode node = null;

        while (!stack.isEmpty()) {
            target = sums.pop();
            node = stack.pop();

            // if node is leaf
            if (node.left == null && node.right == null && node.val == target) {
                return true;
            }
            /** for this question, sequence of left or right isn't matter */
            if (node.right != null) {
                stack.push(node.right);
                sums.push(target - node.val);
            }
            if (node.left != null) {
                stack.push(node.left);
                sums.push(target - node.val);
            }
        }
        return false;
    }
}
