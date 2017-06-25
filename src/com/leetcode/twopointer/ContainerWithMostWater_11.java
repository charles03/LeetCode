package com.leetcode.twopointer;

/**
 * Created by charles on 4/13/17.
 * Given n non-negative integers a1, a2, ..., an, where each represents a point at coordinate (i, ai). n vertical lines are drawn such that the two endpoints of line i is at (i, ai) and (i, 0). Find two lines, which together with x-axis forms a container, such that the container contains the most water.

 Note: You may not slant the container and n is at least 2.
 */
public class ContainerWithMostWater_11 {
    /**
     * Thought:
     * two pointers, left & right
     * if (left == right) {
     *     left++, right--;
     * } else if (left < right) {
     *     left++,
     * } else {
     *     right--;
     * }
     * use Global var to record max of each area
     */
    public int maxArea(int[] height) {
        if (height == null || height.length == 0) {
            return 0;
        }
        int left = 0, right = height.length - 1;
        int max = Integer.MIN_VALUE;
        int area = 0;
        while (left < right) {
            area = Math.min(height[left], height[right]) * (right - left);
            max = Math.max(max, area);
            if (height[left] == height[right]) {
                left++;
                right--;
            } else if (height[left] < height[right]) {
                left++;
            } else {
                right--;
            }
        }
        return max;
    }
}
