package com.system.design.webSystem;

/**
 * Created by charles on 9/18/16.
 *
 * As a follow up for Tiny URL, we are going to support custom tiny url, so that user can create their own tiny url.

 Notice
 Custom url may have more than 6 characters in path.

 Example
 createCustom("http://www.lintcode.com/", "lccode")
 >> http://tiny.url/lccode
 createCustom("http://www.lintcode.com/", "lc")
 >> error
 longToShort("http://www.lintcode.com/problem/")
 >> http://tiny.url/1Ab38c   // this is just an example, you can have you own 6 characters.
 shortToLong("http://tiny.url/lccode")
 >> http://www.lintcode.com/
 shortToLong("http://tiny.url/1Ab38c")
 >> http://www.lintcode.com/problem/
 shortToLong("http://tiny.url/1Ab38d")
 >> null
 */
public class TinyUrlAdvanced {
    /**
     * @param long_url a long url
     * @param short_key
     * @return a short url starts with http://tiny.url/
     */
    String createCustom(String long_url, String short_key) {
        // Write your code here
        return null;
    }

    /**
     * @param long_url a long url
     * @return a short url starts with http://tiny.url/
     */
    public String longToShort(String long_url) {
        // Write your code here
        return null;
    }

    /**
     * @param short_url a short url starts with http://tiny.url/
     * @return a long url
     */
    public String shortToLong(String short_url) {
        // Write your code here
        return null;
    }
}
