package cn.sharenotes.db.exception;

import org.springframework.http.HttpStatus;

/**
 * BeanUtils exception.
 *
 */
public class BeanUtilsException extends SnException {

    public BeanUtilsException(String message) {
        super(message);
    }

    public BeanUtilsException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
