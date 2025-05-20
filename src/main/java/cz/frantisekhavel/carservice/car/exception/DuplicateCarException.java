package cz.frantisekhavel.carservice.car.exception;

import java.net.URI;

import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponseException;

public class DuplicateCarException extends ErrorResponseException {

    public DuplicateCarException(final Throwable cause, final String carName) {
        super(HttpStatus.CONFLICT, cause);
        setType(URI.create("https://datatracker.ietf.org/doc/html/rfc7231#section-6.5.8"));
        setDetail("Duplicate car " + carName);
    }
}
