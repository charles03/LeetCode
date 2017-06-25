package com.advanced.dataStructure2.twoPointers;

/**
 * Created by charles on 10/24/16.
 *
 * Given n non-negative integers representing an elevation map where the width of each bar is 1,
 * compute how much water it is able to trap after raining.
 *
 * Example
 Given [0,1,0,2,1,0,1,3,2,1,2,1], return 6.

 Challenge
 O(n) time and O(1) memory

 O(n) time and O(n) memory is also acceptable.
 */
public class TrappingRainWater {
    public int trapRainWater(int[] heights) {
        int res = 0;
        if (heights == null || heights.length == 0) {
            return res;
        }
        int l = 0, r = heights.length - 1; // left and right index
        int lh = heights[l], rh = heights[r]; // leftHeight and rightHeight
        // main process
        // trap water from both sides
        while (l < r) {
            if (lh < rh) {
                l++;
                // when from left pointer, orig left height > next height(moving right), add value into res
                if (lh > heights[l]) {
                    res += lh - heights[l];
                } else {
                    lh = heights[l];
                }
            } else {
                r--;
                // when from right pointer, orig right height > next height(moving left), add value into res
                if (rh > heights[r]) {
                    res += rh - heights[r];
                } else {
                    rh = heights[r];
                }
            }
        }
        return res;
    }

    public static void main(String[] args) {
        TrappingRainWater water = new TrappingRainWater();
        int[] heights = {0,1,0,2,1,0,1,3,2,1,2,1};
        int res = water.trapRainWater(heights);
        System.out.println(res);
    }
}

