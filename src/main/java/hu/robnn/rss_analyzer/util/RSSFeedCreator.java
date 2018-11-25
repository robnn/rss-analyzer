package hu.robnn.rss_analyzer.util;

import hu.robnn.rss_analyzer.model.Attribute;
import hu.robnn.rss_analyzer.model.NodeHolder;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.*;
import java.io.FileOutputStream;

public final class RSSFeedCreator {
    public static void create(NodeHolder nodeHolder) {
        try {
            String outputFile = "output.xml";
            FileOutputStream fileOutputStream = new FileOutputStream(outputFile);

            // create a XMLOutputFactory
            XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();

            // create XMLEventWriter
            XMLEventWriter eventWriter = outputFactory.createXMLEventWriter(new FileOutputStream(outputFile));

            // create a EventFactory
            XMLEventFactory eventFactory = XMLEventFactory.newInstance();
            XMLEvent end = eventFactory.createDTD("\n");

            // create and write Start Tag
            StartDocument startDocument = eventFactory.createStartDocument();

            eventWriter.add(startDocument);

            // create open tag
            eventWriter.add(end);

            StartElement rssStart = eventFactory.createStartElement("", "", "rss");
            eventWriter.add(rssStart);

            eventWriter.add(eventFactory.createAttribute("version", "2.0"));
            eventWriter.add(end);

            eventWriter.add(eventFactory.createStartElement("", "", "channel"));
            eventWriter.add(end);
            createAttribute(eventWriter, "title", "rss-analyzer");
            createAttribute(eventWriter, "link", nodeHolder.getUrl());
            createAttribute(eventWriter, "description", "This RSS feed is automatically generated from analyzing the website.");


            nodeHolder.getNodes().forEach(node -> node.getElements().forEach(e -> {
                try {
                    eventWriter.add(eventFactory.createStartElement("", "", "item"));
                    eventWriter.add(end);
                } catch (XMLStreamException e1) {
                    e1.printStackTrace();
                }

                createAttribute(eventWriter, "title", e.getContent());
                createAttribute(eventWriter, "description", e.getContent());

                String link = "";

                for (Attribute a : e.getAttributes()) {
                    if (a.getName().equals("href") && a.getValue().startsWith("http")) {
                        link = a.getValue();
                    }
                }

                if (!link.isEmpty()) {
                    createAttribute(eventWriter, "link", link);
                }

                try {
                    eventWriter.add(eventFactory.createEndElement("", "", "item"));
                    eventWriter.add(end);
                } catch (XMLStreamException ne) {
                    ne.printStackTrace();
                }
            }));

            eventWriter.add(end);
            eventWriter.add(eventFactory.createEndElement("", "", "channel"));
            eventWriter.add(end);
            eventWriter.add(eventFactory.createEndElement("", "", "rss"));

            eventWriter.add(end);

            eventWriter.add(eventFactory.createEndDocument());

            eventWriter.close();

            fileOutputStream.flush();
            fileOutputStream.close();
           /* System.out.println(byteArrayOutputStream.toString());
            return byteArrayOutputStream.toString();*/
        } catch (Exception e) {
            System.err.println("Error while processing NodeHolder.");
        }

    }

    private static void createAttribute(XMLEventWriter eventWriter, String name, String value) {
        try {
            XMLEventFactory eventFactory = XMLEventFactory.newInstance();
            XMLEvent end = eventFactory.createDTD("\n");
            XMLEvent tab = eventFactory.createDTD("\t");
            // create Start node
            StartElement sElement = eventFactory.createStartElement("", "", name);
            eventWriter.add(tab);
            eventWriter.add(sElement);

            // create Content
            Characters characters = eventFactory.createCharacters(value);
            eventWriter.add(characters);

            // create End node
            EndElement eElement = eventFactory.createEndElement("", "", name);
            eventWriter.add(eElement);
            eventWriter.add(end);
        } catch (Exception e) {
            System.err.println("Failed to create attribute:" + name + "=" + value);
        }

    }

    private RSSFeedCreator() {
    }
}
