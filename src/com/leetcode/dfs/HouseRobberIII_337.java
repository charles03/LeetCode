package com.leetcode.dfs;

import com.leetcode.tree.TreeNode;

/**
 * Created by charles on 6/18/17.
 * The thief has found himself a new place for his thievery again. There is only one entrance to this area, called the "root." Besides the root, each house has one and only one parent house. After a tour, the smart thief realized that "all houses in this place forms a binary tree". It will automatically contact the police if two directly-linked houses were broken into on the same night.

 Determine the maximum amount of money the thief can rob tonight without alerting the police.

 Example 1:
   3
  / \
 2   3
  \   \
  3   1
 Maximum amount of money the thief can rob = 3 + 3 + 1 = 7.
 Example 2:
     3
    / \
   4   5
  / \   \
 1   3   1
 Maximum amount of money the thief can rob = 4 + 5 = 9.
 */
public class HouseRobberIII_337 {
    /**
     * Good explanation: https://discuss.leetcode.com/topic/39834/step-by-step-tackling-of-the-problem
     * Step 1: use dfs to find max money that we can rob from binary tree at root
     *  split into two scenarios, root is robbed or not
     * the next level of subtrees that are available would be the four "grandchild-subtrees" (root.left.left, root.left.right, root.right.left, root.right.right).
     * However if root is not robbed, the next level of available subtrees would just be the two "child-subtrees" (root.left, root.right).
     *
     * step 2: having DP thought when in searching, we are repeating certain results, so worth keep into memorized
     * For example, to obtain rob(root), we need rob(root.left), rob(root.right), rob(root.left.left), rob(root.left.right), rob(root.right.left), rob(root.right.right); but to get rob(root.left),
     * we also need rob(root.left.left), rob(root.left.right), similarly for rob(root.right)
     *
     * Step 3: redefine dp state definition
     * If we were able to maintain the information about the two scenarios for each tree root, let's see how it plays out. Redefine rob(root) as a new function which will return an array of two elements,
     * the first element of which denotes the maximum amount of money that can be robbed if root is not robbed, while the second element signifies the maximum amount of money robbed if it is robbed.
     */
    // dp[i][0] is max money when not robbing current root node at position i
    // dp[i][1] is max money when robbing current root node at position i
    public int houseRobber3(TreeNode root) {
        int[] res = dfs(root);
        return Math.max(res[0], res[1]);
    }
    private int[] dfs(TreeNode root) {
        if (root == null) {
            return new int[] {0,0};
        }
        int[] left = dfs(root.left);
        int[] right = dfs(root.right);
        int[] curr = new int[2];
        curr[0] = Math.max(left[0], left[1]) + Math.max(right[0], right[1]);
        curr[1] = left[0] + right[0] + root.val;
        return curr;
    }

    /**
     * use auxiliary class as result type contain two fields
     * robbing and not robbing
     */
    private class ResultType {
        int rob;
        int notRob;
        public ResultType() {
            this.rob = 0;
            this.notRob = 0;
        }
    }
    public int houseRobberII3(TreeNode root) {
        ResultType resultType = dfsII(root);
        return Math.max(resultType.rob, resultType.notRob);
    }
    public ResultType dfsII(TreeNode root) {
        ResultType res = new ResultType();
        if (root == null) {
            return res;
        }
        ResultType left = dfsII(root.left);
        ResultType right = dfsII(root.right);

        res.rob = root.val + left.notRob + right.notRob;
        res.notRob = Math.max(left.rob, left.notRob) + Math.max(right.rob, right.notRob);
        return res;
    }

}
