package cz.frantisekhavel.carservice.car.exception;

import java.net.URI;

import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponseException;

public class CarNotFoundException extends ErrorResponseException {

    public CarNotFoundException() {
        super(HttpStatus.NOT_FOUND);
        setType(URI.create("https://datatracker.ietf.org/doc/html/rfc7231#section-6.5.4"));
        setDetail("No car found");
    }
}
