package hu.robnn.rss_analyzer.model;

import java.util.Date;
import java.util.List;

public class NodeHolder {
    private List<String> nodes;
    private Date timeStamp;

    public NodeHolder(List<String> nodes) {
        this.nodes = nodes;
        this.timeStamp = new Date();
    }

    public NodeHolder() {
        this.timeStamp = new Date();
    }

    public List<String> getNodes() {
        return nodes;
    }

    public void setNodes(List<String> nodes) {
        this.nodes = nodes;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }
}
