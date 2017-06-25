package com.leetcode.math;

/**
 * Created by charles on 6/24/17.
 * to get greatest common devisor
 */
public class GreatestCommonDevisor {
    public static int gcd(int a, int b) {
        if (b == 0) {
            return a;
        }
        return gcd(b, a % b);
    }
    public static int gcdII(int a, int b) {
        int tmp = 0;
        while (b != 0) {
            tmp = b;
            b = a % b;
            a = tmp;
        }
        return tmp;
    }
    /** more than two elems
     * gcd(a,b,c) = gcd(a,gcd(b,c))
     *            = gcd(gcd(a,b),c)
     *            = gcd(gcd(a,c),b)
     * */
    public static int gcd(int[] arr) {
        int res = arr[0];
        for (int i = 1; i < arr.length; i++) {
            res = gcd(arr[i], res);
        }
        return res;
    }

    public static int gcdII(int[] arr) {
        int min = findMin(arr);
        boolean isCommonDevisor = true;
        while (min >= 1) {
            isCommonDevisor = true;
            for (int i = 0; i < arr.length; i++) {
                if (arr[i] % min != 0) {
                    isCommonDevisor = false;
                    break;
                }
            }
            if (isCommonDevisor) {
                break;
            }
            min--;
        }
        return min;
    }
    private static int findMin(int[] arr) {
        int min = Integer.MAX_VALUE;
        for (int a : arr) {
            min = Math.min(a, min);
        }
        return min;
    }
}
