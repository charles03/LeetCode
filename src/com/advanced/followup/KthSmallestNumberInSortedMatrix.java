package com.advanced.followup;

import java.util.PriorityQueue;

/**
 * Created by charles on 9/15/16.
 *
 * Find the kth smallest number in at row and column sorted matrix.
 *
 * challenge: O(k log n), n is the maximal number in width and height.
 * Tag: Heap/ PriorityQueue/ Matrix
 */

/*
* Given k = 4 and a matrix:
* [
  [1 ,5 ,7],
  [3 ,7 ,8],
  [4 ,8 ,9],
]
return 5;
* */
public class KthSmallestNumberInSortedMatrix {
    /**
     * use direction array to control direction of Bread First search
     * x = {0, 1}
     * y = {1, 0}
     * vertically look the combination of x & y
     * x = 0 & y = 1 -> goes down
     * x = 1 & y = 0 -> goes left
     */
    private int[] dx = {0, 1};
    private int[] dy = {1, 0};

    public int kthSmallest(int[][] matrix, int k) {
        // for edge case
        if (matrix == null || matrix.length == 0) {
            return -1;
        }
        if (matrix.length * matrix[0].length < k) {
            return -1;
        }
        // use lambda exp to get smallest number on the top of heap
        PriorityQueue<Number> heap = new PriorityQueue<Number>(k, (Number n1, Number n2) -> n1.val - n2.val);
        boolean[][] visited = new boolean[matrix.length][matrix[0].length];

        heap.add(new Number(0, 0, matrix[0][0]));
        visited[0][0] = true;

        int newX = 0;
        int newY = 0;
        for (int i = 0; i < k - 1; i++) {
            Number smallest = heap.poll();
            for (int j = 0; j < 2; j++) {
                newX = smallest.x + dx[j];
                newY = smallest.y + dy[j];
                if (isValid(newX, newY, matrix, visited)) {
                    heap.add(new Number(newX, newY, matrix[newX][newY]));
                    visited[newX][newY] = true;
                }
            }
        }

        return heap.peek().val;
    }

    private boolean isValid(int x, int y, int[][] m, boolean[][] b) {
        if (x < m.length && y < m[x].length && !b[x][y]) {
            // when x & y in the scope of matrix and this element is not visited
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        int[][] m = {{1 ,5 ,7},{3 ,7 ,8},{4 ,8 ,9}};
        KthSmallestNumberInSortedMatrix kSmall = new KthSmallestNumberInSortedMatrix();
        int res = kSmall.kthSmallest(m, 3);
        System.out.println("res " + res);
    }
}

class Number {
    // define x, y index location for each element
    public int x, y, val;
    public Number(int x, int y, int val) {
        this.x = x;
        this.y = y;
        this.val = val;
    }
}
