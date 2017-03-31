package com.leetcode.backtrack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by charles on 1/19/17.
 * A binary watch has 4 LEDs on the top which represent the hours (0-11),and the 6 LEDs on the bottom represent the minutes (0-59).

 Each LED represents a zero or one,with the least significant bit on the right.
 Given a non-negative integer n which represents the number of LEDs that are currently on,return all possible times the watch could represent.

 Example:
 Input: n = 1
 Return: ["1:00","2:00","4:00","8:00","0:01","0:02","0:04","0:08","0:16","0:32"]
 Note:
 The order of output does not matter.
 The hour must not contain a leading zero,for example "01:00" is not valid,it should be "1:00".
 The minute must be consist of two digits and may contain a leading zero,for example "10:2" is not valid,it should be "10:02".
 */
public class BinaryWatch_401 {
    /**
     * High level thought:
     * 1. get single one digit representation separately for hour and minute
     * 2. backtrack separately for hour and minute.
     *      and to make total counts are equal to num
     * 3. add combo of hr and min into result
     *
     * for hour, backtrack process need to be ending when sum of hour >= 12
     * for min, backtrack ending when sum of min >= 60
     */
    public List<String> readBinaryWatch(int num) {
        List<String> res = new ArrayList<>();
        int[] hour = {8,4,2,1};
        int[] minute = {32,16,8,4,2,1};

        List<Integer> hourCombination = new ArrayList<>();
        List<Integer> minuteCombination = new ArrayList<>();
        for (int i = 0; i <= num; i++) {
            hourCombination = backTrack(hour, i, 12);
            minuteCombination = backTrack(minute, num - i, 60);

            for (int h : hourCombination) {
                for (int m : minuteCombination) {
                    res.add(h + ":" + (m < 10 ? "0" + m : m));
                }
            }
        }
        return res;
    }

    private List<Integer> backTrack(int[] nums, int count, int max) {
        List<Integer> res = new ArrayList<>();
        backTrackHelper(nums, count, 0, 0, res, max);
        return res;
    }

    private void backTrackHelper(int[] nums, int count, int pos, int sum, List<Integer> res, int max) {
        if (sum >= max) {
            return;
        }
        if (count == 0) {
            res.add(sum);
            return;
        }
        for (int i = pos; i < nums.length; i++) {
            backTrackHelper(nums, count-1, i+1, sum+nums[i], res, max);
        }
    }



    public List<String> readBinaryWatchByApi(int num) {
        List<String> res = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 60; j++) {
                if (Integer.bitCount(i) + Integer.bitCount(j) == num) {
                    res.add(String.format("%d:%2d",i,j));
                }
            }
        }
        return res;
    }

    public List<String> readBinaryWatchByEnum(int num) {
        String[][] hour = {
                {"0"},
                {"1","2","4","8"},
                {"3","5","6","9","10"},
                {"7","11"}
        };
        String[][] minute = {
                {"00"},
                {"01","02","04","08","16","32"},
                {"03","05","06","09","10","12","17","18","20","24","33","34","36","40","48"},
                {"07","11","13","14","19","21","22","25","26","28","35","37","38","41","42","44","49","50","52","56"},
                {"15","23","27","29","30","39","43","45","46","51","53","54","57","58"},
                {"31","47","55","59"}
        };

        List<String> res = new ArrayList<>();
        for (int i = 0; i <= 3 && i <= num; i++) {
            if (num - i > 5) {
                continue;
            }
            for (String h : hour[i]) {
                for (String m : minute[num - i]) {
                    res.add(h + ":" + m);
                }
            }
        }
        return res;
    }
}
