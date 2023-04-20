package com.epam.esm.handler;

import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.handler.entity.CustomMessage;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * Controller that handle all exceptions
 *
 * @author Denis Davydov
 * @version 2.0
 */
@ControllerAdvice
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * To handle IllegalArgumentException
     *
     * @param request request
     * @return return ResponseEntity with custom message, http headers and http status
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<CustomMessage> handleAnyException(WebRequest request) {
        String data = request.toString().substring(request.toString().lastIndexOf('/') + 1, request.toString().lastIndexOf(';'));
        CustomMessage customMessage = new CustomMessage();
        customMessage.setErrorMessage("Unable find or create resource with data (" + data + ")");
        customMessage.setErrorCode(Integer.parseInt("400"));
        return new ResponseEntity<>(customMessage, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    /**
     * To handle SQLIntegrityConstraintViolationException
     * This exception handler is invoked if adding with similar name or deleting certificate or tag from db which are associated
     *
     * @return return ResponseEntity with custom message, http headers and http status
     */
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<CustomMessage> handleSQLIntegrityConstraintViolationException() {
        CustomMessage customMessage = new CustomMessage();
        customMessage.setErrorMessage("Not Acceptable due to wrong action or data for db");
        customMessage.setErrorCode(406);
        return new ResponseEntity<>(customMessage, new HttpHeaders(), HttpStatus.NOT_ACCEPTABLE);
    }

    /**
     * To handle EmptyResultDataAccessException
     *
     * @param ex Exception
     * @return return ResponseEntity with custom message, http headers and http status
     */
    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<CustomMessage> handleNullPointerException(Exception ex) {
        CustomMessage customMessage = new CustomMessage();
        customMessage.setErrorMessage(ex.getMessage());
        customMessage.setErrorCode(406);
        return new ResponseEntity<>(customMessage, new HttpHeaders(), HttpStatus.NOT_ACCEPTABLE);
    }

    /**
     * To handle custom ResourceNotFoundException
     * This exception handler is invoked if the entity is not found
     *
     * @param ex exception
     * @return return ResponseEntity with custom message, http headers and http status
     * @see ResourceNotFoundException
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<CustomMessage> handleResourceNotFoundException(Exception ex) {
        CustomMessage customMessage = new CustomMessage();
        customMessage.setErrorMessage(ex.getMessage());
        customMessage.setErrorCode(404);
        return new ResponseEntity<>(customMessage, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }
}
