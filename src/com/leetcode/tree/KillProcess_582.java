package com.leetcode.tree;

import java.util.*;

/**
 * Created by charles on 5/14/17.
 * Given n processes, each process has a unique PID (process id) and its PPID (parent process id).

 Each process only has one parent process, but may have one or more children processes. This is just like a tree structure. Only one process has PPID that is 0, which means this process has no parent process. All the PIDs will be distinct positive integers.

 We use two list of integers to represent a list of processes, where the first list contains PID for each process and the second list contains the corresponding PPID.

 Now given the two lists, and a PID representing a process you want to kill, return a list of PIDs of processes that will be killed in the end. You should assume that when a process is killed, all its children processes will be killed. No order is required for the final answer.

 Example 1:
 Input:
 pid =  [1, 3, 10, 5]
 ppid = [3, 0, 5, 3]
 kill = 5
 Output: [5,10]
 Explanation:
    3
  /   \
 1     5
      /
    10
 Kill 5 will also kill 10.
 */
public class KillProcess_582 {
    /**
     * Thought:
     * use HashMap and Breadth First Search
     * map<k,v> key is parent_id, v is list of child_id
     * BFS is to build tree structure level by level.
     */
    public List<Integer> killProcess(List<Integer> pid, List<Integer> ppid, int kill) {
        List<Integer> res = new LinkedList<>();
        Map<Integer, List<Integer>> map = buildMap(pid, ppid);
        res = bfsIteration(map, kill);
        return res;
    }
    private Map<Integer, List<Integer>> buildMap(List<Integer> pid, List<Integer> ppid) {
        Map<Integer, List<Integer>> map = new HashMap<>();
        int key = -1;
        for (int i = 0; i < ppid.size(); i++) {
            key = ppid.get(i);
            List<Integer> child = map.getOrDefault(key, new ArrayList<>());
            child.add(pid.get(i));
            map.put(key, child);
        }
        return map;
    }
    private List<Integer> bfsIteration(Map<Integer, List<Integer>> map, int id) {
        Queue<Integer> queue = new LinkedList<>();
        List<Integer> res = new ArrayList<>();

        queue.add(id);
        int pid = 0;
        while (!queue.isEmpty()) {
            pid = queue.remove();
            res.add(pid);
            // use map to find its children list
            if (map.containsKey(pid)) {
                queue.addAll(map.get(pid));
            }
        }
        return res;
    }

}
