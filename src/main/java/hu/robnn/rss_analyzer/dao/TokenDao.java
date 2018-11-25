package hu.robnn.rss_analyzer.dao;

import hu.robnn.rss_analyzer.dao.model.User;
import hu.robnn.rss_analyzer.dao.model.UserToken;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TokenDao extends CrudRepository<UserToken, Long> {
    UserToken findByUuid(String uuid);
    List<UserToken> findByUserOrderByValidToDesc(User user);
    UserToken findByToken(String token);
}
