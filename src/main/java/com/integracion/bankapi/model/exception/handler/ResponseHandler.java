package com.integracion.bankapi.model.exception.handler;

import com.integracion.bankapi.model.exception.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class ResponseHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ InvalidAccountType.class, AccountLimitSurpassedException.class, PaymentExpireException.class})
    public ResponseEntity<Object> handleInvalidAccountTypeException(final Exception e) {
        return new ResponseEntity<>(new ProblemDetail(e.getMessage()),null, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ ClientNotFoundException.class,ProviderNotFoundException.class, PaymentNotFoundException.class })
    public ResponseEntity<Object> handleClientNotFoundException(final Exception e) {
        return new ResponseEntity<>(new ProblemDetail(e.getMessage()),null, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return this.handleExceptionInternal(ex, (Object)null, headers, status, request);
    }

}