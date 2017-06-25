package com.leetcode.bfs;

import com.leetcode.tree.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by charles on 5/23/17.
 * You need to find the largest value in each row of a binary tree.

 Example:
 Input:

     1
    / \
   3   2
  / \   \
 5   3   9

 Output: [1, 3, 9]
 */
public class FindLargestValueInTreeRow_515 {
    /**
     * BFS solution use level order
     * {@link }
     */
    public List<Integer> largestValues(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) {
            return res;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        int size = 0;
        int max = Integer.MIN_VALUE;
        TreeNode node = null;
        while (!queue.isEmpty()) {
            size = queue.size();
            max = Integer.MIN_VALUE; // at each level, init MIN_VALUE to max
            for (int i = 0; i < size; i++) {
                node = queue.poll();
                max = Math.max(max, node.val);
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }
            res.add(max);
        }
        return res;
    }
}
