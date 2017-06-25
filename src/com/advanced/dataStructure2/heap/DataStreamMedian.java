package com.advanced.dataStructure2.heap;

import java.util.*;

/**
 * Created by charles on 10/30/16.
 * Numbers keep coming, return the median of numbers at every time a new number added.
 What's the definition of Median?
 - Median is the number that in the middle of a sorted array. If there are n numbers in a sorted array A, the median is A[(n - 1) / 2]. For example, if A=[1,2,3], median is 2. If A=[1,19], median is 1.

 Example
 For numbers coming list: [1, 2, 3, 4, 5], return [1, 1, 2, 2, 3].
 For numbers coming list: [4, 5, 1, 3, 2, 6, 0], return [4, 4, 4, 3, 3, 3, 3].
 For numbers coming list: [2, 20, 100], return [2, 2, 20].

 Challenge: Total run time in O(nlog(n)) and stream is extreme large
 */
public class DataStreamMedian {
    public List<Integer> medianDataStream(int[] nums) {
        List<Integer> res = new ArrayList<>();
        if (nums == null || nums.length == 0) {
            return res;
        }
        HashHeap minHeap = new HashHeap("MIN");
        HashHeap maxHeap = new HashHeap("MAX");
        int median = nums[0];
        for (int i = 0; i < nums.length; i++) {
            if (i != 0) {
                if (nums[i] < median) {
                    maxHeap.add(nums[i]);
                } else {
                    minHeap.add(nums[i]);
                }
            }
            while (maxHeap.size() > minHeap.size()) {
                minHeap.add(median);
                median = maxHeap.poll();
            }
            while (minHeap.size() > maxHeap.size() + 1) {
                maxHeap.add(median);
                median = minHeap.poll();
            }
            res.add(median);
        }

        return res;
    }

    /**
     * be careful of reset or re instantiate instance fields
     */
    public List<Integer> medianDataStreamJiuzhang(int[] nums) {
        List<Integer> res = new ArrayList<>();
        if (nums == null || nums.length == 0) {
            return res;
        }
        max_heap = new PriorityQueue<>(Collections.reverseOrder());
        min_heap = new PriorityQueue<>();
        count_element = 0; // should be set inside method

        for (int i = 0; i < nums.length; i++) {
            addNumberInHeaps(nums[i]);
            res.add(getMedianFromHeaps());
        }
        return res;
    }

    private Integer getMedianFromHeaps() {
        return max_heap.peek();
    }

    /**
     * Honestly, below method is not best, too if-else logics
     */
    private void addNumberInHeaps(int num) {
        max_heap.add(num);
        if (count_element % 2 == 0) { // when count is even
            if (min_heap.isEmpty()) {
                count_element++;
                return;
            } else if (max_heap.peek() > min_heap.peek()) {
                Integer maxheap_peek = max_heap.poll();
                Integer minheap_peek = min_heap.poll();
                // swap
                max_heap.add(minheap_peek);
                min_heap.add(maxheap_peek);
            }
        } else {
            // move current max from maxheap to minheap
            min_heap.add(max_heap.poll());
        }
        count_element++;
    }

    // use instance field min_heap and max_heap
    private PriorityQueue<Integer> min_heap;
    private PriorityQueue<Integer> max_heap;
    private int count_element;


    public static void main(String[] args) {
        DataStreamMedian stream = new DataStreamMedian();
        int[] nums = {4, 5, 1, 3, 2, 6, 0};
        int[] nums1 = {1, 2, 3, 4, 5};
        System.out.println(stream.medianDataStream(nums));
        System.out.println(stream.medianDataStream(nums1));

        System.out.println(stream.medianDataStreamJiuzhang(nums));
        System.out.println(stream.medianDataStreamJiuzhang(nums1));
    }
}
