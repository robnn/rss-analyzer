package hu.robnn.rss_analyzer.http;

import hu.robnn.rss_analyzer.model.UrlHolder;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import org.springframework.stereotype.Component;

import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class HttpClientImpl implements HttpClient {
    private final OkHttpClient okHttpClient = new OkHttpClient.Builder().cache(null).build();

    private static final Logger LOGGER = Logger.getLogger("HttpClientImpl");
    @Override
    public String getWebPageAsString(UrlHolder urlHolder) {
        try {
            Request request = new Request.Builder().url(urlHolder.getPageUrl()).build();
            ResponseBody body = okHttpClient.newCall(request).execute().body();
            if(body != null) {
                LOGGER.log(Level.INFO, "Getting " + urlHolder.getPageUrl() + " was successful.");
                return body.string();
            } else {
                throw new RuntimeException("Could not get page for " + urlHolder.getPageUrl());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}