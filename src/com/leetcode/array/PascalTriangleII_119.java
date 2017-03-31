package com.leetcode.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by charles on 3/22/17.
 * Given an index k, return the kth row of the Pascal's triangle.

 For example, given k = 3,
 Return [1,3,3,1].

 Note:
 Could you optimize your algorithm to use only O(k) extra space?
 */
public class PascalTriangleII_119 {
    public List<Integer> getRow(int rowIndex) {
        List<Integer> prev = null;
        /** when rowIndex = 0, result should be {1} instead of null list
         * so i should be end loop by when i == rowIndex
         */
        for (int i = 0; i <= rowIndex; i++) {
            List<Integer> curr = getRow(i, prev);
            prev = curr;
        }
        return prev;
    }
    public List<Integer> getRow(int index, List<Integer> prevRow) {
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i <= index; i++) {
            if (i == 0 || i == index) {
                res.add(1);
            } else {
                res.add(prevRow.get(i-1) + prevRow.get(i));
            }
        }
        return res;
    }

    public List<Integer> getRowII(int rowIndex) {
        // has to create Integer as object array, instead of primitive.
        Integer[] res = new Integer[rowIndex + 1];
        Arrays.fill(res, 0);
        res[0] = 1;
        for (int i = 1; i <= rowIndex; i++) {
            for (int j = i; j >= 1; j--) {
                res[j] += res[j-1];
            }
        }
        /**
         * [1,0,0]
         * [1,1,0]
         * [1,2,1]
         *
         * for each row, res[0] always 1
         * res[i][j] = res[i-1][j-1] + res[i][j-1]
         * use rolling DP array
         * res[i] = res[i-1] + res[i];
         * we only need open k+1 size array until Kth row
         */
//        new String().contains();
        return Arrays.asList(res);
    }
}
