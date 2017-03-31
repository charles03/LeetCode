package com.leetcode.binarysearch;

/**
 * Created by charles on 3/12/17.
 * There is a list of sorted integers from 1 to n. Starting from left to right, remove the first number and every other number afterward until you reach the end of the list.
 Repeat the previous step again, but this time from right to left, remove the right most number and every other number from the remaining numbers.
 We keep repeating the steps again, alternating left to right and right to left, until a single number remains.
 Find the last number that remains starting with a list of length n.
 Example:
 Input:
 n = 9,
 1 2 3 4 5 6 7 8 9
 2 4 6 8
 2 6
 6

 Output:
 6
 */
public class EliminationGame_390 {
    public int lastRemaining(int n) {
        return leftToRight(n);
    }

    private int leftToRight(int n) {
        if (n <= 2) {
            return n;
        }
        System.out.println("left to right : " + n);
        return 2 * rightToLeft(n / 2);
    }

    private int rightToLeft(int n) {
        if (n <= 2) {
            return 1;
        }
        if (n % 2 == 1) {
            System.out.println("right to left : " + n);
            return 2 * leftToRight(n / 2);
        }
        System.out.println("right " + n);
        return 2 * leftToRight(n / 2) - 1;
    }

    /**
     * For me the key to understanding this solution was realizing that the last remaining value is any available remaining value until there is only 1 value left. We can start with 1 and always pick the smallest value remaining when our value gets eliminated.
     The problem becomes:
     When do we eliminate our value?
     Our value is eliminated on a forward pass or if there are an odd number of elements.

     How do we find the next value when our's is eliminated?
     This is probably the trickiest part of the solution. You will probably need to draw this out to see it but turns out if you start with 1 and double each iteration you'll be calculating the number of steps you'll need to take to reach the next available number.

     How do we know when we only have 1 value remaining?
     Each iteration reduces the elements by half and in the case of odd that extra one is removed, so this becomes a simple size/2 integer division.
     */
    public int lastRemainingII(int n) {
        boolean left = true;
        int remaining = n;
        int step = 1;
        int head = 1;

        while (remaining > 1) {
            if (left || remaining % 2 == 1) {
                System.out.println(head + ", " + step + ", " + remaining);
                head += step;
            }
            remaining /= 2;
            step *= 2;
            left = !left;
        }
        return head;
    }

    public static void main(String[] args) {
        EliminationGame_390 e = new EliminationGame_390();
//        e.lastRemaining(9);
        e.lastRemainingII(11);
    }
}
