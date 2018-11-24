package hu.robnn.rss_analyzer.rss;

import hu.robnn.rss_analyzer.api.WebSocketController;
import hu.robnn.rss_analyzer.model.NodeHolder;
import hu.robnn.rss_analyzer.model.RssStringHolder;
import hu.robnn.rss_analyzer.util.NodeParser;
import hu.robnn.rss_analyzer.util.RSSFeedCreator;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.jsoup.nodes.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
@NoArgsConstructor
public class RssFeedSupplierImpl implements RssFeedSupplier {

    private WebSocketController webSocketController;
    private String url;

    @Autowired
    public void setWebSocketController(WebSocketController webSocketController){
        this.webSocketController = webSocketController;
    }

    @Override
    public void sendMessageToRegisteredReaders(List<Node> newNodes) {
        NodeHolder nodeHolder = NodeParser.parse(newNodes, url);
        RSSFeedCreator.create(nodeHolder);

        //TODO ezt a rendes implementációban is meg kell hívni
        //sendToFrontend(newNodes);
    }

    @Override
    public void sendToFrontend(List<Node> nodes, String rss) {
        webSocketController.publishWebSocket(new RssStringHolder(
                nodes.stream().map(Node::toString).collect(Collectors.toList()), new Date(), rss));
    }

    @Override
    public void setWebPageURL(String url){
        this.url = url;
    }
}
