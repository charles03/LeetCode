package com.algorithm.tree.binarySearchTree;

/**
 * Created by charles on 3/9/17.
 * {@link com.leetcode.heap.FindMedianFromDataStream_295}
 */
public class FindMedianFromDataStream_295 {
    /**
     * The idea is to use a BST to store the stream of integers. When adding nodes to the tree, we keep a pointer to the median node (middle node if odd number of elements, smaller of the two middle nodes if even number of elements).
     Two helper functions - getNext and getPrev, allow us to update the median node.
     When should the median node be updated? Only two cases, both considering we have just added a node to the BST:
     We have an even number of elements, and the value added was less than the median value.
     We have an odd number of elements, and the value added was higher or equal to the median value.
     */
    private int count;
    private BST root;
    private BST curr;

    public void addNum(int num) {
        if (root == null) {
            root = new BST(num, null);
            curr = root;
            count = 1;
        } else {
            root.add(num);
            count++;

            if (count % 2 == 1) {
                if (curr.val <= num) {
                    curr = curr.next();
                }
            } else {
                if (curr.val > num) {
                    curr = curr.prev();
                }
            }
        }
    }

    public double findMedian() {
        if (count % 2 == 0) {
            return (curr.next().val + curr.val) * 0.5;
        } else {
            return curr.val;
        }
    }

    public static void main(String[] args) {
        FindMedianFromDataStream_295 f = new FindMedianFromDataStream_295();

        f.addNum(1);
        f.addNum(2);
        System.out.println(f.findMedian());
        f.addNum(5);
        System.out.println(f.findMedian());
        f.addNum(4);
        System.out.println(f.findMedian());
    }
}

/** below is implementation of Binary Search Tree */
class BST {
    int val;
    BST parent, left, right;
    BST(int val, BST parent) {
        this.val = val;
        this.parent = parent;
        left = null;
        right = null;
    }

    void add(int num) {
        if (num >= val) {
            if (right == null) {
                right = new BST(num, this);
            } else {
                right.add(num);
            }
        } else {
            if (left == null) {
                left = new BST(num, this);
            } else {
                left.add(num);
            }
        }
    }
    BST next() {
        BST res = null;
        if (right != null) {
            res = right;
            while (res.left != null) {
                res = res.left;
            }
        } else {
            res = this;
            while (res.parent.right == res) {
                res = res.parent;
            }
            res = res.parent;
        }
        return res;
    }

    BST prev() {
        BST res = null;
        if (left != null) {
            res = left;
            while (res.right != null) {
                res = res.right;
            }
        } else {
            res = this;
            while (res.parent.left == res) {
                res = res.parent;
            }
            res = res.parent;
        }
        return res;
    }
}
