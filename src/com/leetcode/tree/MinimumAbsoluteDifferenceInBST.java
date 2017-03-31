package com.leetcode.tree;

import java.util.Stack;

/**
 * Created by charles on 3/26/17.
 * Given a binary search tree with non-negative values, find the minimum absolute difference between values of any two nodes.

 Example:

 Input:

 1
  \
   3
   /
  2

 Output:
 1

 Explanation:
 The minimum absolute difference is 1, which is the difference between 2 and 1 (o
 */
public class MinimumAbsoluteDifferenceInBST {
    /**
     * Naive Thought: because of BST,
     * diff of current node = min(
     *      diff(node, node.left),
     *      diff(node, node.right),
     *      diff(node, rightmost(node.left))
     *      diff(node, leftmost(node.right))
     *  )
     *  similar solution https://discuss.leetcode.com/topic/80813/very-easy-to-understand-java-solution
     */
    public int getMinimumDifference(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return getMinDiffHelper(root);
    }
    private int getMinDiffHelper(TreeNode root) {
        if (root == null) {
            return Integer.MAX_VALUE;
        }
        int leftMin = Integer.MAX_VALUE;
        int rightMin = Integer.MAX_VALUE;
        int bstLeftDiff = Integer.MAX_VALUE;
        int bstRightDiff = Integer.MAX_VALUE;
        int leftDiff = Integer.MAX_VALUE;
        int rightDiff = Integer.MAX_VALUE;
        if (root.left != null) {
            // due to BST, left < root < right
            leftDiff = root.val - root.left.val;
            leftMin = getMinDiffHelper(root.left);
            bstLeftDiff = root.val - getRightmostNode(root.left).val;
        }
        if (root.right != null) {
            rightDiff = root.right.val - root.val;
            rightMin = getMinDiffHelper(root.right);
            bstRightDiff = getLeftmostNode(root.right).val - root.val;
        }

        return Math.min(
                Math.min(Math.min(leftDiff, rightDiff),
                        Math.min(leftMin, rightMin)),
                Math.min(bstLeftDiff, bstRightDiff));
    }

    private TreeNode getLeftmostNode(TreeNode node) {
        while (node != null && node.left != null) {
            node = node.left;
        }
        return node;
    }
    private TreeNode getRightmostNode(TreeNode node) {
        while (node != null && node.right != null) {
            node = node.right;
        }
        return node;
    }

    /**
     * Better solution
     * Use In-Order traversal (left -> root -> right)
     * Then data will be sorted, returned result should be min(any adjacent of two nodes)
     */
    private int min = Integer.MAX_VALUE;
    private Integer prev = null; // as object

    public int getMinimumDifferenceII(TreeNode root) {
        if (root == null) {
            return min;
        }
        /** traverse left first */
        getMinimumDifferenceII(root.left);
        /** after in root, to get min by compare root value with previous value */
        if (prev != null) {
            min = Math.min(min, root.val - prev);
        }
        prev = root.val; // update prev
        /** traverse right then */
        getMinimumDifferenceII(root.right);
        return min;
    }

    /** use Iteration instead of Recursion, by Stack */
    public int getMinimumDifferenceIII(TreeNode root) {
        int min = Integer.MAX_VALUE;
        Stack<TreeNode> stack = new Stack<>();
        TreeNode curr = null;
        TreeNode prev = null;
        while (curr != null || !stack.isEmpty()) {
            /** traverse left first */
            while (curr != null) {
                stack.push(curr);
                curr = curr.left;
            }
            /** then right */
            curr = stack.pop();
            if (prev != null) {
                min = Math.min(min, curr.val - prev.val);
            }
            prev = curr;
            curr = curr.right;
        }
        return min;
    }

    /** make use of property of BST that value of nodes is bounded by their "prev" and "next" node*/
    public int getMinimumDifferenceIV(TreeNode root) {
        return minDiffByBounds(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
    private int minDiffByBounds(TreeNode root, int low, int high) {
        if (root == null) {
            if (low == Integer.MIN_VALUE || high == Integer.MAX_VALUE) {
                return Integer.MAX_VALUE;
            }
            return high - low;
        }
        int leftMin = minDiffByBounds(root.left, low, root.val);
        int rightMin = minDiffByBounds(root.right, root.val, high);
        return Math.min(leftMin, rightMin);
    }

    /** below method is incorrect*/
    /**
     * The issue is calculation (1 - Integer.MIN_VALUE (-2147483648) = -2147483647) instead of Integer.MAX_VALUE(2147483647)
     * which cause leftBound getting incorrect bounding value.
     */
    private int minDiffWrong(TreeNode root, int low, int high) {
        if (root == null) {
            return Integer.MAX_VALUE;
        }
        System.out.println(String.format("val: %d, low: %d, high: %d", root.val, low, high));
        int leftMin = minDiffWrong(root.left, low, root.val);
        int rightMin = minDiffWrong(root.right, root.val, high);
        int leftBound = root.val - low;
        int rightBound = high - root.val;
        return Math.min(Math.min(leftBound, rightBound), Math.min(leftMin, rightMin));
    }

    public static void main(String[] args) {
        TreeNode root = TreeNodeUtil.deserialize("1,#,3,2");
        System.out.println(TreeNodeUtil.serialize(root));
        MinimumAbsoluteDifferenceInBST m = new MinimumAbsoluteDifferenceInBST();
        System.out.println(m.getMinimumDifferenceIV(root));

        /** below is why issue happend */
        m.minDiffWrong(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
        System.out.println(1-Integer.MIN_VALUE);
        System.out.println(Integer.MIN_VALUE);
    }
}
