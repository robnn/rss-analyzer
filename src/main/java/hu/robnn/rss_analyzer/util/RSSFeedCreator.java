package hu.robnn.rss_analyzer.util;

import hu.robnn.rss_analyzer.model.NodeHolder;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.*;
import java.io.*;

public final class RSSFeedCreator {
    public static String create(NodeHolder nodeHolder) {
        try {
            //String outputFile = "output.txt";
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            // create a XMLOutputFactory
            XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();

            // create XMLEventWriter
            //XMLEventWriter eventWriter = outputFactory.createXMLEventWriter(new FileOutputStream(outputFile));
            XMLEventWriter eventWriter = outputFactory.createXMLEventWriter(byteArrayOutputStream);

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


            nodeHolder.getNodes().forEach(node -> node.getElements().forEach(e -> {
                createAttribute(eventWriter, "tag", e.getTag());
                createAttribute(eventWriter, "content", e.getContent());
                e.getAttributes().forEach(a -> {
                    try {
                        eventWriter.add(eventFactory.createStartElement("", "", "item"));
                        eventWriter.add(end);

                        createAttribute(eventWriter, a.getName(), a.getValue());
                        eventWriter.add(end);

                        eventWriter.add(eventFactory.createEndElement("", "", "item"));
                        eventWriter.add(end);
                    } catch (XMLStreamException ex) {
                        System.err.println("Error while converting attributes to XML.");
                    }

                });
            }));

            eventWriter.add(end);
            eventWriter.add(eventFactory.createEndElement("", "", "channel"));
            eventWriter.add(end);
            eventWriter.add(eventFactory.createEndElement("", "", "rss"));

            eventWriter.add(end);

            eventWriter.add(eventFactory.createEndDocument());

            eventWriter.close();

            return byteArrayOutputStream.toString();
        } catch (Exception e) {
            System.err.println("Error while processing NodeHolder.");
            return null;
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
