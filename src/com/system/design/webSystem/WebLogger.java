package com.system.design.webSystem;

/**
 * Created by charles on 9/18/16.
 *
 * Implement a web logger, which provide two methods:

 hit(timestamp), record a hit at given timestamp.
 get_hit_count_in_last_5_minutes(timestamp), get hit count in last 5 minutes.
 the two methods will be called with non-descending timestamp (in sec).

 Example
 hit(1);
 hit(2);
 get_hit_count_in_last_5_minutes(3);
 >> 2
 hit(300);
 get_hit_count_in_last_5_minutes(300);
 >> 3
 get_hit_count_in_last_5_minutes(301);
 >> 2
 */
public class WebLogger {

    public WebLogger() {
        // initialize your data structure here.
    }

    /**
     * @param timestamp an integer
     * @return void
     */
    public void hit(int timestamp) {
        // Write your code here
    }

    /**
     * @param timestamp an integer
     * @return an integer
     */
    public int get_hit_count_in_last_5_minutes(int timestamp) {
        // Write your code here
        return 0;
    }
}
