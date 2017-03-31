package com.leetcode.dynamicprogram;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by charles on 3/2/17.
 * A frog is crossing a river. The river is divided into x units and at each unit there may or may not exist a stone. The frog can jump on a stone, but it must not jump into the water.

 Given a list of stones' positions (in units) in sorted ascending order, determine if the frog is able to cross the river by landing on the last stone. Initially, the frog is on the first stone and assume the first jump must be 1 unit.

 If the frog's last jump was k units, then its next jump must be either k - 1, k, or k + 1 units. Note that the frog can only jump in the forward direction.

 Note:

 The number of stones is ≥ 2 and is < 1,100.
 Each stone's position will be a non-negative integer < 231.
 The first stone's position is always 0.
 Example 1:

 [0,1,3,5,6,8,12,17]

 There are a total of 8 stones.
 The first stone at the 0th unit, second stone at the 1st unit,
 third stone at the 3rd unit, and so on...
 The last stone at the 17th unit.

 Return true. The frog can jump to the last stone by jumping
 1 unit to the 2nd stone, then 2 units to the 3rd stone, then
 2 units to the 4th stone, then 3 units to the 6th stone,
 4 units to the 7th stone, and 5 units to the 8th stone.
 Example 2:

 [0,1,2,3,4,8,9,11]

 Return false. There is no way to jump to the last stone as
 the gap between the 5th and 6th stone is too large.
 */
public class FrogJump_403 {
    /**
     * Brute Force
     * O(3^n) time and O(n) space
     */
    public boolean canCross(int[] stones) {
        return canCrossHelper(stones, 0, 0);
    }

    /**
     * Then for every function call, we start from the currentPositioncurrentPosition and check if there lies a stone at (currentPostion + newjumpsize)(currentPostion+newjumpsize), where, the newjumpsizenewjumpsize could be jumpsizejumpsize, jumpsize+1jumpsize+1 or jumpsize-1jumpsize−1.
     * In order to check whether a stone exists at the specified positions, we check the elements of the array in a linear manner.
     * If a stone exists at any of these positions, we call the recursive function again with the same stone array, the currentPositioncurrentPosition and the newjumpsizenewjumpsize as the parameters.
     * If we are able to reach the end of the stone array through any of these calls, we return truetrue to indicate the possibility of reaching the end.
     */
    private boolean canCrossHelper(int[] stones, int idx, int jumpSize) {
        for (int i = idx + 1; i < stones.length; i++) {
            int gap = stones[i] - stones[idx];
            if (gap >= jumpSize - 1 && gap <= jumpSize + 1) {
                if (canCrossHelper(stones, i, gap)) {
                    return true;
                }
            }
        }
        return idx == stones.length - 1;
    }

    /**
     * DP solution
     * use hashmap as dp table which contains key,value pairs
     * key refers to position at which a stone is present and value is a set containing the jumpsize which can lead to current stone position
     * start traversing the elems(position) of the given stone array in sequential order
     * for curr position, for every possible jumpsize in the value set, we check if current position + newjumpsize exists in the map
     * where newjumpsize can be either jumpsize - 1, jumpsize, jumpsize + 1.
     * then append corresponding value set with new jumpsize. we continue in the same manner.
     * if at the end the value set correspodning to the last pos is non-empty we conclude that reading the end is possible, otherwise it isn't
     */
    public boolean canCrossDp(int[] stones) {
        Map<Integer, Set<Integer>> map = new HashMap<>();
        for (int i = 0; i < stones.length; i++) {
            map.put(stones[i], new HashSet<>());
        }
        map.get(0).add(0);

        for (int i = 0; i < stones.length; i++) {
            // value is set containing jumpsize which can lead to current position
            for (int k : map.get(stones[i])) {
                // loop for 3 cases (k-1, k, k+1)
                for (int s = k - 1; s <= k + 1; s++) {
                    if (s > 0 && map.containsKey(stones[i] + s)) {
                        map.get(stones[i] + s).add(s);
                    }
                }
            }
        }
        return map.get(stones[stones.length - 1]).size() > 0;
    }
}
