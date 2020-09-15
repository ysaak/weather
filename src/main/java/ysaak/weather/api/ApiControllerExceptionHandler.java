package ysaak.weather.api;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ysaak.common.exception.FunctionalException;

@ControllerAdvice
public class ApiControllerExceptionHandler {

    @ExceptionHandler(FunctionalException.class)
    public ResponseEntity<ApiError> handleFunctionalException(FunctionalException ex, WebRequest webRequest) {
        ApiError error = new ApiError(
                ex.getError().getCode(),
                ex.getMessage()
        );

        return new ResponseEntity<>(error, ex.getError().getStatus());
    }

    public class ApiError {
        private final String code;
        private final String message;

        private ApiError(String code, String message) {
            this.code = code;
            this.message = message;
        }
    }
}
