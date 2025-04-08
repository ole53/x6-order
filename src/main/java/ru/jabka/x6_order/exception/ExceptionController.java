package ru.jabka.x6_order.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.jabka.x6_order.model.ApiError;

public class ExceptionController {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiError> handleBadRequestException(BadRequestException e) {
        return ResponseEntity.badRequest()
                .body(new ApiError(e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleException(Exception e) {
        return ResponseEntity.internalServerError()
                .body(new ApiError(e.getMessage()));
    }
}