package com.leetcode.heap;

import java.util.*;

/**
 * Created by charles on 3/26/17.
 * Given scores of N athletes, find their relative ranks and the people with the top three highest scores, who will be awarded medals: "Gold Medal", "Silver Medal" and "Bronze Medal".

 Example 1:
 Input: [5, 4, 3, 2, 1]
 Output: ["Gold Medal", "Silver Medal", "Bronze Medal", "4", "5"]
 Explanation: The first three athletes got the top three highest scores, so they got "Gold Medal", "Silver Medal" and "Bronze Medal".
 For the left two athletes, you just need to output their relative ranks according to their scores.
 Note:
 N is a positive integer and won't exceed 10,000.
 All the scores of athletes are guaranteed to be unique.
 */
public class RelativeRanks_506 {
    /**
     * High level thought
     * need two containers, one to keep track of index of given number in map
     *      map: key is value of number, value : index in input array
     *
     *      second container is to keep number sorted,
     *      use priority queue (maxHeap)
     */
    public String[] findRelativeRanks(int[] nums) {
        if (nums == null || nums.length == 0) {
            return new String[0];
        }
        int len = nums.length;
        String[] res = new String[len];

        PriorityQueue<Integer> heap = new PriorityQueue<>(Collections.reverseOrder());
        Map<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < len; i++) {
            map.put(nums[i], i);
            heap.add(nums[i]);
        }
        int rank = 1;
        int num = 0;
        while (!heap.isEmpty()) {
            num = heap.poll();
            assignRank(res, map.get(num), rank);
            rank++;
        }
        return res;
    }

    private void assignRank(String[] res, int idx, int rank) {
        if (rank == 1) {
            res[idx] = "Gold Medal";
        } else if (rank == 2) {
            res[idx] = "Silver Medal";
        } else if (rank == 3) {
            res[idx] = "Bronze Medal";
        } else {
            res[idx] = String.valueOf(rank);
        }
    }

    public static void main(String[] args) {
        RelativeRanks_506 r = new RelativeRanks_506();
        int[] n1 = {5,4,2,1,3};
        String[] r1 = r.findRelativeRanks(n1);
        output(r1);
    }
    private static void output(String[] arr) {
        Arrays.asList(arr).stream().forEach(t->System.out.print(t + ","));
        System.out.println();
    }

    /** Method 2, use sort nlg(n) time complexity */
    public String[] findRelativeRanksII(int[] nums) {
        String[] top = new String[] {"Gold Medal", "Silver Medal", "Bronze Medal"};
        int len = nums.length;
        String[] rank = new String[len];
        Map<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            map.put(nums[i], i);
        }
        // sort, small at front, large at tail
        Arrays.sort(nums);
        // assign rank
        int idx = 0;
        for (int i = 0; i < len; i++) {
            idx = map.get(nums[len-1-i]);
            if (i < 3) {
                rank[idx] = top[i];
            } else {
                rank[idx] = String.valueOf(i + 1);
            }
        }
        return rank;
    }

    /** solution 3, use TreeSet to replace PriorityQueue and Map */
    public String[] findRelativeRanksIII(int[] nums) {
        TreeSet<Node> ts = new TreeSet<>();
        String[] ranks = new String[nums.length];
        for(int i = 0;i < nums.length; ++i) {
            ts.add(new Node(nums[i], i));
        }
        int pos = 0;
        Node curNode;
        while(!ts.isEmpty()) {
            pos++;
            curNode = ts.pollFirst();
            ranks[curNode.pos] = getRank(pos);
        }
        return ranks;
    }
    private String getRank(int pos) {
        if(pos == 1) {
            return "Gold Medal";
        } else if(pos == 2) {
            return "Silver Medal";
        } else if(pos == 3) {
            return "Bronze Medal";
        }
        return String.valueOf(pos);
    }
    private class Node implements Comparable<Node> {
        int val, pos;
        public Node(int val, int pos) {
            this.val = val;
            this.pos = pos;
        }
        public int compareTo(Node other) {
            return other.val - this.val;
        }
    }
}
