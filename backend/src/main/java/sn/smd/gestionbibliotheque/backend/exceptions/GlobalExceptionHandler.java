package sn.smd.gestionbibliotheque.backend.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import sn.smd.gestionbibliotheque.backend.payload.ErrorMessage;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundExceptions.class)
    public ResponseEntity<ErrorMessage> handleNotFound(NotFoundExceptions ex,
                                                       HttpServletRequest request) {

        return buildError(
                HttpStatus.NOT_FOUND,
                "NOT_FOUND",
                ex.getMessage(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler(ConflictExceptions.class)
    public ResponseEntity<ErrorMessage> handleConflict(ConflictExceptions ex,
                                                       HttpServletRequest request) {

        return buildError(
                HttpStatus.CONFLICT,
                "CONFLICT_ERROR",
                ex.getMessage(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler(BadRequestExceptions.class)
    public ResponseEntity<ErrorMessage> handleBadRequest(BadRequestExceptions ex,
                                                         HttpServletRequest request) {

        return buildError(
                HttpStatus.BAD_REQUEST,
                "BAD_REQUEST",
                ex.getMessage(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleGeneric(Exception ex,
                                                      HttpServletRequest request) {

        return buildError(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "INTERNAL_ERROR",
                ex.getMessage(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex,
                                                                HttpServletRequest request) {

        Map<String, String> errors = new HashMap<>();

        for (FieldError err : ex.getBindingResult().getFieldErrors()) {
            errors.put(err.getField(), err.getDefaultMessage());
        }

        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("error", "VALIDATION_ERROR");
        response.put("path", request.getRequestURI());
        response.put("errors", errors);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ErrorMessage> buildError(HttpStatus status,
                                                    String code,
                                                    String message,
                                                    String path) {

        ErrorMessage error = ErrorMessage.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .error(code)
                .message(message)
                .path(path)
                .build();

        return new ResponseEntity<>(error, status);
    }
}