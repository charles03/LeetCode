package com.leetcode.dynamicprogram;

import com.leetcode.tree.TreeNode;
import com.leetcode.tree.TreeNodeUtil;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by charles on 1/29/17.
 * Given an integer n, generate all structurally unique BST's (binary search trees) that store values 1...n.

 For example,
 Given n = 3, your program should return all 5 unique BST's shown below.

    1         3     3      2      1
     \       /     /      / \      \
      3     2     1      1   3      2
     /     /       \                 \
    2     1         2                 3
 */
public class UniqueBinarySearchTreeII_95 {
    /**
     * it is intuitive to solve this problem by following the same algorithm.
     * Use Divide-and-Conquer style
     */

    public List<TreeNode> generateTrees(int n) {
        return generateSubtrees(1, n);
    }

    private List<TreeNode> generateSubtrees(int s, int e) {
        List<TreeNode> res = new LinkedList<>();
        if (s > e) {
            res.add(null); // empty tree
            return res;
        }
        for (int i = s; i <= e; i++) {
            List<TreeNode> leftSubtrees = generateSubtrees(s, i - 1);
            List<TreeNode> rightSubtrees = generateSubtrees(i + 1, e);

            for (TreeNode left : leftSubtrees) {
                for (TreeNode right :  rightSubtrees) {
                    TreeNode root = new TreeNode(i);
                    root.left = left;
                    root.right = right;
                    res.add(root);
                }
            }
        }
        return res;
    }

    public static void main(String[] args) {
        UniqueBinarySearchTreeII_95 u = new UniqueBinarySearchTreeII_95();
        List<TreeNode> res = u.generateTrees(5);
        System.out.println(res.size());
        res.forEach(treeNode -> System.out.println(TreeNodeUtil.serialize(treeNode)));
    }
}
