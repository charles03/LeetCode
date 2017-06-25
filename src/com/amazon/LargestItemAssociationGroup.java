package com.amazon;

import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 *
 * All real questions
 * https://instant.1point3acres.com/thread/213923/post/2251199
 *
 *
 * Created by charles on 6/13/17.
 * all items that are linked together by an item association can be considered to be in the same group
 * an item without any association to any other item can be considered to be in its own association group where size is one
 *
 * Given a list of item association relationships, write an algorithm that output
 * the largest item association group, if two group have same number of items then select group
 * which contains the item that appears first in lexicographic order
 */
public class LargestItemAssociationGroup {
    /**
     * example ; item associations
     * [[item1, item2]
     *  [item3, item4]
     *  [item4, item5]]
     *
     * largest group [item3, item4, item5]
     */
    public List<String> getLargestGroupII(String[][] association) {
        Map<String, Set<String>> treeMap = new TreeMap<>();
        addEdges(treeMap, association);
        System.out.println(treeMap);
        Set<String> set = findGroup(treeMap);
        return new ArrayList<>(set);
    }
    private void addEdges(Map<String, Set<String>> treeMap, String[][] edges) {
        Set<String> first, second;
        for (String[] edge : edges) {
            first = treeMap.getOrDefault(edge[0], new TreeSet<>());
            second = treeMap.getOrDefault(edge[1], new TreeSet<>());
            first.add(edge[1]);
            second.add(edge[0]);
            treeMap.put(edge[0], first);
            treeMap.put(edge[1], second);
        }
    }
    private Set<String> findGroup(Map<String, Set<String>> treeMap) {
        int maxCount = 0;
        Set<String> maxGroup = null;
        Set<String> group = null;
        Set<String> visited = new HashSet<>();
        for (Map.Entry<String, Set<String>> entry : treeMap.entrySet()) {
            group = dfs(treeMap, entry.getKey(), visited);
            if (group.size() > maxCount) {
                maxCount = group.size();
                maxGroup = group;
            }
        }
        return maxGroup;
    }
    private Set<String> dfs(Map<String, Set<String>> map, String item, Set<String> visited) {
        if (visited.contains(item)) {
            return new HashSet<>();
        }
        Set<String> associates = new TreeSet<>();
        associates.addAll(map.get(item));

        visited.add(item);
        map.get(item).forEach(edge -> {
            if (map.containsKey(edge)) {
                // dfs search group association for current vertex
                associates.addAll(dfs(map, edge, visited));
            }});
        return associates;
    }

    /**
     * when use union find,
     * because we go through edge pair one by one,
     * in order not to miss connected edge due to without lexicographical order
     * we first order association graph edges,
     * let each pair, small lexicographic is first,
     * for each edge, same smale lexicographic is first
     *
     * Thus, when union find for each node in pair when find correct parent node,
     * as well as without missing connection.
     */
    public List<String> getLargestGroup(String[][] association) {
        Set<String> set = new HashSet<>();
        for (String[] item : association) {
            if (item[0].compareTo(item[1]) > 0) {
                swap(item, 0, 1); // small lexicographic in the front
            }
            set.add(item[0]); // store all vertex
            set.add(item[1]);
        }
        Arrays.sort(association, (a,b) -> a[0].compareTo(b[0])); // sort whole association edges
        UnionFind uf = new UnionFind(set); // init union find

        Map<String, Set<String>> map = new HashMap<>();
        String pa = null;
        String pb = null;
        int maxSize = 0;
        Set<String> maxGroup = null;
        for (String[] item : association) {
            pa = uf.compressFind(item[0]);
            pb = uf.compressFind(item[1]);
            System.out.println(String.format("%s, %s, %s, %s", pa, pb, item[0], item[1]));
            if (!pa.equals(pb)) {
                uf.union(pa, pb); // union parent of first node in pair with parent of second node in pair

                Set<String> group = updateGroup(map, pa, item);
                map.put(pa, group);
                if (group.size() > maxSize) {
                    maxSize = group.size();
                    maxGroup = group;
                }
            }
        }
        System.out.println(maxGroup);
        return new ArrayList<>(maxGroup);
    }
    private void swap(String[] arr, int i, int j) {
        String tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
    private Set<String> updateGroup(Map<String, Set<String>> map, String parent, String[] item) {
        /** use TreeSet to achieve lexicographic order*/
        Set<String> set = map.getOrDefault(parent, new TreeSet<>());
        set.add(item[0]);
        set.add(item[1]);
        return set;
    }

    class UnionFind {
        Map<String, String> father = new HashMap<>();

        public UnionFind(Set<String> set) {
            for (String item : set) {
                father.put(item, item);
            }
        }
        public String compressFind(String item) {
            String parent = father.get(item);
            while (parent != father.get(parent)) {
                parent = father.get(parent);
            }
            String next;
            while (item != father.get(item)) {
                next = father.get(item);
                father.put(item, parent);
                item = next;
            }
            return parent;
        }

        public void union(String a, String b) {
            String aFather = compressFind(a);
            String bFather = compressFind(b);
            if (aFather != bFather) {
                father.put(bFather, aFather);
            }
        }
    }
    public static void main(String[] args) {
        LargestItemAssociationGroup l = new LargestItemAssociationGroup();
        String[][] s1 = {{"i1", "i2"}, {"i2", "i4"}, {"i3", "i5"}, {"i5", "i6"}, {"i5", "i7"}, {"i7", "i8"}};
        System.out.println(l.getLargestGroup(s1));
        System.out.println(l.getLargestGroupII(s1));

        String[][] s2 = {{"i1", "i4"}, {"i1", "i2"}, {"i3", "i5"}, {"i6", "i5"}, {"i5", "i7"}, {"i8", "i1"}};
        System.out.println(l.getLargestGroup(s2));
        System.out.println(l.getLargestGroupII(s2));

        String[][] s3 = {{"i1", "i4"}, {"i1", "i2"}, {"i3", "i5"}, {"i6", "i5"}, {"i5", "i7"}, {"i8", "i1"}, {"i9", "i3"}};
        System.out.println(l.getLargestGroup(s3));
        System.out.println(l.getLargestGroupII(s3));
    }

}
