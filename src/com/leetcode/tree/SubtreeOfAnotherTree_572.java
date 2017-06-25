package com.leetcode.tree;

/**
 * Created by charles on 5/17/17.
 * Given two non-empty binary trees s and t, check whether tree t has exactly the same structure and node values with a subtree of s. A subtree of s is a tree consists of a node in s and all of this node's descendants. The tree s could also be considered as a subtree of itself.

 Example 1:
 Given tree s:

     3
    / \
   4   5
  / \
 1   2
 Given tree t:
   4
  / \
 1   2
 Return true, because t has the same structure and node values with a subtree of s.
 Example 2:
 Given tree s:

     3
    / \
   4   5
  / \
 1   2
    /
   0
 Given tree t:
   4
  / \
 1   2
 Return false.
 */
public class SubtreeOfAnotherTree_572 {
    /**
     * treat every node of the given tree tt as the root,
     * treat it as a subtree and compare the corresponding subtree with the given subtree ss for equality. For checking the equality, we can compare the all the nodes of the two subtrees.
     */
    public boolean isSubtree(TreeNode s, TreeNode t) {
        return traverse(s, t);
    }
    public boolean traverse(TreeNode s, TreeNode t) {
        return s != null
                && (isEquals(s,t)
                    || traverse(s.left, t)
                    || traverse(s.right, t));
    }
    public boolean isEquals(TreeNode x, TreeNode y) {
        if (x == null && y == null) {
            return true;
        }
        if (x == null || y == null) {
            return false;
        }
        return x.val == y.val
                && isEquals(x.left, y.left)
                && isEquals(x.right, y.right);
    }
}
