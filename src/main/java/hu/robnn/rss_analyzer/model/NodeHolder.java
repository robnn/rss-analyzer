package hu.robnn.rss_analyzer.model;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class NodeHolder {
    private List<String> nodes;
    private Date timeStamp;

    public NodeHolder(List<String> nodes) {
        this.nodes = nodes;
        this.timeStamp = new Date();
    }
}
