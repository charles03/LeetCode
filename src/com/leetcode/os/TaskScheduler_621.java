package com.leetcode.os;

import java.util.Arrays;

/**
 * Created by charles on 6/19/17.
 * Given a char array representing tasks CPU need to do. It contains capital letters A to Z where different letters represent different tasks.Tasks could be done without original order. Each task could be done in one interval. For each interval, CPU could finish one task or just be idle.

 However, there is a non-negative cooling interval n that means between two same tasks, there must be at least n intervals that CPU are doing different tasks or just be idle.

 You need to return the least number of intervals the CPU will take to finish all the given tasks.

 Example 1:
 Input: tasks = ['A','A','A','B','B','B'], n = 2
 Output: 8
 Explanation: A -> B -> idle -> A -> B -> idle -> A -> B.
 */
public class TaskScheduler_621 {
    /**
     * count given task array, to have key-value map
     * sort this map. so that create m * n matrix
     * where m is max count of certain task - 1, n is interval
     * initial, fill idle slots with order task map
     *
     * [A,A,A,B,B,B,C] given example, so having below matrix
     * m is max count (3) - 1 = 2
     * n is interval (4)
     * total idle slots at init is m * n
     * then fill idle with tasks and to calculate left-over idle slots
     * idle -= Math.min(max-1, curr count of given task);
     *
     * A B C    idle
     * A B idle idle
     * A B
     *
     */
    public int leastInterval(char[] tasks, int n) {
        int[] map = new int[26];
        for (char c : tasks) {
            map[c - 'A']++;
        }
        Arrays.sort(map);
        int row = map[25] - 1;
        int col = n;
        int countOfIdleSlots = row * col;
        for (int i = 24; i >= 0; i--) {
            countOfIdleSlots -= Math.min(row, map[i]);
        }
        if (countOfIdleSlots > 0) {
            return countOfIdleSlots + tasks.length;
        } else {
            return tasks.length;
        }
    }
}
