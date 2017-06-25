package com.leetcode.tree;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by charles on 4/6/17.
 * Given a binary tree, find the leftmost value in the last row of the tree.

 Example 1:
 Input:

   2
  / \
 1   3

 Output:
 1
 Example 2:
 Input:

     1
    / \
   2   3
  /   / \
 4   5   6
     /
    7

 Output:
 7

 */
public class FindBottomLeftTreeValue_513 {
    /**
     * Thought: BFS level order traversal, besides, record first elem of each level
     * because use same var to record, when all levels finish,
     * the remain one is the left most result need to be returned
     */
    public int findBottomLeftValue(TreeNode root) {
        if (root == null) {
            return 0;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        int size = 0;
        TreeNode node = null;
        int res = 0;
        while (!queue.isEmpty()) {
            size = queue.size();
            for (int i = 0; i < size; i++) {
                node = queue.poll();
                if (i == 0) {
                    res = node.val;
                }
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
        }
        return res;
    }

    /** recursion solution with assistance private wrapper class*/
    public int findBottomLeftValueII(TreeNode root) {
        if (root == null) {
            return 0;
        }
        Wrapper wrapper = helper(root, 0, new Wrapper(0, root.val));
        return wrapper.leftMostValue;
    }
    /** divide and conquer */
    private Wrapper helper(TreeNode root, int row, Wrapper wrap) {
        if (root == null) {
            return wrap;
        }
        if (row > wrap.height) {
            wrap = new Wrapper(row, root.val);
        }
        if (root.left != null) {
            wrap = helper(root.left, row + 1, wrap);
        }
        if (root.right != null) {
            wrap = helper(root.right, row + 1, wrap);
        }
        return wrap;
    }

    private class Wrapper {
        int height;
        int leftMostValue;
        public Wrapper(int height, int leftMostValue) {
            this.height = height;
            this.leftMostValue = leftMostValue;
        }
    }

    /** recursion with assistance of array to record depth and value */
    public int findBottomLeftValueIII(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int[] res = {0, root.val};
        dfsHelper(root, 0, res);
        return res[1];
    }
    private void dfsHelper(TreeNode root, int row, int[] res) {
        if (root == null) {
            return;
        }
        //no need to care about the cols of a row, as here we always go to left first,
        // the left-most node of a row will always be captured first
        if (row > res[0]) {
            res[0] = row;
            res[1] = root.val;
        }
        if (root.left != null) {
            dfsHelper(root.left, row + 1, res);
        }
        if (root.right != null) {
            dfsHelper(root.right, row + 1, res);
        }
    }
}
