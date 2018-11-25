package hu.robnn.rss_analyzer.http;

import hu.robnn.rss_analyzer.model.UrlHolder;

//TODO Ezt az interfészt valósítsa meg egy wrapper egy http kliensre
public interface HttpClient {
    String getWebPageAsString(UrlHolder urlHolder);
}
