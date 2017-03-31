package com.leetcode.greedy;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by charles on 3/8/17.
 * Suppose you have a random list of people standing in a queue. Each person is described by a pair of integers (h, k), where h is the height of the person and k is the number of people in front of this person who have a height greater than or equal to h. Write an algorithm to reconstruct the queue.

 Note:
 The number of people is less than 1,100.

 Example

 Input:
 [[7,0], [4,4], [7,1], [5,0], [6,1], [5,2]]

 Output:
 [[5,0], [7,0], [5,2], [6,1], [4,4], [7,1]]
 */
public class QueueRecontructionByHeight_406 {
    /**
     * Greedy Solution
     * Only consider about taller or equal one
     * The shortest people with smallest k is useless for others. he should be ranked lastest
     * Reversely, the tallest one with largest k is most powerful. who should be ranked first;
     */
    public int[][] reconstructQueue(int[][] people) {
        if (people == null || people.length == 0) {
            return new int[0][0];
        }
        Arrays.sort(people, (p1,p2) -> {
            int compare = Integer.compare(p1[0], p2[0]);
            if (compare != 0) {
                return -compare; // DESC p[0] larger in front
            } else {
                return Integer.compare(p1[1], p2[1]); // ASC p[1] smaller in front
            }
        });

        List<int[]> res = new LinkedList<>();
        for (int[] p : people) {
            res.add(p[1], p); // insert by k;
        }
        return res.stream().toArray(int[][]::new);
    }
}
