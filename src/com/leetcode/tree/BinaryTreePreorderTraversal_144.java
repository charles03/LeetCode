package com.leetcode.tree;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Stack;

/**
 * Created by charles on 12/23/16.
 * Given a binary tree, return the preorder traversal of its nodes' values.
 * Pre-order is root -> all left node -> all right node
 */
public class BinaryTreePreorderTraversal_144 {
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) {
            return res;
        }

        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);

        TreeNode node;
        while (!stack.isEmpty()) {
            node = stack.pop();
            res.add(node.val);
            /** pre order, if use stack, right should go first */
            if (node.right != null) {
                stack.push(node.right);
            }
            if (node.left != null) {
                stack.push(node.left);
            }
        }
        return res;
    }

    public static void main(String[] args) {
        TreeNode root = TreeNodeUtil.deserialize("1,2,3,#,4,5");
        TreeNode root1 = TreeNodeUtil.deserialize("1,2,3,#,4,5,#,6,7,8");
        BinaryTreePreorderTraversal_144 b = new BinaryTreePreorderTraversal_144();
        print(b.preorderTraversal(root));
        print(b.preorderTraversal(root1));
    }

    private static void print(List<Integer> list) {
        list.stream().forEach(t -> System.out.print(t + ","));
        System.out.println();
    }
}
