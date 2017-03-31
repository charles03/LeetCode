package com.leetcode.linkedlist;

/**
 * Created by charles on 12/21/16.
 */
public class ListNode {
    private int val;
    public ListNode next;

    public ListNode(int x) {
        this.val = x;
        this.next = null;
    }
    public void setVal(int val) {
        this.val = val;
    }

    public int getVal() {
        return this.val;
    }
}
