package com.leetcode.linkedlist;

import java.util.Stack;
/**
 * Created by charles on 12/21/16.
 * You are given two linked lists representing two non-negative numbers.
 * The most significant digit comes first and each of their nodes contain a single digit.
 * Add the two numbers and return it as a linked list.

 You may assume the two numbers do not contain any leading zero, except the number 0 itself.
 Example:

 Input: (7 -> 2 -> 4 -> 3) + (5 -> 6 -> 4)
 Output: 7 -> 8 -> 0 -> 7
 */
public class AddTwoNumbersII_445 {
    /**
     * Solution 2 : no list modification, use two HashMaps to store data
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        Stack<Integer> stack1 = new Stack<>();
        Stack<Integer> stack2 = new Stack<>();

        while (l1 != null) {
            stack1.push(l1.getVal());
            l1 = l1.next;
        }
        while (l2 != null) {
            stack2.push(l2.getVal());
            l2 = l2.next;
        }
        int carry = 0, sum = 0;
        int val1 = 0, val2 = 0;

        ListNode head = null;
        ListNode node;
        while (!stack1.isEmpty() || !stack2.isEmpty()) {
            val1 = !stack1.isEmpty() ? stack1.pop() : 0;
            val2 = !stack2.isEmpty() ? stack2.pop() : 0;
            sum = val1 + val2 + carry;

            node = new ListNode(sum % 10);
            // equally append node in the head
            node.next = head;
            head = node;

            carry = sum / 10; // update carry
        }
        if (carry != 0) {
            node = new ListNode(sum / 10);
            node.next = head;
            head = node;
        }
        return head;
    }

    /** Solution 1 : reverse both lists, and add from start, then reverse again
     * {@link AddTwoNumbers_2}
     */
    public ListNode addTwoNumbersByModified(ListNode l1, ListNode l2) {
        ListNode r1 = ListNodeUtil.reverse(l1);
        ListNode r2 = ListNodeUtil.reverse(l2);
        /** take advantage of method implemeted in AddTwoNumber_2 */
        return ListNodeUtil.reverse(new AddTwoNumbers_2().addTwoNumber(r1,r2));
    }

    public static void main(String[] args) {
        AddTwoNumbersII_445 a = new AddTwoNumbersII_445();
        int[] n1 = {7,2,4,3};
        int[] n2 = {5,6,4};

        ListNode l1 = ListNodeUtil.convert(n1);
        ListNode l2 = ListNodeUtil.convert(n2);
        ListNode l3 = a.addTwoNumbers(l1, l2);
        /**
         * obiviously, reverse method modify original lists
         * thus output result will not print whole list of origin
         */
//        ListNode l3 = a.addTwoNumbersByModified(l1, l2);

        ListNodeUtil.print(l1);
        ListNodeUtil.print(l2);
        ListNodeUtil.print(l3);
    }

}

