package com.advanced.dataStructure2.heap;

import java.util.*;

/**
 * Created by charles on 10/30/16.
 Given N buildings in a x-axis，each building is a rectangle and can be represented by a triple (start, end, height)，
 where start is the start position on x-axis, end is the end position on x-axis and height is the height of the building.
 Buildings may overlap if you see them from far away，find the outline of them。

 An outline can be represented by a triple, (start, end, height),
 where start is the start position on x-axis of the outline,
 end is the end position on x-axis and height is the height of the outline.

 Notice
 Please merge the adjacent outlines if they have the same height and make sure different outlines cant overlap on x-axis.
 Given 3 buildings：

 [
 [1, 3, 3],
 [2, 4, 4],
 [5, 6, 1]
 ]
 The outlines are：

 [
 [1, 2, 3],
 [2, 4, 4],
 [5, 6, 1]
 ]

        |-----|
    \---- - \ |
    \   |   \ |
 ___\   |   \ |____

 Thoughts
 -- if a positoin is shadowed by other buildings
    1. height of that building is larger than the building to which current pos belong.
    2. the start point of that building must be smaller(or equal to) than position
    3. the end point of that building must be larger(or equal to) than position
 -- Then we have solution
    1. when reach start point, height of current building immediately takes effect which means
        it could possiblly affect the contour or shadow other when mixed with other following buildings
    2. when reach end point, height of current building will stop influence
    3. our target exists at the pos where height change happens and there is nothing above it shadowing it

 */


public class BuildingOutline {
    /**
     * https://discuss.leetcode.com/topic/28482/once-for-all-explanation-with-clean-java-code-o-n-2-time-o-n-space
     * getSkyLine method from leetcode is a little bit different from api in lintcode
     * output is array of {left pos, right pos, height}
     *
     * @param buildings input is array of 3 elems of building {{left, right, height}}
     * @return output is list of {left pos, right pos, corresponding height}
     */
    public List<int[]> getSkyLine(int[][] buildings) {
        List<int[]> res = new ArrayList<>();
        List<int[]> heightArr = new ArrayList<>();
        // initialize
        for (int[] b: buildings) {
            // use pos and neg to differentiate when add height into max heap or remove
            // start point has negative height value
            heightArr.add(new int[]{b[0], -b[2]});
            // end point has normal height value
            heightArr.add(new int[]{b[1], b[2]});
        }
        // sort height array, based on first value, if necessary, use second to break tie
        Collections.sort(heightArr, (a, b) -> {return a[0] != b[0] ? a[0] - b[0] : a[1] - b[1];});
//        Collections.sort(heightArr, new Comparator<int[]>() {
//            @Override
//            public int compare(int[] o1, int[] o2) {
//                return o1[0] == o2[0] ? o1[1] - o2[1] : o1[0] - o2[0];
//            }
//        });

        // use max heap to store possible heights
        Queue<Integer> heap = new PriorityQueue<>((a, b) -> (b - a));
        // Providing initial value to make it more consistent
        heap.offer(0);
        // before starting previous max height is 0
        int prev = 0;
        // visit all points in order
        for (int[] h: heightArr) {
            if (h[1] < 0) {
                heap.add(-h[1]); // start point, add height
            } else {
                heap.remove(h[1]); // end point, remove height
            }
            int curr = heap.peek(); // current max height
            // compare current max height with previous max height, update result
            // and update previous max height if necessary
            if (prev != curr) {
                res.add(new int[]{h[0], curr});
                prev = curr;
            }
        }
        printResult(res);
        // because output look for pattern (left, right, height)
        // need assistant method for enrichment
        return enrichOutline(res);
    }

    /**
     * also can use TreeMap(red-black) to store the height value
     */
    public List<int[]> getSkylingByTreeMap(int[][] buildings) {
        List<int[]> res = new ArrayList<>();
        List<int[]> heights = new ArrayList<>();
        for (int[] b: buildings) {
            heights.add(new int[]{b[0], -b[2]}); // negative height for start point
            heights.add(new int[]{b[1], b[2]});
        }
        Collections.sort(heights, (a, b) -> (a[0] == b[0]) ? a[1] - b[1] : a[0] - b[0]);
        TreeMap<Integer, Integer> heightMap = new TreeMap<>(Collections.reverseOrder());
        heightMap.put(0, 1);
        int prevHeight = 0;
        for (int[] h : heights) {
            if (h[1] < 0) { // for start point
                Integer cnt = heightMap.get(-h[1]);
                cnt = (cnt == null) ? 1 : cnt + 1;
                heightMap.put(-h[1], cnt);
            } else {
                Integer cnt = heightMap.get(h[1]);
                if (cnt == 1) {
                    heightMap.remove(h[1]);
                } else {
                    heightMap.put(h[1], cnt - 1);
                }
            }
            int currHeight = heightMap.firstKey();
            if (prevHeight != currHeight) {
                res.add(new int[]{h[0], currHeight});
                prevHeight = currHeight;
            }
        }
        printResult(res);
        return enrichOutline(res);
    }

    public static void main(String[] args) {
        BuildingOutline outline = new BuildingOutline();
        int[][] buildings = {{1, 3, 3}, {2, 4, 4}, {5, 6, 1}};
//        outline.getSkyLine(buildings);
        outline.getSkylingByTreeMap(buildings);
    }

    private static void printResult(List<int[]> res) {
        res.stream().forEach(a -> {Arrays.stream(a).forEach(i -> System.out.print(i + ", "));
        System.out.println("");});
    }

    /**
     * @param input (left and height)
     * @return output (preleft, nextleft, height);
     */
    private List<int[]> enrichOutline(List<int[]> input) {
        List<int[]> ans = new ArrayList<>();
        if (input.size() <= 0) {
            return ans;
        }
        int preLeft = input.get(0)[0];
        int height = input.get(0)[1];
        int preRight = 0;
        for (int i = 1; i < input.size(); i++) {
            int[] now = new int[3];
            preRight = input.get(i)[0];
            if (height > 0) {
                now[0] = preLeft;
                now[1] = preRight;
                now[2] = height;

                ans.add(now);
            }
            preLeft = preRight;
            height = input.get(i)[1];
        }
        System.out.println("after enrichment");
        printResult(ans);
        return ans;
    }
}
