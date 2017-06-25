package com.leetcode.backtrack;

/**
 * Created by charles on 5/18/17.
 * Suppose you have N integers from 1 to N. We define a beautiful arrangement as an array that is constructed by these N numbers successfully if one of the following is true for the ith position (1 ≤ i ≤ N) in this array:

 The number at the ith position is divisible by i.
 i is divisible by the number at the ith position.
 Now given N, how many beautiful arrangements can you construct?

 Example 1:
 Input: 2
 Output: 2
 Explanation:

 The first beautiful arrangement is [1, 2]:

 Number at the 1st position (i=1) is 1, and 1 is divisible by i (i=1).

 Number at the 2nd position (i=2) is 2, and 2 is divisible by i (i=2).

 The second beautiful arrangement is [2, 1]:

 Number at the 1st position (i=1) is 2, and 2 is divisible by i (i=1).

 Number at the 2nd position (i=2) is 1, and i (i=2) is divisible by 1.
 Note:
 N is a positive integer and will not exceed 15.
 */
public class BeautifulArrangement_526 {
    /**
     * use visited array to record each track,
     * and then back track to prev state if attempt is end.
     * need to keep a track of numbers which have already been considered earlier so that they aren't reconsidered
     * while generating the permutation.
     * if current number doesn't satisfy the divisibility criteria, we can leave all permutation that can be generated
     * at the particular position. this help to prune the search space of permutation to a great extent.
     */
    public int countArrangement(int N) {
        boolean[] visited = new boolean[N + 1];
        int count = backtrack(N, 1, 0, visited);
        return count;
    }
    private int backtrack(int N, int pos, int count, boolean[] visited) {
        if (pos > N) {
            count++;
            return count;
        }
        for (int i = 1; i <= N; i++) {
            if (!visited[i] && (pos % i == 0 || i % pos == 0)) {
                visited[i] = true;
                count = backtrack(N, pos + 1, count, visited);
                visited[i] = false; // exist recursion, reset back to prev state
            }
        }
        return count;
    }

    public static void main(String[] args) {
        BeautifulArrangement_526 b = new BeautifulArrangement_526();
        System.out.println(b.countArrangement(2));
        System.out.println(b.countArrangement(5));
    }
}
