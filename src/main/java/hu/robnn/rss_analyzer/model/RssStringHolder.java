package hu.robnn.rss_analyzer.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class RssStringHolder {
    private Date timeStamp;
    private String rssString;
}
