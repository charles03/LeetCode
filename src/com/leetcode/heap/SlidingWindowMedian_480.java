package com.leetcode.heap;

import java.util.TreeMap;

/**
 * Created by charles on 2/22/17.
 * {@link com.leetcode.linkedlist.SlidingWindowMedian_480}
 *
 * Wrap up TreeMap into CustomTreeMap to
 *  hold duplicates
 *  insertion/deletion/query min or max in log(k)
 *  get total size in O(1)
 *
 *  hold data in customize tree map, maintain maxHeap.size > minHeap.size
 *  when data moving out and moving in, rebalance two heaps to main maxHeap.size >= minHeap.size
 */
public class SlidingWindowMedian_480 {
    public double[] medianSlidingWindow(int[] nums, int k) {
        int n = nums.length;
        double[] res = new double[n - k + 1];

        CustomTreeMap maxHeap = new CustomTreeMap();
        CustomTreeMap minHeap = new CustomTreeMap();

        for (int i = 0; i < n; i++) {
            maxHeap.add(nums[i]);
            int prevMax = maxHeap.lastKey();
            maxHeap.remove(prevMax);
            minHeap.add(prevMax);
            reBalance(maxHeap, minHeap);

            int j = i - k + 1;
            if (j < 0) {
                continue;
            }
            res[j] = (double)maxHeap.lastKey();
            if (k % 2 == 0) {
                res[j] = (res[j] + (double)minHeap.firstKey()) / 2.0;
            }
            int old = nums[j];
            if (!maxHeap.remove(old)) {
                minHeap.remove(old);
            }
            reBalance(maxHeap, minHeap);
        }
        return res;
    }

    private void reBalance(CustomTreeMap maxHeap, CustomTreeMap minHeap) {
        if (maxHeap.size < minHeap.size) {
            int prevMin = minHeap.firstKey();
            minHeap.remove(prevMin);
            maxHeap.add(prevMin);
        }
    }

    public static void main(String[] args) {
        SlidingWindowMedian_480 s = new SlidingWindowMedian_480();
        int[] n1 = {1,3,-1,-3,5,3,6,7};
        double[] res = s.medianSlidingWindow(n1, 3);
        for (double d : res) {
            System.out.print(d + ",");
        }
    }
}

class CustomTreeMap {
    int size;
    private final TreeMap<Integer, Integer> treeMap;

    CustomTreeMap() {
        treeMap = new TreeMap<>();
        this.size = 0;
    }
    int lastKey() {
        return treeMap.lastKey();
    }
    int firstKey() {
        return treeMap.firstKey();
    }
    void add(int key) {
        this.size++;
        treeMap.put(key, treeMap.getOrDefault(key, 0) + 1);
    }
    boolean remove(int key) {
        if (!treeMap.containsKey(key)) {
            return false;
        }
        this.size--;
        int count = treeMap.get(key);
        if (count == 1) {
            treeMap.remove(key);
        } else {
            treeMap.put(key, count - 1);
        }
        return true;
    }
}
