package com.leetcode.random;

import com.leetcode.linkedlist.ListNode;

import java.util.Random;

/**
 * Created by charles on 3/16/17.
 * Given a singly linked list, return a random node's value from the linked list. Each node must have the same probability of being chosen.

 Follow up:
 What if the linked list is extremely large and its length is unknown to you? Could you solve this efficiently without using extra space?

 Example:

 // Init a singly linked list [1,2,3].
 ListNode head = new ListNode(1);
 head.next = new ListNode(2);
 head.next.next = new ListNode(3);
 Solution solution = new Solution(head);

 // getRandom() should return either 1, 2, or 3 randomly. Each element should have equal probability of returning.
 solution.getRandom();
 */
public class LinkedListRandomNode_382 {
    /** Reservoir Sampling */
    /**
     * One way I can think of (after I know the solution..) is: we want equal probability in N elements,
     * so let's make the probability as 1/N. The numerator is 1, and the denominator is N.

     It turns out that the numerator can come from the element itself,
     and the denominator can come from the last element.
     Then the 1/1 * 1/2 * 2/3 * .. * (N-1)/N method comes in mind..
     */
    private ListNode head;
    private Random random;

    public LinkedListRandomNode_382(ListNode head) {
        this.head = head;
        this.random = new Random();
    }

    public int getRandom() {
        ListNode res = null;
        ListNode curr = head;

        for (int n = 1; curr != null; n++) {
            if (random.nextInt(n) == 0) {
                res = curr;
            }
            curr = curr.next;
        }
        return res.getVal();
    }

}
