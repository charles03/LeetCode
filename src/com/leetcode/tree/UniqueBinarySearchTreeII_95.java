package com.leetcode.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by charles on 1/1/17.
 * Given an integer n, generate all structurally unique BST's (binary search trees) that store values 1...n.
 For example,
 Given n = 3, your program should return all 5 unique BST's shown below.
 */
public class UniqueBinarySearchTreeII_95 {

    public List<TreeNode> generateTrees(int n) {
        return generateTreeHelper(1, n);
    }

    private List<TreeNode> generateTreeHelper(int start, int end) {
        List<TreeNode> list = new ArrayList<>();

        if (start > end) {
            list.add(null);
            return list;
        }
        if (start == end) {
            list.add(new TreeNode(start));
//            System.out.println("when return " + start);
            return list;
        }

        List<TreeNode> left, right;

        for (int i = start; i <= end; i++) {
            left = generateTreeHelper(start, i - 1);
            right = generateTreeHelper(i + 1, end);

            for (TreeNode lNode : left) {
                for (TreeNode rNode : right) {
                    TreeNode node = new TreeNode(i); // current root
                    node.left = lNode;
                    node.right = rNode;
                    list.add(node);
                }
            }
        }
        return list;
    }

    public static void main(String[] args) {
        UniqueBinarySearchTreeII_95 u = new UniqueBinarySearchTreeII_95();
        List<TreeNode> list = u.generateTrees(5);
        outputCount(list);

        System.out.println();
        for (TreeNode node : list) {
            System.out.println(TreeNodeUtil.serialize(node));
        }
    }

    private static void outputCount(List<TreeNode> list) {
        list.stream().collect(Collectors.groupingBy(
                t -> t.val, Collectors.counting()
        )).forEach((v,c) -> System.out.println("val " + v + ", count " + c));
    }
}
