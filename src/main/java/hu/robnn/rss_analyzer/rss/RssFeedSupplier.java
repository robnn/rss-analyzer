package hu.robnn.rss_analyzer.rss;

import org.jsoup.nodes.Node;

import java.util.List;

//TODO ezt az interfészt alósítsa meg egy wrapper egy tényleges RSS feed supplier-re
public interface RssFeedSupplier {
    void sendMessageToRegisteredReaders(List<Node> newNodes);
}
