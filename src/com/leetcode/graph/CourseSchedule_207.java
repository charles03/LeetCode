package com.leetcode.graph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by charles on 2/6/17.
 * There are a total of n courses you have to take, labeled from 0 to n - 1.

 Some courses may have prerequisites, for example to take course 0 you have to first take course 1, which is expressed as a pair: [0,1]

 Given the total number of courses and a list of prerequisite pairs, is it possible for you to finish all courses?

 For example:

 2, [[1,0]]
 There are a total of 2 courses to take. To take course 1 you should have finished course 0. So it is possible.

 2, [[1,0],[0,1]]
 There are a total of 2 courses to take. To take course 1 you should have finished course 0, and to take course 0 you should also have finished course 1. So it is impossible.

 Note:
 The input prerequisites is a graph represented by a list of edges, not adjacency matrices. Read more about how a graph is represented.
 You may assume that there are no duplicate edges in the input prerequisites.

 Hints:
 This problem is equivalent to finding if a cycle exists in a directed graph. If a cycle exists, no topological ordering exists and therefore it will be impossible to take all courses.
 Topological Sort via DFS - A great video tutorial (21 minutes) on Coursera explaining the basic concepts of Topological Sort.
 Topological sort could also be done via BFS.
 */
public class CourseSchedule_207 {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        List<Integer>[] graph = new ArrayList[numCourses];
        int[] degree = new int[numCourses];
        Queue<Integer> queue = new LinkedList<Integer>();
        int count = 0;

        for (int i = 0; i < numCourses; i++) {
            graph[i] = new ArrayList<Integer>();
        }
        int vertex = 0;
        int root = 0;
        for (int i = 0; i < prerequisites.length; i++) {
            vertex = prerequisites[i][1];
            root = prerequisites[i][0];
            degree[vertex]++;
            graph[root].add(vertex);
        }
        for (int i = 0; i < degree.length; i++) {
            if (degree[i] == 0) {
                queue.add(i);
                count++;
            }
        }
        // BFS
        while (queue.size() != 0) {
            int course = queue.poll();
            for (int i = 0; i < graph[course].size(); i++) {
                int currVertex = graph[course].get(i);
                degree[currVertex]--;
                if (degree[currVertex] == 0) {
                    queue.add(currVertex);
                    count++;
                }
            }
        }
        if (count == numCourses) {
            return true;
        } else {
            return false;
        }
    }

    public boolean canFinishByDfs(int numCourses, int[][] prerequisites) {
        List<Integer>[] graph = new ArrayList[numCourses];
        for (int i = 0; i < numCourses; i++) {
            graph[i] = new ArrayList<Integer>();
        }
        boolean[] visited = new boolean[numCourses];
        int root = 0;
        int vertex = 0;
        for (int i = 0; i < prerequisites.length; i++) {
            root = prerequisites[i][0];
            vertex = prerequisites[i][1];
            graph[vertex].add(root);
        }

        for (int i = 0; i < numCourses; i++) {
            if (!dfs(graph, visited, i)) {
                return false;
            }
        }
        return true;
    }

    private boolean dfs(List<Integer>[] graph, boolean[] visited, int course) {
        if (visited[course]) {
            return false;
        } else {
            // update
            visited[course] = true;
        }
        // dfs
        for (int i = 0; i < graph[course].size(); i++) {
            if (!dfs(graph, visited, graph[course].get(i))) {
                return false;
            }
        }
        // undo
        visited[course] = false;
        return true;
    }
}
