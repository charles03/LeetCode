package com.leetcode.tree;

import com.sun.xml.internal.bind.v2.TODO;

/**
 * Created by charles on 1/8/17.
 * This class is based on {@link KthSmallestElementInBST_230}
 * when BST is modified (insert/delete operations) often
 * and you need to find kth smallest frequently > how to optimize
 *
 * Hint:
 1.Try to utilize the property of a BST.
 2.What if you could modify the BST node's structure?
 3.The optimal runtime complexity is O(height of BST).

 As for modified Tree structure.

 */
public class KthSmallestElementInaBST_Followup {

    private CountableTreeNode root;
    /**
     * Created by charles on 1/9/17.
     * TreeNode contain field to record current size
     * So that allow {@link KthSmallestElementInaBST_Followup} take O(height of tree) to
     * search kth smallest tree node
     */
    private class CountableTreeNode {
        public int val;
        public int size; // total size of tree node from current root node
        public CountableTreeNode left;
        public CountableTreeNode right;
        public CountableTreeNode(int x, int size) {
            this.val = x;
            this.size = size;
            left = null;
            right = null;
        }
    }

    public void put(int val) {
        root = put(root, val);
    }

    private CountableTreeNode put(CountableTreeNode node, int val) {
        if (node == null) {
            return new CountableTreeNode(val, 1);
        }
        if (node.val > val) {
            node.left = put(node.left, val);
        } else if (node.val < val) {
            node.right = put(node.right, val);
        } else {
            node.val = val;
        }
        node.size = 1 + size(node.left) + size(node.right);
        return node;
    }

    public int size() {
        return size(root);
    }

    private int size(CountableTreeNode node) {
        if (node == null) {
            return 0;
        } else {
            return node.size;
        }
    }

    public int select(int k) {
        return select(root, k);
    }

    private int select(CountableTreeNode node,  int k) {
        if (node == null) {
            return 0;
        }
        int leftSize = size(node.left);

        if (leftSize > k) {
            return select(node.left, k);
        } else if (leftSize < k) {
            return select(node.right, k - (leftSize+1));
        } else {
            return node.val;
        }
    }

    public static void main(String[] args) {
        KthSmallestElementInaBST_Followup k = new KthSmallestElementInaBST_Followup();

        k.put(5);
        k.put(4);
        k.put(8);
        System.out.println(k.select(2));
        k.put(1);
        System.out.println(k.select(2));
    }
}



