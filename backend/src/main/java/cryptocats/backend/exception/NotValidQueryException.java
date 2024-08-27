package cryptocats.backend.exception;

public class NotValidQueryException extends RuntimeException {

    public NotValidQueryException(String message) {
        super(message);
    }

    public NotValidQueryException(String message, Throwable cause) {
        super(message, cause);
    }
}
