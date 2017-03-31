package com.leetcode.tree;

import java.util.Stack;

/**
 * Created by charles on 3/28/17.
 * Given two binary trees, write a function to check if they are equal or not.

 Two binary trees are considered equal if they are structurally identical and the nodes have the same value
 */
public class SameTree_100 {
    /**
     * Recursion, isSame(p.left, q.left) && isSame(p.right, q.right)
     */
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null) {
            return true;
        }
        if (p == null) {
            return false;
        }
        if (q == null) {
            return false;
        }
        if (p.val == q.val && isSameTree(p.left, q.left) && isSameTree(p.right, q.right)) {
            return true;
        }
        return false;
    }
    /** simplified recursion version */
    public boolean isSameTreeIII(TreeNode p, TreeNode q) {
        if (p == null && q == null) {
            return true;
        }
        if (p == null || q == null) {
            return false;
        }
        return p.val == q.val && isSameTreeIII(p.left, q.left) && isSameTreeIII(p.right, q.right);
    }

    /** Iteration solution */
    public boolean isSameTreeII(TreeNode p, TreeNode q) {
        if (p == null && q == null) {
            return true;
        }
        if (p == null || q == null) {
            return false;
        }
        Stack<TreeNode> stack1 = new Stack<>();
        Stack<TreeNode> stack2 = new Stack<>();
        stack1.push(p);
        stack2.push(q);

        TreeNode p1 = null;
        TreeNode p2 = null;
        while (!stack1.isEmpty() && !stack2.isEmpty()) {
            p1 = stack1.pop();
            p2 = stack2.pop();

            if (p1.val != p2.val) {
                return false;
            }
            // structure different
            if (p1.left == null && p2.left != null) {
                return false;
            } else if (p1.left != null && p2.left == null) {
                return false;
            } else if (p1.right == null && p2.right != null) {
                return false;
            } else if (p1.right != null && p2.right == null) {
                return false;
            }

            if (p1.left != null && p2.left != null) {
                stack1.push(p1.left);
                stack2.push(p2.left);
            }
            if (p1.right != null && p2.right != null) {
                stack1.push(p1.right);
                stack2.push(p2.right);
            }
        }
        return true;
    }
}
