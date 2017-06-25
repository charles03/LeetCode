package com.leetcode.tree;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * Created by charles on 4/9/17.
 * Given a binary tree, find its minimum depth.

 The minimum depth is the number of nodes along the shortest path from the root node down to the nearest leaf node.
 */
public class MinimumDepthBinaryTree_111 {
    /**
     * Recursion, min(depth of left tree, right tree) + 1
     * be aware, need to find leaf node first;
     */
    public int minDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return helper(root, 0);
    }

    public int helper(TreeNode root, int height) {
        if (root == null) {
            return 0;
        }
        if (root.left == null && root.right == null) {
            return height;
        }
        int left = helper(root.right, height + 1);
        int right = helper(root.left, height + 1);
        if (root.left == null) {
            return right;
        }
        if (root.right == null) {
            return left;
        }
        return Math.min(left, right);
    }

    /** Level Order Traversal, check level by level */
    public int minDepthIII(TreeNode root) {
        if (root == null) {
            return 0;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int size = 0;
        TreeNode node = null;
        int height = 1;
        while (!queue.isEmpty()) {
            size = queue.size();
            for (int i = 0; i < size; i++) {
                node = queue.poll();
                if (node.left == null && node.right == null) {
                    return height;
                }
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
            height++;
        }
        return height;
    }

    /**
     * Below implement is not correct version,
     * level order should not use Stack, instead, use Queue,
     * because stack (Last In First Out) will break current level order traversal
     * queue (first in first out) will always poll current level even if we insert new node
     * into queue
     */
    public int minDepthII(TreeNode root) {
        if (root == null) {
            return 0;
        }
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        int size = 0;
        TreeNode node = null;
        int height = 1;
        while (!stack.isEmpty()) {
            // level order
            size = stack.size();
            for (int i = 0; i < size; i++) {
                node = stack.pop();
                if (node.left == null && node.right == null) {
                    return height;
                }
                if (node.left != null) {
                    stack.push(node.left);
                }
                if (node.right != null) {
                    /** here will break level order traversal */
                    stack.push(node.right);
                }
            }
            height++;
        }
        return height;
    }

    public static void main(String[] args) {
        MinimumDepthBinaryTree_111 m = new MinimumDepthBinaryTree_111();
        String s1 = "1,2,3,4,#,#,5";
        TreeNode root = TreeNodeUtil.deserialize(s1);
//        System.out.println(m.minDepthIII(root));
        System.out.println(m.minDepthII(root));
    }
}
