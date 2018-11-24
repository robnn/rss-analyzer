package hu.robnn.rss_analyzer.rss;

import org.jsoup.nodes.Node;

import java.util.List;

public interface RssFeedSupplier {
    void generateRssFile(List<Node> nodes);

    void setWebPageURL(String url);
}
