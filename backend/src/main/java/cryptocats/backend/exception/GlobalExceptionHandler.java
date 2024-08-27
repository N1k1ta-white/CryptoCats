package cryptocats.backend.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @Data
    @AllArgsConstructor
    public static class ErrorResponse {
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss dd.MM.yyyy")
        private LocalDateTime timestamp;
        private String error;
        private String message;
    }

    @ExceptionHandler({ExpiredAuthDateException.class})
    public ResponseEntity<ErrorResponse> handleExpiredAuthDateException(ExpiredAuthDateException ex) {
        return new ResponseEntity<>(new ErrorResponse(LocalDateTime.now(), ex.getClass().getSimpleName(),
                ex.getMessage()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({NoParamInQueryException.class})
    public ResponseEntity<ErrorResponse> handleNoParamInQueryException(NoParamInQueryException ex) {
        return new ResponseEntity<>(new ErrorResponse(LocalDateTime.now(), ex.getClass().getSimpleName(),
                ex.getMessage()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({NotValidQueryException.class})
    public ResponseEntity<ErrorResponse> handleNotValidQueryException(NotValidQueryException ex) {
        return new ResponseEntity<>(new ErrorResponse(LocalDateTime.now(), ex.getClass().getSimpleName(),
                ex.getMessage()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({UserAlreadyExistsException.class})
    public ResponseEntity<ErrorResponse> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        return new ResponseEntity<>(new ErrorResponse(LocalDateTime.now(), ex.getClass().getSimpleName(),
                ex.getMessage()), HttpStatus.CONFLICT);
    }
}
