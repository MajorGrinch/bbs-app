package xyz.majorkevin.bbs.rest.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import xyz.majorkevin.bbs.entity.Reply;
import xyz.majorkevin.bbs.rest.entity.GeneralErrorResponse;
import xyz.majorkevin.bbs.rest.exception.CommentNotFoundException;
import xyz.majorkevin.bbs.rest.exception.ReplyFormException;
import xyz.majorkevin.bbs.rest.exception.UserNotFoundException;
import xyz.majorkevin.bbs.rest.exception.VoteException;

@ControllerAdvice
public class BBSExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<GeneralErrorResponse> handleException(UserNotFoundException exception) {
        GeneralErrorResponse error = new GeneralErrorResponse(HttpStatus.FORBIDDEN.value(), exception.getMessage(), System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler
    public ResponseEntity<GeneralErrorResponse> handleException(CommentNotFoundException exception){
        GeneralErrorResponse error = new GeneralErrorResponse(HttpStatus.NOT_FOUND.value(), exception.getMessage(), System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<GeneralErrorResponse> handleException(VoteException exception){
        GeneralErrorResponse errorResponse = new GeneralErrorResponse(HttpStatus.NOT_FOUND.value(), exception.getMessage(), System.currentTimeMillis());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<GeneralErrorResponse> handleException(ReplyFormException exception){
        GeneralErrorResponse errorResponse = new GeneralErrorResponse(HttpStatus.NOT_FOUND.value(), exception.getMessage(), System.currentTimeMillis());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
