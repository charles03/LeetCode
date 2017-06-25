package com.leetcode.graph;

import java.util.*;

/**
 * Created by charles on 4/20/17.
 * There are a total of n courses you have to take, labeled from 0 to n - 1.

 Some courses may have prerequisites, for example to take course 0 you have to first take course 1, which is expressed as a pair: [0,1]

 Given the total number of courses and a list of prerequisite pairs, return the ordering of courses you should take to finish all courses.

 There may be multiple correct orders, you just need to return one of them. If it is impossible to finish all courses, return an empty array.

 For example:

 2, [[1,0]]
 There are a total of 2 courses to take. To take course 1 you should have finished course 0. So the correct course order is [0,1]

 4, [[1,0],[2,0],[3,1],[3,2]]
 There are a total of 4 courses to take. To take course 3 you should have finished both courses 1 and 2. Both courses 1 and 2 should be taken after you finished course 0. So one correct course order is [0,1,2,3]. Another correct ordering is[0,2,1,3].

 Note:
 The input prerequisites is a graph represented by a list of edges, not adjacency matrices. Read more about how a graph is represented.
 You may assume that there are no duplicate edges in the input prerequisites.
 */
public class CourseScheduleII_210 {
    /**
     * use topological sorting to find order
     * start from 0 in-degree vertex, then search in directional graph
     */
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        List<Integer>[] graph = new ArrayList[numCourses];
        Queue<Integer> queue = new LinkedList<>();
        int[] degree = new int[numCourses];

        constructGraph(graph, prerequisites, degree);
        addGraphRoot(degree, queue);
        int[] res = new int[numCourses];
        int numbersOfVertex = bfsGraph(graph, degree, queue, res);

        if (numbersOfVertex == numCourses) {
            return res;
        }
        return new int[0];
    }
    private void constructGraph(List<Integer>[] graph, int[][] data, int[] degree) {
        for (int i = 0; i < graph.length; i++) {
            graph[i] = new ArrayList<>();
        }
        int root = 0, vertex = 0;
        for (int i = 0; i < data.length; i++) {
            root = data[i][1];
            vertex = data[i][0];
            graph[root].add(vertex);
            degree[vertex]++;
        }
    }
    private void addGraphRoot(int[] degree, Queue<Integer> queue) {
        int count = 0;
        for (int i = 0; i < degree.length; i++) {
            if (degree[i] == 0) {
                queue.offer(i);
            }
        }
    }
    private int bfsGraph(List<Integer>[] graph, int[] degree, Queue<Integer> queue, int[] res) {
        int course = 0;
        int count = 0;
        int levelSize = 0;
        int vertex = 0;
        while (!queue.isEmpty()) {
            course = queue.poll();
            res[count] = course;
            count++;
            levelSize = graph[course].size();
            for (int i = levelSize - 1; i >= 0; i--) {
                vertex = graph[course].get(i);
                degree[vertex]--;
                if (degree[vertex] == 0) {
                    queue.offer(vertex);
                }
            }
        }
        return count;
    }

    public static void main(String[] args) {
        CourseScheduleII_210 c = new CourseScheduleII_210();
        int[][] r1 = {{1,0}};
        output(c.findOrder(2, r1));
        int[][] r2 = {};
        output(c.findOrder(3, r2));
    }
    private static void output(int[] res) {
        Arrays.stream(res).forEach(t->System.out.print(t + ","));
        System.out.println();
    }
}
