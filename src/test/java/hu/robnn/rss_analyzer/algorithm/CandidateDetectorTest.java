package hu.robnn.rss_analyzer.algorithm;

import hu.robnn.rss_analyzer.model.TagWithDepth;
import org.apache.commons.io.IOUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class CandidateDetectorTest {

    /**
     * Tests the parser method, with a large page.
     */
    @Test
    public void parseHtmlTest() {
        CandidateDetector candidateDetector = new CandidateDetector();
        try {
            String testPage = readTestPage("index.html");
            Document document = candidateDetector.parseHtml(testPage);
            assertNotNull(document);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void detectHtmlTest() {
        CandidateDetector candidateDetector = new CandidateDetector();
        try {
            String testPage = readTestPage("index.html");
            Document document = candidateDetector.parseHtml(testPage);
            Map<TagWithDepth, List<Node>> aggregate = candidateDetector.aggregate(document);
            assertEquals(127, aggregate.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads a resource file for testing as String.
     * Need to go up 4 times in folder hierarchy because the package is expected
     */
    @SuppressWarnings("SameParameterValue")
    private String readTestPage(String sourcePath) throws IOException {
        return IOUtils.toString(this.getClass().getResourceAsStream("../../../../"+sourcePath), Charset.defaultCharset());
    }

}