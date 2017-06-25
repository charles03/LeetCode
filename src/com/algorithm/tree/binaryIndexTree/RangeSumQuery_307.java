package com.algorithm.tree.binaryIndexTree;

/**
 * Created by charles on 2/26/17.
 * Ref Link : http://www.geeksforgeeks.org/binary-indexed-tree-or-fenwick-tree-2/
 */
/**
 * Binary Indexed Trees (BIT or Fenwick tree):
 * https://www.topcoder.com/community/data-science/data-science-
 * tutorials/binary-indexed-trees/
 *
 * Example: given an array a[0]...a[7], we use a array BIT[9] to
 * represent a tree, where index [2] is the parent of [1] and [3], [6]
 * is the parent of [5] and [7], [4] is the parent of [2] and [6], and
 * [8] is the parent of [4]. I.e.,
 *
 * BIT[] as a binary tree:
 *            ______________*
 *            ______*
 *            __*     __*
 *            *   *   *   *
 * indices: 0 1 2 3 4 5 6 7 8
 *
 * BIT[i] = ([i] is a left child) ? the partial sum from its left most
 * descendant to itself : the partial sum from its parent (exclusive) to
 * itself. (check the range of "__").
 *
 * Eg. BIT[1]=a[0], BIT[2]=a[1]+BIT[1]=a[1]+a[0], BIT[3]=a[2],
 * BIT[4]=a[3]+BIT[3]+BIT[2]=a[3]+a[2]+a[1]+a[0],
 * BIT[6]=a[5]+BIT[5]=a[5]+a[4], BIT[7]=a[6]
 * BIT[8]=a[7]+BIT[7]+BIT[6]+BIT[4]=a[7]+a[6]+...+a[0], ...
 *
 * Thus, to update a[1]=BIT[2], we shall update BIT[2], BIT[4], BIT[8],
 * i.e., for current [i], the next update [j] is j=i+(i&-i) //double the
 * last 1-bit from [i].
 *
 * Similarly, to get the partial sum up to a[6]=BIT[7], we shall get the
 * sum of BIT[7], BIT[6], BIT[4], i.e., for current [i], the next
 * summand [j] is j=i-(i&-i) // delete the last 1-bit from [i].
 *
 * To obtain the original value of a[7] (corresponding to index [8] of
 * BIT), we have to subtract BIT[7], BIT[6], BIT[4] from BIT[8], i.e.,
 * starting from [idx-1], for current [i], the next subtrahend [j] is
 * j=i-(i&-i), up to j==idx-(idx&-idx) exclusive. (However, a quicker
 * way but using extra space is to store the original array.)
 */

public class RangeSumQuery_307 {
    private int[] nums;
    private int[] BIT;
    private int len;

    public RangeSumQuery_307(int[] nums) {
        this.nums = nums;
        this.len = nums.length;
        BIT = new int[len + 1];
        for (int i = 0; i < len; i++) {
            buildBIT(i, nums[i]);
        }
    }
    /**
     * update(index, val): Updates BIT for operation arr[index] += val
     // Note that arr[] is not changed here.  It changes
     // only BI Tree for the already made change in arr[].
     1) Initialize index as index+1.
     2) Do following while index is smaller than or equal to n.
     ...a) Add value to BITree[index]
     ...b) Go to parent of BITree[index].  Parent can be obtained by removing
     the last set bit from index, i.e., index = index + (index & (-index))
     */
    public void update(int idx, int val) {
        int diff = val - nums[idx];
        nums[idx] = val;
        buildBIT(idx, diff);
    }

    public int sumRange(int i, int j) {
        return getSum(j) - getSum(i - 1);
    }

    /**
     * index of parent of idx can be obtained using following formula
     * parent(i) = i + (i & -i)
     */
    private void buildBIT(int idx, int val) {
        idx++;
        while (idx <= len) {
            BIT[idx] += val;
            idx += lowBit(idx);
        }
    }
    /** it will give you the highest power of 2 that n is evenly divisible by.*/
    private int lowBit(int i) {
        // return n & (~n + 1);
        return i & -i;
    }

    /**
     * index of parent of idx can be obtained using following formular
     * parent(i) = i - (i & -i);
     */
    /**
     // Returns sum of arr[0..index] using BITree[0..n].  It assumes that
     // BITree[] is constructed for given array arr[0..n-1]
     1) Initialize sum as 0 and index as index+1.
     2) Do following while index is greater than 0.
     ...a) Add BITree[index] to sum
     ...b) Go to parent of BITree[index].  Parent can be obtained by removing
     the last set bit from index, i.e., index = index - (index & (-index))
     3) Return sum.
     */
    private int getSum(int idx) {
        int sum = 0;
        idx++;
        while (idx > 0) {
            sum += BIT[idx];
            idx -= lowBit(idx);
        }
        return sum;
    }

    public static void bitwiseToUnderstandChangeOfIndex() {
        for (int i = 1; i < 10; i++) {
            System.out.println(Integer.toBinaryString(i));
            System.out.println(Integer.toBinaryString(-i));
            System.out.println(Integer.toBinaryString(i & -i));
            System.out.println("------------");
        }
    }

    public static void main(String[] args) {
        RangeSumQuery_307.bitwiseToUnderstandChangeOfIndex();
    }
}
