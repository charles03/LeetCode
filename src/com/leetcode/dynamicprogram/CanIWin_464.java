package com.leetcode.dynamicprogram;

import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by charles on 1/30/17.
 * In the "100 game," two players take turns adding, to a running total, any integer from 1..10. The player who first causes the running total to reach or exceed 100 wins.
 What if we change the game so that players cannot re-use integers?
 For example, two players might take turns drawing from a common pool of numbers of 1..15 without replacement until they reach a total >= 100.
 Given an integer maxChoosableInteger and another integer desiredTotal, determine if the first player to move can force a win, assuming both players play optimally.
 You can always assume that maxChoosableInteger will not be larger than 20 and desiredTotal will not be larger than 300.
 */
public class CanIWin_464 {
    /**
     * State
     */
    public boolean canWinByBit(int maxChoosableInteger, int desiredTotal) {
        int m = maxChoosableInteger;
        int t = desiredTotal;
        if (t == 0) {
            return true;
        }
        int sum = (1 + m) * m / 2;
        if (sum < t) { // all sum less than desired total
            return false;
        }
        if (sum == t) {
            return m % 2 > 0;
        }
        int size = 1 << m; // allocate 2^m
        BitSet visited = new BitSet(size);
        BitSet winState = new BitSet(size);


        return false;
    }

    public boolean canWinByDfsWithMap(int maxChoosableInteger, int desiredTotal) {
        int m = maxChoosableInteger;
        int t = desiredTotal;
        if (t <= 0) {
            return true;
        }
        int sum = (1 + m) * m / 2;
        if (sum < t) { // all sum less than desired total
            return false;
        }

        char[] state = new char[m];
        for (int i = 0; i < m; i++) {
            state[i] = '0';
        }
        return dfsHelper(t, state, new HashMap<>());
    }

    private boolean dfsHelper(int total, char[] state, Map<String, Boolean> hash) {
        String key = new String(state); // convert char array to string, as unique key of hash map
        if (hash.containsKey(key)) {
            return hash.get(key);
        }
        // recursion
        for (int i = 0; i < state.length; i++) {
            if (state[i] == '0') { // unvisited
                state[i] = '1'; // set visited
                if (total <= i + 1 || !(dfsHelper(total - (i+1), state, hash))) {
                    hash.put(key, true);
                    state[i] = '0';
                    return true;
                }
                state[i] = '0';
            }
        }
        hash.put(key, false);
        return false;
    }

    /**
     * Java version use BitSet
     * State of Game: initially, we have all M numbers [1, M] available in the pool. Each number may or may not be picked at a state of the game later on, so we have maximum 2^M different states. Note that M <= 20, so int range is enough to cover it. For memorization, we define int k as the key for a game state, where

     the i-th bit of k, i.e., k&(1<<i) represents the availability of number i+1 (1: picked; 0: not picked).
     At state k, the current player could pick any unpicked number from the pool, so state k can only go to one of the valid next states k':

     if i-th bit of k is 0, set it to be 1, i.e., next state k' = k|(1<<i).
     Recursion: apparently

     the current player can win at state k iff opponent can't win at some valid next state k'.
     Memorization: to speed up the recursion, we can use a vector<int> m of size 2^M to memorize calculated results:

     m[k] = 0: game result of state key k not calculated yet;
     m[k] = 1: game result of state key k is current player can win;
     m[k] = -1: game result of state key k is current player can't win.
     */
    public boolean canIWin(int maxChoosableInteger, int desiredTotal) {
        int m = maxChoosableInteger;
        int t = desiredTotal;
        if (t <= 0) {
            return true;
        }
        int sum = (1 + m) * m / 2;
        if (sum < t) { // all sum less than desired total
            return false;
        }
        int size = 1 << m;
        BitSet visited = new BitSet(size);
        BitSet winState = new BitSet(size);

        return helper(visited, winState, size, t, m);
    }

    private boolean helper(BitSet visited, BitSet winState, int size, int total, int bound) {
        if (total <= 0) {
            return false;
        }
        if (visited.get(size)) {
            return winState.get(size);
        }
        boolean win = false;
        for (int i = 0; i < bound; i++) {
            int bit = 1 << i;
            if ((size & bit) == 0) {
                continue;
            }
            if (!helper(visited, winState, size - bit, total-i-1, bound)) {
                win = true;
                break;
            }
        }
        visited.set(size);
        winState.set(size, win);
        return win;
    }

    public static void main(String[] args) {
        System.out.println(1 << 20);
    }
}
