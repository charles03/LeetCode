package com.leetcode.tree;

import java.util.*;

/**
 * Created by charles on 1/4/17.
 * Given a binary tree, find the lowest common ancestor (LCA) of two given nodes in the tree.
 According to the definition of LCA on Wikipedia: “The lowest common ancestor is defined between two nodes v and w as the lowest node in T
 that has both v and w as descendants (where we allow a node to be a descendant of itself).”
 */
public class LowestCommonAncestorOfaBinaryTree_236 {

    public TreeNode lowestCommonAncestorRecursion(TreeNode root, TreeNode p, TreeNode q) {
        // should compare value of tree node instead of node
        if (root == null || root.val == p.val || root.val == q.val) {
            return root;
        }
        TreeNode left = lowestCommonAncestorRecursion(root.left, p, q);
        TreeNode right = lowestCommonAncestorRecursion(root.right, p, q);

        // case when p and q are child, so return root as ancestor
        if (left != null && right != null) {
            return root;
        }
        // if one of sides has null of ancestor,
        if (left != null) {
            return left;
        } else {
            return right;
        }
    }

    public TreeNode lowestCommonAncestorIteration(TreeNode root, TreeNode p, TreeNode q) {
        Map<TreeNode, TreeNode> parent = new HashMap<>();
//        Stack<TreeNode> stack = new Stack<>();

        Deque<TreeNode> deque = new ArrayDeque<>();

        parent.put(root, null);
        deque.push(root);

        TreeNode node = null;
        // get parent relationship map
        System.out.println(parent.containsKey(new TreeNode(3)) + " " + p.hashCode());
        while (!parent.containsKey(p) || !parent.containsKey(q)) {
            node = deque.pop();
            if (node.left != null) {
                parent.put(node.left, node);
                deque.push(node.left);
            }
            if (node.right != null) {
                parent.put(node.right, node);
                deque.push(node.right);
            }
        }
        Set<TreeNode> ancestors = new HashSet<>();
        while (p != null) {
            ancestors.add(p); // store all ancestors from node p
            p = parent.get(p);
        }
        while (!ancestors.contains(q)) {
            q = parent.get(q); // backtrack all ancestors of node q to find lowest common
        }
        return q; // lowest common ancestor between q and p
    }

    public static void main(String[] args) {
        TreeNode root = TreeNodeUtil.deserialize("3,5,1,6,2,0,8,#,#,7,4");

        LowestCommonAncestorOfaBinaryTree_236 l = new LowestCommonAncestorOfaBinaryTree_236();

        System.out.println(l.lowestCommonAncestorRecursion(root, new TreeNode(5), new TreeNode(1)).val);
        System.out.println(root.val);
        System.out.println(l.lowestCommonAncestorIteration(root, new TreeNode(5), new TreeNode(4)).val);
    }
}
