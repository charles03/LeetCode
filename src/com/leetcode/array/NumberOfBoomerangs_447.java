package com.leetcode.array;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by charles on 3/20/17.
 * Given n points in the plane that are all pairwise distinct, a "boomerang" is a tuple of points (i, j, k) such that the distance between i and j equals the distance between i and k (the order of the tuple matters).

 Find the number of boomerangs. You may assume that n will be at most 500 and coordinates of points are all in the range [-10000, 10000] (inclusive).

 Example:
 Input:
 [[0,0],[1,0],[2,0]]

 Output:
 2

 Explanation:
 The two boomerangs are [[1,0],[0,0],[2,0]] and [[1,0],[2,0],[0,0]]
 */
public class NumberOfBoomerangs_447 {
    /** to calculate distance of each two points
     * outer loop, given fix point p
     * inner loop, to find the other two points p1, p2
     * where dist(p1,p) == dist(p2,p)
     * add into map
     * also [a,b,c] and [a,c,b] are two different cases we need to multiply count by 2;
     */
    public int numberOfBoomerangs(int[][] points) {
        int count = 0;
        Map<Integer, Integer> map = new HashMap<>();

        /**
         * two level for loops,
         * [1,0], [2,0], dist of points is 1
         * [1,0], [0,0], dist of points is 1
         *
         * (1,0)
         * (1,0)(0,0)(2,0)
         * map.clear()
         * (0,0)
         * (1,0)(0,0)(2,0)
         * map.clear()
         * (2,0)
         * (1,0)(0,0)(2,0)
         *
         * each time search all possible solution within current loop
         */
        for (int[] left : points) {
            for (int[] right : points) {
                int dx = left[0] - right[0];
                int dy = left[1] - right[1];
                int dist = dx * dx + dy * dy;
                Integer value = map.get(dist);
                if (value != null) {
                    count += 2 * value;
                    map.put(dist, value + 1);
                } else {
                    map.put(dist, 1);
                }
            }
            map.clear();
        }
        return count;
    }
}
