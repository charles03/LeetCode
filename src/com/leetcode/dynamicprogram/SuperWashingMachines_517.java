package com.leetcode.dynamicprogram;

/**
 * Created by charles on 4/22/17.
 * You have n super washing machines on a line. Initially, each washing machine has some dresses or is empty.

 For each move, you could choose any m (1 ≤ m ≤ n) washing machines, and pass one dress of each washing machine to one of its adjacent washing machines at the same time .

 Given an integer array representing the number of dresses in each washing machine from left to right on the line, you should find the minimum number of moves to make all the washing machines have the same number of dresses. If it is not possible to do it, return -1.

 Example1

 Input: [1,0,5]

 Output: 3

 Explanation:
 1st move:    1     0 <-- 5    =>    1     1     4
 2nd move:    1 <-- 1 <-- 4    =>    2     1     3
 3rd move:    2     1 <-- 3    =>    2     2     2
 Example2

 Input: [0,3,0]

 Output: 2

 Explanation:
 1st move:    0 <-- 3     0    =>    1     2     0
 2nd move:    1     2 --> 0    =>    1     1     1
 Example3

 Input: [0,2,0]

 Output: -1

 Explanation:
 It's impossible to make all the three washing machines have the same number of dresses.
 Note:
 The range of n is [1, 10000].
 The range of dresses number in a super washing machine is [0, 1e5].
 */
public class SuperWashingMachines_517 {
    /**
     * first of all, sum of all dresses should be divided by number of machines
     * then convert machine array to a gain/lose array by deducted with average of sum dresses
     * and then is to traverse from left to right to make all of them 0
     *
     * as for details about how these machines give load to each other,
     * the least steps we need to eventually finish this process is determined by peak of abs(cnt) and the max of gain/loss array.
     *
     * in order to calculate min moves of total dresses.
     * we need to know min moves of each machine after having gain/loss array
     *
     */
    public int findMinMoves(int[] machines) {
        int total = 0;
        for (int i : machines) {
            total += i;
        }
        if (total % machines.length != 0) {
            return -1;
        }
        int avg = total / machines.length;
        int maxOfEachMinMove = 0;
        int deviation = 0;
        int accumulateMinMove = 0;

        for (int dress : machines) {
            deviation = dress - avg;
            if (deviation == 0) {
                continue;
            }
            accumulateMinMove += deviation;
            if (deviation > 0) {
                // it means current machine has to move deviation to give out dress so as to reach 0
                // use var to keep track max of overall gain/loss array, which is candidate of total min move for array
                maxOfEachMinMove = Math.max(maxOfEachMinMove, deviation);
            }
            // take max(abs(accumulateMinMove), maxOfEachMinMove)
            maxOfEachMinMove = Math.max(maxOfEachMinMove, Math.abs(accumulateMinMove));
        }
        return maxOfEachMinMove;
    }

    /** simplified in to max(max(a,b),c) */
    public int findMinMovesII(int[] machines) {
        int total = 0;
        for (int dress : machines) {
            total += dress;
        }
        if (total % machines.length != 0) {
            return -1;
        }
        int avg = total / machines.length;
        int maxOfMinMoves = 0;
        int accumulateMoves = 0;
        int deviation = 0;

        for (int dress : machines) {
            deviation = dress - avg;
            accumulateMoves += deviation;
            maxOfMinMoves = Math.max(Math.max(maxOfMinMoves, Math.abs(accumulateMoves)), deviation);
        }
        return maxOfMinMoves;
    }
}
