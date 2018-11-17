package hu.robnn.rss_analyzer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Element {
    private String tag;
    private String content;
    List<Attribute> attributes;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("tag=" + tag + ", content=" + content + "attributes=[");
        attributes.forEach(a -> sb.append(a.toString() + ", "));

        //Levágjuk a fölösleges ", "-t a végéről.
        sb.replace(sb.length() - 2, sb.length(), "");

        sb.append("]");
        return sb.toString();
    }
}
