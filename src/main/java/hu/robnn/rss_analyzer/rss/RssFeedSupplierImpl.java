package hu.robnn.rss_analyzer.rss;

import hu.robnn.rss_analyzer.api.WebSocketController;
import hu.robnn.rss_analyzer.model.NodeHolder;
import hu.robnn.rss_analyzer.model.RssStringHolder;
import hu.robnn.rss_analyzer.util.NodeParser;
import hu.robnn.rss_analyzer.util.RSSFeedCreator;
import lombok.AllArgsConstructor;
import org.jsoup.nodes.Node;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class RssFeedSupplierImpl implements RssFeedSupplier {

    private final WebSocketController webSocketController;

    @Override
    public void sendMessageToRegisteredReaders(List<Node> newNodes) {
        //System.out.println(newNodes);


        //TODO: frontendről bekérni a kívánt tag típusokat
        List<String> desiredTags = new ArrayList<>();
        desiredTags.add("href");
        desiredTags.add("value");

        NodeHolder nodeHolder = NodeParser.parse(newNodes, desiredTags);
        String rssFeed = RSSFeedCreator.create(nodeHolder);

        //TODO ezt a rendes implementációban is meg kell hívni
        //sendToFrontend(nodeHolder);
        sendToFrontend(newNodes, rssFeed);
    }

    @Override
    public void sendToFrontend(List<Node> nodes, String rss) {
        webSocketController.publishWebSocket(new RssStringHolder(
                nodes.stream().map(Node::toString).collect(Collectors.toList()), new Date(), rss));
    }
}
