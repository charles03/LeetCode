package com.leetcode.tree;

import apple.laf.JRSUIUtils;

import java.util.*;

/**
 * Created by charles on 4/6/17.
 * Given a binary tree, return the postorder traversal of its nodes' values.

 For example:
 Given binary tree {1,#,2,3},
 1
  \
   2
  /
 3
 return [3,2,1].

 Note: Recursive solution is trivial, could you do it iteratively?
 */
public class BinaryTreePostOrderTraversal_145 {
    /** naive solution, by recursion */
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        helper(res, root);
        return res;
    }
    private void helper(List<Integer> res, TreeNode root) {
        if (root == null) {
            return;
        }
        helper(res, root.left);
        helper(res, root.right);
        res.add(root.val);
    }

    /** Iterative solution, detailed version*/
    public List<Integer> postorderTraversalII(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) {
            return res;
        }
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);

        TreeNode prev = null;
        while (!stack.empty()) {
            TreeNode curr = stack.peek();

            // go down tree
            // check if current node is leaf, if so, process it and pop from stack
            if (prev == null || prev.left == null || prev.right == curr) {
                // prev == null is the situation for the root node
                if (curr.left != null) {
                    stack.push(curr.left);
                } else if (curr.right != null) {
                    stack.push(curr.right);
                } else {
                    stack.pop();
                    res.add(curr.val);
                }
            } else if (curr.left == prev) {
                // go up tree from left node
                // need to check if there is right child
                // if yes, push it to stack
                // otherwise, process parent and pop from stack
                if (curr.right != null) {
                    stack.push(curr.right);
                } else {
                    stack.pop();
                    res.add(curr.val);
                }
            } else if (curr.right == prev) {
                // go up tree from right node
                // after coming back from right node, process parent node and pop stack
                stack.pop();
                res.add(curr.val);
            }
            prev = curr;
        }
        return res;
    }

    /** simplified version */
    public List<Integer> postorderTraversalIII(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) {
            return res;
        }
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode curr = stack.peek();
            if (curr.left == null && curr.right == null) {
                // this is leaf
                TreeNode node = stack.pop();
                res.add(node.val);
            } else {
                // go right first into stack for reverse purpose when pop out from stack
                if (curr.right != null) {
                    stack.push(curr.right);
                    curr.right = null;
                }
                if (curr.left != null) {
                    stack.push(curr.left);
                    curr.left = null;
                }
            }
        }
        return res;
    }
    /** more simplified by using Deque (ArrayDeque) has bi-direction
     * also taking advantage of api of linkedlist (addFirst())
     * to insert result in reverse order */
    public LinkedList<Integer> postorderTraversalIV(TreeNode root) {
        LinkedList<Integer> res = new LinkedList<>();
        if (root == null) {
            return res;
        }
        Deque<TreeNode> deque = new ArrayDeque<>();
        TreeNode curr = root;
        while (!deque.isEmpty() || curr != null) {
            if (curr != null) {
                deque.push(curr);
                res.addFirst(curr.val); // do in reverse order
                curr = curr.right; // go right first
            } else {
                TreeNode node = deque.pop();
                curr = node.left;
            }
        }
        return res;
    }
}
