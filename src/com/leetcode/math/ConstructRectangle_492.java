package com.leetcode.math;

/**
 * Created by charles on 3/25/17.
 * For a web developer, it is very important to know how to design a web page's size. So, given a specific rectangular web page’s area, your job by now is to design a rectangular web page, whose length L and width W satisfy the following requirements:

 1. The area of the rectangular web page you designed must equal to the given target area.

 2. The width W should not be larger than the length L, which means L >= W.

 3. The difference between length L and width W should be as small as possible.
 You need to output the length L and the width W of the web page you designed in sequence.
 Example:
 Input: 4
 Output: [2, 2]
 Explanation: The target area is 4, and all the possible ways to construct it are [1,4], [2,2], [4,1].
 But according to requirement 2, [1,4] is illegal; according to requirement 3,  [4,1] is not optimal compared to [2,2]. So the length L is 2, and the width W is 2.
 */
public class ConstructRectangle_492 {
    /**
     * Naive solution thought: find integer number mid closest to Square root of given area
     * L is by looking right of mid, W is by looking left of mid
     */
    public int[] constructRectangle(int area) {
        int mid = (int)Math.sqrt(area);
        int diff = Integer.MAX_VALUE;
        int[] res = new int[2];

        for (int w = mid; w >= 1; w--) {
            int l = mid;
            while (l * w <= area) {
                if (l * w == area  && (l-w) < diff) {
                    res[0] = l;
                    res[1] = w;
                    diff = l-w; // update diff
                }
                l++;
            }
        }
        return res;
    }

    /**
     * from closest integer of sqrt(given area)
     * to find closest number can be divided by given area
     */
    public int[] constructRectangleII(int area) {
        int[] res = new int[2];
        if (area == 0) {
            return res;
        }
        int width = (int)Math.sqrt(area);
        // look for closest number can be divided by given area
        while (area % width != 0) {
            width--;
        }
        // a and (area)/a is pair of res directly
        res[0] = area/width; // length should be >= width
        res[1] = width;
        return res;
    }
}
