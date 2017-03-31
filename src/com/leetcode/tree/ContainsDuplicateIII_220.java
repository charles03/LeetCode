package com.leetcode.tree;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

/**
 * Created by charles on 2/27/17.
 * Given an array of integers, find out whether there are two distinct indices i and j in the array
 * such that the absolute difference between nums[i] and nums[j] is at most t and the absolute difference between i and j is at most k.
 */
public class ContainsDuplicateIII_220 {
    /**
     * use TreeSet solution, similar to Binary Search treee
     * Apply Long primitive type is for edge case [-1, 2147483647], k = 1, t = 2146483647
     */
    public boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t) {
        if (nums == null || nums.length == 0) {
            return false;
        }
        TreeSet<Long> set = new TreeSet<>();
        set.add((long) nums[0]);

        /**
         * The time complexity of TreeSet.subSet is O(1) because it essentially creates a wrapper around the original set.
         * However, its isEmpty operation is not instant because it searches for successors/predecessors, which, I believe, is O(log k).
         * So the complexity is O(n log k) like other tree-based solutions.
         */
        for (int i = 1; i < nums.length; i++) {
            // make sure tree set always has k size
            if (i > k) {
                set.remove((long) nums[i-k-1]);
            }
            long left = (long) nums[i] - t;
            long right = (long) nums[i] + t;
            // to determine whether conditions are matched
            if (left <= right && !set.subSet(left, right + 1).isEmpty()) {
                return true;
            }
            set.add((long) nums[i]);
        }
        return false;
    }

    /**
     * Second solution : Bucket
     * if diff(nums[i], nums[j]) < t, then num[i] / t and num[j] / t should be in same bucket
     */
    public boolean containsNearbyAlmostDuplicateII(int[] nums, int k, int t) {
        if (k < 1 || t < 0) {
            return false;
        }
        Map<Long, Long> map = new HashMap<>();
        /**
         * the complication is negative integers are allowed, simple num / t will shrink everthing toward 0
         * therefore, we can just re-position every elemnt to start from Integer.MIN_VALUE
         */
        for (int i = 0; i < nums.length; i++) {
            long mappedNum = (long) nums[i] - Integer.MIN_VALUE;
            long bucketIdx = mappedNum / ((long)t + 1);
            if (map.containsKey(bucketIdx)
                    || (map.containsKey(bucketIdx - 1) && mappedNum - map.get(bucketIdx - 1) <= t)
                    || (map.containsKey(bucketIdx + 1) && map.get(bucketIdx + 1) - mappedNum <= t)) {
                return true;
            }
            // limit size to k
            /**
             * use (t + 1) as bucket size to get right of case when t = 0
             */
            if (map.entrySet().size() >= k) {
                long lastBucketIdx = ((long)nums[i - k] - Integer.MIN_VALUE) / ((long) t + 1);
                map.remove(lastBucketIdx);
            }
            // add new into map
            map.put(bucketIdx, mappedNum);
        }
        return false;
    }


    /******
     * Third solution : Binary Search Tree
     * TODO, below is incomplete, because failed in test cases
     * it seems to fail in handling duplicates
     */
    private TreeNode root;

    public boolean containsNearbyAlmostDuplicateIII(int[] nums, int k, int t) {
        for (int i = 0; i < nums.length; i++) {
            if (i > k) {
                remove(nums[i-k-1]);
            }
            insert(nums[i]);
            if (search((long)nums[i], t)) {
                return true;
            }
        }
        return false;
    }
    TreeNode insert(int val) {
        root = insert(root, val);
        return root;
    }
    TreeNode insert(TreeNode node, int val) {
        if (node == null) {
            node = new TreeNode(val);
        } else if (val > node.val) {
            node.right = insert(node.right, val);
        } else if (val < node.val) {
            node.left = insert(node.left, val);
        }
        return node;
    }

    boolean search(long val, int t) {
        return search(root, val, t);
    }
    boolean search(TreeNode node, long val, int t) {
        if (node == null) {
            return false;
        } else if (Math.abs(val - node.val) <= t) {
            return true;
        } else if (node.val - t > val) {
            return search(node.left, val, t);
        } else {
            return search(node.right, val, t);
        }
    }

    TreeNode remove(int val) {
        root = remove(root, val);
        return root;
    }
    TreeNode remove(TreeNode node, int val) {
        if (node == null) {
            return null;
        }
        if (val < node.val) {
            node.left = remove(node.left, val);
            return node;
        }
        if (val > node.val) {
            node.right = remove(node.right, val);
            return node;
        }
        if (node.left == null) {
            node = node.right;
            return node;
        }
        if (node.right == null) {
            node = node.left;
            return node;
        }
        node.val = findMin(node.right).val;
        node.right = remove(node.right, node.val);
        return node;
    }
    TreeNode findMin(TreeNode node) {
        if (node == null) {
            return null;
        }
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }


    public static void main(String[] args) {
        ContainsDuplicateIII_220 c = new ContainsDuplicateIII_220();
        int[] n1 = {-1,3,5,7,-6,-7,5};
        for (int k = 0; k < 5; k++) {
            for (int t = 0; t < 10; t++) {
                if (c.containsNearbyAlmostDuplicate(n1, k, t) != c.containsNearbyAlmostDuplicateIII(n1, k, t)) {
                    System.out.println(k + ", " + t);
                }
            }
        }
    }
}
