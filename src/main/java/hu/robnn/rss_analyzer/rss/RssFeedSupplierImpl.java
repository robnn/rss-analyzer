package hu.robnn.rss_analyzer.rss;

import hu.robnn.rss_analyzer.model.NodeHolder;
import hu.robnn.rss_analyzer.util.NodeParser;
import hu.robnn.rss_analyzer.util.RSSFeedCreator;
import lombok.NoArgsConstructor;
import org.jsoup.nodes.Node;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@NoArgsConstructor
public class RssFeedSupplierImpl implements RssFeedSupplier {

    private String url;

    @Override
    public void generateRssFile(List<Node> newNodes) {
        NodeHolder nodeHolder = NodeParser.parse(newNodes, url);
        RSSFeedCreator.create(nodeHolder);
    }

    @Override
    public void setWebPageURL(String url){
        this.url = url;
    }
}
