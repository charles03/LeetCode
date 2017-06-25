package com.system.design.database;

import java.util.*;

/**
 * Created by charles on 9/18/16.
 *
 * Cassandra is a NoSQL storage. The structure has two-level keys.

 Level 1: raw_key. The same as hash_key or shard_key.
 Level 2: column_key.
 Level 3: column_value
 raw_key is used to hash and can not support range query. let's simplify this to a string.
 column_key is sorted and support range query. let's simplify this to integer.
 column_value is a string. you can serialize any data into a string and store it in column value.

 implement the following methods:

 insert(raw_key, column_key, column_value)
 query(raw_key, column_start, column_end) // return a list of entries

 Example
 insert("google", 1, "haha")
 query("google", 0, 1)
 >> [（1, "haha")]
 */
public class MiniCassandra {
    private Map<String, NavigableMap<Integer, String>> map;

    public MiniCassandra() {
        map = new HashMap<>();
    }

    public void insert(String rawKey, int columnKey, String columnValue) {
        if (!map.containsKey(rawKey)) {
            map.put(rawKey, new TreeMap<Integer, String>());
        }
        map.get(rawKey).put(columnKey, columnValue);
    }

    /** because inner class is navigable map, treemap is one implementation, support pick up range submap*/
    public List<Column> query(String rawKey, int columnStart, int columnEnd) {
        List<Column> res = new ArrayList<>();
        if (!map.containsKey(rawKey)) {
            return res;
        }
        NavigableMap<Integer, String> range = map.get(rawKey).subMap(columnStart, true, columnEnd, true);
        for (Map.Entry<Integer, String> entry : range.entrySet()) {
            res.add(new Column(entry.getKey(), entry.getValue()));
        }
        return res;
    }

}

class Column {
    public int key;
    public String value;
    public Column(int key, String value) {
        this.key = key;
        this.value = value;
    }
}