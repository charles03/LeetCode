package com.leetcode.design;

import java.util.HashSet;
import java.util.Iterator;

/**
 * Created by charles on 6/25/17.
 * Design and implement a data structure for a compressed string iterator. It should support the following operations: next and hasNext.

 The given compressed string will be in the form of each letter followed by a positive integer representing the number of this letter existing in the original uncompressed string.

 next() - if the original string still has uncompressed characters, return the next letter; Otherwise return a white space.
 hasNext() - Judge whether there is any letter needs to be uncompressed.

 Note:
 Please remember to RESET your class variables declared in StringIterator, as static/class variables are persisted across multiple test cases. Please see here for more details.

 Example:

 StringIterator iterator = new StringIterator("L1e2t1C1o1d1e1");

 iterator.next(); // return 'L'
 iterator.next(); // return 'e'
 iterator.next(); // return 'e'
 iterator.next(); // return 't'
 iterator.next(); // return 'C'
 iterator.next(); // return 'o'
 iterator.next(); // return 'd'
 iterator.hasNext(); // return true
 iterator.next(); // return 'e'
 iterator.hasNext(); // return false
 iterator.next(); // return ' '
 */
public class DesignCompressedStringIterator_604 {
    private int index;
    private String[] strs;
    private int[] counts;

    public DesignCompressedStringIterator_604(String compressStr) {
        decompress(compressStr);
        this.index = 0;
    }
    private void decompress(String str) {
        strs = str.split("\\d+");
        int len = strs.length;
        counts = new int[len];
        for (int i = 0; i < len; i++) {
            counts[i] = Integer.parseInt(strs[i].substring(1));
        }
    }
    public char next() {
        if (!hasNext()) {
            return ' ';
        }
        char ch = strs[index].charAt(0);
        counts[index]--;
        if (counts[index] == 0) {
            index++; // move to next char;
        }
        return ch;
    }
    public boolean hasNext() {
        return index != strs.length;
    }
}
