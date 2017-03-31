package com.leetcode.dynamicprogram;

import java.util.Arrays;

/**
 * Created by charles on 2/23/17.
 * Given a non-empty integer array of size n, find the minimum number of moves required to make all array elements equal, where a move is incrementing n - 1 elements by 1.

 Example:
 Input:
 [1,2,3]
 Output:
 3
 Explanation:
 Only three moves are needed (remember each move increments two elements):
 [1,2,3]  =>  [2,3,3]  =>  [3,4,3]  =>  [4,4,4]
 */
public class MinimumMovesToEqualArrayElement_453 {
    /**
     * The given problem can be simplified if we sort the given array once. If we consider a sorted array aa, instead of trying to work on the complete problem of equalizing every element of the array, we can break the problem for array of size nn into problems of solving arrays of smaller sizes. Assuming, the elements upto index i-1i−1 have been equalized, we can simply consider the element at index ii and add the difference diff=a[i]-a[i-1]diff=a[i]−a[i−1] to the total number of moves for the array upto index ii to be equalized i.e. moves=moves+diffmoves=moves+diff. But when we try to proceed with this step, as per a valid move, the elements following a[i]a[i] will also be incremented by the amount diffdiff i.e. a[j]=a[j]+diffa[j]=a[j]+diff, for j>ij>i. But while implementing this approach, we need not increment all such a[j]a[j]'s. Instead, we'll add the number of movesmoves done so far to the current element i.e. a[i]a[i] and update it to a'[i]=a[i]+movesa​[i]=a[i]+moves.
     In short, we sort the given array, and keep on updating the movesmoves required so far in order to equalize the elements upto the current index without actually changing the elements of the array except the current element. After the complete array has been scanned movesmoves gives the required solution.
     */
    public int minMoves(int[] nums) {
        Arrays.sort(nums);
        int moves = 0;
        for (int i = 1; i < nums.length; i++) {
            // to get current diff, based on sum of prev-moves and diff of nums[i-1, i]
            int diff = moves + nums[i] - nums[i-1];
            // add moves to current index of num so that next one will see the corresponding increment, without make a change to each of j where j > i
            nums[i] += moves;
            moves += diff;
        }
        return moves;
    }

    /**
     * Math Solution without modification
     * Idea :
     * Adding 1 to all elements except one is equivalent to decrementing 1 from a single element
     * Thus problem is simplified to find the number of decrement operations required to equalize all the element of given array
     * Besides, in order to find answer, need not actually decrement elements,
     * we can find number of moves required as moves = sum of nums[i] - min(nums[i]) * n where n is length of array
     */
    public int minMovesII(int[] nums) {
        int moves = 0;
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < nums.length; i++) {
            moves += nums[i];
            min = Math.min(min, nums[i]);
        }
        return moves - min * nums.length;
    }

    /**
     * Math solution with handling integer overflow
     * problem of above approach will cause integer overflow when calculate sum of nums[i]
     * To avoid problem, we can calculate required number of moves on the fly sum of (a[i] - min(a))
     */
    public int minMovesIII(int[] nums) {
        int moves = 0;
        int min = Integer.MAX_VALUE;
        // first to find min in array
        for (int num : nums) {
            min = Math.min(min, num);
        }
        // to calculate moves
        for (int num : nums) {
            moves += num - min;
        }
        return moves;
    }
}
