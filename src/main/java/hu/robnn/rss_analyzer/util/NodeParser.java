package hu.robnn.rss_analyzer.util;

import hu.robnn.rss_analyzer.model.Attribute;
import hu.robnn.rss_analyzer.model.Element;
import hu.robnn.rss_analyzer.model.Node;
import hu.robnn.rss_analyzer.model.NodeHolder;
import org.jsoup.nodes.TextNode;

import java.util.ArrayList;
import java.util.List;

public final class NodeParser {
    public static NodeHolder parse(List<org.jsoup.nodes.Node> newNodes, String url) {
        ArrayList<Node> nodeList = new ArrayList<>();

        newNodes.forEach(n -> {

            //Csak a levelek érdekelnek minket jelenleg
            if (n instanceof org.jsoup.nodes.Element && n.childNodes().stream().allMatch(child -> child instanceof TextNode || child instanceof org.jsoup.nodes.Element)) {
                org.jsoup.nodes.Element casted = (org.jsoup.nodes.Element) n;
                Node node = new Node();

                ArrayList<Element> elements = new ArrayList<>();

                Element element = new Element();
                element.setTag(casted.tagName());

                element.setContent(casted.text());

                ArrayList<Attribute> attributes = new ArrayList<>();
                casted.attributes().forEach(a -> {

                    //Itt lehet típus szerint szűrni
                    //Ha megadott típusokat csak azokat fogadjuk el, ha nem akkor bármit.

                    attributes.add(new Attribute(a.getKey(), a.getValue()));

                });
                element.setAttributes(attributes);
                elements.add(element);

                node.setElements(elements);
                nodeList.add(node);
            }
        });

        return new NodeHolder(nodeList, url);
    }

    private NodeParser() {
    }
}
