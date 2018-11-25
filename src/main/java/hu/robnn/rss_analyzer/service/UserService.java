package hu.robnn.rss_analyzer.service;

import hu.robnn.rss_analyzer.dao.TokenDao;
import hu.robnn.rss_analyzer.dao.UserDao;
import hu.robnn.rss_analyzer.dao.model.User;
import hu.robnn.rss_analyzer.dao.model.UserToken;
import hu.robnn.rss_analyzer.dao.model.dto.UserDTO;
import hu.robnn.rss_analyzer.enums.UserRole;
import hu.robnn.rss_analyzer.exception.UserException;
import hu.robnn.rss_analyzer.mapper.UserMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.List;

@Component
public class UserService {

    private final UserDao userDao;
    private final TokenDao tokenDao;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Autowired
    public UserService(UserDao userDao, TokenDao tokenDao,
                       PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.userDao = userDao;
        this.tokenDao = tokenDao;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    public UserDTO registerUser(UserDTO userDTO) {
        if (!userDao.findByUserName(userDTO.getUserName()).isEmpty()) {
            throw new UserException("USED_USERNAME");
        } else if (!userDao.findByEmailAddress(userDTO.getEmailAddress()).isEmpty()) {
            throw new UserException("USED_EMAIL");
        } else {
            User user = new User();
            user.setRealName(userDTO.getRealName());
            user.setEmailAddress(userDTO.getEmailAddress());
            user.setRole(UserRole.USER.name());
            user.setUserName(userDTO.getUserName());
            user.setPasswordHash(passwordEncoder.encode(userDTO.getPassword()));
            return userMapper.map(userDao.save(user));
        }
    }

    public String login(UserDTO userDTO) {
        List<User> users = userDao.findByUserName(userDTO.getUserName());
        if (users.isEmpty()) {
            throw new UserException("INVALID_USER");
        }
        List<UserToken> loggedInUserTokens = tokenDao.findByUserOrderByValidToDesc(users.get(0));
        if (!loggedInUserTokens.isEmpty() && isTokenValid(loggedInUserTokens.get(0))) {
            renewToken(loggedInUserTokens.get(0));
            return loggedInUserTokens.get(0).getToken();
        } else if (!loggedInUserTokens.isEmpty()) {
            tokenDao.delete(loggedInUserTokens.get(0));
        }
        User user = users.get(0);
        if (user.getPasswordHash() != null && passwordEncoder
                .matches(userDTO.getPassword(), user.getPasswordHash())) {
            UserToken userToken = createUserToken(user);
            tokenDao.save(userToken);
            return userToken.getToken();
        } else {
            throw new UserException("INVALID_PASSWORD");
        }
    }

    private UserToken createUserToken(User user) {
        UserToken userToken = new UserToken();
        userToken.setToken(RandomStringUtils.randomAlphabetic(32));
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 30);
        LocalDateTime localDate = LocalDateTime
                .ofInstant(calendar.toInstant(), ZoneId.systemDefault());
        userToken.setValidTo(localDate);
        userToken.setUser(user);
        return userToken;
    }

    public void authenticate(String token, UserRole requiredRole) {
        UserToken userToken = tokenDao.findByToken(token);
        if (userToken == null || (userToken.getValidTo() != null && !isTokenValid(userToken))) {
            if(userToken != null) {
                tokenDao.delete(userToken);
            }
            throw new UserException("INVALID_TOKEN");
        } else {
            renewToken(userToken);
            if (userToken.getUser() != null) {
                UserRole userRole = UserRole.valueOf(userToken.getUser().getRole());
                if (userRole.getPermissionLevel() < requiredRole.getPermissionLevel()) {
                    throw new UserException("INVALID_PERMISSION");
                }
            }
        }
    }

    private void renewToken(UserToken userToken) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 30);
        userToken
                .setValidTo(LocalDateTime.ofInstant(calendar.toInstant(), ZoneId.systemDefault()));
        tokenDao.save(userToken);
    }

    private boolean isTokenValid(UserToken userToken) {
        if (userToken != null && userToken.getValidTo() != null) {
            return LocalDateTime.now().isBefore(userToken.getValidTo());
        }
        return false;
    }

    public UserDTO getUserForToken(String token) {
        if (token != null) {
            UserToken tokenDMO = tokenDao.findByToken(token);
            if (tokenDMO != null) {
                User source = tokenDMO.getUser();
                if (source != null) {
                    return userMapper.map(source);
                }
            }
        }
        return null;
    }
}
