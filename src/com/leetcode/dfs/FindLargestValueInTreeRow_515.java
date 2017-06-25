package com.leetcode.dfs;

import com.leetcode.tree.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by charles on 5/23/17.
 * {@link com.leetcode.bfs.FindLargestValueInTreeRow_515}
 * this is DFS solution
 */
public class FindLargestValueInTreeRow_515 {
    public List<Integer> largestValues(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) {
            return res;
        }
        dfs(root, 0, res);
        return res;
    }
    private void dfs(TreeNode node, int level, List<Integer> res) {
        if (node == null) {
            return;
        }
        if (res.size() == level) { // add new start value for next level
            res.add(node.val);
        } else {
            if (node.val > res.get(level)) { // find max for each level;
                res.set(level, node.val); // keep numbers of elem in list same as levels of tree
            }
        }
        // to left
        dfs(node.left, level+1, res);
        // to right
        dfs(node.right, level+1, res);
    }
}
