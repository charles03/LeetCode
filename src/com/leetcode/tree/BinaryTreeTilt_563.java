package com.leetcode.tree;

/**
 * Created by charles on 4/27/17.
 * Given a binary tree, return the tilt of the whole tree.

 The tilt of a tree node is defined as the absolute difference between the sum of all left subtree node values and the sum of all right subtree node values. Null node has tilt 0.

 The tilt of the whole tree is defined as the sum of all nodes' tilt.

 Example:
 Input:
    1
  /   \
 2     3
 Output: 1
 Explanation:
 Tilt of node 2 : 0
 Tilt of node 3 : 0
 Tilt of node 1 : |2-3| = 1
 Tilt of binary tree : 0 + 0 + 1 = 1
 */
public class BinaryTreeTilt_563 {
    /**
     * Recursion with Divide and Conquer
     */
    private class ResultType {
        int sum;
        int tilt;
        ResultType(int sum, int tilt) {
            this.sum = sum;
            this.tilt = tilt;
        }
    }
    public int findTilt(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return helper(root).tilt;
    }
    private ResultType helper(TreeNode node) {
        if (node == null) {
            return new ResultType(0, 0);
        }
        ResultType left = helper(node.left);
        ResultType right = helper(node.right);
        int sum = left.sum + right.sum + node.val;
        int currTilt  = Math.abs(left.sum - right.sum);
        int sumTilt = left.tilt + right.tilt + currTilt;
        return new ResultType(sum, sumTilt);
    }

    /**
     * post order traversal, and without using global var
     * use size-1 1-D array as object
     */
    public int findTiltII(TreeNode root) {
        int[] res = new int[1];
        postorder(root, res);
        return res[0];
    }
    /** postorder func return sum of subtree till current node as root */
    private int postorder(TreeNode root, int[] res) {
        if (root == null) {
            return 0;
        }
        int leftSum = postorder(root.left, res);
        int rightSum = postorder(root.right, res);
        res[0] += Math.abs(leftSum - rightSum);
        return leftSum + rightSum + root.val;
    }

}
