package com.leetcode.tree;

import com.advanced.dataStructure2.heap.HashHeap;

import javax.swing.*;
import java.util.*;

/**
 * Created by charles on 1/3/17.
 * You are given a binary tree in which each node contains an integer value.
 Find the number of paths that sum to a given value.
 The path does not need to start or end at the root or a leaf,
 but it must go downwards (traveling only from parent nodes to child nodes).
 The tree has no more than 1,000 nodes and the values are in the range -1,000,000 to 1,000,000
 root = [10,5,-3,3,2,null,11,3,-2,null,1], sum = 8

       10
      /  \
     5   -3
    / \    \
   3   2   11
  / \   \
 3  -2   1

 Return 3. The paths that sum to 8 are:

 1.  5 -> 3
 2.  5 -> 2 -> 1
 3. -3 -> 11
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

    private int backTrack(TreeNode root, int sum, int target, Map<Integer, Integer> preSumMap) {
        if (root == null) {
            return 0;
        }
        sum += root.val; // prefix sum
        int count = preSumMap.getOrDefault(sum - target, 0); // to avoid global var
        System.out.println(String.format("val: %s, count: %s, sum: %s", root.val, count, sum));
        // put new value into map before dfs
        preSumMap.put(sum, preSumMap.getOrDefault(sum, 0) + 1);
        printMapDetail("before", preSumMap);
        // dfs- backtrack
        count += backTrack(root.left, sum, target, preSumMap) + backTrack(root.right, sum, target, preSumMap);
        // remove current node so it won't affect other path
        preSumMap.put(sum, preSumMap.get(sum) - 1);
        printMapDetail("after", preSumMap);
        return count;
    }

    public static void main(String[] args) {
        PathSumIII_437 p = new PathSumIII_437();
        TreeNode root = TreeNodeUtil.deserialize("10,5,-3,3,2,#,11,3,-2,#,1");
        System.out.println(p.pathSum(root, 8));
    }

    private void printMapDetail(String state, Map<Integer, Integer> map) {
        map.forEach((k,v) -> System.out.println(String.format(state + " prefix Sum : %d, count : %d", k,v)));
    }

    /**
     * Iteration Solution
     * use one queue to visit all nodes in level order
     * and keep another queue tp record list of sums in ancestor so far from root;
     */
    public int pathSumII(TreeNode root, int target) {
        if (root == null) {
            return 0;
        }
        // queue for level order traversal
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        // queue for list of sums from root or any ancestor node for current node in level-order queue
        Queue<List<Integer>> preSumQueue = new LinkedList<>();
        List<Integer> preSum = new ArrayList<>();
        preSumQueue.offer(preSum);

        int count = 0;
        while (!queue.isEmpty()) {
            preSum = preSumQueue.remove();
            root = queue.remove();
            if (root.val == target) {
                count++; // current node itself matches
            }
            for (int val : preSum) {
                if ((val + root.val) == target) {
                    count++; // any prefix sum from any ancestor until before current node plus current node matches
                }
            }
            // update into left
            if (root.left != null) {
                queue.offer(root.left);
                addNewPrefixSumIntoQueue(preSumQueue, preSum, root.val);
            }
            if (root.right != null) {
                queue.offer(root.right);
                addNewPrefixSumIntoQueue(preSumQueue, preSum, root.val);
            }
        }
        return count;
    }

    private void addNewPrefixSumIntoQueue(Queue<List<Integer>> prefixSumQueue, List<Integer>prevPrefixSumList, int base) {
        List<Integer> newSumList = new ArrayList<>();
        prevPrefixSumList.forEach(t -> newSumList.add(t + base));
        prefixSumQueue.offer(newSumList);
    }
}
