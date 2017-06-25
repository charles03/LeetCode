package com.algorithm.tree.binarySearchTree;

import java.util.Arrays;

/**
 * Created by charles on 5/4/17.
 * Given an array nums, we call (i, j) an important reverse pair if i < j and nums[i] > 2*nums[j].

 You need to return the number of important reverse pairs in the given array.

 Example1:

 Input: [1,3,2,3,1]
 Output: 2
 Example2:

 Input: [2,4,3,5,1]
 Output: 3
 */
public class ReversePairs_493 {
    /**
     * Thought:
     * For partition recurrence relation, setting i = 0, j = n - 1, m = (n-1)/2, we have:
     T(0, n - 1) = T(0, m) + T(m + 1, n - 1) + C
     where the subproblem C now reads "find the number of important reverse pairs
     with the first element of the pair coming from the left subarray nums[0, m]
     while the second element of the pair coming from the right subarray nums[m + 1, n - 1]".

     Again for this subproblem, the first of the two aforementioned conditions is met automatically.
     As for the second condition, we have as usual this plain linear scan algorithm,
     applied for each element in the left (or right) subarray. This, to no surprise, leads to the O(n^2) naive solution.

     Fortunately the observation holds true here that the order of elements in the left or right subarray does not matter,
     which prompts sorting of elements in both subarrays. With both subarrays sorted,
     the number of important reverse pairs can be found in linear time by employing the so-called two-pointer technique:
     one pointing to elements in the left subarray while the other to those in the right subarray
     and both pointers will go only in one direction due to the ordering of the elements.

     since we need to partition array into halves, to adapt merge sort.

     [a1, a2, a3, a4, a5, a6]
         /           \
     [a1, a2, a3] [a4, a5, a6]
        /     \      /       \
     [a1, a2] [a3] [a4, a5] [a6]

     from bottom to up. while sorting [a1, a2], to count whether there is matching the num[i] > 2 * num[j];
     then sort [a3] into [a1,a2], use two-pointer to merge a3 into array in linear time, and also record counts of matching criteria.
     repeat above process until merge two sub-array.
     */
    public int reversePairs(int[] nums) {
        return mergeSortHelper(nums, 0, nums.length - 1);
    }
    // return result is numbers of reverse pair matching requirement
    private int mergeSortHelper(int[] nums, int start, int end) {
        if (start >= end) {
            return 0;
        }
        int mid = start + (end - start) / 2; // split into two parts
        int count = mergeSortHelper(nums, start, mid) + mergeSortHelper(nums, mid + 1, end); // divide and conquer
        // before: having sorted left subarray [start, mid] and right subarray [mid+1, end]
        // then call mergesort method to merge two subs and return new count;
        // besides, use auxiliary array to help update origin array into sorted
        count += mergeSort(nums, start, mid, end);
        return count;
    }
    private int mergeSort(int[] nums, int l, int m, int r) {
        int[] merge = new int[r - l + 1];
        int i = l; // left sub merge index
        int j = m + 1; // right sub merge index
        int pointer = m + 1; // pointer to find max index where num[i] > 2*num[pointer]
        int k = 0; // merge index in auxiliary array
        int cnt = 0; // var to record total number of reverse pairs in the round of mergesort
        while (i <= m) {
            //
            while (pointer <= r && nums[i] > 2L * nums[pointer]) {
                // use 2L is to convert int to long in order to handel corner case
                // move pointer at right until not meet requirement num[i] > 2 * num[j]
                pointer++;
            }
            cnt += pointer - (m+1);
            // second step to merge small elem at right sub into left sub
            while (j <= r && nums[i] >= nums[j]) {
                merge[k++] = nums[j++];
            }
            // then merge elem at left
            merge[k++] = nums[i++];
        }
        // merge remain at right
        while (j <= r) {
            merge[k++] = nums[j++];
        }
        // bulk copy auxiliary sorted array back to input array.
        // merge array is source, nums is destination.
        System.arraycopy(merge, 0, nums, l, merge.length);
        return cnt;
    }

    /** short line version but slower performance in sort*/
    public int mergeSortHelperII(int[] nums, int l, int r) {
        if (l >= r) {return 0;}
        int mid = l + (r-l)>>1;
        int cnt = mergeSortHelperII(nums,l,mid) + mergeSortHelperII(nums, mid+1, r);
        for (int i=l, j=mid+1; i <= mid; i++) {
            while (j <= r && nums[i] > 2L * nums[j]) {
                j++;
            }
            cnt += j-(mid+1);
        }
        // use system sort method to sort [start, end] altogether
        Arrays.sort(nums, l, r);
        return cnt;
    }

    /**
     * Second Solution Binary Index Tree (BIT)
     * {@link com.algorithm.tree.binaryIndexTree.ReversePairs_493}
     */

    public static void main(String[] args) {
        ReversePairs_493 r = new ReversePairs_493();
        int[] a1 = {1,3,2,3,1};
        r.reversePairs(a1);
    }
}
