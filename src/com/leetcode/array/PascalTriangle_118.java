package com.leetcode.array;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by charles on 3/22/17.
 * Given numRows, generate the first numRows of Pascal's triangle.

 For example, given numRows = 5,
 Return

 [
 [1],
 [1,1],
 [1,2,1],
 [1,3,3,1],
 [1,4,6,4,1]
 ]
 */
public class PascalTriangle_118 {
    /**
     * High level: find relationship between last level and current level
     * [1,1] -> [1,2,1]
     */
    public List<List<Integer>> generate(int numRows) {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> prevLevel = null;
        for (int i = 0; i < numRows; i++) {
            List<Integer> currLevel = generateCurrentLevel(i, prevLevel);
            prevLevel = currLevel;
            res.add(currLevel);
        }
        return res;
    }

    List<Integer> generateCurrentLevel(int curr, List<Integer> prev) {
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i <= curr; i++) {
            if (i == 0 || i == curr) {
                res.add(1);
            } else {
                res.add(prev.get(i-1) + prev.get(i));
            }
        }
        return res;
    }
}
