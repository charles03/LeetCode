package com.advanced.followup;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created by charles on 8/28/16.
 *
 * To find K largest number in N arrays
 *
 * Tag: Heap
 * can swap element in arrays
 */
/*
* In n=2 arrays [[9,3,2,4,7],[1,2,3,4,8]], the 3rd largest element is 7.

In n=2 arrays [[9,3,2,4,8],[1,2,3,4,2]], the 1st largest element is 9,
2nd largest element is 8, 3rd largest element is 7 and etc.
* */

public class KthLargestInNArrays {

    public static int KthLargestInArrays(int[][] arrays, int k) {
        isValid(arrays);
//        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(k, (o1, o2) -> o1.compareTo(o2));
        Queue<Integer> priorityQueue = new PriorityQueue<>(k);

        for  (int i = 0; i < arrays.length; i++) {
            for (int j = 0; j < arrays[i].length; j++) {
                if (priorityQueue.size() < k) {
                    priorityQueue.add(arrays[i][j]);
                } else if (arrays[i][j] > priorityQueue.peek()) {
                    int tmp = priorityQueue.poll();
                    System.out.println("i: " + i + " j: " + j + " value: " + arrays[i][j]);
                    System.out.println("value is polled " + tmp);
                    priorityQueue.add(arrays[i][j]);
                }
            }
        }

        return priorityQueue.peek();
    }

    private static void isValid(int[][] arrays) {
        if (arrays == null || arrays.length == 0) {
            throw new RuntimeException("invalid array");
        }

    }


    public static void main(String[] args) {
        int[][] arr = {{11},{1,2,3,4,112,87},{564},{789,12,15}};
        int res = KthLargestInArrays(arr, 7);
        System.out.println("res " + res);
    }

}
