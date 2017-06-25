package com.leetcode.array;

/**
 * Created by charles on 6/10/17.
 * A zero-indexed array A consisting of N different integers is given. The array contains all integers in the range [0, N - 1].

 Sets S[K] for 0 <= K < N are defined as follows:

 S[K] = { A[K], A[A[K]], A[A[A[K]]], ... }.

 Sets S[K] are finite for each K and should NOT contain duplicates.

 Write a function that given an array A consisting of N integers, return the size of the largest set S[K] for this array.

 Example 1:
 Input: A = [5,4,0,3,1,6,2]
 Output: 4
 Explanation:
 A[0] = 5, A[1] = 4, A[2] = 0, A[3] = 3, A[4] = 1, A[5] = 6, A[6] = 2.

 One of the longest S[K]:
 S[0] = {A[0], A[5], A[6], A[2]} = {5, 6, 2, 0}
 Note:
 N is an integer within the range [1, 20,000].
 The elements of A are all distinct.
 Each element of array A is an integer within the range [0, N-1].
 */
public class ArrayNesting_565 {
    /**
     * naive solution is to iterate over all indices of given number array
     * for each index i chosen, increment count and go for next index nums[i] and terminating when i == nums[i]
     * since all elem in nums lie in the range (0..N-1) the new indices will never lie outside array size limits
     *
     * [1,2,3,4,5,0] worst case
     * in worst case, all elem are added to set corresponding to all starting indices.
     * but all these sets corresponds to same set of elements only, leading to redundant calculation
     *
     * for each set, same elems will be added to current set irrespective of
     * first elem chose to be added to set out of these marked elements
     *
     * we can use visited auxiliary array to mark elems in same set, so as to avoid redundant calculation
     *
     * ## ultimate version, only O(1) space
     * use Larger Integer outside of range (0, N-1) like Integer.MAX_VALUE to mark position
     * bad thing is to modify given input array.
     */
    public int arrayNesting(int[] nums) {
        int res = 0;
        int next = 0, prev = 0;
        int count = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != Integer.MAX_VALUE) { // not visited
                next = nums[i];
                count = 0;
                while (nums[next] != Integer.MAX_VALUE) {
                    prev = next;
                    next = nums[next]; // move to next
                    nums[prev] = Integer.MAX_VALUE; // update visited to prev
                    count++;
                }
                res = Math.max(res, count);
            }
        }
        return res;
    }
}
