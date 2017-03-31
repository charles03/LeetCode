package com.leetcode.sort;

/**
 * Created by charles on 2/26/17.
 * Given an array of citations (each citation is a non-negative integer) of a researcher, write a function to compute the researcher's h-index.

 According to the definition of h-index on Wikipedia: "A scientist has index h if h of his/her N papers have at least h citations each, and the other N − h papers have no more than h citations each."

 For example, given citations = [3, 0, 6, 1, 5], which means the researcher has 5 papers in total and each of them had received 3, 0, 6, 1, 5 citations respectively. Since the researcher has 3 papers with at least 3 citations each and the remaining two with no more than 3 citations each, his h-index is 3.

 Note: If there are several possible values for h, the maximum one is taken as the h-index.

 Hint:

 An easy approach is to sort the array first.
 What are the possible values of h-index?
 A faster approach is to use extra space.
 */
public class HIndex_274 {
    /**
     * Bucket Sort
     *  h-index is defined as the number of papers with reference greater than the number. So assume n is the total number of papers,
     *  if we have n+1 buckets, number from 0 to n, then for any paper with reference corresponding to the index of the bucket, we increment the count for that bucket. The only exception is that for any paper with larger number of reference than n, we put in the n-th bucket.
     *
     *  Then we iterate from the back to the front of the buckets, whenever the total count exceeds the index of the bucket,
     *  meaning that we have the index number of papers that have reference greater than or equal to the index. Which will be our h-index result. The reason to scan from the end of the array is that we are looking for the greatest h-index. For example, given array [3,0,6,5,1], we have 6 buckets to contain how many papers have the corresponding index. Hope to image and explanation help.
     */
    public int hIndex(int[] citations) {
        int len = citations.length;
        int[] buckets = new int[len + 1];

        for (int cite : citations) {
            if (cite > len) {
                buckets[len]++;
            } else {
                buckets[cite]++;
            }
        }
        int count = 0;
        for (int i = len; i >= 0; i--) {
            count += buckets[i];
            if (count >= i) {
                return i;
            }
        }
        return 0;
    }

    /**
     * using in-place quick select, and time in average case is n + n/2 + n/4 +... = 2n
     * in worst case is O(n^2)
     */
    public int hIndexII(int[] citations) {
        int len = citations.length;
        int start = 0;
        int end = len - 1;
        int hIdx = 0;

        while (start <= end) {
            int curr = divideByPartition(citations, start, end);
            if (len - curr <= citations[curr]) {
                hIdx = len - curr;
                end = curr - 1;
            } else {
                start = curr + 1;
            }
        }
        return hIdx;
    }

    private int divideByPartition(int[] nums, int start, int end) {
        if (start == end) {
            return end;
        }
        int pivot = nums[end];
        int head = start;
        /**
         * quick sort, select nums[end] as pivot, and move all small to left, and right to pivot right
         */
        for (int i = start; i < end; i++) {
            if (nums[i] < pivot) {
                int tmp = nums[head];
                nums[head] = nums[i];
                nums[i] = tmp;
                head++;
            }
        }
        nums[end] = nums[head];
        nums[head] = pivot;
        return head; // return index of pivot, where left is small and right is large
    }



}
