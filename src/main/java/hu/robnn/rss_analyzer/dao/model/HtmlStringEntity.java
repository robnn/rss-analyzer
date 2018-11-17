package hu.robnn.rss_analyzer.dao.model;

import lombok.Data;

import javax.persistence.*;
@Data
@Entity
@Table(name = "rss_html_string")
public class HtmlStringEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rss_html_seq")
    @SequenceGenerator(name = "rss_html_seq", sequenceName = "rss_html_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "htmlText")
    private String htmlText;
}
