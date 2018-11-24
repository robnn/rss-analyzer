package hu.robnn.rss_analyzer.algorithm;

import hu.robnn.rss_analyzer.dao.HtmlRepository;
import hu.robnn.rss_analyzer.dao.model.HtmlStringEntity;
import hu.robnn.rss_analyzer.http.HttpClient;
import hu.robnn.rss_analyzer.model.TagWithDepth;
import hu.robnn.rss_analyzer.model.UrlHolder;
import hu.robnn.rss_analyzer.rss.RssFeedSupplier;
import lombok.Setter;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;


/**
 * This component need to be a singleton, because once set, the configuration parameters must not change
 */
@Component
@Scope("singleton")
public class ChangeDetector{

    private static final Logger LOGGER = Logger.getLogger("ChangeDetector");
    private final CandidateDetector candidateDetector;
    private final HtmlRepository htmlRepository;
    private final HttpClient httpClient;
    private final RssFeedSupplier rssFeedSupplier;

    @Setter
    private TagWithDepth neededFeedable;

    @Setter
    private Integer interval = 5;

    @Setter
    private UrlHolder urlHolder;

    private Integer counter = 0;

    @Setter
    private Long previousId = null;

    public ChangeDetector(CandidateDetector candidateDetector, HtmlRepository htmlRepository, HttpClient httpClient, RssFeedSupplier rssFeedSupplier) {
        this.candidateDetector = candidateDetector;
        this.htmlRepository = htmlRepository;
        this.httpClient = httpClient;
        this.rssFeedSupplier = rssFeedSupplier;
    }


    /**
     * Scheduled for every seconds, so the interval must be set in seconds.
     */
    @Scheduled(fixedRate = 1000)
    public void detectAndSendChanges(){
        if(urlHolder == null || neededFeedable == null)
            return;
        if(counter < interval){
            counter++;
            return;
        } else {
            counter = 0;
        }

        String newVersionOfWebPage = httpClient.getWebPageAsString(urlHolder);
        if (previousId != null) {
            String oldVersionOfWebPage = htmlRepository.findById(previousId).orElseThrow(() ->
                    new IllegalStateException("Cannot find entity for id, " + previousId)).getHtmlText();

            Document previousPage = candidateDetector.parseHtml(oldVersionOfWebPage);
            Document newPage = candidateDetector.parseHtml(newVersionOfWebPage);

            List<Node> nodes = detectChangedNodes(previousPage, newPage);
            if(!nodes.isEmpty()) {
                rssFeedSupplier.sendMessageToRegisteredReaders(nodes);
            }

        }
        HtmlStringEntity newEntity = new HtmlStringEntity();
        newEntity.setHtmlText(newVersionOfWebPage);
        HtmlStringEntity saved = htmlRepository.save(newEntity);
        previousId = saved.getId();
    }

    private List<Node> detectChangedNodes(Document previousPage, Document newPage){
        Map<TagWithDepth, List<Node>> previousAggregate = candidateDetector.aggregate(previousPage);
        Map<TagWithDepth, List<Node>> newAggregate = candidateDetector.aggregate(newPage);

        List<Node> oldElements = previousAggregate.get(neededFeedable);
        List<Node> newElements = newAggregate.get(neededFeedable);

        List<Node> newNodes = new ArrayList<>();
        newElements.forEach(node -> {
            if (oldElements.stream().noneMatch(prevNode ->((Element)prevNode).html().equals(((Element)node).html()))) {
                newNodes.add(node);
            }
        });

        if (!newNodes.isEmpty()) {
            LOGGER.info("New nodes : " + newNodes.stream().map(Node::outerHtml).collect(Collectors.toList()));
        }

        return newNodes;
    }



}
