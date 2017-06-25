package com.advanced.dataStructure2.heap;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Created by charles on 10/29/16.
 * Given an array of n integer, and a moving window(size k),
 * move the window at each iteration from the start of the array,
 * find the median of the element inside the window at each moving.
 * (If there are even numbers in the array, return the N/2-th number after sorting the element in the window. )
 *
 * Example
 For array [1,2,7,8,5], moving window size k = 3. return [2,7,7]

 At first the window is at the start of the array like this

 [ | 1,2,7 | ,8,5] , return the median 2;

 then the window move one step forward.

 [1, | 2,7,8 | ,5], return the median 7;

 then the window move one step forward again.

 [1,2, | 7,8,5 | ], return the median 7;

 Tag: Heap
 Challenge: O(nlgn)

 Thoughts for O(n^2) maintain Max Heap and Min Heap in size balance.
            Median
    Max Heap     Min Heap
 if (size diff (max heap, min heap)) > 1, then offer median into one heap with smaller size, and poll from the other as new median
 Because operation of Remove(Node) in heap is O(n) time complexity,
 How to reduce to log(n) time complexity, use HashHeap

 ** Hash Heap **
 defined data structure to improve the performance of remove node.
 */
public class SlidingWindowMedian {
    public List<Integer> medianSlidingWindow(int[] nums, int k) {
        List<Integer> res = new ArrayList<>();
        if (nums == null || nums.length == 0) {
            return res;
        }
        // initialize priority queue with capacity K size
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(k, Collections.reverseOrder());
        PriorityQueue<Integer> minHeap = new PriorityQueue<>(k);
        for (int i = 0; i < nums.length; i++) {
            // add new element into corresponding heap
            // !!!! important to add condition when maxHeap is empty
            if (maxHeap.isEmpty() || nums[i] < maxHeap.peek()) {
                maxHeap.offer(nums[i]);
            } else {
                minHeap.offer(nums[i]);
            }
            // remove old element outside of window size
            if (i >= k) {
                if (nums[i - k] <= maxHeap.peek()) {
                    maxHeap.remove(nums[i - k]);
                } else {
                    minHeap.remove(nums[i - k]);
                }
            }
            // balance two heaps, make sure max heap contains one more elements if k is odd
            while (maxHeap.size() > minHeap.size() + 1) {
                minHeap.offer(maxHeap.poll());
            }
            while (minHeap.size() >= maxHeap.size() + 1) {
                maxHeap.offer(minHeap.poll());
            }
            //!!!  add median into result under condition
            if (i >= k - 1) {
                res.add(maxHeap.peek());
            }
        }
        return res;
    }

    public static void main(String[] args) {
        SlidingWindowMedian median = new SlidingWindowMedian();
        int[] nums = {1,2,2,7,7,8,2,8,5};
        System.out.println(median.medianSlidingWindow(nums, 3));
        System.out.println(median.medianSlidingWindow(nums, 4));
    }
}
