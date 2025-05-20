package cz.frantisekhavel.carservice.config.advice;

import cz.frantisekhavel.carservice.car.exception.DuplicateCarException;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(DuplicateCarException.class)
    public ProblemDetail handleDuplicateCarException(final ErrorResponse e) {
        return e.getBody();
    }
}
