package com.leetcode.stack;

import com.advanced.dataStructure2.stack.MinStack;

import java.util.Stack;

/**
 * Created by charles on 4/11/17.
 * Design a stack that supports push, pop, top, and retrieving the minimum element in constant time.

 push(x) -- Push element x onto stack.
 pop() -- Removes the element on top of the stack.
 top() -- Get the top element.
 getMin() -- Retrieve the minimum element in the stack.
 Example:
 MinStack minStack = new MinStack();
 minStack.push(-2);
 minStack.push(0);
 minStack.push(-3);
 minStack.getMin();   --> Returns -3.
 minStack.pop();
 minStack.top();      --> Returns 0.
 minStack.getMin();   --> Returns -2.
 */
public class MinStack_155 {
    /**
     * The design can be handled by single stack and one instance var min
     */
    private Stack<Integer> stack;
    private int min;

    public MinStack_155() {
        stack = new Stack<>();
        min = Integer.MAX_VALUE;
    }

    public void push(int x) {
        if (x <= min) {
            /** in order to keep previous min value of stack before adding newest min
             * need to push prev min into stack
             */
            stack.push(min);
            // and then update min to current new number
            min = x;
        }
        // and then push this number into stack as top
        stack.push(x);
    }
    public int pop() {
        int peek = stack.pop();
        if (peek == min) {
            /** based on how we push number, if popped num is current min,
             * need to update min with num after stack.pop() again.
             * set info back to original status before adding popped min.
             */
            min = stack.pop();
        }
        return peek;
    }
    public int top() {
        return stack.peek();
    }
    public int getMin() {
        return min;
    }

    public static void main(String[] args) {
        /**
         * for case {2,3,4,1,0}
         * stack will become {2,3,4,2,1,1,0} when push all of them sequentially
         */
        MinStack_155 m = new MinStack_155();
        m.push(2);
        m.push(3);
        m.push(4);
        m.push(1);
        m.push(0);
        System.out.println(m.top() == 0);
        System.out.println(m.getMin() == 0);
        m.pop();
        System.out.println(m.getMin() == 1);
        m.pop();
        System.out.println(m.getMin() == 2);

    }
}
