package com.leetcode.math;

/**
 * Created by charles on 5/13/17.
 * Given integers n and k, find the lexicographically k-th smallest integer in the range from 1 to n.

 Note: 1 ≤ k ≤ n ≤ 109.

 Example:

 Input:
 n: 13   k: 2

 Output:
 10

 Explanation:
 The lexicographical order is [1, 10, 11, 12, 13, 2, 3, 4, 5, 6, 7, 8, 9], so the second smallest number is 10.
 */
public class KthSmallestInLexicographicalOrder_440 {
    /**
     * {@link com.leetcode.array.LexicographicalNumbers_386}
     * Solution one, to construct first k lexicographical number
     * bad performance O(n) time complexity.
     */
    public int findKthNumber(int n, int k) {
        int res = 0;
        /** use long to avoid loss of accuracy between long and int */
        for (long i = 1, curr = 1; i <= k; i++) {
            res = (int)curr;
            if (curr * 10 <= n) {
                curr *= 10;
            } else {
                while (curr % 10 == 9 || curr == n) {
                    curr /= 10;
                }
                curr++;
            }
        }
        return res;
    }

    /**
     * because lexicographical order is denary tree (each node has 10 children)
     * so find kth elem is to do a k step preorder traverse of the tree.
     * we don't really need to do a exact k steps preorder traverse of denary tree.
     * idea is to calculate the steps between curr and curr + 1 (neighbor nodes in same level)
     * in order to skip unnecessary moves
     *
     * if (gapStep <= k) it means we can move to curr + 1, and narrow dow k to (k - gapSteps)
     * else if (gap > k) it means curr + 1 is behind target node in the preorder path, cannot jump to curr +1
     *         then is to move forward only 1 step and repeat iteration.
     *
     * how to calculate gap steps
     * let n1 = curr, n2 = curr + 1 at the same level;
     * n2 is always the next right node beside n1's rightmost node
     *
     * steps depends on accumulation of intervals at each level.
     * each level, need to check n2 as exclusive should not exceet given number
     *
     * this current interval = Math.min(n+1, n2) - n1;
     * and then move n2 and n1 to next child level n2*=10, n1*=10;
     */
    public int findKthNumberII(int n, int k) {
        int curr = 1;
        k = k - 1; // above take one step
        int step = 0;
        while (k > 0) {
            step = calculateSteps(n, curr, curr + 1);
            if (step <= k) {
                curr += 1; // go to curr + 1 directly
                k -= step;
            } else {
                curr *= 10; // go to child level
                k -= 1; // move step by step
            }
        }
        return curr;
    }
    // use long in case of overflow
    // how to calculate step is to accumulate intervals at each level
    /**
     * [res, res+1)
     * [res*10, (res+1)*10)
     * [res*100, (res+1)*100)
     *
     * besides, exclusive should not exceed given number
     */
    public int calculateSteps(int n, long inclusive, long exclusive) {
        int interval = 0;
        int exclusiveEdge = n+1;
        while (inclusive <= n) {
            interval += Math.min(exclusiveEdge, exclusive) - inclusive;
            inclusive *= 10;
            exclusive *= 10;
        }
        return interval;
    }

    public static void main(String[] args) {
        KthSmallestInLexicographicalOrder_440 k = new KthSmallestInLexicographicalOrder_440();
        System.out.println(k.findKthNumber(957747794, 424238336));
    }
}
