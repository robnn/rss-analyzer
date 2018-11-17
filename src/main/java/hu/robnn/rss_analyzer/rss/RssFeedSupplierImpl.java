package hu.robnn.rss_analyzer.rss;

import hu.robnn.rss_analyzer.api.WebSocketController;
import hu.robnn.rss_analyzer.model.NodeHolder;
import hu.robnn.rss_analyzer.util.NodeParser;
import hu.robnn.rss_analyzer.util.RSSFeedCreator;
import lombok.AllArgsConstructor;
import org.jsoup.nodes.Node;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

//TODO hasonlóan a http-hez ezt is törölni kell, amint megvan a tényleges implementáció
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
        sendToFrontend(rssFeed);
    }

    @Override
    public void sendToFrontend(NodeHolder nodeHolder) {
        webSocketController.publishWebSocket(nodeHolder);
    }

    @Override
    public void sendToFrontend(String rss) {
        webSocketController.publishWebSocket(rss);
    }
}
