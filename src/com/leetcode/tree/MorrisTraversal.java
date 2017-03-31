package com.leetcode.tree;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by charles on 1/7/17.
 * Reference Link: http://www.geeksforgeeks.org/inorder-tree-traversal-without-recursion-and-without-stack/
 * http://www.cnblogs.com/AnnieKim/archive/2013/06/15/morristraversal.html
 *
 * Morris Traversal allow to traverse tree without using stack and recursion.
 * Idea is based on Threaded Binary Tree.
 *  -> create links to In-order successor and print data using links
 *  -> and then revert changes to restore original tree structure.
 */
public class MorrisTraversal {

    /**
     * 1. Initialize current as root
     * 2. while current is not null
     *      if current does not have left child
     *          a) print current's data
     *          b) go to right, current = current->right
     *      else
     *          a) make current as right child of the rightmost node in current's left subtree
     *          b) go to this left child, current = current->left
     */
    public void inOrderTraverse(TreeNode root) {
        if (root == null) {
            return;
        }
        TreeNode current = root;
        TreeNode prev = null;

        while (current != null) {
            if (current.left == null) {
                System.out.print(current.val + ", ");
                current = current.right;
            } else {
                /* Find inorder predecessor of current */
                prev = current.left;
                while (prev.right != null && prev.right != current) {
                    prev = prev.right;
                }
                /* make current as right child of its inorder predecessor */
                if (prev.right == null) {
                    prev.right = current;
                    current = current.left;
                } else {
                /* revert change made in if part to restore the original tree, fix right child of predecessor */
                    prev.right = null;
                    System.out.print(current.val + ", ");
                    current = current.right;
                }
            }
        }
    }

    public void preOrderTraverse(TreeNode root) {
        if (root == null) {
            return;
        }
        TreeNode current = root;
        TreeNode prev = null;

        List<Integer> list = new LinkedList<>();

        while (current != null) {
            if (current.left == null) {
//                System.out.println("output_1: " + current.val + ", ");
                list.add(current.val);

                current = current.right;
                System.out.println("go to right of current");
            } else {
                // find predecessor
                prev = current.left; // when current.left is not null
                System.out.println("prev -> " + prev.val);
                while (prev.right != null && prev.right != current) {
                    System.out.println("while loop: " + prev.right.val + ", " + current.val);
                    prev = prev.right;
                    System.out.println("prev -> right");
                }
                if (prev.right == null) {
                    System.out.println("if not null, prev: " + prev.val);
//                    System.out.println("output_2: " + current.val);
                    /**
                     * here is the difference between inorder & preorder morris traversal
                     * output order will be different
                     */
                    list.add(current.val);

                    prev.right = current;
                    System.out.println("prev.right = " + current.val +", current to left");
                    current = current.left;
                } else {
                    // to restore original tree;

                    System.out.println("in restore: " + prev.val);
                    prev.right = null;
                    System.out.println("move current: " + current.val + " to right");
                    current = current.right;
                }
            }
        }

        list.stream().forEach(t -> System.out.print(t + ","));
        System.out.println();
    }

    public static void main(String[] args) {
        TreeNode root = TreeNodeUtil.deserialize("1,2,3,#,4,5,#,6,7");
        MorrisTraversal m = new MorrisTraversal();
//        m.inOrderTraverse(root);
        m.preOrderTraverse(root);
        System.out.println();
        System.out.println(TreeNodeUtil.serialize(root));
    }
}
