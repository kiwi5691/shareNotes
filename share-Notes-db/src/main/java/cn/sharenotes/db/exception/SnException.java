package cn.sharenotes.db.exception;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * Base exception of the project.
 *
 */
public abstract class SnException extends RuntimeException {

    /**
     * Error errorData.
     */
    private Object errorData;

    public SnException(String message) {
        super(message);
    }

    public SnException(String message, Throwable cause) {
        super(message, cause);
    }

    @NonNull
    public abstract HttpStatus getStatus();

    @Nullable
    public Object getErrorData() {
        return errorData;
    }

    /**
     * Sets error errorData.
     *
     * @param errorData error data
     * @return current exception.
     */
    @NonNull
    public SnException setErrorData(@Nullable Object errorData) {
        this.errorData = errorData;
        return this;
    }
}
