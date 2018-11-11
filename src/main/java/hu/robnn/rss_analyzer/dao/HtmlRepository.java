package hu.robnn.rss_analyzer.dao;

import hu.robnn.rss_analyzer.dao.model.HtmlStringEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HtmlRepository extends JpaRepository<HtmlStringEntity, Long> {
}
