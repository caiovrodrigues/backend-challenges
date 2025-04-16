package com.challenge.itau.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail validationError(MethodArgumentNotValidException e) {
        var pb = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);

        pb.setTitle("Your request parameters didn't validate.");

        var errors = e.getFieldErrors().stream().map(InvalidParam::new).toList();

        pb.setProperties(Map.of("invalid-params", errors));

        return pb;
    }

    @ExceptionHandler(ItauException.class)
    public ProblemDetail polymorphismError(ItauException e) {
        return e.toProblemDetail();
    }

    private record InvalidParam(String name, String reason) {
        public InvalidParam(FieldError field) {
            this(field.getField(), field.getDefaultMessage());
        }
    }
}
