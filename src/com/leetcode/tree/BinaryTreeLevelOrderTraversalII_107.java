package com.leetcode.tree;

import java.util.*;

/**
 * Created by charles on 4/1/17.
 * Given a binary tree, return the bottom-up level order traversal of its nodes' values. (ie, from left to right, level by level from leaf to root).

 For example:
 Given binary tree [3,9,20,null,null,15,7],
      3
     / \
    9  20
      /  \
     15   7
 return its bottom-up level order traversal as:
 [
 [15,7],
 [9,20],
 [3]
 ]

 */
public class BinaryTreeLevelOrderTraversalII_107 {
    /**
     * Solution 1: BFS iteration + Reverse
     * apply same logic like {@link BinaryTreeLevelOrderTraversal_102}
     * and then reverse final result List<List<>>
     */
    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if (root == null) {
            return res;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int size = 0; // var to record current level size
        while (!queue.isEmpty()) {
            size = queue.size();
            List<Integer> level = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                level.add(node.val);
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }
            res.add(level);
        }
        // only reverse outer list
        Collections.reverse(res);
        return res;
    }
    /**
     * Solution 2: BFS + Stack
     * use Stack to buffer List<Integer> of previous levels
     * feature of first In last out, so that will reverse output sequence
     */
    public List<List<Integer>> levelOrderBottomII(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if (root == null) {
            return res;
        }
        Stack<List<Integer>> buffer = new Stack<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        int size = 0;
        while (!queue.isEmpty()) {
            size = queue.size();
            List<Integer> level = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                level.add(node.val);
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
            buffer.push(level);
        }
        while (!buffer.isEmpty()) {
            res.add(buffer.pop());
        }
        return res;
    }
}
