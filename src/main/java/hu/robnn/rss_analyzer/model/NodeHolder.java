package hu.robnn.rss_analyzer.model;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class NodeHolder {
    private List<Node> nodes;
    private Date timeStamp;
    private String url;

    public NodeHolder(List<Node> nodes, String url) {
        this.nodes = nodes;
        this.timeStamp = new Date();
        this.url = url;
    }
}
