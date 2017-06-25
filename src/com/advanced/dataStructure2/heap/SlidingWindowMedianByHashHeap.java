package com.advanced.dataStructure2.heap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by charles on 10/30/16.
 * Same question in {@link SlidingWindowMedian}
 * only difference is how to implement O(nlog(n)) time complexity.
 * Use Hash Heap data structure, let remove function in heap is log(n) time complexity.
 *
 * another similar existing Heap in java is TreeMap (based on Red-Black Tree / AVL tree / S - Play tree)
 * use TreeMap, remove is log(n) theoretically.
 */

public class SlidingWindowMedianByHashHeap {

    public List<Integer> medianSlidingWindow(int[] nums, int k) {
        List<Integer> res = new ArrayList<>();
        if (nums == null || nums.length == 0) {
            return res;
        }
        HashHeap minHeap = new HashHeap("MIN");
        HashHeap maxHeap = new HashHeap("MAX");
        for (int i = 0; i < nums.length; i++) {
            if (maxHeap.isEmpty() || nums[i] < maxHeap.peek()) {
                maxHeap.add(nums[i]);
            } else {
                minHeap.add(nums[i]);
            }
            // remove outside window
            if (i >= k) {
                if (nums[i - k] <= maxHeap.peek()) {
                    maxHeap.delete(nums[i - k]);
                } else {
                    minHeap.delete(nums[i - k]);
                }
            }
            while (maxHeap.size() > minHeap.size() + 1) {
                minHeap.add(maxHeap.poll());
            }
            while (minHeap.size() >= maxHeap.size() + 1) {
                maxHeap.add(minHeap.poll());
            }
            if (i + 1 >= k) {
                res.add(maxHeap.peek());
            }
        }
        return res;
    }

    public List<Integer> medianSlidingWindowJiuzhang(int[] nums, int k) {
        List<Integer> ans = new ArrayList<>();
        if (nums == null || nums.length == 0) {
            return  ans;
        }
        int median = nums[0];
        HashHeap minHeap = new HashHeap("MIN");
        HashHeap maxHeap = new HashHeap("MAX");
        for (int i = 0; i < nums.length; i++) {
            if (i != 0) {
                // skip first element
                if (nums[i] > median) {
                    minHeap.add(nums[i]);
                } else {
                    maxHeap.add(nums[i]);
                }
            }
            if (i >= k) {
                if (median == nums[i - k]) {
                    if (maxHeap.size() > 0) {
                        median = maxHeap.poll();
                    } else if (minHeap.size() > 0) {
                        median = minHeap.poll();
                    }
                } else if (median < nums[i - k]) {
                    minHeap.delete(nums[i - k]);
                } else {
                    maxHeap.delete(nums[i - k]);
                }
            }
            // re-balance
            while (maxHeap.size() > minHeap.size()) {
                minHeap.add(median);
                median = maxHeap.poll();
            }
            while (minHeap.size() > maxHeap.size() + 1) {
                maxHeap.add(median);
                median = minHeap.poll();
            }
            if (i + 1 >= k) {
                ans.add(median);
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        SlidingWindowMedianByHashHeap median = new SlidingWindowMedianByHashHeap();
        int[] nums = {1,2,2,7,7,8,2,8,5};
        System.out.println(median.medianSlidingWindow(nums, 3));
        System.out.println(median.medianSlidingWindow(nums, 4));

        System.out.println(median.medianSlidingWindowJiuzhang(nums, 3));
        System.out.println(median.medianSlidingWindowJiuzhang(nums, 4));
    }
}
