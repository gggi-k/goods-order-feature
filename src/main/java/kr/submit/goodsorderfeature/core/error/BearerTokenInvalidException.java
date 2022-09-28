package kr.submit.goodsorderfeature.core.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class BearerTokenInvalidException extends RuntimeException {
    public BearerTokenInvalidException(String message) {
        super(message);
    }

    public BearerTokenInvalidException(String message, Throwable cause) {
        super(message, cause);
    }

    public BearerTokenInvalidException(Throwable cause) {
        super(cause);
    }

    public BearerTokenInvalidException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}