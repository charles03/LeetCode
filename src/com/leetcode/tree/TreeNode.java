package com.leetcode.tree;

/**
 * Created by charles on 12/22/16.
 */
public class TreeNode {
    public int val;
    public TreeNode left;
    public TreeNode right;
    public TreeNode(int x) {
        this.val = x;
        left = null;
        right = null;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TreeNode)) {
            return false;
        }
        TreeNode node = (TreeNode) o;
        /**
         * be careful, for tree node, no need to check equal for left/right
         */
        return node.val == ((TreeNode) o).val;
    }

    @Override
    /** may need override hashcode if use treenode class with hashmap or set */
    public int hashCode() {
        int result = 17;
        result = 31 * result + this.val;
        return result;
    }

}
