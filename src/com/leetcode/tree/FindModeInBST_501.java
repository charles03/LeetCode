package com.leetcode.tree;

import com.leetcode.linkedlist.ListNode;
import com.leetcode.linkedlist.ListNodeUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

/**
 * Created by charles on 4/1/17.
 * Given a binary search tree (BST) with duplicates, find all the mode(s) (the most frequently occurred element) in the given BST.

 Assume a BST is defined as follows:

 The left subtree of a node contains only nodes with keys less than or equal to the node's key.
 The right subtree of a node contains only nodes with keys greater than or equal to the node's key.
 Both the left and right subtrees must also be binary search trees.
 For example:
 Given BST [1,null,2,2],
  1
   \
    2
   /
  2
 return [2].
 */
public class FindModeInBST_501 {
    /**
     * Thought: pre-order traverse BST, to store all values into list
     * iterate above list twice
     * first, to get max count;
     * second, each number, if count == max
     * then add into final result;
     */
    public int[] findMode(TreeNode root) {
        if (root == null) {
            return new int[0];
        }
        List<Integer> preOrder = preOrderTraversal(root);
        int maxCount = getMaxCount(preOrder);
        output(preOrder);
        System.out.println(maxCount);
        return getFinalModes(preOrder, maxCount);
    }
    private int getMaxCount(List<Integer> list) {
        if (list.size() == 0) {
            return 0;
        }
        int maxCount = 1;
        int cnt = 1;
        int prev = list.get(0);
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i) == prev) {
                cnt++;
            } else {
                cnt = 1;
                prev = list.get(i);
            }
            maxCount = Math.max(maxCount, cnt);
        }
        return maxCount;
    }

    private int[] getFinalModes(List<Integer> list, int maxCount) {
        if (list.size() == 0) {
            return new int[0];
        }
        List<Integer> res = new ArrayList<>();
        int cnt = 1;
        int prev = list.get(0);
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i) == prev) {
                cnt++;
            } else {
                if (cnt == maxCount) {
                    res.add(prev);
                }
                cnt = 1;
                prev = list.get(i);
            }
        }
        if (cnt == maxCount) {
            res.add(prev);
        }
        // convert list to array;
        int[] ans = new int[res.size()];
        for (int i = 0; i < res.size(); i++) {
            ans[i] = res.get(i);
        }
        return ans;
    }

    private List<Integer> preOrderTraversal(TreeNode root) {
        List<Integer> res = new LinkedList<>();
        if (root == null) {
            return res;
        }
        List<Integer> left = preOrderTraversal(root.left);
        List<Integer> right = preOrderTraversal(root.right);
        res.addAll(left);
        res.add(root.val);
        res.addAll(right);
        return res;
    }

    public static void main(String[] args) {
        FindModeInBST_501 f = new FindModeInBST_501();
        TreeNode root = TreeNodeUtil.deserialize("1,#,2,2");
//        TreeNode root = TreeNodeUtil.deserialize("2147483647");
//        TreeNode root = TreeNodeUtil.deserialize("1,#,2");
//        TreeNode root = TreeNodeUtil.deserialize("2,1,2");
        int[] arr = f.findModeII(root);
        f.output(IntStream.of(arr).mapToObj(Integer::valueOf).collect(Collectors.toList()));
    }

    private void output(List<Integer> list) {
        list.forEach(t-> System.out.print(t + ","));
        System.out.println();
    }

    /**
     * Time Optimization
     * Because above solution waste a lot in traversal tree
     *
     */
    public int[] findModeII(TreeNode root) {
        if (root == null) return new int[0];
        List<Integer> list = new ArrayList<>();
        int count = 1;
        int max = 0;
        traverse(list, root, null, count, max);
        int[] res = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            res[i] = list.get(i);
        }
        return res;
    }

    /**
     * Below function is example of incorrect method
     * because we cannot assign prev to root directly
     * otherwise prev and root will share same memory location,
     * and then it will lose meaning of comparsing leaf value of both tree nodes
     */
    private void traverseII(List<Integer> list, TreeNode root, TreeNode prev, int count, int max) {
        if (root == null) {
            return;
        }
        traverseII(list, root.left, prev, count, max);
        if (prev != null) {
            if (root.val == prev.val) {
                count++;
            } else {
                count = 1;
            }
        }
        if (count > max) {
            max = count;
            list.clear(); // have to use same memory location
            // below way isn't working, will cause empty list
//            list = new ArrayList<>();
            list.add(root.val);
        } else if (count == max) {
            list.add(root.val);
        }
        /**
         * Below is incorrect.
         */
        prev = root;
        traverseII(list, root.right, prev, count, max);
    }

    /**
     * Correct version
     */
    private void traverse(List<Integer> list, TreeNode root, TreeNode prev, int count, int max) {
        if (root == null) {
            return;
        }
        // traverse left first
        traverse(list, root.left, prev, count, max);
        // until left most
        // in order traversal
        if (prev != null) {
            if (prev.val == root.val) {
                count++;
            } else {
                count = 1;
            }
        }
        // rewrite below logic
        if (count >= max) {
            if (count > max) {
                list.clear();
            }
            max = count;
            list.add(root.val);
        }
        /*if (count == max) {
            list.add(root.val);
        } else if (count > max) {
            list.clear();
            list.add(root.val);
            max = count;
        }*/
        /** be careful */
        if (prev == null) {
            prev = new TreeNode(root.val);
        } else {
            // if prev is exist, then change value only, assign value of root to value of prev
            prev.val = root.val;
        }
        // traverse right
        traverse(list, root.right, prev, count, max);
    }
}
