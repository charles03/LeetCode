package com.leetcode.tree;

import javax.xml.transform.Result;

/**
 * Created by charles on 3/29/17.
 * Given a binary tree, you need to compute the length of the diameter of the tree. The diameter of a binary tree is the length of the longest path between any two nodes in a tree. This path may or may not pass through the root.

 Example:
 Given a binary tree
     1
    / \
   2   3
  / \
 4   5
 Return 3, which is the length of the path [4,2,1,3] or [5,2,1,3].

 Note: The length of path between two nodes is represented by the number of edges between them.
 */
public class DiameterOfBinaryTree_543 {
    /**
     * Recursion with Devide and conquer,
     * diameter path of current node is maxDepth(root.left) + maxDepth(root.right)
     * left is self call (root.left), right as well;
     * return result Math.max(curr, left, right)
     */
    public int diameterOfBinaryTree(TreeNode root) {
        int res = 0;
        if (root == null) {
            return res;
        }
        int curr = maxDepth(root.left) + maxDepth(root.right);
        int left = diameterOfBinaryTree(root.left);
        int right = diameterOfBinaryTree(root.right);

        return Math.max(curr, Math.max(left, right));
    }

    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return 1 + Math.max(maxDepth(root.left), maxDepth(root.right));
    }

    public int diameterOfBinaryTreeII(TreeNode root) {
        ResultType result = helper(root);
        return result.maxPath;
    }
    private ResultType helper(TreeNode root) {
        if (root == null) {
            return new ResultType(0, 0);
        }
        // Divide
        ResultType left = helper(root.left);
        ResultType right = helper(root.right);
        // conquer
        int depth = Math.max(left.depth, right.depth) + 1;
        int maxDepth = Math.max(left.maxPath, right.maxPath);
        maxDepth = Math.max(maxDepth, left.depth + right.depth);
        return new ResultType(depth, maxDepth);
    }

    private class ResultType {
        int depth;
        int maxPath;
        ResultType(int depth, int maxPath) {
            this.depth = depth;
            this.maxPath = maxPath;
        }
    }
}
