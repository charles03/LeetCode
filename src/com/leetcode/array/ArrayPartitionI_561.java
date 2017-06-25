package com.leetcode.array;

import java.util.Arrays;

/**
 * Created by charles on 4/28/17.
 * Given an array of 2n integers, your task is to group these integers into n pairs of integer, say (a1, b1), (a2, b2), ..., (an, bn) which makes sum of min(ai, bi) for all i from 1 to n as large as possible.

 Example 1:
 Input: [1,4,3,2]

 Output: 4
 Explanation: n is 2, and the maximum sum of pairs is 4.
 Note:
 n is a positive integer, which is in the range of [1, 10000].
 All the integers in the array will be in the range of [-10000, 10000].
 */
public class ArrayPartitionI_561 {
    /** solution 1: sort array -> then group pair by pair
     * Proof :
     * given a1 < a2 < a3 < a4
     * to prove min(a1,a3) + min(a2,a4) < min(a1,a2) + min(a3,a4)
     */
    public int arrayPairSum(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        Arrays.sort(nums);
        int res = 0;
        for (int i = 1; i <= nums.length; i += 2) {
            res += nums[i-1];
        }
        return res;
    }
    /** solution 2: use hash table to store all values into map[20001]
     * Without sorting
     * because numbers in range of [-10000, 10000],
     * then use index to keep order of number,
     *
     */
    public int arrayPairSumII(int[] nums) {
        int n = nums.length >> 1;
        int[] table = new int[20001];
        // shift negative number to positive
        // keep track of count of appearance as well as the order
        for (int num : nums) {
            table[num + 10000] += 1;
        }
        int res = 0;
        int index = 0;

        while (n > 0) {
            // table[i] == 0 means i-10000 doesn't exist in input array
            while (table[index] == 0) {
                index++;
            }
            // current val is min of pair(n1, n2)
            res += (index - 10000);
            table[index]--;
            while (table[index] == 0) { // find next existing in input array
                index++;
            }
            table[index]--; // skip max of pair(n1, n2);
            n--;
        }
        return res;
    }

    /** simplified version, use tabel[20001] to reduce time complexity
     * given input in range -10000, 10000
     * so shift range to 0, 20001 */
    private static final int BASE = 10000;
    public int arrayPairSumIII(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int sum = 0;
        int[] map = new int[20001];
        for (int num : nums) {
            map[num + BASE]++;
        }
        /**
         * for now, given inputs are sorted in map due to index order
         * as for dups in map, only chose map[i] / 2 + 1
         * example
         * map (nums is count of occurrence in array): [1,4,5,4]
         * because start from small index,
         * choose 1, then need borrow one from next index
         * after borrow, 4 -> 3
         *  (3+1)/ 2 is count of number chosen to add into sum
         * besides, 3 % 2 != 0, need to borrow from next index
         * after borrow, 5 -> 4
         *  (4+1)/ 2 is count of number choosen to add into sum
         */
        int borrowFromNext = 0;
        int countForCalc = 0;
        for (int i = 0; i < map.length; i++) {
            if (map[i] == 0) {
                continue;
            }
            // purpose of map[i] - borrow + 1 is to get upper bound of (odd number / 2)
            // at the same time no affect on even number
            // 3/2 ==> expect result is 2 <== so use (3+1)/2
            // 4/2 ==> expect result is 2 <== no effect (4+1)/2
            countForCalc = (map[i] - borrowFromNext + 1) / 2;
            sum += (i-BASE) * countForCalc;
            borrowFromNext = (map[i] - borrowFromNext) % 2;
        }
        return sum;
    }
}
