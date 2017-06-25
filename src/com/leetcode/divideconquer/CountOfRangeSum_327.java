package com.leetcode.divideconquer;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by charles on 2/16/17.
 * {@link com.algorithm.tree.segmentTree.CountOfRangeSum_327}
 *
 * Use Prefix-array based divide & conquer
 * Rephrase question:
 * To find total number of range sums lying in the given range with 0 <= (i,j) <= n-1, or in symbolic way T(0, n-1)
 * Now if we break original input array into two subarrays, [0,m], [m+1, n-1] with m = (n-1)/2
 * then original problems can be divided into three parts,
 *      if (both i, j in [0,m]) -> T(0,m)
 *      else if (both i, j in (m+1, n-1)) -> T(m+1, n-1)
 *      else Then having new problem C,
 *      For new Problem C, naive solution is to compute all possible answers user O(n^2)
 *
 *      But oberservation here : each range sum can be computed by prefix[j] - prefix[i] with j in [m+1, n-1] and i in [0,m]
 *      for each index i, the order doesn't matter in which we compute the range sum for j running from m+1 to n,
 *      vice versa for j, thus we can sort two subarrays,
 *      Then, use two pointer like method in merge sort
 *
 *      https://discuss.leetcode.com/topic/34108/summary-of-the-divide-and-conquer-based-and-binary-indexed-tree-based-solutions/4
 */
public class CountOfRangeSum_327 {
    public int countRangeSum(int[] nums, int lower, int upper) {
        if (nums == null || nums.length == 0 || lower > upper) {
            return 0;
        }
        long[] prefixArray = new long[nums.length + 1];
        for (int i = 1; i < prefixArray.length; i++) {
            prefixArray[i] = prefixArray[i-1] + nums[i-1];
        }
        return divideConquer(prefixArray, 0, prefixArray.length - 1, lower, upper);
    }

    /**
     * Thanks for the sorted property of sub-problem([begin,mid][begin,mid] and [mid+1,end][mid+1,end]), the Combine step could be solved in O(n)O(n) time complexity using "two pointers" technique.
     * As a consequence, the time complexity of the solution equals to merge sort, which is O(nlogn)O(nlog⁡n).
     */
    private int divideConquer(long[] prefixSum, int l, int r, int low, int hi) {
        if (l >= r) {
            return 0;
        }
        int mid = l + (r - l) / 2;
        int count = divideConquer(prefixSum, l, mid, low, hi) + divideConquer(prefixSum, mid + 1, r, low, hi);

        // promise: [begin, mid] and [mid + 1, end] are sorted.
        // count the crossing ranges.
        // below is problem C
        long[] sorted = new long[r - l + 1];
//      modified version of merging
//        while (i < array1.length) {
//            while (j < array2.length && array2[j] < array1[i]) {
//                array[k++] = array2[j++];
//            }
//
//            array[k++] = array1[i++];
//        }
//
//        while (j < array2.length) {
//            array[k++] = array2[j++];
//        }
        // left array length is mid, left start at l, right array start at mid + 1
        int i = l, j = mid + 1;
        int k = mid + 1;
        int p = 0, q = mid + 1; // p is initial index of sorted helper array while q is init index of right part of prefix sum array
        while (i <= mid) {
            // to get count of prefix sum lying in range of (low, hi)
            while (j <= r && prefixSum[j] - prefixSum[i] < low) {
                j++;
            }
            while (k <= r && prefixSum[k] - prefixSum[i] < hi) {
                k++;
            }
            count += k - j;
            // to keep array sorted in merge
            // merge smaller at front
            while (q <= r && prefixSum[q] < prefixSum[i]) {
                sorted[p++] = prefixSum[q++];
            }
            sorted[p++] = prefixSum[i++];
        }
        while (q <= r) {
            sorted[p++] = prefixSum[q++];
        }
        // copy array back to prefixArray
        System.arraycopy(sorted, 0, prefixSum, l, sorted.length);
        return count;
    }

    /**
     * Another solution using TreeMap or TreeSet
     * Similar to LeetCode 363, use TreeMap because there might be cases when exist duplicates, need to count all of them
     * https://discuss.leetcode.com/topic/58592/java-treemap-solution
     */
    public int countRangeSumII(int[] nums, int lower, int upper) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        // key is sum of (i,j) value is count of occurrence
        TreeMap<Long, Long> rangeMap = new TreeMap<>();
        rangeMap.put(0L, 1L); // key is sum, value is count of this sum
        long sum = 0;
        long count = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            long from = sum - upper; // treemap ceiling
            long to = sum - lower; // treemap flooring
            Map<Long, Long> subMap = rangeMap.subMap(from, true, to, true);
            // iterate solution
            for (Long cnt : subMap.values()) {
                count += cnt;
            }
            if (rangeMap.containsKey(sum)) {
                rangeMap.put(sum, rangeMap.get(sum) + 1);
            } else {
                rangeMap.put(sum, 1L);
            }
        }
        return (int)count;
    }

    /**
     * Merge sort solution:
     * count answer while doing the merge, we have sorted left half[start, mid)
     * and right half [mid, end)
     * iterate through left half with index i, for each i, need to find two indices k and j
     * in the right half where
     *  j is first index satisfy sum[j] - sum[i] > upper
     *  k is first index satisfy sum[k] - sum[i] >= lower
     *
     *  The number of sum in [lower, upper] is j-k
     *  we also use another index t to copy elements satisfy sum[t] < sum[i] to a cache
     *  in order to complete the merge sort
     *
     *  "merge & count" stage is still liner because indices k,j,t only increase but not decrease
     *  each of them will only traversal right half once at most.
     *
     *  use long in sum to avoid overflow integer
     */
    public int countRangeSumIII(int[] nums, int lower, int upper) {
        int len = nums.length;
        long[] prefixSum = new long[len + 1];
        for (int i = 0; i < len; i++) {
            prefixSum[i+1] = prefixSum[i] + nums[i];
        }
        return countInMergeSort(prefixSum, 0, len+1, lower, upper);
    }
    private int countInMergeSort(long[] sum, int start, int end, int lower, int upper) {
        if (start + 1 >= end) {
            return 0;
        }
        int mid = start + (end - start) / 2;
        // divide and conquer
        int count = countInMergeSort(sum, start, mid, lower, upper)
                + countInMergeSort(sum, mid, end, lower, upper);
        // process merge
        // having left half [start, mid] and right half [mid, end]
        int j = mid, k = mid, t = mid;
        long[] cache = new long[end - start];
        int idx = 0;
        for (int i = start; i < mid; i++) {
            // to find starting point k where sum[k] - sum[i] >= lower
            while (k < end && sum[k] - sum[i] < lower) {
                k++;
            }
            // to find starting point j while sum[j] - sum[i] > upper
            while (j < end && sum[j] - sum[i] <= upper) {
                j++;
            }
            // merge, keep small in either half at front
            while (t < end && sum[t] < sum[i]) {
                cache[idx++] = sum[t++];
            }
            cache[idx] = sum[i];
            count += j - k;
        }
        System.arraycopy(cache, 0, sum, start, t - start);
        return count;
    }
}
