package hu.robnn.rss_analyzer.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class AttributesHolder {
    private List<String> attributes;
}
