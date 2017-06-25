package com.algorithm.tree.segmentTree;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by charles on 2/16/17.
 * Given an integer array nums, return the number of range sums that lie in [lower, upper] inclusive.
 Range sum S(i, j) is defined as the sum of the elements in nums between indices i and j (i ≤ j), inclusive.

 Note:
 A naive algorithm of O(n2) is trivial. You MUST do better than that.

 Example:
 Given nums = [-2, 5, -1], lower = -2, upper = 2,
 Return 3.
 The three ranges are : [0, 0], [2, 2], [0, 2] and their respective sums are: -2, -1, 2.
 */
public class CountOfRangeSum_327 {

    private class SegmentNode {
        int count;
        long min;
        long max;
        SegmentNode left;
        SegmentNode right;
        SegmentNode(long min, long max) {
            this.min = min;
            this.max = max;
        }
    }

    private SegmentNode buildSegmentTree(Long[] arr, int low, int high) {
        if (low > high) {
            return null;
        }
        SegmentNode node = new SegmentNode(arr[low], arr[high]);
        if (low == high) {
            return node;
        }
        int mid = low + (high - low) / 2;
        node.left = buildSegmentTree(arr, low, mid);
        node.right = buildSegmentTree(arr, mid + 1, high);
        return node;
    }
    private void updateSegmentTree(SegmentNode node, Long val) {
        if (node == null) {
            return;
        }
        if (val >= node.min && val <= node.max) {
            node.count++;
            updateSegmentTree(node.left, val);
            updateSegmentTree(node.right, val);
        }
    }
    private int getCount(SegmentNode node, long min, long max) {
        if (node == null) {
            return 0;
        }
        // complete exclusive
        if (min > node.max || max < node.min) {
            return 0;
        }
        // complete inclusive
        if (min <= node.min && max >= node.max) {
            return node.count;
        }
        // partial
        return getCount(node.left, min, max) + getCount(node.right, min, max);
    }

    public int countRangeSum(int[] nums, int lower, int upper) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int cnt = 0;
        // why using Set
        /**
         * Because in this method, what really matters is the range of sum. So duplicates has no use at all.
         * You will know it after goint through the whole process.
         */
        Set<Long> valSet = new HashSet<>();
        // use Long to prevent stackoverflow
        long sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += (long) nums[i];
            valSet.add(sum);
        }
        //valSet now contains all sum of range(i, j) where i = 0 and j from 0 to nums.length - 1
        Long[] arr = valSet.toArray(new Long[0]);
        // Do not use primitive here, "long" does not work;
        Arrays.sort(arr);

        /**
         * Before diving into "buildSegmentTree" function, you can imagine the tree looks like this:
         * This is a binary tree, each node contains a range formed by "min" and "max".
         * the "min" of a parent node is determined by the minimum lower boundary of all its children
         * the "max" is determined by the maximum upper boundary of all its children.
         * And remember, the boundary value must be a sum of a certain range(i, j). And values between
         * min and max may not corresponding to a valid sum;
         * This node also contains a "Count" property which marks how many sub ranges under this node.
         */
        SegmentNode root = buildSegmentTree(arr, 0, arr.length - 1);
        for (int i = nums.length - 1; i >= 0; i--) {
            /**
             * Core part 1 : "updateTree" function will update nodes cnt value by plusing 1 if this node cotains range [sum(0, i)].
             * How?
             * Each leafe of the segment tree contains range [sum[0, i], sum[0,i]] where i starts from 1 to nums.length
             * so, we will definitely find the leafe if we search from the root of the tree;
             * And during the process of finding this leafe, update every node's count value by 1
             * because it must contains the leafe's range by definition.
             */
            updateSegmentTree(root, sum);
            /**
             * Core part 2 : why subtract nums[i] here ?
             * because of its usage in the next part;
             */
            sum -= (long) nums[i];
            /**
             * Core part 3 :
             * why sum + lower and sum + upper
             * In core part 2, sum is now the sum of range (0, i - 1), and it serves as a base now.
             * What base?
             * getCount method is trying to return how many valid subranges under [sum + lower, sum + upper]
             * we plus "sum" to range[lower, upper] is because we want it to search the ranges formed by all
             * ranges which starts from i - 1;
             * why ?
             * To understand this, let's imagine sum is 0, and it will be getCount(root, 0 + lower, 0 + upper)
             * this will return number of valid ranges formed by sum(0, j)
             * Oh yeah. Hope you accept this.
             * but we still need the number of valid of ranges formed by sum(i, j) where i is not 0
             * that is what "base" is doing now
             * sum serves as a base here which makes ranges must start from sum(0, i - 1)
             * really hard to explain...... Sorry
             */
            cnt += getCount(root, (long) lower + sum, (long) upper + sum);
        }
        return cnt;
    }
}
