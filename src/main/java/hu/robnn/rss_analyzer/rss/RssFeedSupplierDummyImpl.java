package hu.robnn.rss_analyzer.rss;

import org.jsoup.nodes.Node;
import org.springframework.stereotype.Component;

import java.util.List;

//TODO hasonlóan a http-hez ezt is törölni kell, amint megvan a tényleges implementáció
@Component
public class RssFeedSupplierDummyImpl implements RssFeedSupplier {
    @Override
    public void sendMessageToRegisteredReaders(List<Node> newNodes) {
        System.out.println(newNodes);
    }
}
