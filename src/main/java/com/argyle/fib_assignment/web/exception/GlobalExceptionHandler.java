package com.argyle.fib_assignment.web.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.CompletionException;
import java.util.concurrent.TimeoutException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.REQUEST_TIMEOUT;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception,
                                                                            HttpServletRequest request) {
        log.error("Error processing request {} {}", request.getMethod(), request.getRequestURI(), exception);

        String message = exception.getName() + " should be of type " + exception.getRequiredType().getName();
        return buildFailureResponse(BAD_REQUEST, request, message);
    }

    @ExceptionHandler({ConstraintViolationException.class, CompletionException.class})
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException exception,
                                                                     HttpServletRequest request) {
        log.error("Error processing request {} {}", request.getMethod(), request.getRequestURI(), exception);

        return buildFailureResponse(BAD_REQUEST, request, exception.getMessage());
    }

    @ExceptionHandler(TimeoutException.class)
    public ResponseEntity<Object> handleTimeoutException(TimeoutException exception,
                                                         HttpServletRequest request) {
        log.error("Error processing request {} {}", request.getMethod(), request.getRequestURI(), exception);

        return buildFailureResponse(REQUEST_TIMEOUT, request, "Request Timeout. Please try with a smaller value!");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception exception,
                                                         HttpServletRequest request) {
        log.error("Error processing request {} {}", request.getMethod(), request.getRequestURI(), exception);

        return buildFailureResponse(INTERNAL_SERVER_ERROR, request, "Something went wrong. Please retry later!");
    }

    private ResponseEntity<Object> buildFailureResponse(HttpStatus status,
                                                        HttpServletRequest request,
                                                        String message) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("status", status.value());
        body.put("message", message);
        body.put("path", request.getRequestURI());
        return new ResponseEntity<>(body, status);
    }
}
