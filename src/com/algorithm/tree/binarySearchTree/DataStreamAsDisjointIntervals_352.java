package com.algorithm.tree.binarySearchTree;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by charles on 5/7/17.
 * Given a data stream input of non-negative integers a1, a2, ..., an, ..., summarize the numbers seen so far as a list of disjoint intervals.

 For example, suppose the integers from the data stream are 1, 3, 7, 2, 6, ..., then the summary will be:

 [1, 1]
 [1, 1], [3, 3]
 [1, 1], [3, 3], [7, 7]
 [1, 3], [7, 7]
 [1, 3], [6, 7]
 Follow up:
 What if there are lots of merges and the number of disjoint intervals are small compared to the data stream's size?
 */
public class DataStreamAsDisjointIntervals_352 {
    /**
     * use TreeMap to find lower and higher keys,
     * the key is start of interval. merge lower and higher intervals when necessary.
     * time complexity is logN for below actions
     * add, lowerkey, higherkey, put, and remove
     */
    private TreeMap<Integer, Interval> map;

    public DataStreamAsDisjointIntervals_352() {
        map = new TreeMap<>();
    }
    public void addNum(int val) {
        if (map.containsKey(val)) {
            return;
        }
        Integer low = map.lowerKey(val); // Returns the greatest key strictly less than the given key, or null if there is no such key.
        Integer high = map.higherKey(val); // Returns the least key strictly greater than the given key, or null if there is no such key.

        if (low != null && high != null
                && map.get(low).end + 1 == val
                && high == val + 1) {
            /** case 1, new number is in adjacent low and high */
            map.get(low).end = map.get(high).end;
            map.remove(high); // remove high key
        } else if (low != null && map.get(low).end + 1 >= val) {
            /** case 2, new number is in in the low key interval or adjacent to low end,
             * but not connect to high key interval
             */
            map.get(low).end = Math.max(map.get(low).end, val);
        } else if (high != null && high == val + 1) {
            /** case 3, new number is adjacent to high key, add new entry, remove previous high key interval*/
            map.put(val, new Interval(val, map.get(high).end));
            map.remove(high);
        } else {
            /** case 4, independent interval*/
            map.put(val, new Interval(val, val));
        }
    }
    public List<Interval> getIntervals() {
        return new ArrayList<>(map.values());
    }
}
class Interval {
    int start;
    int end;
    Interval() {
        this.start = 0;
        this.end = 0;
    }
    Interval(int start, int end) {
        this.start = start;
        this.end = end;
    }
}
