package com.leetcode.tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by charles on 3/20/17.
 * Given a binary tree, imagine yourself standing on the right side of it, return the values of the nodes you can see ordered from top to bottom.

 For example:
 Given the following binary tree,
    1            <---
  /   \
 2     3         <---
  \     \
   5     4       <---
 You should return [1, 3, 4].
 */
public class BinaryTreeRightSideView_199 {
    /** BFS level order, only add last elem at each level into result list */
    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) {
            return res;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int size = 0;
        TreeNode node = null;
        while (!queue.isEmpty()) {
            size = queue.size();
            for (int i = 0; i < size; i++) {
                node = queue.poll();
                if (i == size - 1) { // last elem at current level
                    res.add(node.val);
                }
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
        }
        return res;
    }

    /** recursion divide and conquer*/
    public List<Integer> rightSideViewII(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) {
            return res;
        }
        List<Integer> left = rightSideViewII(root.left);
        List<Integer> right = rightSideViewII(root.right);

        res.add(root.val);
        for (int i = 0; i < Math.max(left.size(), right.size()); i++) {
            if (i >= right.size()) {
                res.add(left.get(i));
            } else {
                res.add(right.get(i));
            }
        }
        return res;
    }

    /** DFS solution */
    public List<Integer> rightSideViewIII(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) {
            return res;
        }
        return helper(res, root, 0);
    }
    List<Integer> helper(List<Integer> res, TreeNode root, int height) {
        /**
         * height == result.size() is the core part in this recursion,
         * it limits the amount of Node add to the result in each level(height) of the Tree.
         */
        if (height == res.size()) {
            res.add(root.val);
        }
        /** if ask left side view, just swap order of below two if blocks */
        if (root.right != null) {
            helper(res, root.right, height + 1);
        }
        if (root.left != null) {
            helper(res, root.left, height + 1);
        }
        return res;
    }
}
