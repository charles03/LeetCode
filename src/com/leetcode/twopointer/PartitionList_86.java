package com.leetcode.twopointer;

import com.leetcode.linkedlist.ListNode;
import com.leetcode.linkedlist.ListNodeUtil;

/**
 * Created by charles on 4/16/17.
 * Given a linked list and a value x, partition it such that all nodes less than x come before nodes greater than or equal to x.

 You should preserve the original relative order of the nodes in each of the two partitions.

 For example,
 Given 1->4->3->2->5->2 and x = 3,
 return 1->2->2->4->3->5.
 */
public class PartitionList_86 {
    /** maintain two dummy nodes with following queue
     * then concat these two queues.
     * and set tail of second queue with null next.
     * no need to instantiate new listnode,
     * use point next of small to head, then small point to head
     *
     * p1--> p5 --> p3 --> p2 --> null
     *
     * dummy1 -> p1 -> p2 -> small
     * dummy2 -> p5 -> p3 -> large
     */
    public ListNode partition(ListNode head, int x) {
        ListNode dummy1 = new ListNode(0);
        ListNode dummy2 = new ListNode(0);

        ListNode small = dummy1;
        ListNode large = dummy2;

        while (head != null) {
            if (head.getVal() < x) {
                small.next = head;
                small = head;
            } else {
                large.next = head;
                large = head;
            }
            head = head.next;
        }
        large.next = null;
        small.next = dummy2.next;

        return dummy1.next;
    }

    /** version by allocate new memory for list node
     */
    public ListNode partitionII(ListNode head, int x) {
        if (head == null) {
            return null;
        }
        ListNode dummy = new ListNode(0);
        ListNode dummy1 = new ListNode(0);

        ListNode small = dummy;
        ListNode large = dummy1;

        while (head != null) {
            if (head.getVal() < x) {
                small.next = new ListNode(head.getVal());
                small = small.next;
            } else {
                large.next = new ListNode(head.getVal());
                large = large.next;
            }
            head = head.next;
        }
        small.next = dummy1.next;

        return dummy.next;
    }

    public static void main(String[] args) {
        PartitionList_86 p = new PartitionList_86();
        int[] a1 = {1,3,5,2};
        ListNode n1 = ListNodeUtil.convert(a1);
        ListNodeUtil.print(p.partition(n1, 3));
    }
}
