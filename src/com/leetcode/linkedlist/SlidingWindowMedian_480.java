package com.leetcode.linkedlist;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

/**
 * Created by charles on 2/21/17.
 * Median is the middle value in an ordered integer list. If the size of the list is even, there is no middle value. So the median is the mean of the two middle value.

 Examples:
 [2,3,4] , the median is 3

 [2,3], the median is (2 + 3) / 2 = 2.5

 Given an array nums, there is a sliding window of size k which is moving from the very left of the array to the very right. You can only see the k numbers in the window. Each time the sliding window moves right by one position. Your job is to output the median array for each window in the original array.

 For example,
 Given nums = [1,3,-1,-3,5,3,6,7], and k = 3.

 Window position                Median
 ---------------               -----
 [1  3  -1] -3  5  3  6  7       1
 1 [3  -1  -3] 5  3  6  7       -1
 1  3 [-1  -3  5] 3  6  7       -1
 1  3  -1 [-3  5  3] 6  7       3
 1  3  -1  -3 [5  3  6] 7       5
 1  3  -1  -3  5 [3  6  7]      6
 Therefore, return the median sliding window as [1,-1,-1,3,5,6].
 */
public class SlidingWindowMedian_480 {
    // instance field
    private List<Double> sortList = new ArrayList<>();

    public double[] medianSlidingWindow(int[] nums, int k) {
        for (int i = 0; i < k; i++) {
            add(nums[i]);
        }
        double[] res = new double[nums.length - k + 1];
        res[0] = getMean();
        for (int i = k; i < nums.length; i++) {
            add(nums[i]);
            remove(nums[i - k]);
            res[i - k + 1] = getMean();
        }
        return res;
    }
    private double getMean() {
        int size = sortList.size();
        if (size % 2 == 1) {
            return sortList.get(size / 2);
        }
        return (sortList.get(size / 2) + sortList.get(size/2 - 1)) / 2;
    }

    private void remove(int num) {
        sortList.remove(findIndex(num));
    }

    private void add(int num) {
        sortList.add(findIndex(num), (double)num);
    }

    private int findIndex(int num) {
        int start = 0;
        int end = sortList.size();
        int mid = 0;
        while (start < end) {
            mid = start + (end - start) / 2;
            if (sortList.get(mid) > num) {
                end = mid;
            } else if (sortList.get(mid) == num) {
                return mid;
            } else {
                start = mid + 1;
            }
        }
        return start;
    }

    public static void main(String[] args) {
        SlidingWindowMedian_480 s = new SlidingWindowMedian_480();
        int[] n1 = {1,3,-1,-3,5,3,6,7};
        output(s.medianSlidingWindow(n1, 3));
    }

    private static void output(double[] arr) {
        DoubleStream.of(arr).mapToObj(Double::valueOf).collect(Collectors.toList()).stream().forEach(t -> System.out.print(t + ","));
        System.out.println();
    }
}
