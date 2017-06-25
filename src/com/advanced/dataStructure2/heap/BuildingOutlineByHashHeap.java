package com.advanced.dataStructure2.heap;

import java.util.*;

/**
 * Created by charles on 11/6/16.
 * Question same as {@link BuildingOutline}
 */
public class BuildingOutlineByHashHeap {
    public List<List<Integer>> buildOutline(int[][] buildings) {
        List<List<Integer>> res = new ArrayList<List<Integer>>();
        if (buildings == null || buildings.length == 0 || buildings[0].length == 0) {
            return res;
        }
        List<Edge> edges = new ArrayList<>();
        for (int[] b : buildings) {
            // better way to mark start point height as negative
            edges.add(new Edge(b[0], b[2], true));// start point
            edges.add(new Edge(b[1], b[2], false));// end point
        }
        Collections.sort(edges, new EdgeComparator());
        // can use priority queue or hash heap
        HashHeap heap = new HashHeap("MAX");

        List<Integer> now = null;
        for (Edge e: edges) {
            if (e.isStart) {
                if (heap.isEmpty() || e.height > heap.peek()) {
                    // update when heap is emtpy or new height is large than existing max height in heap
                    now = new ArrayList<>(Arrays.asList(e.pos, e.height));
                    res.add(now);
                }
                heap.add(e.height);
            } else { // end point
                heap.delete(e.height); // lg(n) in hash heap
                if (heap.isEmpty() || e.height > heap.peek()) {
                    if (heap.isEmpty()) { // new height is 0
                        now = new ArrayList<>(Arrays.asList(e.pos, 0));
                    } else {
                        now = new ArrayList<>(Arrays.asList(e.pos, heap.peek()));
                    }
                    res.add(now);
                }
            }
        }
        System.out.println("before enrichment");
        print(res);
        return enrichOutput(res);
    }

    private List<List<Integer>> enrichOutput(List<List<Integer>> res) {
        List<List<Integer>> ans = new ArrayList<List<Integer>>();

        if (res.size() > 0) {
            int preLeft = res.get(0).get(0);
            int height = res.get(0).get(1);
            int preRight = 0;
            for (int i = 1; i < res.size(); i++) {
                List<Integer> now = new ArrayList<Integer>();
                // left position of now is end pos of last point
                preRight = res.get(i).get(0);
                if (height > 0) {
                    now.add(preLeft);
                    now.add(preRight);
                    now.add(height);

                    ans.add(now);
                }
                preLeft = preRight;
                height = res.get(i).get(1);
            }
        }
        System.out.println("after enrichment");
        print(ans);
        return ans;
    }

    private void print(List<List<Integer>> in) {
        List<List<Integer>> out = in;
        out.stream().forEach(l -> {l.stream().forEach(i -> System.out.print(i + ", ")); System.out.println("");});
    }

    public static void main(String[] args) {
        BuildingOutlineByHashHeap heap = new BuildingOutlineByHashHeap();
        int[][] buildings = {{1, 3, 3}, {2, 4, 4}, {5, 6, 1}};
        heap.buildOutline(buildings);
    }
}

class Edge {
    int pos;
    int height;
    boolean isStart; // boolean of start point or end point
    public Edge(int pos, int height, boolean isStart) {
        this.pos = pos;
        this.height = height;
        this.isStart = isStart;
    }
}

class EdgeComparator implements Comparator<Edge> {

    @Override
    public int compare(Edge o1, Edge o2) {
        Edge e1 = (Edge)o1;
        Edge e2 = (Edge)o2;
        // left most is positive
        if (e1.pos != e2.pos) {
            return compareInt(e1.pos, e2.pos);
        }
        // max height is positive at start point
        if (e1.isStart && e2.isStart) {
            return compareInt(e2.height, e1.height);
        }
        // min height is positive at end point
        if (!e1.isStart && !e2.isStart) {
            return compareInt(e1.height, e2.height);
        }
        return e1.isStart ? -1 : 1;
    }
    private int compareInt(int a, int b) {
        return a <= b ? -1 : 1;
    }
}