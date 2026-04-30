package sn.smd.gestionbibliotheque.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundExceptions extends RuntimeException {

    private final String errorCode;

    public NotFoundExceptions(String message) {
        super(message);
        this.errorCode = "NOT_FOUND";
    }

    public NotFoundExceptions(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}