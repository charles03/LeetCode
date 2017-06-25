package com.leetcode.twopointer;

import java.util.Arrays;

/**
 * Created by charles on 4/10/17.
 * Given two sorted integer arrays nums1 and nums2, merge nums2 into nums1 as one sorted array.

 Note:
 You may assume that nums1 has enough space (size that is greater or equal to m + n) to hold additional elements from nums2.
 The number of elements initialized in nums1 and nums2 are m and n respectively.
 */
public class MergeSortedArray_88 {
    /** no need extra space,
     * assign larger value from tail of (m+n) index to front
     */
    public void mergeII(int[] nums1, int m, int[] nums2, int n) {
        int idx1 = m-1, idx2 = n-1;
        int start = m+n-1;
        while (idx1 >= 0 && idx2 >= 0) {
            if (nums1[idx1] > nums2[idx2]) {
                nums1[start] = nums1[idx1];
                start--;
                idx1--;
            } else {
                nums1[start] = nums2[idx2];
                start--;
                idx2--;
            }
        }
        if (idx1 < 0) {
            while (idx2 >= 0) {
                nums1[start--] = nums2[idx2--];
            }
        }
    }
    public void mergeIII(int[] nums1, int m, int[] nums2, int n) {
        int i = m-1, j = n-1;
        int k = m+n-1;
        while (i >= 0 && j >= 0) {
            nums1[k--] = nums1[i] > nums2[j] ? nums1[i--] : nums2[j--];
        }
        // if still left over nums2;
        while (j >= 0) {
            nums1[j] = nums2[j--];
        }
    }

    public static void main(String[] args) {
        int[] n1 = {1,4,5,7,-1,-1,-1};
        int[] n2 = {2,6};
        MergeSortedArray_88 m = new MergeSortedArray_88();
        m.mergeII(n1,4,n2,2);
        output(n1);

    }

    private static void output(int[] arr) {
        Arrays.stream(arr).forEach(t->System.out.print(t + ","));
        System.out.println();
    }
}
