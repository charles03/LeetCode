package com.system.design.webSystem;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by charles on 9/18/16.
 *
 * Given a long url, make it shorter. To make it simpler, let's ignore the domain name.

 You should implement two methods:

 longToShort(url). Convert a long url to a short url.
 shortToLong(url). Convert a short url to a long url starts with http://tiny.url/.
 You can design any shorten algorithm, the judge only cares about two things:

 The short key's length should equal to 6 (without domain and slash).
 And the acceptable characters are [a-zA-Z0-9]. For example: abcD9E
 No two long urls mapping to the same short url and no two short urls mapping to the same long url.

 Example
 Given url = http://www.lintcode.com/faq/?id=10, run the following code (or something similar):

 short_url = longToShort(url) // may return http://tiny.url/abcD9E
 long_url = shortToLong(short_url) // return http://www.lintcode.com/faq/?id=10
 The short_url you return should be unique short url
 and start with http://tiny.url/ and 6 acceptable characters. For example "http://tiny.url/abcD9E" or something else.

 The long_url should be http://www.lintcode.com/faq/?id=10 in this case.
 */
public class TinyUrl {
    private final String allowChars = "0123456789" +
            "abcdefghijklmnopqrstuvwxyz" +
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final String ROOT = "http://tiny.url";

    private Map<String, String> longToShortUrl;
    private Map<String, String> shortToLongUrl;

    public TinyUrl() {
        longToShortUrl = new HashMap<>();
        shortToLongUrl = new HashMap<>();
    }

    public String shortToLong(String shortUrl) {
        if (!shortToLongUrl.containsKey(shortUrl)) {
            return null;
        }
        return shortToLongUrl.get(shortUrl);
    }
    // use random to generate index to get char from char array
    public String longToShort(String url) {
        if (longToShortUrl.containsKey(url)) {
            return longToShortUrl.get(url);
        }
        while (true) {
            String shortUrl = generateShortUrl();
            if (!shortToLongUrl.containsKey(shortUrl)) {
                shortToLongUrl.put(shortUrl, url);
                longToShortUrl.put(url, shortUrl);
                return shortUrl;
            }
        }
    }
    String generateShortUrl() {
        Random random = new Random();
        String baseUrl = "http://tiny.url";
        StringBuilder sb = new StringBuilder();
        int idx = 0;
        for (int i = 0; i < 6; i++) {
            idx = random.nextInt(62);
            sb.append(allowChars.charAt(idx));
        }
        return sb.insert(0, baseUrl).toString();
    }

}
