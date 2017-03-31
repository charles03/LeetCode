package com.leetcode.sort;

import java.util.Arrays;

/**
 * Created by charles on 2/21/17.
 * Given an unsorted array, find the maximum difference between the successive elements in its sorted form.
 * Try to solve it in linear time/space.
 Return 0 if the array contains less than 2 elements.
 You may assume all elements in the array are non-negative integers and fit in the 32-bit signed integer range.
 */
public class MaximumGap_164 {
    /**
     * Suppose there are N elements in the array, the min value is min and the max value is max. Then the maximum gap will be no smaller than ceiling[(max - min ) / (N - 1)].
     Let gap = ceiling[(max - min ) / (N - 1)]. We divide all numbers in the array into n-1 buckets, where k-th bucket contains all numbers in [min + (k-1)gap, min + k*gap). Since there are n-2 numbers that are not equal min or max and there are n-1 buckets, at least one of the buckets are empty. We only need to store the largest number and the smallest number in each bucket.
     After we put all the numbers into the buckets. We can scan the buckets sequentially and get the max gap
     */
    /**
     * choose a bucket size b such that 1 < b <= (max - min)/(n-1) let's choose b = [max-min/n-1]
     * thus alll n elements would be divided among k (max-min/b) buckets
     * hence ith bucket would hold range of value [min + (i-1)*b, min + i*b)
     * it is trivial to calculate the index of bucket to which a particular elem belongs,
     * Once all n elem has been distributed, we compare k - 1 adjacent bucket pairs to find max gap
     */
    public int maximumGap(int[] nums) {
        if (nums == null || nums.length < 2) {
            return 0;
        }
        // get min and max of array
        int min = nums[0];
        int max = nums[0];
        for (int num : nums) {
            min = Math.min(num, min);
            max = Math.max(num, max);
        }
        int n = nums.length;
        int gap = (int)Math.ceil((double)(max - min) / (n - 1));
        int[] minInBucket = new int[n-1]; // store min in that bucket
        int[] maxInBucket = new int[n-1];
        Arrays.fill(minInBucket, Integer.MAX_VALUE);
        Arrays.fill(maxInBucket, Integer.MIN_VALUE);
        // distribute num into bucket
        for (int num : nums) {
            if (num == min || num == max) {
                continue;
            }
            int idx = (num - min) / gap; // index of buckets to hold current num
            minInBucket[idx] = Math.min(num, minInBucket[idx]);
            maxInBucket[idx] = Math.max(num, maxInBucket[idx]);
        }

        // scan buckets for max gap
        int maxGap = Integer.MIN_VALUE;
        int prevBucketMax = min;
        for (int i = 0; i < n - 1; i++) {
            if (minInBucket[i] == Integer.MAX_VALUE && maxInBucket[i] == Integer.MIN_VALUE) {
                // empty bucket
                continue;
            }
            // min value minus previous value is the current gap
            maxGap = Math.max(maxGap, minInBucket[i] - prevBucketMax);
            // update prev buicket value
            prevBucketMax = maxInBucket[i];
        }
        maxGap = Math.max(maxGap, max - prevBucketMax); // update final max gap
        return maxGap;
    }

    public static void main(String[] args) {
        MaximumGap_164 m = new MaximumGap_164();
        int[] n1 = {8,2,1,5,4};
        System.out.println(m.maximumGap(n1));
    }
}
