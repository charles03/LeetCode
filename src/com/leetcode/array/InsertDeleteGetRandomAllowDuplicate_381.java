package com.leetcode.array;

import java.util.*;

/**
 * Created by charles on 12/15/16.
 * Design a data structure that supports all following operations in average O(1) time.

 Note: Duplicate elements are allowed.
 insert(val): Inserts an item val to the collection.
 remove(val): Removes an item val from the collection if present.
 getRandom: Returns a random element from current collection of elements. The probability of each element being returned is linearly related to the number of same value the collection contains.
 Example:

 // Init an empty collection.
 RandomizedCollection collection = new RandomizedCollection();

 // Inserts 1 to the collection. Returns true as the collection did not contain 1.
 collection.insert(1);

 // Inserts another 1 to the collection. Returns false as the collection contained 1. Collection now contains [1,1].
 collection.insert(1);

 // Inserts 2 to the collection, returns true. Collection now contains [1,1,2].
 collection.insert(2);

 // getRandom should return 1 with the probability 2/3, and returns 2 with the probability 1/3.
 collection.getRandom();

 // Removes 1 from the collection, returns true. Collection now contains [1,2].
 collection.remove(1);

 // getRandom should return 1 and 2 both equally likely.
 collection.getRandom();
 */
public class InsertDeleteGetRandomAllowDuplicate_381 {
    // map to store number and set to store indexes in the list
    // in order to remove from list
    private Map<Integer, Set<Integer>> map;
    private List<Integer> list; // array list to store all numbers
    private Random random = new Random();

    public InsertDeleteGetRandomAllowDuplicate_381() {
        map = new HashMap<>();
        list = new ArrayList<>();
    }
    /** Inserts a value to the collection. Returns true if the collection did not already contain the specified element. */
    public boolean insert(int val) {
        boolean contained = map.containsKey(val);
        if (!contained) {
            map.put(val, new LinkedHashSet<Integer>());
        }
        // add index into set and add number into list
        map.get(val).add(list.size());
        list.add(val);
        return contained;
    }

    /** Removes a value from the collection. Returns true if the collection contained the specified element. */
    public boolean remove(int val) {
        boolean contained = map.containsKey(val);
        if (!contained) { // no need to remove
            return false;
        }
        Set<Integer> indexSet= map.get(val);
        int index = indexSet.iterator().next();
        // remove index from set in map
        indexSet.remove(index);
        // if index is not last one in list
        // should update index of last one in both list and map
        int last = list.size() - 1; // index of last element
        if (index < last) {
            int lastOne = list.get(last);
            list.set(index, lastOne); // update index of last one by using index of one got removed
            Set<Integer> lastIdxSet = map.get(lastOne);
            lastIdxSet.remove(last); // remove last index for last one
            lastIdxSet.add(index);
        }
        // remove last node in list after set last node with new index
        list.remove(last);
        // edge case when set is empty
        if (indexSet.isEmpty()) {
            map.remove(val); // remove completely
        }
        return contained;
    }

    /** Get a random element from the collection. */
    public int getRandom() {
        return list.get(random.nextInt(list.size()));
    }

    public static void main(String[] args) {
        InsertDeleteGetRandomAllowDuplicate_381 i = new InsertDeleteGetRandomAllowDuplicate_381();
        i.insert(1);
        i.insert(2);
        i.insert(1);
        testProbability(i);
        i.remove(1);
        testProbability(i);
        i.insert(3);
        testProbability(i);
    }

    private static void testProbability(InsertDeleteGetRandomAllowDuplicate_381 instance) {
        int c1 = 0;
        int c2 = 0;
        int c3 = 0;
        for (int i = 0; i < 10000; i++) {
            switch(instance.getRandom()) {
                case 1:
                    c1++;
                    break;
                case 2:
                    c2++;
                    break;
                case 3:
                    c3++;
                    break;
            }
        }
        int sum = c1 + c2 + c3;
        System.out.println("1: " + (double)c1/sum + " 2: " + (double)c2/sum + " 3: " + (double)c3/sum);
    }
}
