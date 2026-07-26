package cz.ferdo.iot_server.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DeviceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleDeviceNotFoundException(DeviceNotFoundException exception) {

        return buildErrorResponse(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(DeviceAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleDeviceAlreadyExistsException(DeviceAlreadyExistsException exception) {

        return buildErrorResponse(HttpStatus.CONFLICT, exception.getMessage());
    }

    // === Private ===

    private ResponseEntity<ErrorResponse> buildErrorResponse(HttpStatus status, String errorMessage) {
        return ResponseEntity
            .status(status)
            .body(new ErrorResponse(
                errorMessage,
                status.value(),
                LocalDateTime.now()
            ));
    }
}
