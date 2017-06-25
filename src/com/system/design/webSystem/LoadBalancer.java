package com.system.design.webSystem;

/**
 * Created by charles on 9/18/16.
 *
 * Implement a load balancer for web servers. It provide the following functionality:

 Add a new server to the cluster => add(server_id).
 Remove a bad server from the cluster => remove(server_id).
 Pick a server in the cluster randomly with equal probability => pick().

 Example
 At beginning, the cluster is empty => {}.

 add(1)
 add(2)
 add(3)
 pick()
 >> 1         // the return value is random, it can be either 1, 2, or 3.
 pick()
 >> 2
 pick()
 >> 1
 pick()
 >> 3
 remove(1)
 pick()
 >> 2
 pick()
 >> 3
 pick()
 >> 3
 */
public class LoadBalancer {

    public LoadBalancer() {
        // Initialize your data structure here.
    }

    // @param server_id add a new server to the cluster
    // @return void
    public void add(int server_id) {
        // Write your code here
    }

    // @param server_id server_id remove a bad server from the cluster
    // @return void
    public void remove(int server_id) {
        // Write your code here
    }

    // @return pick a server in the cluster randomly with equal probability
    public int pick() {
        // Write your code here
        return 0;
    }
}