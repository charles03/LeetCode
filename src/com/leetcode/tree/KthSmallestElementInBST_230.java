package com.leetcode.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by charles on 1/6/17.
 * Given a binary search tree, write a function kthSmallest to find the kth smallest element in it.

 Note:
 You may assume k is always valid, 1 ≤ k ≤ BST's total elements.

 Follow up:
 What if the BST is modified (insert/delete operations) often and you need to find the kth smallest frequently? How would you optimize the kthSmallest routine?
 */
public class KthSmallestElementInBST_230 {

    /** DFS solution */
    public int kthSmallestDFS(TreeNode root, int k) {
        if (k == 0) { // not necessary edge case, actually, input k should be valid
            return 0;
        }
        int size = getSizeByDFS(root.left);
        if (k <= size) {
            return kthSmallestDFS(root.left, k);
        } else if (k > size + 1) {
            return kthSmallestDFS(root.right, k - (size + 1)); // 1 is counted as current node
        }
        return root.val;
    }

    private int getSizeByDFS(TreeNode node) {
        if (node == null) {
            return 0;
        }
        return 1 + getSizeByDFS(node.left) + getSizeByDFS(node.right);
    }

    /** based on morris inorder traversal, to return kth smallest */
    public int kthSmallestMorrisTraversal(TreeNode root, int k) {
        if (root == null || k <= 0) {
            return 0;
        }
        TreeNode current = root;
        TreeNode prev = null;
        List<Integer> list = new ArrayList<>(); // used for store all value of tree node in inorder sequence
        while (current != null) {
            if (current.left == null) {
                list.add(current.val); // add node into list
                current = current.right;
            } else {
                // to find predecessor
                prev = current.left;
                while (prev.right != null && prev.right != current) {
                    prev = prev.right;
                }
                /** below implementation is not good, it modified tree but didn't revert back */
//                temp = current.left;
//                current.left = null;
//                prev.right = current;
//                current = temp;

                /* make current as right child of its inorder predecessor */
                if (prev.right == null) {
                    prev.right = current;
                    current = current.left;
                } else {
                /* revert change made in if part to restore the original tree, fix right child of predecessor */
                    prev.right = null;
                    list.add(current.val); // inorder traversal, need add into list under this condition
                    current = current.right;
                }
            }
        }
//        list.stream().forEach(t -> System.out.print(t + ","));
//        System.out.println();
        return list.get(k - 1);
    }

    public static void main(String[] args) {
        TreeNode root = TreeNodeUtil.deserialize("4,2,6,1,3,5,7");
        KthSmallestElementInBST_230 k = new KthSmallestElementInBST_230();
//        System.out.println(k.kthSmallestDFS(root, 0));
//        System.out.println(k.kthSmallestMorrisTraversal(root, 2));
//        System.out.println(TreeNodeUtil.serialize(root));
        for (int i = 0; i < 6; i++) {
            if (k.kthSmallestMorrisTraversal(root, i) != k.kthSmallestDFS(root, i)) {
                System.out.println(k.kthSmallestMorrisTraversal(root, i));
                System.out.println(k.kthSmallestDFS(root, i));
            }
        }
    }
}
