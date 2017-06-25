package com.advanced.dynamicprogram;

import java.util.Arrays;
import java.util.Stack;

/**
 * Created by charles on 12/4/16.
 *
 * Given a 2D boolean matrix filled with False and True,
 * find the largest rectangle containing all True and return its area.
 * Given a matrix:
 [
 [1, 1, 0, 0, 1],
 [0, 1, 0, 0, 1],
 [0, 0, 1, 1, 1],
 [0, 0, 1, 1, 1],
 [0, 0, 0, 0, 1]
 ]
 return 6
 */
public class MaximalRectangle {

    /**
     * Thought: use {@link com.advanced.dataStructure2.stack.LargestRectangleInHistogram}
     * for each row, covert question as largest rectangle in histogram
     */
    public int maximalRectangle(boolean[][] matrix) {
        if (matrix == null) {
            return 0;
        }
        return maximalRectangle(convertToIntMatrix(matrix));
    }

    /**
     * O(n ^ 2) time and O(n ^ 2) space
     */
    public int maximalRectangle(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }
        int row = matrix.length;
        int col = matrix[0].length;
        int[][] heights = new int[row][col + 1]; // need extra col to store -1 value so that pop out all bar index

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (i == 0) {
                    heights[i][j] = matrix[i][j] == 0 ? 0 : 1;
                } else {
                    heights[i][j] += matrix[i][j] == 0 ? 0 : heights[i-1][j] + 1;
                }
            }
        }

        int maxArea = 0; // global max area
        int area = 0; // local var for rectangle area calculation
        // for row perspective
        for (int i = 0; i < row; i++) {
            area = maxAreaInHist(heights[i]);
            maxArea = Math.max(maxArea, area);
        }
        return maxArea;
    }

    public int maximalRectangleLessSpace(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }
        int row = matrix.length;
        int col = matrix[0].length;
        int maxArea = 0;
        int[] heights = new int[col + 1]; // for each row, height array
        int currHeight = 0; // current height for comparison
        int h = 0; // height of rectangle
        int w = 0; // width of rectangle
        Stack<Integer> stack = new Stack<>(); // stack to store bar index

        for (int i = 0; i < row; i++) {
            // for each row, get height array
            for (int j = 0; j < col; j++) {
                heights[j] = matrix[i][j] == 0 ? 0 : heights[j] + 1;
            }

            // cal maximum rectangle area by col
            // assign -1 as extra col so as to pop out all value
            for (int j = 0; j <= col; j++) {
                currHeight = j < col ? heights[j] : -1;
                // go through stack until stack is empty or stack.peek < currentHeight
                while (!stack.isEmpty() && heights[stack.peek()] >= currHeight) {
                    h = heights[stack.pop()];
                    w = stack.isEmpty() ? j : (j-1) - stack.peek();
                    maxArea = Math.max(maxArea, h * w);
                    System.out.println(String.format("h %s, w %s, j %s, area %s, stack peek %s", h, w, j, maxArea,
                            stack.isEmpty() ? "N/A" : stack.peek()));
                }
                stack.push(j);
            }
        }
        return maxArea;
    }

    private int maxAreaInHist(int[] heights) {
        Stack<Integer> stack = new Stack<>();
        int i = 0; // global index
        int h = 0; // height of rectangle;
        int w = 0; // width of rectangle;
        int max = 0;

        while (i < heights.length) {
            if (stack.isEmpty() || heights[stack.peek()] <= heights[i]) {
                stack.push(i);
                i++; // move to next pos
            } else { // cal rectangle area when value is popped
                h = heights[stack.pop()];
                // width need to consider when stack is empty
                w = stack.isEmpty() ? i : (i-1)-stack.peek();
                max = Math.max(max, h * w);
            }
        }
        return max;
    }

    private int[][] convertToIntMatrix(boolean[][] matrix) {
        // no case when matrix is null
        int[][] intMatrix = new int[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                intMatrix[i][j] = matrix[i][j] ? 1 : 0;
            }
        }
        return intMatrix;
    }

    public static void main(String[] args) {
        MaximalRectangle m = new MaximalRectangle();
        int[][] matrix = {{1, 1, 0, 0, 1}, {0, 1, 0, 0, 1}, {0, 0, 1, 1, 1}, {0, 0, 1, 1, 1}, {0, 0, 0, 0, 1}};
//        System.out.println(m.maximalRectangle(matrix));
        System.out.println(m.maximalRectangleLessSpace(matrix));
    }
}
