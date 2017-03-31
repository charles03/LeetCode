package com.leetcode.tree;

/**
 * Created by charles on 12/30/16.
 * Given n, how many structurally unique BST's (binary search trees) that store values 1...n?

 For example,
 Given n = 3, there are a total of 5 unique BST's

 First note that dp[k] represents the number of BST trees built from 1....k;
 Then assume we have the number of the first 4 trees: dp[1] = 1 ,dp[2] =2 ,dp[3] = 5, dp[4] =14 ,
 how do we get dp[5] based on these four numbers is the core problem here.

 The essential process is: to build a tree, we need to pick a root node,
 then we need to know how many possible left sub trees and right sub trees can be held under that node,
 finally multiply them.

 To build a tree contains {1,2,3,4,5}.
 First we pick 1 as root, for the left sub tree, there are none;
 for the right sub tree, we need count how many possible trees are there constructed from {2,3,4,5},
 apparently it's the same number as {1,2,3,4}. So the total number of trees under "1" picked as root is dp[0] * dp[4] = 14. (assume dp[0] =1).
 Similarly, root 2 has dp[1]*dp[3] = 5 trees. root 3 has dp[2]*dp[2] = 4, root 4 has dp[3]*dp[1]= 5 and root 5 has dp[0]*dp[4] = 14.
 Finally sum the up and it's done.

 Now, we may have a better understanding of the dp[k], which essentially represents the number of BST trees with k consecutive nodes.
 t is used as database when we need to know how many left sub trees are possible for k nodes when picking (k+1) as root.
 */
public class UniqueBinarySearchTrees_96 {
    /**
     * Thought: DP
     * State: dp[i] is total count of unique BSTs when i number of nodes,
     * iteratively pick num -> 1<= num <=i as root to build BST
     * example if num is 2 and i is 3;
     * BST root of 2, left tree has one node, right tree has one node
     *
     * Function: dp[i] = dp[0] * dp[i-1] + dp[1] * dp[i-2] +...+ dp[num-1] * dp[i - num];
     * Initialization: dp[0] = 1; dp[1] = 1;
     * Answer: dp[n];
     */
    public long numTrees(int n) {
        if (n == 0) {
            return 1l;
        }
        long[] dp = new long[n + 1];
        dp[0] = 1; // init
        dp[1] = 1;

        // num is current count of nodes for BST
        // pos is current position of root for BST
        for (int num = 2; num <= n; num++) {
            for (int pos = 1; pos <= num; pos++) {
                dp[num] += dp[pos - 1] * dp[num - pos]; // given "num" count of nodes, root is in pos position
                // dp[pos-1] is left of tree, and dp[num-pos] is right of tree;
            }
        }
        return dp[n];
    }

    /**
     * Another Solution: O(n) time complexity, better than DP
     * Thought: Math -> Catalan Number
     * https://en.wikipedia.org/wiki/Catalan_number
     * since choosing n numbers from a 2n set of numbers can be uniquely divided into 2 parts:
     * choosing i numbers out of the first n numbers and then choosing n-i numbers from the remaining n numbers.
     */
    public long numTreesCatalan(int n) {
        long num = 1;
        for (long i = 1; i <= n; i++) {
            num = num * (i + n) / i;
        }
        return num / (n + 1);
    }

    public static void main(String[] args) {
        UniqueBinarySearchTrees_96 u = new UniqueBinarySearchTrees_96();

        System.out.println(u.numTrees(0) == 1);
        System.out.println(u.numTrees(0) == u.numTrees(0));

        /**
         * if use long to represent catalan number, it only takes up to 35
         * otherwise hit maximum of long 9223372036854775807
         * if use int as result, takes up to 18;
         * otherwise maximum of int
         */
        for (int i = 0; i < 31; i++) {
            if (u.numTrees(i) != u.numTreesCatalan(i)) {
                System.out.println(i + ", " + u.numTrees(i) + ", " + u.numTreesCatalan(i));
            }
        }
    }
}
