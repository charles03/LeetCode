package com.leetcode.graph;

import java.util.*;

/**
 * Created by charles on 2/6/17.
 * Equations are given in the format A / B = k, where A and B are variables represented as strings, and k is a real number (floating point number). Given some queries, return the answers. If the answer does not exist, return -1.0.

 Example:
 Given a / b = 2.0, b / c = 3.0.
 queries are: a / c = ?, b / a = ?, a / e = ?, a / a = ?, x / x = ? .
 return [6.0, 0.5, -1.0, 1.0, -1.0 ].

 The input is: vector<pair<string, string>> equations, vector<double>& values, vector<pair<string, string>> queries , where equations.size() == values.size(), and the values are positive. This represents the equations. Return vector<double>.

 According to the example above:

 equations = [ ["a", "b"], ["b", "c"] ],
 values = [2.0, 3.0],
 queries = [ ["a", "c"], ["b", "a"], ["a", "e"], ["a", "a"], ["x", "x"] ].
 The input is always valid. You may assume that evaluating the queries will result in no division by zero and there is no contradiction.
 */
public class EvaluateDivision_399 {
    /**
     * Solution One: by Graph, DFS + HashMap
     */
    public double[] calcEquation(String[][] equations, double[] values, String[][] queries) {
        Map<String, List<String>> nodeMap = new HashMap<>();
        Map<String, List<Double>> valMap = new HashMap<>();

        for (int i = 0; i < equations.length; i++) {
            String[] equation = equations[i];
            String dividend = equation[0];
            String divisor = equation[1];
            double quotient = values[i];
            if (!nodeMap.containsKey(dividend)) {
                nodeMap.put(dividend, new ArrayList<String>());
                valMap.put(dividend, new ArrayList<Double>());
            }
            if (!nodeMap.containsKey(divisor)) {
                nodeMap.put(divisor, new ArrayList<>());
                valMap.put(divisor, new ArrayList<>());
            }
            nodeMap.get(dividend).add(divisor);
            nodeMap.get(divisor).add(dividend);
            valMap.get(dividend).add(quotient);
            valMap.get(divisor).add(1/quotient);
        }

        double[] res = new double[queries.length];
        for (int i = 0; i < queries.length; i++) {
            String[] query = queries[i];
            res[i] = dfsHelper(query[0], query[1], nodeMap, valMap, new HashSet<String>(), 1.0);
            if (res[i] == 0.0) {
                res[i] = -1.0;
            }
        }
        return res;
    }

    private double dfsHelper(String start, String end, Map<String, List<String>> pairMap, Map<String, List<Double>> valMap, Set<String> set, double val) {
        if (set.contains(start)) {
            return 0.0;
        }
        if (!pairMap.containsKey(start)) {
            return 0.0;
        }
        if (start.equals(end)) {
            return val;
        }
        set.add(start);
        List<String> nodes = pairMap.get(start);
        List<Double> vals = valMap.get(start);

        double tmp = 0.0;
        for (int i = 0; i < nodes.size(); i++) {
            tmp = dfsHelper(nodes.get(i), end, pairMap, valMap, set, val*vals.get(i));
            if (tmp != 0.0) {
                break;
            }
        }
        set.remove(start);
        return tmp;
    }

    /**
     * Solution Two
     */
    public double[] calcEquationII(String[][] equations, double[] values, String[][] queries) {
        Map<String, Integer> idxMap = new HashMap<>();
        buildIndexMap(equations, idxMap);
        int m = idxMap.size();
        double[][] graph = new double[m][m];
        buildGraph(graph, equations, values, idxMap);

        double[] res = new double[queries.length];
        int index = 0;
        int x = -1, y = -1;
        for (String[] query : queries) {
            x = idxMap.getOrDefault(query[0], -1);
            y = idxMap.getOrDefault(query[1], -1);
            if (x < 0 || y < 0) {
                res[index] = -1.0;
            } else if (x == y) {
                res[index] = 1.0;
            } else if (graph[x][y] != 0) {
                res[index] = graph[x][y];
            } else {
                graph[x][y] = dfsCalculate(1.0, x, y, graph, new boolean[m][m]);
                res[index] = graph[x][y];
                graph[y][x] = 1.0 / graph[x][y];
            }
            index++;
        }
        return res;
    }

    private void buildIndexMap(String[][] equations, Map<String, Integer> map) {
        int index = 0;
        for (String[] equation : equations) {
            for (String node : equation) {
                if (!map.containsKey(node)) {
                    map.put(node, index);
                    index++;
                }
            }
        }
    }

    /** graph[x][y] is map.get(x) / map.get(y) */
    private void buildGraph(double[][] graph, String[][] equations, double[] values, Map<String, Integer> idxMap) {
        int x = 0, y = 0;
        int i = 0;
        for (String[] equation : equations) {
            x = idxMap.get(equation[0]);
            y = idxMap.get(equation[1]);
            graph[x][y] = values[i];
            graph[y][x] = 1.0 / values[i];
            i++;
        }
    }

    private double dfsCalculate(double curl, int x, int y, double[][] graph, boolean[][] visited) {
        // return condition
        if (x == y || graph[x][y] > 0) {
            return graph[x][y] * curl;
        }
        double product = -1.0; // init negative
        for (int i = 0; i < graph[0].length; i++) {
            // loop from index 0 to index map.size(), graph is m * m size
            if (graph[x][i] > 0 && !visited[x][i]) {
                visited[x][i] = true;
                product = dfsCalculate(graph[x][i] * curl, i, y, graph, visited);
                if (product > 0) {
                    break;
                }
            }
        }
        return product;
    }
}
