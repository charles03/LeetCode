package com.leetcode.stack;

import java.util.Stack;

/**
 * Created by charles on 2/27/17.
 * Implement the following operations of a queue using stacks.

 push(x) -- Push element x to the back of queue.
 pop() -- Removes the element from in front of queue.
 peek() -- Get the front element.
 empty() -- Return whether the queue is empty.
 Notes:
 You must use only standard operations of a stack -- which means only push to top, peek/pop from top, size, and is empty operations are valid.
 Depending on your language, stack may not be supported natively. You may simulate a stack by using a list or deque (double-ended queue), as long as you use only standard operations of a stack.
 You may assume that all operations are valid (for example, no pop or peek operations will be called on an empty queue).
 */
public class ImplementQueueUsingStack_232 {
    private Stack<Integer> s1;
    private Stack<Integer> s2;
    private int front;

    /** initialize your data structure here */
    public ImplementQueueUsingStack_232() {
        s1 = new Stack<>();
        s2 = new Stack<>();
    }

    /** Push elem x to the back of queue */
    public void push(int x) {
        if (s1.isEmpty()) {
            front = x;
        }
        s1.push(x);
    }

    /** Remove elem from in front of queue and returns that element */
    /**
     * to transfer data from s1 to s2
     * Why this is Amortized O(1)
     * Amortized analysis give the average performance (over time) of each operation in the worst case
     * the basic idea is that a worst case operation can alter the state in such a way that worst case
     * cannot occur again for a long time, thus amortizing its cost
     *
     * Although a single pop operation could be expensive, it is expensive only once per n times (queue size),
     * when s2 is empty and there is a need for data transfer between s1 and s2. Hence the total time complexity of the sequence is : n (for push operations) + 2*n (for first pop operation) + n - 1 ( for pop operations) which is O(2*n)O(2∗n).This gives O(2n/2n)O(2n/2n) = O(1)O(1) average time per operation.
     */
    public int pop() {
        if (s2.isEmpty()) {
            while (!s1.isEmpty()) {
                s2.push(s1.pop());
            }
        }
        return s2.pop();
    }

    /** get the front elem */
    /**
     * front element is kept in constant memory and is modified when we push an element.
     * When s2 is not empty, front element is positioned on the top of s2
     */
    public int peek() {
        if (!s2.isEmpty()) {
            return s2.peek();
        }
        return front;
    }

    /** return whether queue is empty */
    public boolean empty() {
        return s1.isEmpty() && s2.isEmpty();
    }
}


/**
 * below is naive implementation,
 */
class ImplementQueueUsingStack {
    private Stack<Integer> s1;
    private Stack<Integer> s2;
    private int front;

    /** initialize your data structure here */
    public ImplementQueueUsingStack() {
        s1 = new Stack<>();
        s2 = new Stack<>();
    }

    /** Push elem x to the back of queue */
    public void push(int x) {
        if (s1.isEmpty()) {
            while (!s2.isEmpty()) {
                s1.push(s2.pop());
            }
        }
        s1.push(x);
    }

    /** Remove elem from in front of queue and returns that element */
    public int pop() {
        if (s2.isEmpty()) {
            while (!s1.isEmpty()) {
                s2.push(s1.pop());
            }
        }
        return s2.pop();
    }

    /** get the front elem */
    public int peek() {
        if (s2.isEmpty()) {
            while (!s1.isEmpty()) {
                s2.push(s1.pop());
            }
        }
        return s2.peek();
    }

    /** return whether queue is empty */
    public boolean empty() {
        return s1.isEmpty() && s2.isEmpty();
    }
}