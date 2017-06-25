package com.leetcode.geometry;

import org.junit.experimental.theories.DataPoints;

import java.util.*;

/**
 * Created by charles on 6/6/17.
 * There are some trees, where each tree is represented by (x,y) coordinate in a two-dimensional garden. Your job is to fence the entire garden using the minimum length of rope as it is expensive. The garden is well fenced only if all the trees are enclosed. Your task is to help find the coordinates of trees which are exactly located on the fence perimeter.
 Note:

 All trees should be enclosed together. You cannot cut the rope to enclose trees that will separate them in more than one group.
 All input integers will range from 0 to 100.
 The garden has at least one tree.
 All coordinates are distinct.
 Input points have NO order. No order required for output.
 */
public class ErectFence_587 {
    /**
     * this is Convex Hull algo
     * Reference : https://en.wikipedia.org/wiki/Convex_hull_algorithms
     *
     * Here primarily implement with Graham Scan -> better algo is Chan's
     *
     * steps:
     * 1. select initial bottom-left point to start hull
     * 2. sort given set of points based on their polar angles formed w.r.t, a vertical line drawn through initial point
     * 3. use orientation function
     *      this func take three args
     *      p : current point added in the hull
     *      q : next point being considered to be added in the hull
     *      r : any other point in given point space.
     *    the function returns a negative value if the point q is more counter-clockwise to p then point r.
     *
     *
     * The trick is that once all points are sorted by polar angle with respect to the reference point:

     For collinear points in the begin positions, make sure they are sorted by distance to reference point in ascending order.
     For collinear points in the end positions, make sure they are sorted by distance to reference point in descending order.
     For example:
     (0, 0), (2, 0), (3, 0), (3, 1), (3, 2), (2, 2), (1, 2), (0, 2), (0, 1)
     These points are sorted by polar angle
     The reference point (bottom left point) is (0, 0)

     In the begin positions (0, 0) collinear with (2, 0), (3, 0) sorted by distance to reference point in ascending order.
     In the end positions (0, 0) collinear with (0, 2), (0, 1) sorted by distance to reference point in descending order.
     */
    public List<Point> convexHull(Point[] points) {
        if (points.length <= 1) {
            return Arrays.asList(points);
        }
        Point p0 = getBottomLeft(points);
        sortByPolar(points, p0);
        reverseOrderForCollinearPointFromEnd(points, p0);
        Stack<Point> stack = new Stack<>();
        stack.push(points[0]);
        stack.push(points[1]);

        for (int i = 2; i < points.length; i++) {
            Point convex = stack.pop();
            while (orientation(stack.peek(), convex, points[i]) < 0) {
                convex = stack.pop();
            }
            stack.push(convex);
            stack.push(points[i]);
        }
        return new ArrayList<>(stack);

    }

    /**
     For collinear points in the begin positions, make sure they are sorted by distance to reference point in ascending order.
     For collinear points in the end positions, make sure they are sorted by distance to reference point in descending order.
     */
    private void sortByPolar(Point[] points, Point r) {
        Arrays.sort(points, (p,q) -> {
            // turn > 0 is counter-clockwise (left turn)
            // turn < 0 is clockwise (right turn)
            int polar = orientation(r, p, q); // polar angle from rp -> rq, where r is bottom left
            int dist = distance(r,p) - distance(r,q);
            return polar == 0 ? dist : polar;
        });
    }

    /**
     * graham scan start from bottom left point
     * other points can either be more left x coordinate but high y coordinate
     * or be more right but high y coordinate
     */
    private void reverseOrderForCollinearPointFromEnd(Point[] points, Point base) {
        // find col-linear points in the end positions
        int cnt = points.length;
        Point last = points[cnt - 1];
        int next = cnt - 2;
        while (next >= 0 && orientation(base,last,points[next]) == 0) {
            next--;
        }
        // reverse sort order of col-linear points in the end position
        int start = next + 1;
        int end = cnt - 1;

        while (start < end) {
            swap(points, start, end);
            start++;
            end--;
        }
    }
    private void swap(Point[] points, int i, int j) {
        Point tmp = points[i];
        points[i] = points[j];
        points[j] = tmp;
    }

