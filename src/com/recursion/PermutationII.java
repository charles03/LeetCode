package com.recursion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Arrays;
/**
 * Created by charles on 7/27/16.
 */
public class PermutationII {
    public static ArrayList<ArrayList<Integer>> permuteUnique(ArrayList<Integer> nums) {
        ArrayList<ArrayList<Integer>> res = new ArrayList<ArrayList<Integer>>();
        if (nums == null || nums.size() == 0) {
            return res;
        }
        Collections.sort(nums);
        ArrayList<Integer> tmps = new ArrayList<Integer>();
        permuteUniqueHandler(nums, tmps, res, new boolean[nums.size()]);
        return res;
    }

    private static void permuteUniqueHandler(ArrayList<Integer> nums, ArrayList<Integer> tmps,
                                      ArrayList<ArrayList<Integer>> res, boolean[] visited) {
         if (nums.size() == tmps.size()) {
             res.add(new ArrayList(tmps));
             return;
         }
         for (int i = 0; i < nums.size(); i++) {
             if (visited[i] || (i > 0 && !visited[i - 1] && nums.get(i) == nums.get(i - 1))) {
                 System.out.print("i : " + i);
                 System.out.println(" " + visited[i]);
                 System.out.println(Arrays.toString(visited));
                 continue;
             }
             visited[i] = true;
             tmps.add(nums.get(i));
             tmps.stream().forEach(integer -> System.out.print(integer + ", "));
             System.out.println("");
             permuteUniqueHandler(nums, tmps, res, visited);
             tmps.remove(tmps.size() - 1);
             visited[i] = false;
         }
    }

    public static void main(String[] args) {
        ArrayList<Integer> test = new ArrayList<>();
        test.add(1);
        test.add(2);
        test.add(2);
        permuteUnique(test);
    }

}
