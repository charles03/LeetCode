package com.leetcode.binarysearch;

/**
 * Created by charles on 2/23/17.
 * We are playing the Guess Game. The game is as follows:

 I pick a number from 1 to n. You have to guess which number I picked.

 Every time you guess wrong, I'll tell you whether the number is higher or lower.

 You call a pre-defined API guess(int num) which returns 3 possible results (-1, 1, or 0):

 -1 : My number is lower
 1 : My number is higher
 0 : Congrats! You got it!
 Example:
 n = 10, I pick 6.

 Return 6.
 */
public class GuessNumberHighOrLow_374 extends GuessGame {
    /**
     * Binary Search
     * from 1 to n, select mid to guess
     */
    public int guessNumber(int n) {
        int low = 1;
        int high = n;
        int mid = 0;
        int res = 0;
        while (low <= high) { // because low start from 1
            mid = low + (high - low) / 2;
            res = guess(mid);
            if (res == 0) {
                return mid;
            } else if (res < 0) {
                high = mid - 1; // to avoid infinite loop
            } else {
                low = mid + 1;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        GuessNumberHighOrLow_374 g = new GuessNumberHighOrLow_374();
        g.pick(10);
        System.out.println(g.guessNumber(5));
//        g.pick(10);
        System.out.println(g.guessNumber(11));
        System.out.println(g.guessNumber(10));

        System.out.println(g.guessNumberII(10));
        System.out.println(g.guessNumberII(11));
        System.out.println(g.guessNumberII(5));
    }

    /**
     * Ternary Search // divide by 3
     * In Binary Search, we choose the middle element as the pivot in splitting. In Ternary Search, we choose two pivots (say m1m1 and m2m2) such that the given range is divided into three equal parts.
     * If the required number numnum is less than m1m1 then we apply ternary search on the left segment of m1m1. If numnum lies between m1m1 and m2m2, we apply ternary search between m1m1 and m2m2. Otherwise we will search in the segment right to m2m2.
     */
    public int guessNumberII(int n) {
        int low = 1;
        int high = n;
        while (low <= high) {
            int leftMid = low + (high - low) / 3;
            int rightMid = high - (high - low) / 3;
            int leftRes = guess(leftMid);
            int rightRes = guess(rightMid);

            if (leftRes == 0) {
                return leftMid;
            }
            if (rightRes == 0) {
                return rightMid;
            }
            if (leftRes < 0) {
                high = leftMid - 1;
            } else if (rightRes > 0) {
                low = rightMid + 1;
            } else {
                low = leftMid + 1;
                high = rightMid -1;
            }
        }
        return -1;
    }

}
