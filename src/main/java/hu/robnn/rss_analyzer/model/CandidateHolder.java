package hu.robnn.rss_analyzer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.jsoup.nodes.Node;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class CandidateHolder {
    private TagWithDepth tagWithDepth;
    private List<String> candidates = new ArrayList<>();
}
