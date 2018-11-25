package hu.robnn.rss_analyzer.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Attribute {
    private String name;
    private String value;

    @Override
    public String toString() {
        return name + "=" + value;
    }
}
