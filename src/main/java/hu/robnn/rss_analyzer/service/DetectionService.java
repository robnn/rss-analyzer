package hu.robnn.rss_analyzer.service;

import hu.robnn.rss_analyzer.algorithm.CandidateDetector;
import hu.robnn.rss_analyzer.algorithm.ChangeDetector;
import hu.robnn.rss_analyzer.http.HttpClient;
import hu.robnn.rss_analyzer.model.CandidateHolder;
import hu.robnn.rss_analyzer.model.TagWithDepth;
import hu.robnn.rss_analyzer.model.UrlHolder;
import hu.robnn.rss_analyzer.rss.RssFeedSupplier;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class DetectionService {

    private final CandidateDetector candidateDetector;
    private final ChangeDetector changeDetector;
    private final HttpClient httpClient;
    private final RssFeedSupplier rssFeedSupplier;

    @Autowired
    public DetectionService(CandidateDetector candidateDetector, ChangeDetector changeDetector, HttpClient httpClient, RssFeedSupplier rssFeedSupplier) {
        this.candidateDetector = candidateDetector;
        this.changeDetector = changeDetector;
        this.httpClient = httpClient;
        this.rssFeedSupplier = rssFeedSupplier;
    }

    public List<CandidateHolder> detectForUrlHolder(UrlHolder urlHolder){
        changeDetector.setUrlHolder(urlHolder);
        Document document = candidateDetector.parseHtml(httpClient.getWebPageAsString(urlHolder));

        Map<TagWithDepth, List<Node>> aggregate = candidateDetector.aggregate(document);

        return aggregate.entrySet().stream().map(entry -> new CandidateHolder(entry.getKey(),
                        entry.getValue().stream().map(Node::toString).collect(Collectors.toList())))
                .collect(Collectors.toList());
    }

    /**
     * Sends out all current nodes, and start detection
     */
    public void setNeededFeedAndStartChangeDetection(TagWithDepth tagWithDepth){
        rssFeedSupplier.sendMessageToRegisteredReaders(candidateDetector.getCandidates().get(tagWithDepth));
        changeDetector.setNeededFeedable(tagWithDepth);
    }

    public void changeInterval(Integer interval){
        changeDetector.setInterval(interval);
    }
}
