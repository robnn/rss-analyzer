package hu.robnn.rss_analyzer.dao.model;

import javax.persistence.*;

@Entity
@Table(name = "rss_html_string")
public class HtmlStringEntity {

    private Long id;

    private String htmlText;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rss_html_seq")
    @SequenceGenerator(name = "rss_html_seq", sequenceName = "rss_html_seq", allocationSize = 1)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "htmlText")
    public String getHtmlText() {
        return htmlText;
    }

    public void setHtmlText(String htmlText) {
        this.htmlText = htmlText;
    }
}
