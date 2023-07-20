package com.example.employe.management.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(UserFoundException.class)
    public ResponseEntity<String> handleUserFoundException(UserFoundException ex) {
        // Log the exception or perform any additional handling if needed

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ex.getMessage());
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.toList());

        return ResponseEntity.badRequest().body(errors);}
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleJsonParsingException(HttpMessageNotReadableException ex) {
        String errorMessage = "Invalid JSON format";
        return ResponseEntity.badRequest().body(errorMessage);
    }
    @ExceptionHandler(MultipartException.class)
   public ResponseEntity<String> handleMultipartException(MultipartException ex) {
       String errorMessage = "Invalid multipart request: " + ex.getMessage();
       return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);}

}