package com.advanced.followup;

import java.util.HashSet;
import java.util.Objects;
import java.util.PriorityQueue;

/**
 * Created by charles on 9/16/16.
 *
 * Given two integer arrays sorted in ascending order and an integer k.
 * Define sum = a + b, where a is an element from the first array and b is an element from the second one.
 * Find the kth smallest sum out of all possible sums.
 *
 * Given [1, 7, 11] and [2, 4, 6].

 For k = 3, return 7.

 For k = 4, return 9.

 For k = 8, return 15.

 challenge:
 Do it in either of the following time complexity:

 O(k log min(n, m, k)). where n is the size of A, and m is the size of B.
 O( (m + n) log maxValue). where maxValue is the max number in A and B.

 Tag: Heap / Priority Queue
 */
public class KthSmallestSumInTwoSortedArray {
    private int[] dx = {0, 1};
    private int[] dy = {1, 0};

    public int kthSmallestSum(int[] A, int[] B, int k) {
        if (A == null || B == null) {
            return 0;
        }
        if (A.length == 0 && B.length == 0) {
            return 0;
        } else if (A.length == 0 && k < B.length) {
            return B[k];
        } else if (B.length == 0 && k < A.length) {
            return A[k];
        }

        HashSet<Pair> isVisited = new HashSet<Pair>();
        PriorityQueue<Pair> minHeap = new PriorityQueue<Pair>();

        Pair p = new Pair(0, 0, A[0] + B[0]);
        minHeap.add(p);
        isVisited.add(p);

        int nextX = 0;
        int nextY = 0;
        int nextSum = 0;

        /**
         * be careful, the edge is k-1 instead k
         */
        Pair nextP = null;
        for (int i = 0; i < k - 1; i++) {
            p = minHeap.poll();
            for (int j = 0; j < 2; j++) {
                nextX = p.x + dx[j];
                nextY = p.y + dy[j];
                nextP = new Pair(nextX, nextY, 0);
                if (nextX >= 0 && nextX < A.length
                        && nextY >= 0 && nextY < B.length
                        && !isVisited.contains(nextP)) {
                    nextSum = A[nextX] + B[nextY];
                    nextP.sum = nextSum;
                    minHeap.add(nextP);
                    isVisited.add(nextP);
                }
            }
        }

        return minHeap.peek().sum;
    }

    public static void main(String[] args) {
        int[] A = {1, 7, 11};
        int[] B = {2, 4, 6};

        KthSmallestSumInTwoSortedArray kthSmall = new KthSmallestSumInTwoSortedArray();

        System.out.println(kthSmall.kthSmallestSum(A, B, 3));
    }
}

/*
Assistance class to store x and y index location and sum
 */
class Pair implements Comparable<Pair> {
    public int x, y, sum;
    public Pair(int x, int y, int sum) {
        this.x = x;
        this.y = y;
        this.sum = sum;
    }

    @Override
    // For Heap/PriorityQueue
    public int compareTo(Pair o) {
        if (this.sum == o.sum) {
            return 0;
        }
        return this.sum < o.sum ? -1 : 1;
    }

    @Override
    // For HashSet
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (!(obj instanceof Pair)) {
            return false;
        }
        Pair another = (Pair) obj;
        return this.x == another.x && this.y == another.y;
    }

    @Override
    public int hashCode() {
        return x * 101 + y;
    }

}
