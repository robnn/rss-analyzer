package hu.robnn.rss_analyzer.model;

public class UrlHolder {
    private String pageUrl;

    public UrlHolder(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    public UrlHolder() {
    }

    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }
}
