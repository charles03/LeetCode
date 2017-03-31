package com.leetcode.math;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by charles on 3/23/17.
 * Write a program that outputs the string representation of numbers from 1 to n.

 But for multiples of three it should output “Fizz” instead of the number and for the multiples of five output “Buzz”. For numbers which are multiples of both three and five output “FizzBuzz”.

 Example:

 n = 15,

 Return:
 [
 "1",
 "2",
 "Fizz",
 "4",
 "Buzz",
 "Fizz",
 "7",
 "8",
 "Fizz",
 "Buzz",
 "11",
 "Fizz",
 "13",
 "14",
 "FizzBuzz"
 ]
 */
public class FizzBuzz_412 {
    /**
     * Thought:
     * take n Mod 15,
     * from 1 -> 15
     * [3,6,9,12] -> Fizz, [5,10]->Buzz, 15->FizzBuzz
     * then all self increment 15
     */
    /**
     * Second thought, use two counters to count 3/5/15 separately
     * reset to 0 when counter reach 3 or 5
     */
    public List<String> fizzBuzz(int n) {
        int fizz = 0;
        int buzz = 0;
        List<String> res = new LinkedList<>();
        for (int i = 1; i <= n; i++) {
            fizz++;
            buzz++;
            if (fizz == 3 && buzz == 5) {
                res.add("FizzBuzz");
                fizz = 0;
                buzz = 0;
            } else if (fizz == 3) {
                res.add("Fizz");
                fizz = 0;
            } else if (buzz == 5) {
                res.add("Buzz");
                buzz = 0;
            } else {
                res.add(String.valueOf(i));
            }
        }
        return res;
    }
}
