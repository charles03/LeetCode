package com.leetcode.hash;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by charles on 6/4/17.
 * We define a harmonious array is an array where the difference between its maximum value and its minimum value is exactly 1.

 Now, given an integer array, you need to find the length of its longest harmonious subsequence among all its possible subsequences.

 Example 1:
 Input: [1,3,2,2,5,2,3,7]
 Output: 5
 Explanation: The longest harmonious subsequence is [3,2,2,2,3].
 */
public class LongestHarmoniousSubsequence_594 {
    /**
     * use map to store numbers of times an element occurs in array along with element value
     * after map created, traverse key in the map.
     * to find key+1 in the map, if found, such element can be counted for harmonic subsequence
     *
     * no need to consider key-1, because key-1 case is been considered when key-1 as current key,
     */
    public int findLHS(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        int res = 0;
        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }
        for (int key : nums) {
            if (map.containsKey(key+1)) {
                res = Math.max(res, map.get(key) + map.get(key+1));
            }
        }
        return res;
    }

    /**
     * single loop to get result
     * we can determin lengths of harmonic subsequence possible till current index of nums array
     * but this time we need to consider existence of both key+1 and key-1 exclusively
     *
     * because key+1 and key-1 are different direction, for current key, it may join key+1 direction or key-1 direction,
     * but cannot in both ways. otherwise will miss result;
     */
    public int findLHSII(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        int res = 0;
        for (int num : nums) {
            // record count for same number as key till current index in array
            map.put(num, map.getOrDefault(num, 0) + 1);
            if (map.containsKey(num+1)) {
                res = Math.max(res, map.get(num) + map.get(num+1));
            }
            if (map.containsKey(num-1)) {
                res = Math.max(res, map.get(num) + map.get(num-1));
            }
        }
        return res;
    }
}
