package com.leetcode.design;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by charles on 5/18/17.
 * {@link com.system.design.webSystem.TinyUrl}
 */
public class TinyUrl_535 {
    /**
     * use self increment static var as global ID
     *
     */
    private static int GLOBAL_ID = 0;
    private static String BASE_URL = "http://tiny.url/";
    private static String BASE62 = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static int BASE = 62;
    private Map<Integer, String> idToUrl;
    private Map<String, Integer> urlToId;

    public TinyUrl_535() {
        idToUrl = new HashMap<>();
        urlToId = new HashMap<>();;
    }

    public String longToShort(String url) {
        if (urlToId.containsKey(url)) {
            return BASE_URL + idToShortKey(urlToId.get(url));
        }
        GLOBAL_ID++;
        urlToId.put(url, GLOBAL_ID);
        idToUrl.put(GLOBAL_ID, url);
        return BASE_URL + idToShortKey(GLOBAL_ID);
    }

    public String shortToLong(String url) {
        String shortKey = getShortKey(url);
        int id = shortKeyToId(shortKey);
        return idToUrl.get(id);
    }

    private String idToShortKey(int id) {
        StringBuilder sb = new StringBuilder();
        while (id > 0) {
            sb.append(BASE62.charAt(id % BASE));
            id /= BASE;
        }
        while (sb.length() < 6) {
            sb.insert(0, "0");
        }
        return sb.toString();
    }

    private String getShortKey(String url) {
        return url.substring(BASE_URL.length());
    }
    private int shortKeyToId(String key) {
        int id = 0;
        for (int i = 0; i < key.length(); i++) {
            id = id * BASE + toBase62(key.charAt(i));
        }
        return id;
    }
    private int toBase62(char c) {
        if (Character.isDigit(c)) {
            return c - '0';
        }
        if (Character.isLowerCase(c)) {
            return c - 'a' + 10; // offset of 10 digits
        }
        return c - 'a' + 36; // offset of both digits and lowercase
    }
}
