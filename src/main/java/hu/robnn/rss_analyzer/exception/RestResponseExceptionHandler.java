package hu.robnn.rss_analyzer.exception;

import hu.robnn.rss_analyzer.dao.model.dto.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Collections;

@ControllerAdvice
public class RestResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {UserException.class})
    protected ResponseEntity<Message> handleUserError(RuntimeException ex, WebRequest request){
        String message = getMessage((UserException) ex);
        Message messageObject = new Message();
        messageObject.setMessages(Collections.singletonList(message));
        return new ResponseEntity<>(messageObject, HttpStatus.BAD_REQUEST);
    }

    private String getMessage(UserException ex) {
        switch (ex.getMessage()){
            case "USED_USERNAME":
                return "Username already taken!";
            case "USED_EMAIL":
                return "Email address already taken!";
            case "INVALID_USER":
                return "Invalid username or password!";
            case "INVALID_PASSWORD":
                return "Invalid username or password!";
            case "INVALID_TOKEN":
                return "You are not logged in!";
            case "INVALID_PERMISSION":
                return "You haven't got permission to do this.";
            default:
                return "Unknown user error!";
        }
    }

}
