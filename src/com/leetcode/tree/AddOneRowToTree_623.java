package com.leetcode.tree;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by charles on 6/19/17.
 * Given the root of a binary tree, then value v and depth d, you need to add a row of nodes with value v at the given depth d. The root node is at depth 1.

 The adding rule is: given a positive integer depth d, for each NOT null tree nodes N in depth d-1, create two tree nodes with value v as N's left subtree root and right subtree root. And N's original left subtree should be the left subtree of the new left subtree root, its original right subtree should be the right subtree of the new right subtree root. If depth d is 1 that means there is no depth d-1 at all, then create a tree node with value v as the new root of the whole original tree, and the original tree is the new root's left subtree.

 Example 1:
 Input:
 A binary tree as following:
      4
    /   \
   2     6
  / \   /
 3   1 5

 v = 1

 d = 2

 Output:
       4
      / \
     1   1
    /     \
   2       6
  / \     /
 3   1   5

 Example 2:
 Input:
 A binary tree as following:
     4
    /
   2
  / \
 3   1

 v = 1

 d = 3

 Output:
       4
      /
     2
    / \
   1   1
  /     \
 3       1
 Note:
 The given d is in range [1, maximum depth of the given tree + 1].
 The given binary tree has at least one tree node.
 */
public class AddOneRowToTree_623 {
    /** recursion */
    public TreeNode addOneRow(TreeNode root, int v, int d) {
        if (d == 1) {
            TreeNode newRoot = new TreeNode(v);
            newRoot.left = root;
            return newRoot;
        }
        insert(root, v, 1, d);
        return root;
    }
    private void insert(TreeNode root, int val, int row, int depth) {
        if (root == null) {
            return;
        }
        if (row == depth - 1) {
            TreeNode left = root.left;
            root.left = new TreeNode(val);
            root.left.left = left;
            TreeNode right = root.right;
            root.right = new TreeNode(val);
            root.right.right = right;
        } else {
            insert(root.left, val, row+1, depth);
            insert(root.right, val, row+1, depth);
        }
    }

    private void insertII(TreeNode root, int val, int row, int depth) {
        if (root == null) {
            return;
        }
        if (row == depth - 1) {
            TreeNode left = root.left;
            root.left = new TreeNode(val);
            root.left.left = left;
            /**
             * to improve performance
             * saving one action if root.right is null
             */
            if (root.right != null) {
                TreeNode right = root.right;
                root.right = new TreeNode(val);
                root.right.right = right;
            } else {
                root.right = new TreeNode(val);
            }
        } else {
            insert(root.left, val, row+1, depth);
            insert(root.right, val, row+1, depth);
        }
    }

    /** BFS */
    public TreeNode addOneRowII(TreeNode root, int v, int d) {
        if (d == 1) {
            TreeNode newRoot = new TreeNode(v);
            newRoot.left = root;
            return newRoot;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int row = 1;
        int size = 0;
        TreeNode node = null;
        while (row < d-1) {
            size = queue.size();
            for (int i = 0; i < size; i++) {
                node = queue.poll();
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
            row++;
        }
        // for now only d-1 row remained in the queue
        // add new node with value for left/right children of each node at d-1 depth
        while (!queue.isEmpty()) {
            node  = queue.poll();
            TreeNode left = node.left;
            node.left = new TreeNode(v);
            node.left.left = left;
            TreeNode right = node.right;
            node.right = new TreeNode(v);
            node.right.right = right;
        }
        return root;
    }
}
