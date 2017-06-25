package com.leetcode.tree;

/**
 * Created by charles on 4/1/17.
 * Using Morris Traversal, we can traverse the tree without using stack and recursion.
 * The idea of Morris Traversal is based on Threaded Binary Tree. In this traversal,
 * we first create links to Inorder successor and print the data using these links, and finally revert the changes to restore original tree.
 *
 * 1. Initialize current as root
    2. While current is not NULL
        If current does not have left child
            a) Print current’s data
            b) Go to the right, i.e., current = current->right
        Else
            a) Make current as right child of the rightmost
                node in current's left subtree
            b) Go to this left child, i.e., current = current->left
 */
public class BinaryTreeMorrisTraversal {
    // http://www.geeksforgeeks.org/inorder-tree-traversal-without-recursion-and-without-stack/
    // TODO
    public void inorder(TreeNode root) {
        TreeNode current = root;
        while (current != null) {
            if (current.left == null) {
                current = current.right;
            } else {
                // find inorder predecessor of current
                TreeNode prev = current.left;
                while (prev.right != null && prev.right != current) {
                    prev = prev.right;
                    // make current as right child of its inorder predecessor
                    if (prev.right == null) {
                        prev.right = current;
                        current = current.left;
                    } else {
                        // revert changes which made in if block
                        // so as to restore original tree
                        // fix right child of predecessor
                        prev.right = null;
                        current = current.right;
                    }
                }
            }
        }
    }
}
