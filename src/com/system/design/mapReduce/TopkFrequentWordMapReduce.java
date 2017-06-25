package com.system.design.mapReduce;

/**
 * Created by charles on 9/19/16.
 *
 * Find top k frequent words with map reduce framework.

 The mapper's key is the document id, value is the content of the document, words in a document are split by spaces.
 For reducer, the output should be at most k key-value pairs, which are the top k words and their frequencies in this reducer.
 The judge will take care about how to merge different reducers' results to get the global top k frequent words,
 so you don't need to care about that part.
 The k is given in the constructor of TopK class.

 Notice
 For the words with same frequency, rank them with alphabet.

 Example
 Given document A =

 lintcode is the best online judge
 I love lintcode
 and document B =

 lintcode is an online judge for coding interview
 you can test your code online at lintcode
 The top 2 words and their frequencies should be

 lintcode, 4
 online, 3
 */


import java.util.Iterator;


public class TopkFrequentWordMapReduce {

    public static class Map {
        public void map(String _, Document value,
                        OutputCollector<String, Integer> output) {
            // Write your code here
            // Output the results into output buffer.
            // Ps. output.collect(String key, int value);
        }
    }

    public static class Reduce {

        public void setup(int k) {
            // initialize your data structure here
        }

        public void reduce(String key, Iterator<Integer> values) {
            // Write your code here
        }

        public void cleanup(OutputCollector<String, Integer> output) {
            // Output the top k pairs <word, times> into output buffer.
            // Ps. output.collect(String key, Integer value);
        }
    }
}

class Document {
    public int id;
    public String content;
}
