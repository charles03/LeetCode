package com.amazon;

import java.util.Stack;

/**
 * Created by charles on 6/13/17.
 * given a list of integer, consist a BST by inserting each integer by given order
 * without rebalancing. then find distance two given nodes,
 * if node1 or node2 is not present in BST, return -1;
 */
public class TwoNodesDistanceInBST {
    /**
     * use given array to construct binary search tree
     * by definition, left < root < right
     *
     * Construct BST from given preorder traversal
     * http://www.geeksforgeeks.org/construct-bst-from-given-preorder-traversal-set-2/
     * use Stack to push pop node when current value from array > peek
     */


    public int distTwoNodes(int[] arr, int size, int a, int b) {
        TreeNode root = buildBST(arr, size);
        return helper(root, a, b);
    }
    private int helper(TreeNode root, int a, int b) {
        if (root == null) {
            return -1;
        }
        if (a < root.val && b < root.val) {
            return helper(root.left, a, b);
        } else if (a > root.val && b > root.val) {
            return helper(root.right, a, b);
        } else {
            int d1 = singleDist(root, a);
            int d2 = singleDist(root, b);
            if (d1 < 0 || d2 < 0) {
                return -1;
            }
            return d1 + d2;
        }
    }
    private int singleDist(TreeNode root, int a) {
        if (root == null) {
            return Integer.MIN_VALUE;
        }
        if (root.val == a) {
            return 0;
        } else if (root.val > a) {
            return singleDist(root.left, a) + 1;
        } else {
            return singleDist(root.right, a) + 1;
        }
    }

    /**given traversal is {10, 5, 1, 7, 40, 50}
     *    10
        /    \
       5     40
      / \     \
     1   7    50
     */
    /**
     1.Make the first value as root. Push it to the stack.
     2.Keep on popping while the stack is not empty and the next value is greater than stack’s top value. Make this value as the right child of the last popped node. Push the new node to the stack.
     3.If the next value is less than the stack’s top value, make this value as the left child of the stack’s top node. Push the new node to the stack.
     4.Repeat steps 2 and 3 until there are items remaining in pre[].
     */
    private TreeNode buildBST(int[] nums, int size) {
        TreeNode root = new TreeNode(nums[0]);
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);

        TreeNode node = null;
        for (int i = 1; i < size; i++) {
            node = null;
            /** after exit while loop, nums[i] in the range [node.val, stack.peek.val] */
            while (!stack.isEmpty() && nums[i] > stack.peek().val) {
                node = stack.pop();
            }
            // Make this greater value as the right child and push it to the stack
            if (node != null) {
                node.right = new TreeNode(nums[i]);
                stack.push(node.right);
            } else {
                // If the next value is less than the stack's top value, make this value
                // as the left child of the stack's top node. Push the new node to stack
                node = stack.peek();
                node.left = new TreeNode(nums[i]);
                stack.push(node.left);
            }
        }
        return root;
    }

    class TreeNode {
        int val;
        TreeNode left, right;
        public TreeNode(int val) {
            this.val = val;
            this.left = null;
            this.right = null;
        }
    }

    public static void main(String[] args) {
        int[] a1 = {10, 5, 1, 7, 40, 50};
        TwoNodesDistanceInBST t = new TwoNodesDistanceInBST();
        System.out.println(t.distTwoNodes(a1, 6, 1, 10));
        System.out.println(t.distTwoNodes(a1, 6, 5, 60));
    }

}