    /** at least lowest y coordinate, then relative left-most */
    private Point getBottomLeft(Point[] points) {
        Point bottomLeft = points[0];
        for (Point p : points) {
            if (p.y < bottomLeft.y) {
                bottomLeft.x = p.x;
                bottomLeft.y = p.y;
            } else if (p.y == bottomLeft.y && p.x < bottomLeft.x) {
                bottomLeft.x = p.x;
            }
        }
        return bottomLeft;
    }

    /**
     * if Points p,q,r traversed in same order in counter-clockwise direction
     * the cross product of vector pq and qr should be in direction out of plane of the scree
     * which should be positive;
     *
     * vector pq * vector pr > 0
     *
     * |(q.x - p.x) (q.y - p.y)|
     * |(r.x - p.x) (r.y - p.y)| > 0
     *
     * (q.x-p.x) * (r.y - p.y) - (q.y-p.y) * (r.x-p.x) > 0
     *
     * p is common starting point for two vectors
     * if cross product pq * pr > 0;
     * which means vector pq orient anti-clockwise to vector pr
     * and then from vector qr is left turn again pq
     *
     *
     * To find orientation of ordered triplet points (p,q,r)
     * if val == 0 : p,q,r are co-liner
     * elif val < 0 : clockwise from vector pq to vector pr, because pq * pr < 0
     * else val > 0 : counter-clockwise from pq to pr.
     */
    private int orientation(Point p, Point q, Point r) {
        int val = (q.x-p.x) * (r.y-p.y) - (r.x-p.x) * (q.y-p.y);
        return val;
    }
    /** utility func to return square of distance of two points */
    private int distance(Point p, Point q) {
        return (p.x - q.x) * (p.x - q.x) + (p.y - q.y) * (p.y - q.y);
    }


    /***************
     * monotone chain solution
     *Instead of sorting the points based on their polar angles as in Graham Scan, we sort the points on the basis of their x-coordinate values. If two points have the same x-coordinate values, the points are sorted based on their y-coordinate values.
     * we reach a state such that we reach the point with the largest x-coordinate. But, the hull isn't complete yet. The portion of the hull formed till now constitutes the lower poriton of the hull. Now, we need to form the upper portion of the hull.
     * we continue the process of finding the next counterclockwise points and popping in case of a conflict, but this time we consider the points in the reverse order of their x-coordinate values. For this, we can simply traverse over the sorted pointspoints array in the reverse order
     */
    public List<Point> outerTreesII(Point[] points) {
        /**
         * we need not consider the case of collinear points explicitly, since the points have already been sorted based on their x-coordinate values.
         * So, the collinear points, if any, will implicitly be considered in the correct order.
         */
        Arrays.sort(points, (p,q) -> {
            return p.x == q.x ? q.y - p.y : q.x - p.x;
        });
        Stack<Point> hull = new Stack<>();
        // build lower hull
        /** index hull.size()-2 is common vertex of two vectors
         * orientation < 0 means (p,q,r) pq need clockwise orientation to pr */
        for (int i = 0; i < points.length; i++) {
            while (hull.size() >= 2
                    && orientation(hull.get(hull.size()-2), hull.get(hull.size()-1), points[i]) < 0) {
                hull.pop();
            }
            hull.push(points[i]);
        }
        hull.pop();
        // build upper hull
        /** from end to start */
        for (int i = points.length-1; i >= 0; i--) {
            while (hull.size() >= 2
                    && orientation(hull.get(hull.size()-2), hull.get(hull.size()-1), points[i]) <= 0) {
                hull.pop();
            }
            hull.push(points[i]);
        }
        return new ArrayList<>(new HashSet<>(hull));
    }
}
class Point {
    int x;
    int y;
    Point() {
        this.x = 0;
        this.y = 0;
    }
    Point(int a, int b) {
        this.x = a;
        this.y = b;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}

