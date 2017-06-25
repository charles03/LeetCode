package com.algorithm.tree.segmentTree;

/**
 * Created by charles on 2/25/17.
 * Given an integer array nums, find the sum of the elements between indices i and j (i ≤ j), inclusive.

 The update(i, val) function modifies nums by updating the element at index i to val.
 Example:
 Given nums = [1, 3, 5]

 sumRange(0, 2) -> 9
 update(1, 2)
 sumRange(0, 2) -> 8
 Note:
 The array is only modifiable by the update function.
 You may assume the number of calls to update and sumRange function is distributed evenly.
 */
public class RangeSumQueryMutable_307 {
    private int[] tree;
    private int len;

    public RangeSumQueryMutable_307(int[] nums) {
        if (nums.length > 0) {
            len = nums.length;
            tree = new int[len * 2];
            buildTree(nums);
        }
    }

    public void update(int pos, int val) {
        pos += len; // from bottom of segment tree back to top
        tree[pos] = val;

        while (pos > 0) {
            int left = pos;
            int right = pos;
            if (pos % 2 == 0) {
                right = pos + 1;
            } else {
                left = pos - 1;
            }
            // to update parent after child is updated
            tree[pos / 2] = tree[left] + tree[right];
            pos /= 2;
        }
    }

    public int sumRange(int left, int right) {
        left += len; // get leaf value
        right += len;
        int sum = 0;
        while (left <= right) {
            if ((left % 2) == 1) {
                sum += tree[left];
                left++;
            }
            if ((right % 2) == 0) {
                sum += tree[right];
                right--;
            }
            left /= 2;
            right /= 2;
        }
        return sum;
    }

    /**
     * build segment tree in array, instead of recursion
     */
    private void buildTree(int[] nums) {
        int j = 0;
        for (int i = len; i < 2 * len; i++) {
            tree[i] = nums[j];
            j++;
        }
        for (int i = len - 1; i > 0; i--) {
            tree[i] = tree[i * 2] + tree[i * 2 + 1];
        }
    }
}

