package com.leetcode.math;

/**
 * Created by charles on 4/9/17.
 * We define the Perfect Number is a positive integer that is equal to the sum of all its positive divisors except itself.

 Now, given an integer n, write a function that returns true when it is a perfect number and false when it is not.
 Example:
 Input: 28
 Output: True
 Explanation: 28 = 1 + 2 + 4 + 7 + 14
 */
public class PerfectNumber_507 {
    public boolean checkPerfectNumber(int num) {
        if (num <= 1) {
            return false;
        }
        int range = (int) Math.sqrt(num);
        int sum = 1;
        for (int i = 2; i <= range; i++) {
            if (isDivisor(num, i)) {
                sum += i;
                sum += num / i;
            }
        }
        return sum == num;
    }
    private boolean isDivisor(int num, int divisor) {
        if (num % divisor == 0) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        PerfectNumber_507 p = new PerfectNumber_507();
        System.out.println(p.checkPerfectNumber(28));
        System.out.println(p.checkPerfectNumber(3));
        System.out.println(p.checkPerfectNumber(1) == false);
    }
}
