package com.leetcode.tree;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by charles on 2/27/17.
 * Invert a binary tree.

      4
    /   \
   2     7
  / \   / \
 1   3 6   9
 to
      4
    /   \
   7     2
  / \   / \
 9   6 3   1
 Trivia:
 This problem was inspired by this original tweet by Max Howell:
 Google: 90% of our engineers use the software you wrote (Homebrew), but you can’t invert a binary tree on a whiteboard so fuck off.
 */
public class InvertBinaryTree_226 {
    /** recursion */
    public TreeNode invertTree(TreeNode root) {
        return invertHelper(root);
    }

    public TreeNode invertHelper(TreeNode node) {
        if (node == null) {
            return node;
        }
        TreeNode left = invertHelper(node.left);
        TreeNode right = invertHelper(node.right);

        node.left = right;
        node.right = left;
        return node;
    }

    /** Iteration solution
     * The idea is that we need to swap the left and right child of all nodes in the tree
     * so we create a queue to store nodes whose left and right child have not been swapped yet.
     * Initially, only the root is in the queue, as long as the queue is not empty, remove the next node from the queue
     * swap its children, and add the children to the queue. NULL nodes are not added to the queue.
     * Eventually the queue will be empty and all the children swapped. and we return the original root
     * */
    public TreeNode invertTreeII(TreeNode root) {
        if (root == null) {
            return root;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            TreeNode curr = queue.poll();
            TreeNode tmp = curr.left;
            curr.left = curr.right;
            curr.right = tmp;

            if (curr.left != null) {
                queue.offer(curr.left);
            }
            if (curr.right != null) {
                queue.offer(curr.right);
            }
        }
        return root;
    }

}
