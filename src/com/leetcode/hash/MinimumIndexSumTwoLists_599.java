package com.leetcode.hash;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by charles on 6/4/17.
 * Suppose Andy and Doris want to choose a restaurant for dinner, and they both have a list of favorite restaurants represented by strings.

 You need to help them find out their common interest with the least list index sum. If there is a choice tie between answers, output all of them with no order requirement. You could assume there always exists an answer.

 Example 1:
 Input:
 ["Shogun", "Tapioca Express", "Burger King", "KFC"]
 ["Piatti", "The Grill at Torrey Pines", "Hungry Hunter Steakhouse", "Shogun"]
 Output: ["Shogun"]
 Explanation: The only restaurant they both like is "Shogun".
 Example 2:
 Input:
 ["Shogun", "Tapioca Express", "Burger King", "KFC"]
 ["KFC", "Shogun", "Burger King"]
 Output: ["Shogun"]
 Explanation: The restaurant they both like and have the least index sum is "Shogun" with index sum 1 (0+1).

 */
public class MinimumIndexSumTwoLists_599 {
    public String[] findRestaurant(String[] list1, String[] list2) {
        if (list1.length > list2.length) {
            return findRestaurant(list2, list1);
        }
        // add all elem in short array into map
        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i < list1.length; i++) {
            map.put(list1[i], i);
        }
        int min = Integer.MAX_VALUE; // global min of sum
        int sum = 0;
        List<String> res = new ArrayList<>();
        String str = null;
        for (int j = 0; j < list2.length; j++) {
            str = list2[j];
            if (!map.containsKey(str)) {
                continue;
            }
            sum = j + map.get(str);
            if (sum < min) {
                res.clear(); // flush previous elem in result list
                res.add(str);
                min = sum;
            } else if (sum == min) {
                res.add(str);
            }
        }
        // narrow the result size
        return res.toArray(new String[res.size()]);
    }
}
