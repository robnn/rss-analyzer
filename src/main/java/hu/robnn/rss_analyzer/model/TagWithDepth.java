package hu.robnn.rss_analyzer.model;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class TagWithDepth implements Comparable<TagWithDepth>{
    private String tag;
    private int depth;

    public TagWithDepth() {
    }

    public TagWithDepth(String tag, int depth) {
        this.tag = tag;
        this.depth = depth;
    }

    public int getDepth() {
        return depth;
    }

    public String getTag() {
        return tag;
    }

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

    /**
     * Need to implement the Comparable interface, to be able to be the key of a sorted map, like TreeMap
     */
    @Override
    public int compareTo(@NotNull TagWithDepth tagWithDepth) {
        return Integer.compare(tagWithDepth.depth, this.depth);
    }
}
