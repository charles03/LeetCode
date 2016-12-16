package com.leetcode.array;

import java.util.*;

/**
 * Created by charles on 12/15/16.
 * Design a data structure that supports all following operations in average O(1) time.

 insert(val): Inserts an item val to the set if not already present.
 remove(val): Removes an item val from the set if present.
 getRandom: Returns a random element from current set of elements. Each element must have the same probability of being returned.
 Example:

 // Init an empty set.
 RandomizedSet randomSet = new RandomizedSet();

 // Inserts 1 to the set. Returns true as 1 was inserted successfully.
 randomSet.insert(1);

 // Returns false as 2 does not exist in the set.
 randomSet.remove(2);

 // Inserts 2 to the set, returns true. Set now contains [1,2].
 randomSet.insert(2);

 // getRandom should return either 1 or 2 randomly.
 randomSet.getRandom();

 // Removes 1 from the set, returns true. Set now contains [2].
 randomSet.remove(1);

 // 2 was already in the set, so return false.
 randomSet.insert(2);

 // Since 1 is the only number in the set, getRandom always return 1.
 randomSet.getRandom();
 */
public class InsertDeleteGetRandom_380 {
    // map key is number, value is index in the list
    private Map<Integer, Integer> map;
    private List<Integer> list;
    private Random rand = new Random();

    public InsertDeleteGetRandom_380() {
        map = new HashMap<>();
        list = new ArrayList<>();
    }

    /** Inserts a value to the set. Returns true if the set did not already contain the specified element. */
    public boolean insert(int val) {
        if (map.containsKey(val)) {
            return false;
        }
        map.put(val, list.size());
        list.add(val);
        return true;
    }

    /** Removes a value from the set. Returns true if the set contained the specified element. */
    public boolean remove(int val) {
        if (!map.containsKey(val)) {
            return false;
        }
        int index = map.get(val);
        int last = list.size() - 1;
        if (index < last) {
            int lastOne = list.get(last);
            list.set(index, lastOne);
            // be careful, should update index in map as well
            // otherwise, will remove wrong value in map in remove action
            map.put(lastOne, index);
        }
        list.remove(last);
        map.remove(val);
        return true;
    }

    /** Get a random element from the set. */
    public int getRandom() {
        return list.get(rand.nextInt(list.size()));
    }

    public static void main(String[] args) {
        InsertDeleteGetRandom_380 i = new InsertDeleteGetRandom_380();
        System.out.println(i.insert(1));
        System.out.println(i.insert(2));
        System.out.println(i.insert(1));

        testProbability(i);
        System.out.println(i.remove(3));
        System.out.println(i.remove(1));
        testProbability(i);
        i.insert(3);
        testProbability(i);
        i.remove(2);
        testProbability(i);
    }
    private static void testProbability(InsertDeleteGetRandom_380 instance) {
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
