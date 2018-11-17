package hu.robnn.rss_analyzer.model;

import lombok.Data;

import java.util.List;

@Data
public class Node {
    private List<Element> elements;

    @Override
    public String toString() {
        return "Node [" + elements.toString() + "]";
    }

}
