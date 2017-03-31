package com.leetcode.graph;

import java.util.*;

/**
 * Created by charles on 2/7/17.
 * Given a list of airline tickets represented by pairs of departure and arrival airports [from, to], reconstruct the itinerary in order. All of the tickets belong to a man who departs from JFK. Thus, the itinerary must begin with JFK.

 Note:
 If there are multiple valid itineraries, you should return the itinerary that has the smallest lexical order when read as a single string. For example, the itinerary ["JFK", "LGA"] has a smaller lexical order than ["JFK", "LGB"].
 All airports are represented by three capital letters (IATA code).
 You may assume all tickets form at least one valid itinerary.
 Example 1:
 tickets = [["MUC", "LHR"], ["JFK", "MUC"], ["SFO", "SJC"], ["LHR", "SFO"]]
 Return ["JFK", "MUC", "LHR", "SFO", "SJC"].
 Example 2:
 tickets = [["JFK","SFO"],["JFK","ATL"],["SFO","ATL"],["ATL","JFK"],["ATL","SFO"]]
 Return ["JFK","ATL","JFK","SFO","ATL","SFO"].
 Another possible reconstruction is ["JFK","SFO","ATL","JFK","ATL","SFO"]. But it is larger in lexical order.
 */
public class ReconstructItinerary_332 {
    /**
     * * When you run random custom test cases in editor you will get to know that they require a topological sort to be done on the input.
     * For ex feeding [["JFK",NRT],["JFK",KUL]] returns ["JFK","NRT","KUL"] which seems wrong as per the explanation but since input is not a valid itinerary hence the result.
     * This problem needs a topological sort in short. Hence do a topological sort after storing nodes in a sorted order.
     * Note :-
     * Topological sort is used only for DAGs** hence we need to *remove the edges* once it is visited. Thats why the solution uses a priority queue which sorts the nodes as well as helps in removing it in an efficient way.
     */
    public List<String> findItinerary(String[][] tickets) {
        List<String> res = new ArrayList<>();
        // uss Priority Queue
        Map<String, PriorityQueue<String>> map = new HashMap<>();
        String start = "JFK";
        int length = tickets.length;
        for (int i = 0; i < length; i++) {
            if (!map.containsKey(tickets[i][0])) {
                map.put(tickets[i][0], new PriorityQueue<String>());
            }
            map.get(tickets[i][0]).add(tickets[i][1]);
        }
        // dfs
        dfsHelper(start, map, res);
        Collections.reverse(res);
        return res;
    }

    /** DFS doing topological sort */
    private void dfsHelper(String curr, Map<String, PriorityQueue<String>> map, List<String> res) {
        while (map.containsKey(curr) && !map.get(curr).isEmpty()) {
            // poll is to get peek and remove as well
            dfsHelper(map.get(curr).poll(), map, res);
        }
        res.add(curr);
    }

    public List<String> findItineraryII(String[][] tickets) {
        List<String> res = new ArrayList<>();
        if (tickets == null || tickets.length == 0) {
            return res;
        }
        int len = tickets.length;
        Map<String, List<String>> map = new HashMap<>();

        for (int i = 0; i < len; i++) {
            if (map.containsKey(tickets[i][0])) {
                List<String> list = map.get(tickets[i][0]);
                addIntoSortedList(tickets[i][1], list);
            } else {
                List<String> list = new ArrayList<>();
                list.add(tickets[i][1]);
                map.put(tickets[i][0], list);
            }
        }
        String start = "JFK";
        res.add(start);
        itineraryDfs(start, map, res, 1, len + 1);
        return res;
    }

    private void addIntoSortedList(String val, List<String> list) {
        if (list.size() == 0) {
            list.add(val);
            return;
        } else {
            int i = 0;
            while (i < list.size()) {
                // find first smaller than value of string need to be inserted
                if ((val.compareTo(list.get(i))) <= 0) {
                    list.add(i, val);
                    return; // return directly
                }
                i++;
            }
            list.add(val); // else add new value to tail of list
            return;
        }
    }

    private boolean itineraryDfs(String curr, Map<String, List<String>> map, List<String> res, int num, int total) {
        if (num >= total) {
            return true;
        }
        if (!map.containsKey(curr) || map.get(curr).size() == 0) {
            return false;
        }
        List<String> path = map.get(curr);
        int i = 0;
        while (i < path.size()) {
            String next = path.remove(i);
            res.add(next);
            if (itineraryDfs(next, map, res, total, num + 1)) {
                return true;
            }
            res.remove(res.size() - 1);
            addIntoSortedList(next, path);
            i++;
        }
        return false;
    }
}
