package com.leetcode.hash;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by charles on 6/11/17.
 * There is a brick wall in front of you. The wall is rectangular and has several rows of bricks. The bricks have the same height but different width. You want to draw a vertical line from the top to the bottom and cross the least bricks.

 The brick wall is represented by a list of rows. Each row is a list of integers representing the width of each brick in this row from left to right.

 If your line go through the edge of a brick, then the brick is not considered as crossed. You need to find out how to draw the line to cross the least bricks and return the number of crossed bricks.

 You cannot draw a line just along one of the two vertical edges of the wall, in which case the line will obviously cross no bricks.

 Example:
 Input:
 [[1,2,2,1],
 [3,1,2],
 [1,3,2],
 [2,4],
 [3,1,2],
 [1,3,1,1]]
 Output: 2
 Note :
 The width sum of bricks in different rows are the same and won't exceed INT_MAX.
 The number of bricks in each row is in range [1,10,000]. The height of wall is in range [1,10,000]. Total number of bricks of the wall won't exceed 20,000.
 */
public class BrickWall_554 {
    /**
     * this question is equally to find most numbers of same prefix width sum
     * for each row of bricks, use map to record prefix sum and count of occurrence.
     */
    public int leastBricks(List<List<Integer>> wall) {
        if (wall.size() == 0) {
            return 0;
        }
        int count = 0;
        Map<Integer, Integer> map = new HashMap<>();
        for (List<Integer> row : wall) {
            int prefixWidthSum = 0;
            for (int i = 0; i < row.size() - 1; i++) { // should stop before row.size()
                /**
                 * !!!! important : i < row.size() - 1
                 * cut from edge of most common location among all levels.
                 */
                prefixWidthSum += row.get(i);
                map.put(prefixWidthSum, map.getOrDefault(prefixWidthSum, 0) + 1);
                count = Math.max(count, map.get(prefixWidthSum));
            }
        }
        // count is result of most occurrence of prefix width sum
        return wall.size() - count;
    }
    /**
     * use list to record all prefix width sums after loop through each row
     * then sort list of prefix sum
     * so iterate this list, counting and find max of count of same prefix sum
     */
    public int leastBricksII(List<List<Integer>> wall) {
        if (wall.size() == 0) {
            return 0;
        }
        int[] prefixSums = getPrefixSums(wall);
        // sort prefix width sum, thus same sums will be adjacent
        Arrays.sort(prefixSums);
        int maxCnt = getMaxCountInPrefixSum(prefixSums);
        return wall.size() - maxCnt;
    }

    /**
     * do not include last elem in each row,
     */
    private int[] getPrefixSums(List<List<Integer>> wall) {
        int prefixSum = 0;
        List<Integer> list = new ArrayList<>();
        for (List<Integer> bricks : wall) {
            prefixSum = 0;
            for (int j = 0; j < bricks.size() - 1; j++) {
                prefixSum += bricks.get(j);
                list.add(prefixSum);
            }
        }
        return list.stream().mapToInt(i->i).toArray();
    }
    private int getMaxCountInPrefixSum(int[] arr) {
        int maxCount = 0;
        int len = arr.length;
        int currCount = 0;
        int i = 0;
        int j = 0;
        while (i < len) {
            currCount = 1;
            j = i + 1;
            while (j < len && arr[j] == arr[i]) {
                currCount++;
                j++;
            }
            maxCount = Math.max(maxCount, currCount);
            i = j;
        }
        return maxCount;
    }

    /**
     * due to worse performance in java 8 stream api
     * below having better performance
     * 1. get total counts of bricks to init int array
     * 2. directly working on int array instead of list
     */
    public int leastBricksIII(List<List<Integer>> wall) {
        if (wall.size() == 0) {
            return 0;
        }
        int[] sum = getPrefixSumsII(wall);
        int currPrefix = -1;
        int countOfSame = 0;
        int max = 0;
        Arrays.sort(sum);
        for (int prefix : sum) {
            if (prefix == 0) {
                continue;
            }
            if (prefix != currPrefix) {
                currPrefix = prefix;
                countOfSame = 1;
            } else {
                countOfSame++;
            }
            if (countOfSame > max) {
                max = countOfSame;
            }
        }
        return wall.size() - max;
    }
    private int[] getPrefixSumsII(List<List<Integer>> wall) {
        int totalSize = 0;
        for (List<Integer> bricks : wall) {
            totalSize += bricks.size();
        }
        // because below offset
        // exclude last elem of each line and do not let prefix sum = 0
        // array size is still totalsize instead of totalsize - wall.size
        int[] sum = new int[totalSize - wall.size()];
        int index = 0; // index to iterate overall list of list
        for (List<Integer> bricks : wall) {
            for (int i = 0; i < bricks.size() - 1; i++) {
                if (i == 0) {
                    sum[index++] = bricks.get(0);
                } else {
                    sum[index] = sum[index-1] + bricks.get(i);
                    index++;
                }
            }
        }
        print(sum);
        return sum;
    }

    public static void main(String[] args) {
        int[] a1 = {7, 1, 2};
        int[] a2 = {3, 5, 1, 1};
        int[] a3 = {10};
        List<List<Integer>> l1 = createTest(a1, a2, a3);
        BrickWall_554 b = new BrickWall_554();
        System.out.println(b.leastBricksIII(l1));
    }
    private static List<List<Integer>> createTest(int[]... arrs) {
        List<List<Integer>> res = new ArrayList<>();
        for (int[] arr : arrs) {
            List<Integer> list = Arrays.stream(arr).boxed().collect(Collectors.toList());
            res.add(list);
        }
        return res;
    }
    private void print(int[] arr) {
        Arrays.stream(arr).forEach(t-> System.out.print(t + ","));
        System.out.println();
    }
}
