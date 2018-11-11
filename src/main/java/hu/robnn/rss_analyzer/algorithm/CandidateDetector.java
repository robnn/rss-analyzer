package hu.robnn.rss_analyzer.algorithm;

import hu.robnn.rss_analyzer.model.TagWithDepth;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.NodeFilter;
import org.jsoup.select.NodeTraversor;
import org.springframework.stereotype.Component;

import java.util.*;

@SuppressWarnings("WeakerAccess")
@Component
public class CandidateDetector {

    private Map<TagWithDepth, List<Node>> candidates = new TreeMap<>();

    /**
     * Aggregates the document elements by the depth in the dom, and tag
     */
    public Map<TagWithDepth, List<Node>> aggregate(Document document){
        Element body = document.select("body").get(0);
        NodeTraversor.filter(new AggregatingFilter(), body);

//        printTextNodesWithMultipleOccurrence();
        return candidates;
    }

    /**
     * Parses an html String to the jsoup Document.
     */
    public Document parseHtml(String html){
        return Jsoup.parse(html);
    }


    /**
     * Prints the candidates to the standard output.
     * Use this for testing purposes only.
     */
    @SuppressWarnings("unused")
    public void printTextNodesWithMultipleOccurrence(){
        candidates.forEach((k, v) -> {
            System.out.println(k);
            System.out.println("-----------------");
            if(v.size() > 1 && v.stream().noneMatch(node -> node.childNodes().isEmpty())){
                v.forEach(node -> System.out.println(node.childNode(0).outerHtml()));
            } else {
                v.forEach(node -> System.out.println(node.outerHtml()));
            }

            System.out.println("-----------------");
        });
    }

    class AggregatingFilter implements NodeFilter {

        @Override
        public FilterResult head(Node node, int depth) {
            if (node instanceof Element) {
                TagWithDepth key = new TagWithDepth(((Element) node).tagName(), depth);
                candidates.putIfAbsent(key, new ArrayList<>());
                candidates.get(key).add((node));
            }
            return FilterResult.CONTINUE;
        }

        @Override
        public FilterResult tail(Node node, int depth) {
            return FilterResult.CONTINUE;
        }
    }

    public Map<TagWithDepth, List<Node>> getCandidates() {
        return candidates;
    }
}
