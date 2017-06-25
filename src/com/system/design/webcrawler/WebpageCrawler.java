package com.system.design.webcrawler;

/**
 * Created by charles on 9/18/16.
 *
 * Implement a webpage Crawler to crawl webpages of http://www.wikipedia.org/. To simplify the question, let's use url instead of the the webpage content.

 Your crawler should:

 Call HtmlHelper.parseUrls(url) to get all urls from a webpage of given url.
 Only crawl the webpage of wikipedia.
 Do not crawl the same webpage twice.
 Start from the homepage of wikipedia: http://www.wikipedia.org/

 Notice
 You need do it with multithreading.

 Example
 Given

 "http://www.wikipedia.org/": ["http://www.wikipedia.org/help/"]
 "http://www.wikipedia.org/help/": []
 Return ["http://www.wikipedia.org/", "http://www.wikipedia.org/help/"]

 Given:

 "http://www.wikipedia.org/": ["http://www.wikipedia.org/help/"]
 "http://www.wikipedia.org/help/": ["http://www.wikipedia.org/", "http://www.wikipedia.org/about/"]
 "http://www.wikipedia.org/about/": ["http://www.google.com/"]
 Return ["http://www.wikipedia.org/", "http://www.wikipedia.org/help/", "http://www.wikipedia.org/about/"]

 Tage: Bread First Search
 */
public class WebpageCrawler {
}
