package com.leetcode.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by charles on 12/23/16.
 * Given a binary tree, return the inorder traversal of its nodes' values.

 For example:
 Given binary tree [1,null,2,3],
 return [1,3,2]
 */
public class BinaryTreeInorderTraversal_94 {
    public List<Integer> inorderTraversalRecursion(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        dfsHelper(root, res);
        return res;
    }

    /** left -> root -> right */
    private void dfsHelper(TreeNode root, List<Integer> res) {
        if (root == null) {
            return;
        }
        if (root.left != null) {
            dfsHelper(root.left, res);
        }
        // add current node into result list
        res.add(root.val);

        if (root.right != null) {
            dfsHelper(root.right, res);
        }
    }

    public List<Integer> inorderTraversalIteration(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        // in-order use stack, need to push left most first
        Stack<TreeNode> stack = new Stack<>();
        TreeNode curr = root;

        while (curr != null || !stack.isEmpty()) {
            // go left most
            while (curr != null) {
                stack.push(curr);
                curr = curr.left;
            }
            // start with left most
            curr = stack.pop();
            res.add(curr.val);
            // check left most has right child
            curr = curr.right;
        }
        return res;
    }

    public static void main(String[] args) {

    }
}
