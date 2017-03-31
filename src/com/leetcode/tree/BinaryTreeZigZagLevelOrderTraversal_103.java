package com.leetcode.tree;

import sun.tools.jstat.Literal;

import javax.swing.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by charles on 3/20/17.
 * Given a binary tree, return the zigzag level order traversal of its nodes' values. (ie, from left to right, then right to left for the next level and alternate between).

 For example:
 Given binary tree [3,9,20,null,null,15,7],
    3
   / \
  9  20
  /  \
 15   7
 return its zigzag level order traversal as:
 [
 [3],
 [20,9],
 [15,7]
 ]

 */
public class BinaryTreeZigZagLevelOrderTraversal_103 {
    /** recursion solution */
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        helper(res, root, 0);
        return res;
    }

    void helper(List<List<Integer>> res, TreeNode root, int level) {
        if (root == null) {
            return;
        }
        if (res.size() == level) {
            List<Integer> newLevel = new ArrayList<>();
            res.add(newLevel);
        }
        if (level % 2 == 1) {
            res.get(level).add(root.val);
        } else {
            res.get(level).add(0, root.val); // insert from front
        }

        helper(res, root.left, level + 1);
        helper(res, root.right, level + 1);
    }

    /** BFS iteration solution */
    public List<List<Integer>> zigzagLevelOrderII(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if (root == null) {
            return res;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int level = 1;
        TreeNode node = null;

        while (!queue.isEmpty()) {
            List<Integer> newLevel = new LinkedList<>();
            int levelSize = queue.size();

            for (int i = 0; i < levelSize; i++) {
                node = queue.poll();
                if (level % 2 == 1) {
                    newLevel.add(node.val);
                } else {
                    newLevel.add(0, node.val);
                }
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
            res.add(newLevel);
            level++;
        }
        return res;
    }
}
