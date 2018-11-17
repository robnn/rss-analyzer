package hu.robnn.rss_analyzer.model;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class NodeHolder {
    private List<Node> nodes;
    private Date timeStamp;

    public NodeHolder(List<Node> nodes) {
        this.nodes = nodes;
        this.timeStamp = new Date();
    }
}
