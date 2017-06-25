package com.leetcode.binarysearch;

import javax.swing.plaf.metal.MetalTheme;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.TreeSet;

/**
 * Created by charles on 4/11/17.
 * Winter is coming! Your first job during the contest is to design a standard heater with fixed warm radius to warm all the houses.

 Now, you are given positions of houses and heaters on a horizontal line, find out minimum radius of heaters so that all houses could be covered by those heaters.

 So, your input will be the positions of houses and heaters seperately, and your expected output will be the minimum radius standard of heaters.

 Note:
 Numbers of houses and heaters you are given are non-negative and will not exceed 25000.
 Positions of houses and heaters you are given are non-negative and will not exceed 10^9.
 As long as a house is in the heaters' warm radius range, it can be warmed.
 All the heaters follow your radius standard and the warm radius will the same.
 Example 1:
 Input: [1,2,3],[2]
 Output: 1
 Explanation: The only heater was placed in the position 2, and if we use the radius 1 standard, then all the houses can be warmed.
 Example 2:
 Input: [1,2,3,4],[1,4]
 Output: 1
 Explanation: The two heater was placed in the position 1 and 4. We need to use radius 1 standard, then all the houses can be warmed.
 */
public class Heaters_475 {
    /**
     * Thought:
     * in order to use binary search, need to sort heater array
     * based on location of each heater, insert index of houses between two heaters
     * calculate distances between this house and left heater and right heater.
     * corner cases are there is no left or right heater.
     * between those two heaters, find min radius (heat1 - house, head2-house)
     *
     * And then maintain global Max var to record min radius of each house.
     * return this max
     */
    public int findRadius(int[] houses, int[] heaters) {
        Arrays.sort(heaters);
        int res = Integer.MIN_VALUE;

        int insertIdx = 0;
        int leftDist = 0;
        int rightDist = 0;
        for (int house : houses) {
            insertIdx = Arrays.binarySearch(heaters, house);
            if (insertIdx < 0) {
                insertIdx = ~insertIdx;
                leftDist = insertIdx-1 >= 0 ? house - heaters[insertIdx - 1] : Integer.MAX_VALUE;
                rightDist = insertIdx < heaters.length ? heaters[insertIdx] - house : Integer.MAX_VALUE;

                res = Math.max(res, Math.min(leftDist, rightDist));
            }
        }
        return res;
    }

    public int findRadiusII(int[] houses, int[] heaters) {
        Arrays.sort(heaters);
        int res = Integer.MIN_VALUE;
        int idx = 0, len = heaters.length;
        int left = Integer.MAX_VALUE, right = Integer.MAX_VALUE;
        for (int house : houses) {
            idx = binarySearchInsertIdx(heaters, house);
//            if (idx > 0) {
//                left = house - heaters[idx - 1];
//            } else {
//                left = Integer.MAX_VALUE;
//            }
//            if (idx < heaters.length) {
//                right = heaters[idx] - house;
//            } else {
//                right = Integer.MAX_VALUE;
//            }
            left = idx > 0 ? house - heaters[idx - 1] : Integer.MAX_VALUE;
            right = idx < len ? heaters[idx] - house : Integer.MAX_VALUE;
            res = Math.max(res, Math.min(left, right));
        }
        return res;
    }
    /** similar to {@link com.leetcode.binarysearch.SearchInsertPosition_35} to find left most insert point */
    private int binarySearchInsertIdx(int[] arr, int target) {
        int start = 0;
        int end = arr.length;
        int mid = 0;
        while (start < end) {
            mid = start + (end - start) / 2;
            if (arr[mid] < target) {
                start = mid + 1;
            } else {
                end = mid;
            }
        }
        return start;
    }

    public static void main(String[] args) {
        Heaters_475 h = new Heaters_475();
        int[] a1 = {1,5};
        int[] a2 = {2};
        System.out.println(h.findRadiusII(a1, a2) == 3);
    }
    /**
     * Use api TreeSet
     * to find ceiling/flooring of new insert number
     */
    public int findRadiusIII(int[] houses, int[] heaters) {
        TreeSet<Integer> treeSet = new TreeSet<>();
        // add index of heaters into tree
        for (int heater : heaters) {
            treeSet.add(heater);
        }
        int res = Integer.MIN_VALUE;
        int left, right = 0;
        for (int house : houses) {
            Integer upper = treeSet.ceiling(house);
            Integer lower = treeSet.floor(house);

            left = lower == null ? Integer.MAX_VALUE : house - lower;
            right = upper == null ? Integer.MAX_VALUE : upper - house;
            res = Math.max(res, Math.min(left, right));
        }
        return res;
    }

}
