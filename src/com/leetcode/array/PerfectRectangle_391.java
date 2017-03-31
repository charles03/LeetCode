package com.leetcode.array;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by charles on 3/12/17.
 * Given N axis-aligned rectangles where N > 0, determine if they all together form an exact cover of a rectangular region.

 Each rectangle is represented as a bottom-left point and a top-right point. For example, a unit square is represented as [1,1,2,2]. (coordinate of bottom-left point is (1, 1) and top-right point is (2, 2)).


 Example 1:

 rectangles = [
 [1,1,3,3],
 [3,1,4,2],
 [3,2,4,4],
 [1,3,2,4],
 [2,3,3,4]
 ]

 Return true. All 5 rectangles together form an exact cover of a rectangular region.

 Example 2:

 rectangles = [
 [1,1,2,3],
 [1,3,2,4],
 [3,1,4,2],
 [3,2,4,4]
 ]

 Return false. Because there is a gap between the two rectangular regions.

 Example 3:

 rectangles = [
 [1,1,3,3],
 [3,1,4,2],
 [1,3,2,4],
 [3,2,4,4]
 ]

 Return false. Because there is a gap in the top center.

 Example 4:

 rectangles = [
 [1,1,3,3],
 [3,1,4,2],
 [1,3,2,4],
 [2,2,4,4]
 ]

 Return false. Because two of the rectangles overlap with each other.
 */
public class PerfectRectangle_391 {
    public boolean isRectangleCover(int[][] rectangles) {
        if (rectangles == null || rectangles.length == 0 || rectangles[0].length == 0) {
            return false;
        }
        int x1 = Integer.MAX_VALUE;
        int y1 = Integer.MAX_VALUE;
        int x2 = Integer.MIN_VALUE;
        int y2 = Integer.MIN_VALUE;

        Set<String> set = new HashSet<>();
        int area = 0;

        String s1, s2, s3, s4;

        for (int[] rect : rectangles) {
            x1 = Math.min(rect[0], x1);
            y1 = Math.min(rect[1], y1);
            x2 = Math.max(rect[2], x2);
            y2 = Math.max(rect[3], y2);
            area += (rect[2] - rect[0]) * (rect[3] - rect[1]);

            s1 = rect[0] + " " + rect[1];
            s2 = rect[0] + " " + rect[3];
            s3 = rect[2] + " " + rect[3];
            s4 = rect[2] + " " + rect[1];

            if (!set.add(s1)) {
                set.remove(s1);
            }
            if (!set.add(s2)) {
                set.remove(s2);
            }
            if (!set.add(s3)) {
                set.remove(s3);
            }
            if (!set.add(s4)) {
                set.remove(s4);
            }
        }
        if (!set.contains(x1 + " " + y1)
                || !set.contains(x1 + " " + y2)
                || !set.contains(x2 + " " + y1)
                || !set.contains(x2 + " " + y2)
                || set.size() != 4) {
            return false;
        }
        return area == (x2 - x1) * (y2 - y1);
    }

    public boolean isRectangleCoverII(int[][] rectangles) {
        final int N = rectangles.length;

        int LEFT = Integer.MAX_VALUE, BOTTOM = LEFT, RIGHT = Integer.MIN_VALUE, TOP = RIGHT;

        for(int i=0; i<N; i++) {
            final int[] rec = rectangles[i];
            LEFT = Math.min(LEFT, rec[0]);
            BOTTOM = Math.min(BOTTOM, rec[1]);
            RIGHT = Math.max(RIGHT, rec[2]);
            TOP = Math.max(TOP, rec[3]);
        }

        final int X = RIGHT - LEFT + 1;
        final int SIZE = (RIGHT - LEFT) * (TOP - BOTTOM);

        final Set<Integer> points = new HashSet<>();
        int size = 0;

        for(int n=0; n<N; n++) {
            final int[] rec = rectangles[n];
            final int left = rec[0];
            final int bottom = rec[1];
            final int right = rec[2];
            final int top = rec[3];

            size += (right - left) * (top - bottom);
            if(size >= SIZE && n != N-1) return false;

            int point = left - LEFT + (bottom - BOTTOM) * X;
            if(!points.add(point)) points.remove(point);

            point = right - LEFT + (bottom - BOTTOM) * X;
            if(!points.add(point)) points.remove(point);

            point = right - LEFT + (top - BOTTOM) * X;
            if(!points.add(point)) points.remove(point);

            point = left - LEFT + (top - BOTTOM) * X;
            if(!points.add(point)) points.remove(point);
        }

        return size == SIZE && points.size() == 4 && points.contains(0) && points.contains(RIGHT-LEFT) && points.contains((TOP-BOTTOM) * X) && points.contains(RIGHT - LEFT + (TOP-BOTTOM) * X);
    }
}
