package itu.mbds.transversal.controller;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.io.IOException;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllExceptionMethod(Exception ex, WebRequest request) {

        ExceptionMessage exceptionMessageObj = new ExceptionMessage();

        ex.printStackTrace();

        // Handle All Field Validation Errors
        if (ex instanceof ConstraintViolationException) {
            String ms = ((ConstraintViolationException) ex).getConstraintViolations().stream()
                    .map(ConstraintViolation::getMessage)
                    .toList().get(0);
            exceptionMessageObj.setMessage(ms);
        } else if (ex instanceof IOException) {
            String ms = ex.getMessage();
            exceptionMessageObj.setMessage(ms);
        } else {
            exceptionMessageObj.setMessage(ex.getLocalizedMessage());
        }

        exceptionMessageObj.setError(ex.getClass().getCanonicalName());
        exceptionMessageObj.setPath(((ServletWebRequest) request).getRequest().getServletPath());

        // return exceptionMessageObj;
        return new ResponseEntity<>(exceptionMessageObj, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class ExceptionMessage {
    String error;
    String path;
    String message;
}