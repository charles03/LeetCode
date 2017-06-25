package com.advanced.dataStructure2.stack;

import java.util.Stack;

/**
 * Created by charles on 12/4/16.
 * Given n non-negative integers representing the histogram's bar height where the width of each bar is 1,
 * find the area of largest rectangle in the histogram.
 * Above is a histogram where width of each bar is 1, given height = [2,1,5,6,2,3]
 * The largest rectangle is shown in the shaded area, which has area = 10 unit.
 * Given height = [2,1,5,6,2,3],
   return 10
 */
public class LargestRectangleInHistogram {
    /**
     * Thought:
     * the second solution is to find biggest area of bar which is poped out
     * when value of curr bar which smaller than previous bar appears.
     * poped out all bars until all value of bars in stack smalller than curr
     * and push curr to repeat above process
     * the stack is used to store the index
     */
    public int largestRectangleArea(int[] height) {
        if (height == null || height.length == 0) {
            return 0;
        }
        Stack<Integer> stack = new Stack<>(); // store index

        int currHeight;
        int h = 0; // height of rectangle
        int w = 0; // width of rectangle
        int max = 0; // max of rectangle area
        for (int i = 0; i <= height.length; i++) {
            // assign -1 to last index when reach end of array
            currHeight = height.length == i ? -1 : height[i];

            // while loop inside of stack, pop out all indexes which height is larger than current height
            // until height[index] < current height
            while (!stack.isEmpty() && currHeight <= height[stack.peek()]) {
                h = height[stack.pop()];
                if (stack.isEmpty()) { // check stack size, before call peek() method
                    w = i;
                } else { // stack has other values
                    w = (i-1) - stack.peek(); // i-1 is index has been popped.
                }
                System.out.println(String.format("h %s, w %s", h, w));
                max = Math.max(max, h * w);
            }
            stack.push(i); // add current index into stack
        }
        return max;
    }

    public static void main(String[] args) {
        LargestRectangleInHistogram l = new LargestRectangleInHistogram();
        int[] heights = {2, 1, 5, 6, 2, 3};
        System.out.println(l.largestRectangleArea(heights));
    }
}
