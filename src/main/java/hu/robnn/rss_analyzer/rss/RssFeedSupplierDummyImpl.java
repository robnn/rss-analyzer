package hu.robnn.rss_analyzer.rss;

import hu.robnn.rss_analyzer.api.WebSocketController;
import hu.robnn.rss_analyzer.model.NodeHolder;
import org.jsoup.nodes.Node;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

//TODO hasonlóan a http-hez ezt is törölni kell, amint megvan a tényleges implementáció
@Component
public class RssFeedSupplierDummyImpl implements RssFeedSupplier {

    //TODO ezt a rendes implementációban is meg kell hívni
    private final WebSocketController webSocketController;

    public RssFeedSupplierDummyImpl(WebSocketController webSocketController) {
        this.webSocketController = webSocketController;
    }


    @Override
    public void sendMessageToRegisteredReaders(List<Node> newNodes) {
        System.out.println(newNodes);
        sendToFrontend(new NodeHolder(newNodes.stream().map(Node::toString).collect(Collectors.toList())));
    }

    @Override
    public void sendToFrontend(NodeHolder nodeHolder) {
        webSocketController.publishWebSocket(nodeHolder);
    }
}
