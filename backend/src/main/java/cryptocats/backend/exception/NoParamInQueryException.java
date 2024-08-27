package cryptocats.backend.exception;

public class NoParamInQueryException extends RuntimeException {

    public NoParamInQueryException(String message) {
        super(message);
    }

    public NoParamInQueryException(String message, Throwable cause) {
        super(message, cause);
    }
}
