package com.plim.plimserver.global.error;

import com.plim.plimserver.domain.user.exception.EmailDuplicateException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Map<String, String>>> handleValidationExceptions(MethodArgumentNotValidException ex){
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors()
                .forEach(c -> errors.put(((FieldError) c).getField(), c.getDefaultMessage()));
        Map<String, Map<String, String>> responseError = new HashMap<>();
        responseError.put("error-message", errors);
        return ResponseEntity.badRequest().body(responseError);
    }

    @ExceptionHandler(EmailDuplicateException.class)
    public ResponseEntity<Map<String, String>> handleEmailDuplicateException(EmailDuplicateException e) {
        Map<String, String> error = new HashMap<>();
        error.put("error-message", e.getMessage());
        return ResponseEntity.badRequest().body(error);
    }

}
