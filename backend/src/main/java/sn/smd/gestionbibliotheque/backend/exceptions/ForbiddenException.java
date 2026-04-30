package sn.smd.gestionbibliotheque.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ForbiddenException extends RuntimeException {

    private final String errorCode;

    public ForbiddenException(String message) {
        super(message);
        this.errorCode = "FORBIDDEN_ACCESS";
    }

    public ForbiddenException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}