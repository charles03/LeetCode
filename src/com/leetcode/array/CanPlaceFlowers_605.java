package com.leetcode.array;

/**
 * Created by charles on 6/4/17.
 * Suppose you have a long flowerbed in which some of the plots are planted and some are not. However, flowers cannot be planted in adjacent plots - they would compete for water and both would die.

 Given a flowerbed (represented as an array containing 0 and 1, where 0 means empty and 1 means not empty), and a number n, return if n new flowers can be planted in it without violating the no-adjacent-flowers rule.

 Example 1:
 Input: flowerbed = [1,0,0,0,1], n = 1
 Output: True
 Example 2:
 Input: flowerbed = [1,0,0,0,1], n = 2
 Output: False
 */
public class CanPlaceFlowers_605 {
    /**
     * to find current max plant while traversing array until index i;
     * we find those elems which are 0 (implying empty),
     * for such elem, we check if its both adjacent pos are also empty;
     * if so, we plant a flower at the current pos
     * for first and last pos of elem, need not check prev and next adjacent pos respectively
     * if count obtained is greated then or equal to n. the required number of flowers to be planted,
     */
    public boolean canPlaceFlowers(int[] flowerbed, int n) {
        int i = 0;
        int count = 0;
        int len = flowerbed.length;
        while (i < len) {
            if (flowerbed[i] == 0
                    && (i == 0 || flowerbed[i-1] == 0)
                    && (i == len-1 || flowerbed[i+1] == 0)) {
                flowerbed[i] = 1;
                count++;
            }
            if (count >= n) {
                return true;
            }
            i++;
        }
        return false;
    }
}
