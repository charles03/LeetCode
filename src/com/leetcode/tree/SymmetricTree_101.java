package com.leetcode.tree;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by charles on 2/20/17.
 * Given a binary tree, check whether it is a mirror of itself (ie, symmetric around its center).

 For example, this binary tree [1,2,2,3,4,4,3] is symmetric:

     1
    / \
   2   2
  / \ / \
 3  4 4  3
 But the following [1,2,2,null,3,null,3] is not:
   1
  / \
 2   2
  \   \
  3    3
 Bonus points if you could solve it both recursively and iteratively.
 */
public class SymmetricTree_101 {
    /**
     * Solution One recursion
     * wo trees are a mirror reflection of each other if:

     Their two roots have the same value.
     The right subtree of each tree is a mirror reflection of the left subtree of the other tree.
     */
    public boolean isSymmetric(TreeNode root) {
        return isMirror(root, root);
    }

    public boolean isMirror(TreeNode r1, TreeNode r2) {
        if (r1 == null && r2 == null) {
            return true;
        }
        if (r1 == null || r2 == null) {
            return false;
        }
        return (r1.val == r2.val)
                && (isMirror(r1.right, r2.left))
                && (isMirror(r1.left, r2.right));
    }

    /**
     * Iterative solution
     * iteration with aid of queue. Each two consecutive nodes in queue should be equal
     * working similar to BFS, but key difference:
     * two nodes are extracted and compare their value
     * then right and left children of two nodes inserted in queue in opposite order
     */
    public boolean isSymmetricII(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        queue.add(root);

        TreeNode r1;
        TreeNode r2;
        while (!queue.isEmpty()) {
            r1 = queue.poll();
            r2 = queue.poll();
            // compare
            if (r1 == null && r2 == null) {
                continue;
            }
            if (r1 == null || r2 == null) {
                return false;
            }
            if (r1.val != r2.val) {
                return false;
            }
            // insertion, left and right are in opposite order
            queue.add(r1.left);
            queue.add(r2.right);
            queue.add(r1.right);
            queue.add(r2.left);
        }
        return true;
    }
}
