package cryptocats.backend.exception;

public class NoAuthCookieException extends RuntimeException {

    public NoAuthCookieException(String message) {
        super(message);
    }

    public NoAuthCookieException(String message, Throwable cause) {
        super(message, cause);
    }
}
