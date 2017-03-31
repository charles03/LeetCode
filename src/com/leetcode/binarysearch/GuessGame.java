package com.leetcode.binarysearch;

/**
 * Created by charles on 2/23/17.
 */
public class GuessGame {
    private int target;

    public int guess(int num) {
        if (num > target) {
            return -1;
        } else if (num == target) {
            return 0;
        } else {
            return 1;
        }
    }

    public void pick(int chosen) {
        target = chosen;
    }
}
