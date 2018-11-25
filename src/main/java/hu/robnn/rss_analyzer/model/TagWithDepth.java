package hu.robnn.rss_analyzer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagWithDepth {
    private String tag;
    private int depth;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TagWithDepth that = (TagWithDepth) o;
        return depth == that.depth &&
                Objects.equals(tag, that.tag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tag, depth);
    }

    @Override
    public String toString() {
        return "TagWithDepth{" +
                "tag='" + tag + '\'' +
                ", depth=" + depth +
                '}';
    }
}
