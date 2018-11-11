package hu.robnn.rss_analyzer.model;

import org.jsoup.nodes.Node;

import java.util.ArrayList;
import java.util.List;

public class CandidateHolder {
    private TagWithDepth tagWithDepth;
    private List<String> candidates = new ArrayList<>();

    public CandidateHolder(TagWithDepth tagWithDepth, List<String> candidates) {
        this.tagWithDepth = tagWithDepth;
        this.candidates = candidates;
    }

    public CandidateHolder() {
    }

    public TagWithDepth getTagWithDepth() {
        return tagWithDepth;
    }

    public void setTagWithDepth(TagWithDepth tagWithDepth) {
        this.tagWithDepth = tagWithDepth;
    }

    public List<String> getCandidates() {
        return candidates;
    }

    public void setCandidates(List<String> candidates) {
        this.candidates = candidates;
    }
}
