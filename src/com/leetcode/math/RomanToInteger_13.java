package com.leetcode.math;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by charles on 3/29/17.
 * Given a roman numeral, convert it to an integer.

 Input is guaranteed to be within the range from 1 to 3999.
 */
public class RomanToInteger_13 {
    /**
     * Thought:
     * compose union array for all bases of roman number
     * III-> 3 MMM-> 3000
     */
    int[] values = {1000,900,500,400,100,90,50,40,10,9,5,4,1};
    String[] strs = {"M","CM","D","CD","C","XC","L","XL","X","IX","V","IV","I"};

    public int romanToInt(String s) {
        char[] digits = s.toCharArray();
        int sum = 0;
        for (int i = 0; i < digits.length; i++) {
            if (digits[i] == 'M') {
                if (i >= 1 && digits[i-1] == 'C') {
                    sum += 900;
                } else {
                    sum += 1000;
                }
            } else if (digits[i] == 'D') {
                if (i >= 1 && digits[i-1] == 'C') {
                    sum += 400;
                } else {
                    sum += 500;
                }
            } else if (digits[i] == 'C') {
                if (i >= 1 && digits[i-1] == 'X') {
                    sum += 90;
                } else {
                    sum += 100;
                }
            } else if (digits[i] == 'L') {
                if (i >= 1 && digits[i-1] == 'X') {
                    sum += 40;
                } else {
                    sum += 50;
                }
            } else if (digits[i] == 'X') {
                if (i >= 1 && digits[i-1] == 'I') {
                    sum += 9;
                } else {
                    sum += 10;
                }
            } else if (digits[i] == 'V') {
                if (i >= 1 && digits[i-1] == 'I') {
                    sum += 4;
                } else {
                    sum += 5;
                }
            } else if (digits[i] == 'I') {
                sum += 1;
            }
        }
        return sum;
    }

    /** thought :
     *  III-> from tail to front
     *  1 + 1 + 1
     *  IV -> from tail to front, because I(1) < V(5)
     *  then res = 5 - 1 = 4;
     *  VII -> from tail to front, because V(5) > I(1)
     *  then res = 5 + 1 + 1 = 7;
     */
    public int romanToIntII(String s) {
        if (s == null || s.length() == 0) {
            return -1;
        }
        Map<Character, Integer> map = createRomanMap();
        int len = s.length();
        int res = map.get(s.charAt(len - 1));
        for (int i = len-2; i >= 0; i--) {
            if (map.get(s.charAt(i)) >= map.get(s.charAt(i+1))) {
                res += map.get(s.charAt(i));
            } else {
                res -= map.get(s.charAt(i));
            }
        }
        return res;
    }

    private Map<Character, Integer> createRomanMap() {
        Map<Character, Integer> map = new HashMap<>();
        map.put('I', 1);
        map.put('V', 5);
        map.put('X', 10);
        map.put('L', 50);
        map.put('C', 100);
        map.put('D', 500);
        map.put('M', 1000);
        return map;
    }

}
