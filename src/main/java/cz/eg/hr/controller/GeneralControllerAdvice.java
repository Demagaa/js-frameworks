package cz.eg.hr.controller;

import cz.eg.hr.rest.Errors;
import cz.eg.hr.rest.ValidationError;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@ControllerAdvice
public class GeneralControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Errors> handleValidationException(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<ValidationError> errorList = result.getFieldErrors().stream()
            .map(e -> new ValidationError(e.getField(), e.getDefaultMessage()))
            .toList();

        return ResponseEntity.badRequest().body(new Errors(errorList));
    }


    @ExceptionHandler(NoSuchElementException.class)
    public
    ResponseEntity <Map<String, String>> handleValidationException(NoSuchElementException ex) {
        String errorMessage = ex.getMessage();
        Map<String, String> errorResponse = Collections.singletonMap("error", errorMessage);
        return ResponseEntity.badRequest().body(errorResponse);
    }

}
