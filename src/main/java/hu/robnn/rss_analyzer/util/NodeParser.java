package hu.robnn.rss_analyzer.util;

import hu.robnn.rss_analyzer.model.Attribute;
import hu.robnn.rss_analyzer.model.Element;
import hu.robnn.rss_analyzer.model.Node;
import hu.robnn.rss_analyzer.model.NodeHolder;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public final class NodeParser {
    public static NodeHolder parse(List<org.jsoup.nodes.Node> newNodes, List<String> desiredAttributes) {
        ArrayList<Node> nodeList = new ArrayList<>();

        newNodes.forEach(n -> {

            //Csak a levelek érdekelnek minket jelenleg
            if (n.childNodes().stream().allMatch(child -> child instanceof TextNode)) {

                Node node = new Node();

                Document document = n.ownerDocument();
                org.jsoup.select.Elements elementsFromDoc = document.select(n.nodeName());

                ArrayList<Element> elements = new ArrayList<>();

                elementsFromDoc.forEach(e -> {
                    Element element = new Element();
                    element.setTag(e.tagName());
                    System.out.println("TagName: " + e.tagName());

                    element.setContent(e.text());
                    System.out.println("TagValue: " + e.text());

                    ArrayList<Attribute> attributes = new ArrayList<>();
                    e.attributes().forEach(a -> {

                        //Itt lehet típus szerint szűrni
                        //Ha megadott típusokat csak azokat fogadjuk el, ha nem akkor bármit.
                        if (desiredAttributes == null || desiredAttributes .isEmpty() || desiredAttributes .contains(a.getKey())) {
                            attributes.add(new Attribute(a.getKey(), a.getValue()));
                            System.out.println("\tAttribute: " + a.toString());
                        }

                    });
                    element.setAttributes(attributes);
                    elements.add(element);

                });

                node.setElements(elements);
                nodeList.add(node);
            }
        });

        return new NodeHolder(nodeList);
    }

    private NodeParser() {
    }
}
