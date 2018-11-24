package hu.robnn.rss_analyzer.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class RssStringHolder {
    private List<String> nodes;
    private Date timeStamp;
    private String rssString;
}
