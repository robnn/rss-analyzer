package hu.robnn.rss_analyzer.algorithm;

import hu.robnn.rss_analyzer.dao.HtmlRepository;
import hu.robnn.rss_analyzer.dao.model.HtmlStringEntity;
import hu.robnn.rss_analyzer.http.HttpClient;
import hu.robnn.rss_analyzer.model.TagWithDepth;
import hu.robnn.rss_analyzer.model.UrlHolder;
import hu.robnn.rss_analyzer.rss.RssFeedSupplier;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;


/**
 * This component need to be a singleton, because once set, the configuration parameters must not change
 */
@Component
@Scope("singleton")
public class ChangeDetector{

    private final CandidateDetector candidateDetector;
    private final HtmlRepository htmlRepository;
    private final HttpClient httpClient;
    private final RssFeedSupplier rssFeedSupplier;

    private TagWithDepth neededFeedable;
    private Integer interval;
    private UrlHolder urlHolder;
    private Integer counter = 0;

    public ChangeDetector(CandidateDetector candidateDetector, HtmlRepository htmlRepository, HttpClient httpClient, RssFeedSupplier rssFeedSupplier) {
        this.candidateDetector = candidateDetector;
        this.htmlRepository = htmlRepository;
        this.httpClient = httpClient;
        this.rssFeedSupplier = rssFeedSupplier;
    }

    public void setNeededFeedable(TagWithDepth neededFeedable) {
        this.neededFeedable = neededFeedable;
    }

    public void setInterval(Integer interval) {
        this.interval = interval;
    }

    public void setUrlHolder(UrlHolder urlHolder) {
        this.urlHolder = urlHolder;
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
        //TODO db használatot kitalálni, addig csak 1 elem lesz mindig felülcsapva
        String oldVersionOfWebPage = htmlRepository.findAll().get(0).getHtmlText();
        Document previousPage = candidateDetector.parseHtml(oldVersionOfWebPage);

        String newVersionOfWebPage = httpClient.getWebPageAsString(urlHolder);
        Document newPage = candidateDetector.parseHtml(newVersionOfWebPage);

        List<Node> nodes = detectChangedNodes(previousPage, newPage);
        rssFeedSupplier.sendMessageToRegisteredReaders(nodes);

        //TODO jelenleg töröljük a db-t és belementjük az új stringet, ezt nem így kéne
        htmlRepository.deleteAll();
        HtmlStringEntity newEntity = new HtmlStringEntity();
        newEntity.setHtmlText(newVersionOfWebPage);
        htmlRepository.save(newEntity);
    }

    private List<Node> detectChangedNodes(Document previousPage, Document newPage){
        Map<TagWithDepth, List<Node>> previousAggregate = candidateDetector.aggregate(previousPage);
        Map<TagWithDepth, List<Node>> newAggregate = candidateDetector.aggregate(newPage);

        List<Node> oldElements = previousAggregate.get(neededFeedable);
        List<Node> newElements = newAggregate.get(neededFeedable);

        newElements.removeAll(oldElements);
        return newElements;
    }



}
