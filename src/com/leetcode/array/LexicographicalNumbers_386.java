package com.leetcode.array;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by charles on 3/15/17.
 * Given an integer n, return 1 - n in lexicographical order.
 For example, given 13, return: [1,10,11,12,13,2,3,4,5,6,7,8,9].

 Please optimize your algorithm to use less time and space. The input size may be as large as 5,000,000.
 */
public class LexicographicalNumbers_386 {
    /**
     * basic idea is to find next number to add
     * if curr number = 499, and n = 600
     * then 5 is next after 499 so devide 499 by 10 until last digit is not 9
     */
    public List<Integer> lexicalOrder(int n) {
        List<Integer> res = new ArrayList<>();
        for (int i = 1, curr = 1; i <= n; i++) {
            res.add(curr);

            if (curr * 10 <= n) {
                curr *= 10;
            } else {
                while (curr % 10 == 9 || curr == n) {
                    curr /= 10;
                }
                curr++;
            }
        }
        return res;
    }

    public static void main(String[] args) {
        LexicographicalNumbers_386 l = new LexicographicalNumbers_386();
        output(l.lexicalOrder(200), 10);
    }

    /** use array as counter inside lambda Stream
     * to beautify printing, every size number of elem, start in new line.
     */
    private static void output(List<Integer> list, int size) {
        final int[] count = {0};
        list.stream().forEach(a -> {
            count[0]++;
            System.out.print(a + ",");
            if (count[0] % size == 0) {
                System.out.println();
            }
        });
        System.out.println();
    }
}
