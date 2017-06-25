package com.system.design.mapReduce;

import java.util.Iterator;
import java.util.List;

/**
 * Created by charles on 9/19/16.
 *
 * Use map reduce to build inverted index for given documents.
 *
 */

public class InvertedIndexMapReduce {

    public static class Map {
        public void map(String _, Document value,
                        OutputCollector<String, Integer> output) {
            // Write your code here
            // Output the results into output buffer.
            // Ps. output.collect(String key, int value);
        }
    }

    public static class Reduce {
        public void reduce(String key, Iterator<Integer> values,
                           OutputCollector<String, List<Integer>> output) {
            // Write your code here
            // Output the results into output buffer.
            // Ps. output.collect(String key, List<Integer> value);
        }
    }
}
