package hu.robnn.rss_analyzer.http;

import hu.robnn.rss_analyzer.model.UrlHolder;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

@Component
public class HttpClientImpl implements HttpClient {
    @Override
    public String getWebPageAsString(UrlHolder urlHolder) {
        try {
            String line;
            URL url = new URL(urlHolder.getPageUrl());
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));

            StringBuilder sb = new StringBuilder();

            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
            }

            System.out.println("Reading " + urlHolder.getPageUrl() + " succeeded.");
            br.close();

            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "<html><body><p>The given url is invalid.</p></body></html>";
        }
    }
}