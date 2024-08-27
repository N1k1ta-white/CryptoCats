package cryptocats.backend.exception;

public class ExpiredAuthDateException extends RuntimeException {

    public ExpiredAuthDateException(String message) {
        super(message);
    }

    public ExpiredAuthDateException(String message, Throwable cause) {
        super(message, cause);
    }
}
