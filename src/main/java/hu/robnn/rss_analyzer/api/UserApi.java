package hu.robnn.rss_analyzer.api;

import hu.robnn.rss_analyzer.dao.model.dto.Token;
import hu.robnn.rss_analyzer.dao.model.dto.UserDTO;
import hu.robnn.rss_analyzer.exception.UserException;
import hu.robnn.rss_analyzer.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Component
@RestController("/users")
@CrossOrigin
public class UserApi {

    private final UserService userService;

    public UserApi(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(path = "users", method = RequestMethod.POST)
    public ResponseEntity<UserDTO> register(@RequestBody UserDTO user) {
        UserDTO created = userService.registerUser(user);
        if (created != null) {
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(path = "users/login", method = RequestMethod.POST)
    public ResponseEntity<Token> login(@RequestBody UserDTO user) {
        String token = userService.login(user);
        if (token != null) {
            return new ResponseEntity<>(new Token(token), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(path = "users/byToken/{token}", method = RequestMethod.GET)
    public ResponseEntity<UserDTO> getUserForToken(@PathVariable() String token){
        UserDTO userForToken = userService.getUserForToken(token);
        if(userForToken != null){
            return new ResponseEntity<>(userForToken, HttpStatus.OK);
        } else {
            throw new UserException("INVALID_TOKEN");
        }
    }

}
