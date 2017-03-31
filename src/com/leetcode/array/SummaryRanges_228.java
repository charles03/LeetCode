package com.leetcode.array;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by charles on 12/16/16.
 * Given a sorted integer array without duplicates, return the summary of its ranges.

 For example, given [0,1,2,4,5,7], return ["0->2","4->5","7"].
 */
public class SummaryRanges_228 {
    public List<String> summaryRanges(int[] nums) {
        List<String> res = new ArrayList<>();
        if (nums == null || nums.length == 0) {
            return res;
        }
        int len = nums.length;
        if (len == 1) {
            res.add("" + nums[0]);
            return res;
        }

        int start = 0;
        for (int i = 0; i < len; i++) {
            System.out.println("before : " + i);
            start = nums[i];
            while ((i + 1) < len && (nums[i + 1] - nums[i]) == 1) {
                i++;
            }
            if (start != nums[i]) {
                res.add(start + "->" + nums[i]);
            } else {
                res.add(start + "");
            }
            System.out.println("after : " + i);
        }
        return res;
    }

    public static void main(String[] args) {
        SummaryRanges_228 s = new SummaryRanges_228();
        int[] nums = {0,1,2,4,5,7};
        print(s.summaryRanges(nums));
    }

    private static void print(List<String> list) {
        list.stream().forEach(t -> System.out.println(t));
    }

}
