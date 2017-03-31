package com.leetcode.tree;

/**
 * Created by charles on 1/6/17.
 * Given a binary search tree (BST), find the lowest common ancestor (LCA) of two given nodes in the BST.
 According to the definition of LCA on Wikipedia: “The lowest common ancestor is defined between two nodes v and w as the lowest node in T that has both v and w as descendants
 (where we allow a node to be a descendant of itself).”

          _______6______
         /              \
      ___2__          ___8__
     /      \        /      \
    0      _4       7       9
          /  \
         3   5
 For example, the lowest common ancestor (LCA) of nodes 2 and 8 is 6.
 Another example is LCA of nodes 2 and 4 is 2, since a node can be a descendant of itself according to the LCA definition.
 */
public class LowestCommonAncestorOfaBinarySearchTree_235 {
    /**
     * Unlike Binary Tree to search whole tree,
     * if p and q are smaller than root.val, then only search in left tree from current root node
     * vice versa when p and q are larger root.val then only search in right tree
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) {
            return null;
        }
        if (p == null) {
            return q;
        }
        if (q == null) {
            return p;
        }
        while (root != null) {
            if (p.val < root.val && q.val < root.val) {
                root = root.left;
            } else if (p.val > root.val && q.val > root.val) {
                root = root.right;
            } else {
                break;
            }
        }
        return root;
    }

    public TreeNode lowestCommonAncestorRecursion(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) {return null;}
        if (p == null) {return q;}
        if (q == null) {return p;}

        if (root.val < Math.min(p.val, q.val)) {
            return lowestCommonAncestorRecursion(root.right, p, q);
        }
        if (root.val > Math.max(p.val, q.val)) {
            return lowestCommonAncestorRecursion(root.left, p, q);
        }
        return root;
    }

    public static void main(String[] args) {
        TreeNode root = TreeNodeUtil.deserialize("6,2,8,0,4,7,9,#,#,3,5");
        LowestCommonAncestorOfaBinarySearchTree_235 l = new LowestCommonAncestorOfaBinarySearchTree_235();
        System.out.println(l.lowestCommonAncestor(root, new TreeNode(2), new TreeNode(8)).val);

        System.out.println(l.lowestCommonAncestorRecursion(root, new TreeNode(2), new TreeNode(4)).val);
    }
}
