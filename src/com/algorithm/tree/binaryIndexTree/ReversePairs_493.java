package com.algorithm.tree.binaryIndexTree;

import java.util.Arrays;
import java.util.IdentityHashMap;

/**
 * Created by charles on 5/6/17.
 * {@link com.algorithm.tree.binarySearchTree.ReversePairs_493}
 */
public class ReversePairs_493 {
    /**
     * array,
     * [1,3,2,3,1]
     * [1,1,2,3,3];
     *
     * we search for all elements greater then twice of current element
     * while inserting element itself into BIT,
     *
      8           ______________*
      4           ______*
      2           __*     __*
      1           *   *   *   *
     * indices: 0 1 2 3 4 5 6 7 8
     * 横坐标是x, 纵坐标是lowbit(x)
     *
     对于节点x，
     为左子结点，则父结点的编号是x+lowbit(x)，
     为右子结点，则父结点的编号是x-lowbit(x)
     *
     * reference: https://www.byvoid.com/zhs/blog/binary-index-tree
     * binary index tree can play a role as segement tree at certain time.
     * 凡是树状数组可以解决的问题，线段树都可以解决，反过来线段树可以解决的问题，树状数组不一定能解决
     * 树状数组(二进制索引树)的本质就是一种通过二进制位来维护一个序列前i和的数据结构。
     * 对于维护的序列A，定义C[i]=A[j+1]+...+A[i]，其中j为i的二进制表示中把最右边的1换成0的值。j的值可以通过lowbit求出，即i-lowbit(i)。
     lowbit(a)为2^(a的二进制表示末尾0的个数)。可以用下面式子求出
     lowbit(a)=a&(~a+1)
     或者根据补码的性质简化为
     lowbit(a)=a&(-a)
     */
    public int reversePairs(int[] nums) {
        int cnt = 0;
        int[] copy = Arrays.copyOf(nums, nums.length);
        int[] bit = new int[copy.length + 1]; // binary index tree array

        Arrays.sort(copy);

        int singleCnt = 0;
        int idx = 0;
        for (int i = 0; i < nums.length; i++) {
            idx = binarySearchIndex(copy, 2L * nums[i] + 1);
            // look for frequency of numbers larger than 2L * nums[i](current node)
            // thus, use sumPostfix(); current node as left, use x+lowbit(x) to get parent
            singleCnt = sumPostfix(bit, idx);
            cnt += singleCnt;
            // modify BIT when add new node
            // at this time, current node as right of parent, use x-lowbit(x) to get parent
            idx = binarySearchIndex(copy, nums[i]);
            modifyPrefix(bit, idx, 1);
        }
        return cnt;
    }
    //n     1110
    //~n+1  0010
    //&     0010
    //return number of tail zero of binary(given number)
    private int lowBit(int i) {
        // return n & (~n + 1);
        return i & -i;
    }
    /** regular sum method to calculate prefix sum from [0,k] */
    private int sumPrefix(int[] bit, int i) {
        int sum = 0;
        while (i > 0) {
            sum += bit[i];
            // current node is right node of parent
            // so move to parent, need to x - lowbit(x)
            i -= lowBit(i);
        }
        return sum;
    }
    /** sum method to calculate postfix sum from [k, len] */
    private int sumPostfix(int[] bit, int i) {
        int sum = 0;
        while (i < bit.length) {
            sum += bit[i];
            // current node is left node of parent
            // so move to parent, need to x + lowbit(x)
            i += lowBit(i);
        }
        return sum;
    }

    /** assume modify a[i] to increment delta
     * delta can be negative
     * then need to do same modification to the range where include a[i] in BIT
     * time complexity relate to digits of binary, O(lgn)
     */
    private void modifyPrefix(int[] bit, int i, int delta) {
        while (i > 0) {
            bit[i] += delta;
            // current node is right node of parent
            // so move to parent, need to x - lowbit(x)
            i -= lowBit(i);
        }
    }
    private void modifyPostfix(int[] bit, int i, int delta) {
        while (i < bit.length) {
            bit[i] += delta;
            // current node is left node of parent
            // so move to parent, need to x + lowbit(x)
            i += lowBit(i);
        }
    }
    private int binarySearchIndex(int[] arr, long val) {
        int l = 0, r = arr.length - 1;
        int m = 0;
        while (l < r) {
            m = l + (r - l) / 2;
            if (arr[m] >= val) {
                r = m - 1;
            } else {
                l = m + 1;
            }
        }
        return l + 1;
    }

    public static void main(String[] args) {
        ReversePairs_493 r = new ReversePairs_493();
        int[] a1 = {1,3,2,3,1};
        System.out.println(r.reversePairs(a1));
    }

    private void print(int[] arr) {
        Arrays.stream(arr).forEach(t->System.out.print(t+","));
        System.out.println();
    }
}
