package com.advanced.twopointer;

import java.util.Random;

/**
 * Created by charles on 11/11/16.
 * Find K-th largest element in an array.
 * In array [9,3,2,4,8], the 3rd largest element is 4.
 In array [1,2,3,4,5], the 1st largest element is 5, 2nd largest element is 4, 3rd largest element is 3 and etc
 Challenge O(n) time, O(1) extra memory
 */
public class KthLargestElement {
    /**
     * use quick select to find kth smallest element in O(n) time and O(1) space
     * quick select based on partition
     * choose one element as pivot an spartition the data in two based on pivot
     * however, instead of recursing into both sides, quick select only recurses into one side
     * the side with element it is searching for,
     * this reduces average complexity from O(nlgn) to O(n)
     * So pivot location should meet all left side > nums[k]
     * and right side < nums[k]
     */
    /*
     * @param k : description of k
     * @param nums : array of nums
     * @return: description of return
     */
    public int kthLargestElement(int k, int[] nums) {
        if (nums == null || nums.length == 0) {return Integer.MAX_VALUE;}
        if (k <= 0) {return Integer.MAX_VALUE;}
        int len = nums.length;
        // quick select should select index reversely
        // if select k largest, should set (len-k+1)
        return quickSelectRecursion(nums, len - k, 0, len - 1);
    }

    public int kthSmallestElement(int k, int[] nums) {
        if (nums == null || nums.length == 0) {return 0;}
        if (k <= 0) {return 0;}
        int len = nums.length;
        return quickSelectRecursion(nums, k - 1, 0, len - 1);
    }

    private int quickSelectRecursion(int[] nums, int nth, int left, int right) {
        if (left == right) {
            return nums[left]; // if list contains only one element
        }
//        int pivotIdx = partitionWiki(nums, left, right);
        int pivotIdx = partition(nums, left, right);

        if (pivotIdx == nth) {
            return nums[pivotIdx]; // pivot is in it's final sorted position
        } else if (pivotIdx < nth) {
            return quickSelectRecursion(nums, nth, pivotIdx + 1, right); // recurse in one side
        } else {
            return quickSelectRecursion(nums, nth, left, pivotIdx - 1);
        }
    }

    private int quickSelectNonRecusion(int[] nums, int nth, int left, int right) {
        int pivotIdx = 0;
        while (left < right) {
            pivotIdx = partitionWiki(nums, left, right);
            if (pivotIdx == nth) {
                return nums[pivotIdx];
            } else if (pivotIdx < nth) {
                left = pivotIdx + 1;
            } else {
                right = pivotIdx - 1;
            }
        }
        return Integer.MAX_VALUE;
    }

    // partition for nth smallest without swap
    private int partition(int[] nums, int l, int r) {
        // select random index as pivot index
        int pivotIdx = l + new Random().nextInt(r - l + 1);
        int pivot = nums[pivotIdx];
        nums[pivotIdx] = nums[l];

        while (l < r) {
            while (l < r && nums[r] >= pivot) {
                r--;
            }
            nums[l] = nums[r];
            while (l < r && nums[l] <= pivot) {
                l++;
            }
            nums[r] = nums[l];
        }
        nums[l] = pivot; //return back to array
        return l;
    }

    // partition by swap
    private int partitionWiki(int[] nums, int l, int r) {
        int pivotIdx = l + new Random().nextInt(r - l + 1);
        swap(nums, pivotIdx, r); // move pivot to end
        int partition_idx = l; // set final return index start from left
        for (int i = l; i < r; i++) {
            // how to determine left most large or small by
            // controling less than or larger than logic
            if (nums[i] < nums[r]) {
                if (partition_idx != i) {
                    // when i is ahead of
                    swap(nums, partition_idx, i);
                }
                partition_idx++;
            }
        }
        swap(nums, partition_idx, r); // move pivot to its final place
        return partition_idx;
    }

    private void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }



    public static void main(String[] args) {
        KthLargestElement k = new KthLargestElement();
        int[] a = {9,3,2,4,8,10,5,7,1};
        System.out.println(k.kthLargestElement(3, a) == 8);
        System.out.println(k.kthSmallestElement(3, a) == 3);
    }
}
