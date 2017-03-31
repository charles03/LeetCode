package com.leetcode.array;

import java.util.*;

/**
 * Created by charles on 3/7/17.
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
     * without % modular function
     */
    public List<String> fizzBuzz(int n) {
        List<String> res = new ArrayList<>();
        int fizz = 0;
        int buzz = 0;
        for (int i = 0; i <= n; i++) {
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

    /**
     * Follow up question : how to implement to make it more concise and extendable
     * Apply composite design pattern, first thing is to design action interface
     */
    public List<String> fizzBuzzII(int n) {
        List<String> res = new ArrayList<>();
        RuleContainer r = new RuleContainer();
        for (int i = 1; i <= n; i++) {
            res.add(r.getValue(i));
        }
        return res;
    }
}


interface Rule {
    boolean apply(int i);
}

class RuleContainer {
    private Map<Rule, String> ruleMap = new HashMap<>();
    private Set<Rule> rules = new HashSet<>();

    public RuleContainer() {
        addRule(i -> i % 15 == 0, "FizzBuzz");
        addRule(i -> i % 3 == 0, "Fizz");
        addRule(i -> i % 5 == 0, "Buzz");
    }

    public void addRule(Rule rule, String res) {
        rules.add(rule);
        ruleMap.put(rule, res);
    }

    public String getValue(int i) {
        for (Rule rule : rules) {
            if (rule.apply(i)) {
                return ruleMap.get(rule);
            }
        }
        return String.valueOf(i);
    }
}
