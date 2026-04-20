package sn.smd.gestionbibliotheque.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class DataIntegrityException extends RuntimeException {

    private final String errorCode;

    public DataIntegrityException(String message) {
        super(message);
        this.errorCode = "DATA_INTEGRITY_ERROR";
    }

    public DataIntegrityException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}