package com.algorithm.tree.binaryTree;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Created by charles on 10/1/16.
 *
 * Given a regular binary tree with left, right and peer node pointers.
 * The left and the right pointers are already populated.
 * We need to make the peer pointer point to the next right neighbor on the same level.
 *
 * Link: http://techieme.in/binary-tree-linking-neighbors/
 *
 * iteration solution based on BFS has disadvantage of requiring addtional O(N) space
 * Recursion solution will has disadvantage of using stack memmory
 */
public class BinaryTreeLinkingNeighbor {
    /* use BFS */
    private TreeNode root;

    public void connectInApproachOne(TreeNode root) {
        if (root == null) {
            return;
        }
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.add(root);
        int size = 0;
        TreeNode node = null;
        TreeNode nextNode = null;
        while (!queue.isEmpty()) {
            size = queue.size();
            node = queue.remove();
            if (node.left != null) {
                queue.add(node.left);
            }
            if (node.right != null) {
                queue.add(node.right);
            }

            for (int i = 1; i < size; i++) {
                nextNode = queue.remove();
                node.peer = nextNode;
                System.out.println(node.val + " ---> " + nextNode.val);
                node = nextNode;
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }
        }
    }

    /* use Recursion to save space */

    /**
     * If P has a left child PL then
     * If P has a right child PR then point PL‘s peer pointer to the right child PR.
     * Else, find the next neighbor of the PL and point PL‘s peer pointer to the next neighbor.
     * If P has right child PR then
     * Find the next neighbor of the PR and point PR‘s the peer pointer to the next neighbor.
     */
    public void connectInApproachTwo(TreeNode root) {
        if (root == null) {
            return;
        }
        TreeNode neighbor = null;
        neighbor = getNeighbor(root);
        if (root.left != null) {
            if (root.right != null) {
                printPeer(root.left, root.right);
                root.left.peer = root.right;
            } else {
                printPeer(root.left, neighbor);
                root.left.peer = neighbor;
            }
        }
        if (root.right != null) {
            printPeer(root.right, neighbor);
            root.right.peer = neighbor;
        }

        //recursion
        connectInApproachTwo(root.left);
        connectInApproachTwo(root.right);

    }
    private void printPeer(TreeNode n1, TreeNode n2) {
        String node;
        node = n2 == null ? "null" : String.valueOf(n2.val);
        System.out.println(n1.val + " --> " + node);
    }

    private TreeNode getNeighbor(TreeNode node) {
        while (node.peer != null) {
            if (node.peer.left != null) {
                return node.peer.left;
            }
            if (node.peer.right != null) {
                return node.peer.right;
            }
            node = node.peer;
        }
        return null;
    }

    public static void main(String[] args) {
        BinaryTreeLinkingNeighbor b = new BinaryTreeLinkingNeighbor();
        b.root = new TreeNode(0);
        b.root.left = new TreeNode(1);
        b.root.right = new TreeNode(2);
        b.root.left.left = new TreeNode(3);
        b.root.right.right = new TreeNode(4);
        b.root.left.left.left = new TreeNode(5);
        b.root.left.left.right = new TreeNode(6);
        b.root.right.right.left = new TreeNode(7);

//        b.connectInApproachOne(b.expTreeNode);
        b.connectInApproachTwo(b.root);
        b.printTree(b.root);
    }

    public void printTree(TreeNode root) {
        // python has api drawTree by import lib
        // url https://github.com/msbanik/drawtree

        // or implement method by self
        // http://algorithms.tutorialhorizon.com/level-order-traversal-print-each-level-in-separate-line/
        // http://stackoverflow.com/questions/4965335/how-to-print-binary-tree-diagram

        // get height or group by each level up
        // http://stackoverflow.com/questions/4965335/how-to-print-binary-tree-diagram
    }
}

class TreeNode {
    public int val;
    public TreeNode left;
    public TreeNode right;
    public TreeNode peer;

    public TreeNode(int x) {
        this.val = x;
        this.left = null;
        this.right = null;
        this.peer = null;
    }
}
