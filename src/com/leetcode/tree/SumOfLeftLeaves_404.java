package com.leetcode.tree;

import java.util.Stack;

/**
 * Created by charles on 1/3/17.
 * Find the sum of all left leaves in a given binary tree
 */
public class SumOfLeftLeaves_404 {

    public int sumOfLeftLeavesRecursion(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int ans = 0;
        if (root.left != null) {
            if (root.left.left == null && root.left.right == null) {
                ans += root.left.val;
            } else {
                ans += sumOfLeftLeavesRecursion(root.left);
            }
        }
        ans += sumOfLeftLeavesRecursion(root.right);

        return ans;
    }

    public int sumOfLeftLeavesIteration(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int ans = 0;
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);

        while (!stack.empty()) {
            TreeNode node = stack.pop();
            if (node.left != null) {
                if (node.left.left == null && node.left.right == null) {
                    ans += node.left.val;
                } else {
                    stack.push(node.left);
                }
            }
            if (node.right != null) {
                if (node.right.left != null || node.right.right != null) {
                    stack.push(node.right);
                }
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        SumOfLeftLeaves_404 s = new SumOfLeftLeaves_404();

        TreeNode root = TreeNodeUtil.deserialize("3,9,20,#,#,15,7");
        System.out.println(s.sumOfLeftLeavesIteration(root));
        System.out.println(s.sumOfLeftLeavesRecursion(root));
    }
}
