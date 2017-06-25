package com.advanced.dataStructure.array;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by charles on 9/22/16.
 *
 * Given an interval list which are flying and landing time of the flight. How many airplanes are on the sky at most?
 *
 * Notice
 * If landing and flying happens at the same time, we consider landing should happen at first.
 *
 * Example
 For interval list

 [
 [1,10],
 [2,3],
 [5,8],
 [4,7]
 ]
 Return 3
 */
public class NumberOfAirplaneInSky {
    /**
     * @param: List<Interval> An interval array
     * @return: Count of airplanes are in the sky.
     */
    public int countOfAirplanes(List<Interval> airplanes) {
        if (airplanes == null || airplanes.size() == 0) {
            return 0;
        }
        List<Point> list = new ArrayList<>(airplanes.size() * 2);
        airplanes.forEach((a) ->
            {list.add(new Point(a.start, 1));
                list.add(new Point(a.end, 0));});
        list.sort((Point a, Point b) -> {
            if (a.time == b.time) {
                return a.flag - b.flag;
            } else {
                return a.time - b.time;
            }
        });

        int count = 0;
        int ans = 0;
        for (Point p : list) {
            if (p.flag == 1) {
                count++;
            } else {
                count--;
            }
            ans = Math.max(ans, count);
        }

        return ans;
    }

    public static void main(String[] args) {
        NumberOfAirplaneInSky sky = new NumberOfAirplaneInSky();
        List<Interval> intervals = new ArrayList<>();
        intervals.add(new Interval(1, 10));
        intervals.add(new Interval(2, 3));
        intervals.add(new Interval(5, 8));
        intervals.add(new Interval(4, 7));
        System.out.println(sky.countOfAirplanes(intervals));
    }
}

/*
define interval class
 */
class Interval {
    public int start;
    public int end;
    public Interval(int start, int end) {
        this.start = start;
        this.end = end;
    }
}
/*
customize define point class, give time ans flag as current state
flag for in the sky is 1; flag for land is 0
 */
class Point {
    public int time;
    public int flag;
    public Point(int time, int flag) {
        this.time = time;
        this.flag = flag;
    }
}