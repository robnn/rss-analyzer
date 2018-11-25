package hu.robnn.rss_analyzer.rss;

import hu.robnn.rss_analyzer.model.NodeHolder;
import hu.robnn.rss_analyzer.util.NodeParser;
import hu.robnn.rss_analyzer.util.RSSFeedCreator;
import lombok.NoArgsConstructor;
import org.jsoup.nodes.Node;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.logging.Logger;

@Component
@NoArgsConstructor
public class RssFeedSupplierImpl implements RssFeedSupplier {

    private static final Logger LOGGER = Logger.getLogger("RssFeedSupplierImpl");

    private String url;

    @Override
    public void generateRssFile(List<Node> newNodes) {
        NodeHolder nodeHolder = NodeParser.parse(newNodes, url);
        LOGGER.info("Generating new rss feed file.");
        RSSFeedCreator.create(nodeHolder);
    }

    @Override
    public void setWebPageURL(String url){
        this.url = url;
    }
}
