package com.leetcode.tree;

import com.advanced.dataStructure2.heap.HashHeap;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by charles on 1/3/17.
 * You are given a binary tree in which each node contains an integer value.
 Find the number of paths that sum to a given value.
 The path does not need to start or end at the root or a leaf,
 but it must go downwards (traveling only from parent nodes to child nodes).
 The tree has no more than 1,000 nodes and the values are in the range -1,000,000 to 1,000,000
 */
public class PathSumIII_437 {
    /**
     * Similar to Two Sum, using HashMap to store (key: prefix sum, value : how manys ways get to this prefix sum)
     * whenever reach a node, check if prefix sum - target exists in hashmap or not
     */
    public int pathSum(TreeNode root, int sum) {
        Map<Integer, Integer> map = new HashMap<>(); // key sum, value count
        map.put(0, 1); // default sum = 0 has one count
        return backTrack(root, 0, sum, map);
    }

    private int backTrack(TreeNode root, int sum, int target, Map<Integer, Integer> preSum) {
        if (root == null) {
            return 0;
        }
        sum += root.val; // prefix sum
        int count = preSum.getOrDefault(sum - target, 0); // to avoid global var
        System.out.println(String.format("val: %s, count: %s, sum: %s", root.val, count, sum));

        preSum.put(sum, preSum.getOrDefault(sum, 0) + 1);
        // dfs- backtrack
        count += backTrack(root.left, sum, target, preSum) + backTrack(root.right, sum, target, preSum);
        System.out.println("remove current node, sum: " + sum);
        preSum.put(sum, preSum.get(sum) - 1); // remove current node so it won't affect other path
        return count;
    }

    public static void main(String[] args) {
        PathSumIII_437 p = new PathSumIII_437();
        TreeNode root = TreeNodeUtil.deserialize("10,5,-3,3,2,#,11,3,-2,#,1");
        System.out.println(p.pathSum(root, 8));
    }
}
