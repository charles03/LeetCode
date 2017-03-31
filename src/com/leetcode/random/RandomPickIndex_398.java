package com.leetcode.random;

import java.util.Random;

/**
 * Created by charles on 3/10/17.
 * Given an array of integers with possible duplicates, randomly output the index of a given target number. You can assume that the given target number must exist in the array.

 Note:
 The array size can be very large. Solution that uses too much extra space will not pass the judge.

 Example:

 int[] nums = new int[] {1,2,3,3,3};
 Solution solution = new Solution(nums);

 // pick(3) should return either index 2, 3, or 4 randomly. Each index should have equal probability of returning.
 solution.pick(3);

 // pick(1) should return 0. Since in the array only nums[0] is equal to 1.
 solution.pick(1);
 */
public class RandomPickIndex_398 {
    /**
     * Reservoir sampling,
     * is a family of randomized algorithms for randomly choosing a sample of k items from a list S containing n items, where n is either a very large or unknown number. Typically n is large enough that the list doesn't fit into main memory.
     * Keep the first item in memory.
     When the i-th item arrives (for {\displaystyle i>1} i>1):
     with probability {\displaystyle 1/i} 1/i, keep the new item (discard the old one)
     with probability {\displaystyle 1-1/i} {\displaystyle 1-1/i}, keep the old item (ignore the new one)
     So:

     when there is only one item, it is kept with probability 1;
     when there are 2 items, each of them is kept with probability 1/2;
     when there are 3 items, the third item is kept with probability 1/3, and each of the previous 2 items is also kept with probability (1/2)(1-1/3) = (1/2)(2/3) = 1/3;
     by induction, it is easy to prove that when there are n items, each item is kept with probability 1/n
     */


    int[] nums;
    Random random;

    public RandomPickIndex_398(int[] nums) {
        this.nums = nums;
        this.random = new Random();
    }

    public int pick(int target) {
        int total = 0;
        int res = -1;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == target) {
                // randomly select an int from 0 to the nums of target.
                // if x equals 0, set the res as the current index.
                // the probability is always 1/nums for the latest appeared number.
                // for example, 1 for 1st num, 1/2 for 2nd num, 1/3 for 3rd num (1/2 * 2/3 for each of the first 2 nums)
                int x = random.nextInt(++total);
                if (x == 0) {
                    res = i;
                }
            }
        }
        return res;
    }

    /**
     * Think of that: if there is only one digit, the only random number has to be 0.
     How about two, three, four? in order to make sure we have equal probability, 0 is one good and valid indicator the rest number has same probability as first matches number.
     Be aware that, random number has nothing to do with the number in the array. We just use that random number to know whether current number has same probablity
     */
    public int pickII(int target) {
        int index = -1;
        int count = 1;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == target && random.nextInt(count) == 0) {
                index = i;
            }
            count++;
        }
        return index;
    }
}
