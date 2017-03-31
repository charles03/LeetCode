package com.leetcode.tree;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * Created by charles on 12/22/16.
 * Given a binary tree, find its maximum depth.
 The maximum depth is the number of nodes along the longest path from the root node down to the farthest leaf node.
 */
public class MaximumDepthOfBinaryTree_104 {

    public int maxDepthRecursion(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return 1 + Math.max(maxDepthRecursion(root.left), maxDepthRecursion(root.right));
    }

    /** queue implementation for BFS */
    public int maxDepthBFS(TreeNode root) {
        if (root == null) {
            return 0;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int size = 0;
        int height = 0;
        while (!queue.isEmpty()) {
            size = queue.size();
            while (size-- > 0) {
                TreeNode node = queue.poll();
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

    /** stack implementation for DFS */
    public int maxDepthDFS(TreeNode root) {
        if (root == null) {
            return 0;
        }
        Stack<TreeNode> stack = new Stack<>();
        Stack<Integer> height = new Stack<>();
        stack.push(root);

        int temp = 0;
        int max = 0;
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            temp = height.pop();
            max = Math.max(temp, max);
            if (node.left != null) {
                stack.push(node.left);
                height.push(temp + 1);
            }
            if (node.right != null) {
                stack.push(node.right);
                height.push(temp + 1);
            }
        }
        return max;
    }

    public static void main(String[] args) {

    }
}
