package xyz.majorkevin.bbs.rest.exception;

public class ReplyFormException extends RuntimeException{

    public ReplyFormException() {
    }

    public ReplyFormException(String message) {
        super(message);
    }

    public ReplyFormException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReplyFormException(Throwable cause) {
        super(cause);
    }

    public ReplyFormException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
