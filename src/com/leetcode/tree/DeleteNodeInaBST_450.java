package com.leetcode.tree;

/**
 * Created by charles on 1/2/17.
 * Given a root node reference of a BST and a key, delete the node with the given key in the BST.
 * Return the root node reference (possibly updated) of the BST.
 Basically, the deletion can be divided into two stages:

 Search for a node to remove.
 If the node is found, delete the node.
 root = [5,3,6,2,4,null,7]
 key = 3

     5
    /  \
    3   6
  / \   \
 2   4   7

 Given key to delete is 3. So we find the node with value 3 and delete it.

 One valid answer is [5,4,6,2,null,null,7], shown in the following BST.

      5
     / \
    4   6
  /     \
 2       7

 Another valid answer is [5,2,6,null,4,null,7].

   5
  / \
 2   6
  \   \
  4   7
 */
public class DeleteNodeInaBST_450 {
    /**
     * Recursively find the node that has the same value as the key, while setting the left/right nodes equal to the returned subtree
     Once the node is found, have to handle the below 4 cases
     node doesn't have left or right - return null
     node only has left subtree- return the left subtree
     node only has right subtree- return the right subtree
     node has both left and right - find the minimum value in the right subtree, set that value to the currently found node, then recursively delete the minimum value in the right subtree
     */
    public TreeNode deleteNodeRecursion(TreeNode root, int key) {
        if (root == null) {
            return null;
        }
        if (key < root.val) {
            root.left = deleteNodeRecursion(root.left, key);
        } else if (key > root.val) {
            root.right = deleteNodeRecursion(root.right, key);
        } else {
            if (root.left == null) { // only has left subtree
                return root.right;
            } else if (root.right == null) { // only has right subtree
                return root.left;
            }
            // has both left and right
            // find minimum value of node in right subtree, then recursively remove that node
            TreeNode leftMostInRightTree = findLeftMost(root.right);
            root.val = leftMostInRightTree.val;
            root.right = deleteNodeRecursion(root.right, root.val);
        }
        return root;
    }

    /**
     * without recursion.
     */
    public TreeNode deleteNodeI(TreeNode root, int key) {
        if (root == null || root.val == key) {
            return deleteRoot(root);
        }
        TreeNode node = root;
        while (true) {
            if (key > node.val) {
                if (node.right == null || node.right.val == key) {
                    node.right = deleteRoot(node.right);
                    break;
                }
                node = node.right;
            } else {
                if (node.left == null || node.left.val == key) {
                    node.left = deleteRoot(node.left);
                    break;
                }
                node = node.left;
            }
        }
        return root;
    }

    public TreeNode deleteNodeII(TreeNode root, int key) {
        TreeNode curr = root;
        TreeNode prev = null;

        while (curr != null && curr.val != key) {
            prev = curr;
            if (key < curr.val) {
                curr = curr.left;
            } else if (key > curr.val) {
                curr = curr.right;
            }
        }
        TreeNode next = deleteSingleNode(curr);
        if (prev == null) {
            return next;
        }
        if (prev.left == curr) {
            prev.left = next;
        } else {
            prev.right = next;
        }
        return root;
    }

    /** delete given root node */
    public TreeNode deleteRoot(TreeNode root) {
        if (root == null) {
            return null;
        }
        if (root.right == null) {
            return root.left;
        }
        // to give reference of root.right, instead pass as param directly
//        TreeNode leftMostInRight = findLeftMost(root.right);
        TreeNode rightTree = root.right;
        TreeNode leftMostInRight = findLeftMost(rightTree);
        // re-wire tree structure after remove original root.

        leftMostInRight.left = root.left;
        return root.right;
    }

    /** delete given input node from tree, return valid next node in tree */
    public TreeNode deleteSingleNode(TreeNode node) {
        if (node == null) { // case 1
            return null;
        }
        if (node.left == null) { // case 2
            return node.right;
        }
        if (node.right == null) { // case 3
            return node.left;
        }
        // case 4;
        TreeNode leftMostInRight = findLeftMost(node.right);
        leftMostInRight.left = node.left;
        return node.right;
    }

    /** from given node as root, find left most */
    public TreeNode findLeftMost(TreeNode node) {
        if (node == null) {
            return null;
        }
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    public static void main(String[] args) {
        TreeNode root = TreeNodeUtil.deserialize("5,3,6,2,4,#,7");
        DeleteNodeInaBST_450 d = new DeleteNodeInaBST_450();
        System.out.println(TreeNodeUtil.serialize(d.deleteNodeI(root, 3)));
        System.out.println(TreeNodeUtil.serialize(d.deleteNodeRecursion(root, 3)));
        System.out.println(TreeNodeUtil.serialize(d.deleteNodeII(root, 3)));
    }
}
