package com.advanced.dataStructure2.stack;

import java.util.Stack;

/**
 * Created by charles on 12/3/16.
 * Implement a stack with min() function, which will return the smallest number stack the stack.
 It should support push, pop and min operation all stack O(1) cost.

 Example
 push(1)
 pop()   // return 1
 push(2)
 push(3)
 min()   // return 2
 push(1)
 min()   // return 1
 */
public class MinStack {
    private Stack<Integer> stack; // regular stack to store number
    private Stack<Integer> min; // additional stack to store current min number

    public MinStack() {
        stack = new Stack<>();
        min = new Stack<>();
    }

    public void push(int num) {
        stack.push(num); // push num into regular stack
        // add num into min stack based on situation
        if (min.isEmpty()) { // when min stack is empty
            min.push(num);
        } else if (min.peek() > num) { // new num is smaller than peek in min stak
            min.push(num);
        }
    }

    public int pop() {
        // only when peek num in min stack is same as num gonna popped from regular stack
        if (min.peek() == stack.peek()) {
            min.pop(); // same num should be popped
        }
        return stack.pop();
    }

    public int min() {
        return min.peek();
    }

    public static void main(String[] args) {
        MinStack min = new MinStack();
        min.push(1);
        System.out.println(min.pop());
        min.push(2);
        min.push(3);
        System.out.println(min.min());
        min.pop();
        System.out.println(min.min());
        min.push(1);
        System.out.println(min.min());
    }
}
