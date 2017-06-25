package com.leetcode.binarysearch;

import javax.jnlp.IntegrationService;

/**
 * Created by charles on 5/30/17.
 * There are two sorted arrays nums1 and nums2 of size m and n respectively.

 Find the median of the two sorted arrays. The overall run time complexity should be O(log (m+n)).

 Example 1:
 nums1 = [1, 3]
 nums2 = [2]

 The median is 2.0
 Example 2:
 nums1 = [1, 2]
 nums2 = [3, 4]

 The median is (2 + 3)/2 = 2.5
 */
public class MedianTwoSortedArray_4 {
    /**
     * if n is odd, median -> arr[n/2 + 1];
     * else n is even, median -> (arr[n/2] + arr[n/2+1]) / 2;
     *
     * as for two sorted array,
     * assume len(A) is n, len(B) is m;
     * separately to get kth-small elem in each array.
     * answer: (n+m)/2 + 1 when n+m is odd
     *          (value[(n+m)/2] + value[(n+m)/2+1]) / 2 when n+m is even
     *
     * if A[k/2] > B[k>2] thus, B[0..k/2] can be ignored
     * else A[0..k/2] can be ignored
     *
     * Then we have subproblem when k become smaller k -> k-k/2
     * O(lg(m+n))
     */
    public double findMedianSortedArray(int[] A, int[] B) {
        int totalLen = A.length + B.length;
        if (totalLen % 2 == 1) {
            return findKth(A, 0, B, 0, totalLen / 2 + 1);
        }
        return (findKth(A,0,B,0,totalLen/2) + findKth(A,0,B,0,totalLen/2 + 1)) / 2.0;
    }
    private int findKth(int[] A, int aStart, int[] B, int bStart, int k) {
        if (aStart >= A.length) {
            return B[bStart+k-1];
        }
        if (bStart >= B.length) {
            return A[aStart+k-1];
        }
        if (k == 1) {
            return Math.min(A[aStart], B[bStart]);
        }
        int ka = 0, kb = 0;
        if (aStart + k / 2 - 1 < A.length) {
            ka = A[aStart + k / 2 - 1];
        } else {
            ka = Integer.MAX_VALUE;
        }
        if (bStart + k / 2 - 1 < B.length) {
            kb = A[bStart + k / 2 - 1];
        } else {
            kb = Integer.MAX_VALUE;
        }
        if (ka < kb) {
            return findKth(A, aStart + k/2, B, bStart, k - k/2);
        } else {
            return findKth(A, aStart, B, bStart + k/2, k - k/2);
        }
    }

    /**
     * The basic idea is that if you are given two arrays A and B and know
     the length of each, you can check whether an element A[i] is the median in constant
     time. Suppose that the median is A[i]. Since the array is sorted, it is greater than
     exactly i − 1 values in array A. Then if it is the median, it is also greater than exactly
     j = |n/2| − (i − 1) elements in B. It requires constant time to check if B[j]
     A[i] � B[j + 1]. If A[i] is not the median, then depending on whether A[i] is greater
     or less than B[j] and B[j + 1], you know that A[i] is either greater than or less than
     the median. Thus you can binary search for A[i] in �(lg n) worst-case time.
     */
    /**
     * do binary search. suppose the shorter list is A with length n. the runtime is O(log(n)) which means no matter how large B array is,
     * it only depends on the size of A. It makes sense because if A has only one element while B has 100 elements, the median must be one of A[0], B[49], and B[50] without check everything else.
     * If A[0] <= B[49], B[49] is the answer; if B[49] < A[0] <= B[50], A[0] is the answer; else, B[50] is the answer.

     After binary search, we get the approximate location of median. Now we just need to compare at most 4 elements to find the answer. This step is O(1).

     the same solution can be applied to find kth element of 2 sorted arrays.
     */
    public double findMedianSortedArraysII(int[] A, int[] B) {
        int aLen = A.length;
        int bLen = B.length;
        // the following call is to make sure len(A) <= len(B).
        // yes, it calls itself, but at most once, shouldn't be
        // consider a recursive solution
        if (aLen > bLen) {
            return findMedianSortedArraysII(B, A);
        }
        int midOfAll = (aLen + bLen - 1) / 2;
        int mid = binarySearchMedianIdxOnShorterArray(A, B, aLen, bLen, midOfAll);

        // after binary search, we almost get the median because it must be between
        // these 4 numbers: A[mid-1], A[mid], B[midOfAll-mid], and B[midOfAll-mid+1]
        // where mid is index in shorter length of array to split array into leftA and rightA
        // midOfAll - mid is index in longer length of array to split array into leftB and rightB
        // len(leftA + leftB) should be equal to len(rightA + rightB);
        /**
         * while to get 4 candidates, need to take care of corner cases
         * when mid is 0 or len(A)
         * To solve problem, we can add two extra elements
         * INT_MIN at A[-1] and INT_MAX at A[len]
         * these additions don't change result, but make implementation easier.
         */
        int leftMidA = mid > 0 ? A[mid - 1] : Integer.MIN_VALUE;
        int rightMidA = mid < aLen ? A[mid] : Integer.MAX_VALUE;
        int leftMidB = midOfAll - mid >= 0 ? B[midOfAll - mid] : Integer.MIN_VALUE;
        int rightMidB = midOfAll - mid + 1 < bLen ? B[midOfAll - mid + 1] : Integer.MAX_VALUE;

        // if (n+m) is odd, the median is the larger one between A[l-1] and B[k-l].
        int midLeft = Math.max(leftMidA, leftMidB);
        if (((aLen + bLen) & 1) == 1) {
            return (double)midLeft;
        }
        // if (n+m) is even, the median can be calculated by
        // median = (max(A[l-1], B[k-l]) + min(A[l], B[k-l+1]) / 2.0
        int midRight = Math.min(rightMidA, rightMidB);
        return (midLeft + midRight) / 2.0;
    }
    private int binarySearchMedianIdxOnShorterArray(int[] A, int[] B, int m, int n, int k) {
        int l = 0;
        int r = Math.min(m, k); // r is either len of shorter array or mid index of merge length of two arrays

        int midA = 0;
        int midB = 0;
        while (l < r) {
            midA = l + (r - l) / 2;
            midB = k - midA;
            if (A[midA] < B[midB]) {
                l = midA + 1;
            } else {
                r = midA;
            }
        }
        return l;
    }
}
