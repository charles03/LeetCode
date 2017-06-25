package com.leetcode.binarysearch;

/**
 * Created by charles on 4/13/17.
 * Implement int sqrt(int x).

 Compute and return the square root of x.
 */
public class SqrtX_68 {
    /** naive solution, binary search, each time only pick mid
     * then compare mid with x/mid
     */
    public int mySqrt(int x) {
        if (x == 0) {
            return 0;
        }
        int left = 0, right = x;
        int mid = 0;
        while (true) {
            mid = left + (right - left) / 2;
            if (mid > x / mid) { // to avoid overflow
                right = mid - 1;
            } else {
                // expect result is integer
                // thus diff is 1 at most.
                if (mid + 1 > x / (mid + 1)) {
                    return mid;
                }
                left = mid + 1;
            }
        }
    }
    /** newton solution, based on formula
     * r = 1/2 * (r + a/r)
     * to calculate r = a ^ 1/2, square root of a
     * */
    public int mySqrtII(int a) {
        long r = a;
        while (r * r > a) {
            r = (r + a/r) / 2;
        }
        return (int)r;
    }
    /**
     * best way is Carmack's method.
     * to find inverse square root of float number
     * http://en.wikipedia.org/wiki/Fast_inverse_square_root
     * Carmack's method is fast because:

     The initial guess is super accurate.
     Does not use division.
     Does not use loops.

     Carmack's method is no longer useful nowadays and this is just for fun.
     http://www.procedurego.com/article/180938.html
     */
    public float inverseSqrt(float x) {
        float half = 0.5f * x;
        int i = Float.floatToIntBits(x); // get bits for floating value
        i = 0x5f3759df - (i >> 1); // give initial guess for 1/sqrt()
        x = Float.intBitsToFloat(i); // convert bits back to float
        x *= (1.5f - half * x * x); // newton step, repeating increases accuracy
        return x;
    }
    public double inverseSqrt(double x) {
        double xhalf = 0.5d * x;
        long i = Double.doubleToLongBits(x);
        i = 0x5fe6ec85e7de30daL - (i >> 1); // this magic number for double
        x = Double.longBitsToDouble(i);
        x *= (1.5d - xhalf * x * x);
        return x;
    }
}
