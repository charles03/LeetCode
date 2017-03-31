package com.leetcode.bitwise;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by charles on 2/17/17.
 * Given a non-empty array of numbers, a0, a1, a2, … , an-1, where 0 ≤ ai < 231.

 Find the maximum result of ai XOR aj, where 0 ≤ i, j < n.
 Could you do this in O(n) runtime?
 Example:
 Input: [3, 10, 5, 25, 2, 8]
 Output: 28
 Explanation: The maximum result is 5 ^ 25 = 28.
 */
public class MaximumXorOfTwoNumbersInArray_421 {
    public int findMaximumXor(int[] nums) {
        int max = 0;
        int mask = 0;
        int candidate = 0;
        for (int i = 31; i >= 0; i--) {
            mask |= (1 << i);
            Set<Integer> bitSet = new HashSet<>();
            for (int num : nums) {
                printInfo("add into set ", num, mask, num & mask);
                bitSet.add(num & mask); // reserve Left bits and ignore Right bits
            }
            // if a ^ prefix = candidate then a ^ candidate = prefix, prefix ^ candidate = a
            // prefix is already in set
            // check if "a" exist in the set

            /**
             * XOR feature:
             * if a ^ b = c : then a ^ (a^b) = b -> a ^ c = b
             *                     b ^ (a^b) = b ^ c = a
             */
            candidate = max | (1 << i);
            for (Integer prefix : bitSet) {
                printInfo("search in set ", prefix, candidate, candidate ^ prefix);
                if (bitSet.contains(candidate ^ prefix)) {
                    max = candidate;
                    break;
                }
            }
        }
        return max;
    }

    private void printInfo(String desc, int src, int mask, int des) {
        System.out.println(desc + String.format("%s, %s, %s", Integer.toBinaryString(src), Integer.toBinaryString(mask), Integer.toBinaryString(des)));
    }

    public static void main(String[] args) {
        MaximumXorOfTwoNumbersInArray_421 m = new MaximumXorOfTwoNumbersInArray_421();
        int[] n1 = {3, 10, 5, 25, 2, 8};
        m.findMaximumXor(n1);
    }
}
