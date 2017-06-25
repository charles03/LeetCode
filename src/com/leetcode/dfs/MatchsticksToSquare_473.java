package com.leetcode.dfs;

import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.IntStream;

/**
 * Created by charles on 6/17/17.
 * please find out a way you can make one square by using up all those matchsticks. You should not break any stick, but you can link them up, and each matchstick must be used exactly one time.

 Your input will be several matchsticks the girl has, represented with their stick length. Your output will either be true or false, to represent whether you could make one square using all the matchsticks the little match girl has.

 Example 1:
 Input: [1,1,2,2,2]
 Output: true

 Explanation: You can form a square with length 2, one side of the square came two sticks with length 1.
 Example 2:
 Input: [3,3,3,3,4]
 Output: false

 Explanation: You cannot find a way to form a square with all the matchsticks.

 */
public class MatchsticksToSquare_473 {
    public boolean makeSquare(int[] nums) {
        if (nums == null || nums.length < 4) {
            return false;
        }
        int sum = getSum(nums);
        if (sum % 4 != 0) {
            return false;
        }
        /**
         * sort given input array in order to improve speed
         * because checking larger value will fail fast in edge comparison
         */
        nums = IntStream.of(nums).boxed()
                .sorted(Comparator.reverseOrder()).mapToInt(i->i).toArray();
        /**
         * lambda sort in stream is much slower than arrays.sort
         */
        int[] edges = new int[4];
        return dfs(nums, 0, edges, sum/4);
    }
    private int getSum(int[] nums) {
        int sum = 0;
        for (int a : nums) {
            sum += a;
        }
        return sum;
    }
    private boolean dfs(int[] nums, int cursor, int[] edges, int edge) {
        if (cursor == nums.length) {
            if (edges[0] == edge && edges[1] == edge
                    && edges[2] == edge && edges[3] == edge) {
                return true;
            }
            return false;
        }
        for (int i = 0; i < 4; i++) {
            if (edges[i] + nums[cursor] > edge) {
                continue;
            }
            edges[i] += nums[cursor];
            if (dfs(nums, cursor+1, edges, edge)) {
                return true;
            }
            edges[i] -= nums[cursor];
        }
        return false;
    }

    /**
     * without lambda sorting, iterate from tail of sorted array to front
     */
    public boolean makesquareII(int[] nums) {
        if (nums == null || nums.length < 4) {
            return false;
        }
        int sum = getSum(nums);
        if (sum % 4 != 0) {
            return false;
        }
        Arrays.sort(nums); // so iterate from tail to front
        print(nums);
        return dfsII(nums, nums.length - 1, new int[4], sum/4);
    }
    private boolean dfsII(int[] nums, int cursor, int[] perimeter, int edge) {
        // from tail to start, so cursor end up with -1
        print(perimeter);
        System.out.println("------------: " + cursor);
        if (cursor == -1) {
            if (perimeter[0] == edge && perimeter[1] == edge
                    && perimeter[2] == edge && perimeter[3] == edge) {
                return true;
            }
            return false;
        }
        for (int i = 0; i < 4; i++) {
            if (perimeter[i] + nums[cursor] > edge) {
                continue;
            }
//            if (perimeter[i] + nums[cursor] > edge
//                    || (i > 0 && perimeter[i] == perimeter[i-1])) {
//                continue;
//            }
            perimeter[i] += nums[cursor];
            if (dfsII(nums, cursor-1, perimeter, edge)) {
                return true;
            }
            perimeter[i] -= nums[cursor];
        }
        return false;
    }

    public static void main(String[] args) {
        MatchsticksToSquare_473 m = new MatchsticksToSquare_473();
        int[] a1 = {1,1,4,3,2,1,3,1};
        m.makesquareII(a1);

        int[] a2 = {1,5,3,2,1,3,1};
//        m.makesquareII(a2);
    }
    private void print(int[] arr) {
        Arrays.stream(arr).forEach(a-> System.out.print(a + ","));
        System.out.println();
    }
}
