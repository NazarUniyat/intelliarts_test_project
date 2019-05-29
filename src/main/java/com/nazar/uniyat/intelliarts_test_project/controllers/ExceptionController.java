package com.nazar.uniyat.intelliarts_test_project.controllers;

import com.nazar.uniyat.intelliarts_test_project.exceptions.ConnectionExternalApiException;
import com.nazar.uniyat.intelliarts_test_project.exceptions.QuarryingDatabaseException;
import com.nazar.uniyat.intelliarts_test_project.exceptions.WrongDateInputException;
import com.nazar.uniyat.intelliarts_test_project.exceptions.WrongInputException;
import com.nazar.uniyat.intelliarts_test_project.wires.ExceptionWire;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;


@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ExceptionWire> constViolationException(ConstraintViolationException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionWire(HttpStatus.BAD_REQUEST.toString(), e.getMessage()));
    }

    @ExceptionHandler(ConnectionExternalApiException.class)
    public ResponseEntity<ExceptionWire> connectionExternalApiException(ConnectionExternalApiException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionWire(HttpStatus.BAD_REQUEST.toString(), e.getMessage()));
    }

    @ExceptionHandler(WrongInputException.class)
    public ResponseEntity<ExceptionWire> wrongInput(WrongInputException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionWire(HttpStatus.BAD_REQUEST.toString(), e.getMessage()));
    }

    @ExceptionHandler(WrongDateInputException.class)
    public ResponseEntity<ExceptionWire> wrongDateInput(WrongDateInputException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionWire(HttpStatus.BAD_REQUEST.toString(), e.getMessage()));
    }

    @ExceptionHandler(QuarryingDatabaseException.class)
    public ResponseEntity<ExceptionWire> wrongDateInput(QuarryingDatabaseException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ExceptionWire(HttpStatus.BAD_REQUEST.toString(), e.getMessage()));
    }

}
