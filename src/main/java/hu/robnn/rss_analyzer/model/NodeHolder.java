package hu.robnn.rss_analyzer.model;

import java.util.List;

public class NodeHolder {
    List<String> nodes;

    public NodeHolder(List<String> nodes) {
        this.nodes = nodes;
    }

    public NodeHolder() {
    }

    public List<String> getNodes() {
        return nodes;
    }

    public void setNodes(List<String> nodes) {
        this.nodes = nodes;
    }
}
