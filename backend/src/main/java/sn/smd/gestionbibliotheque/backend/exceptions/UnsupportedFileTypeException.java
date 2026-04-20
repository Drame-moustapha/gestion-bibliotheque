package sn.smd.gestionbibliotheque.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
public class UnsupportedFileTypeException extends RuntimeException {

    private final String errorCode;

    public UnsupportedFileTypeException(String message) {
        super(message);
        this.errorCode = "UNSUPPORTED_FILE_TYPE";
    }

    public UnsupportedFileTypeException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}