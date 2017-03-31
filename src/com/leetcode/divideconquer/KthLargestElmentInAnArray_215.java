package com.leetcode.divideconquer;

/**
 * Created by charles on 2/13/17.
 * Find the kth largest element in an unsorted array. Note that it is the kth largest element in the sorted order, not the kth distinct element.

 For example,
 Given [3,2,1,5,6,4] and k = 2, return 5.
 */
public class KthLargestElmentInAnArray_215 {
    public int findKthLargest(int[] nums, int k) {
        int n = nums.length;
        /**
         * quick select for n-k+1 th smallest, which is kth largest
         */
        int idx = quickSelect(nums, 0, n - 1, n-k+1);
        return nums[idx];
    }

    public int quickSelect(int[] nums, int left, int right, int k) {
        /**
         * put nums that are <= pivot to left
         * put nums that are > pivot to right
         */
        int i = left, j = right;
        int pivot = nums[right];
        while (i < j) {
            if (nums[i++] > pivot) {
                /**
                 * as you can see, --i makes sure we can still check a[i] after the swap,
                 * and --j makes sure we won't overwrite the ones that are already done
                 */
                swap(nums, --i, --j);
            }
        }
        swap(nums, i, right);

        // count nums that are <= pivot from left
        int m = i - left + 1;
        // pivot is the one
        if (m == k) {
            return i;
        } else if (m > k) { // pivot is too big, so it must be on the left
            return quickSelect(nums, left, i - 1, k);
        } else {
            return quickSelect(nums, i + 1, right, k - m);
        }
    }

    /**
     * Below method is faster
     */
    public int findKthLargestII(int[] nums, int k) {
        int n = nums.length;
        quickSelectII(nums, 0, n-1, n-k); // will modify original array
        return nums[n-k];
    }
    private void quickSelectII(int[] nums, int start, int end, int k) {
        if (start >= end) {
            return ;
        }
        int mid = start + (end - start) / 2;
        int pivot = choosePivot(nums[start], nums[mid], nums[end]);

        int i = start, j = end;
        while (i <= j) {
            while (nums[i] < pivot) {
                i++;
            }
            while (nums[j] > pivot) {
                j--;
            }
            if (i <= j) {
                if (nums[i] != nums[j]) {
                    swap(nums, i, j);
                }
                i++;
                j--;
            }
        }
        if (k <= i - 1) {
            quickSelectII(nums, start, i-1, k);
        } else {
            quickSelectII(nums, i, end, k);
        }
    }
    // select mid of three elem
    private int choosePivot(int a, int b, int c) {
        int min = Math.min(Math.min(a, b), c);
        int max = Math.max(Math.max(a, b), c);
        if (a == max) {
            return Math.max(b, c);
        } else if (b == max) {
            return Math.max(a, c);
        }
        return Math.max(a, b);
    }

    private void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }

    public static void main(String[] args) {
        KthLargestElmentInAnArray_215 k = new KthLargestElmentInAnArray_215();
        int[] n1 = {-1, 2, 0};
        System.out.println(k.findKthLargestII(n1, 2));
    }
}
