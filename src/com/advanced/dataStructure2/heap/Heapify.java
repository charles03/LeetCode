package com.advanced.dataStructure2.heap;

import java.util.Arrays;

/**
 * Created by charles on 10/27/16.
 * Given an integer array, heapify it into a min-heap array.

 For a heap array A, A[0] is the expTreeNode of heap,
 and for each A[i], A[i * 2 + 1] is the left child of A[i] and A[i * 2 + 2] is the right child of A[i].

 Clarification
 What is heap?

 Heap is a data structure, which usually have three methods: push, pop and top. where "push" add a new element the heap, "pop" delete the minimum/maximum element in the heap, "top" return the minimum/maximum element.

 What is heapify?
 Convert an unordered integer array into a heap array. If it is min-heap, for each element A[i], we will get A[i * 2 + 1] >= A[i] and A[i * 2 + 2] >= A[i].

 What if there is a lot of solutions?
 Return any of them.
 Example
 Given [3,2,1,4,5], return [1,2,3,4,5] or any legal heap array.

 Challenge: O(n)
 */
public class Heapify {
    public void heapify(int[] A) {
        if (A == null || A.length == 0) {
            return;
        }
        // when choose sift down, start index must be A.length / 2
        // in order to match conditions from back to front/ from bottom to up
        for (int i = A.length / 2; i >= 0; i--) {
            siftDown(A, i);
        }
    }

    public void siftDown(int[] A, int k) {
        int smallest = 0;
        int temp = 0;
        while (k < A.length) {
            smallest = k;
            if (k * 2 + 1 < A.length && A[k * 2 + 1] < A[smallest]) {
                smallest = k * 2 + 1;
            }
            if (k * 2 + 2 < A.length && A[k * 2 + 2] < A[smallest]) {
                smallest = k * 2 + 2;
            }
            // break while loop when meet requirement of min heap
            if (smallest == k) {
                break;
            }
            // swap
            temp = A[smallest];
            A[smallest] = A[k];
            A[k] = temp;
            // continue to sift down
            k = smallest;
        }
    }
    public static void main(String[] args) {
        Heapify heap = new Heapify();
        int[] arr = {3, 2, 1, 4, 5};
        heap.heapify(arr);
        Arrays.stream(arr).forEach(a -> System.out.print(a));
    }
}
