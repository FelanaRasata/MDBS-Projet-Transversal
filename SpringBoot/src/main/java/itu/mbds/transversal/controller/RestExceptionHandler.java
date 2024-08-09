package itu.mbds.transversal.controller;

import itu.mbds.transversal.utils.enumeration.Message;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
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

        return new ResponseEntity<>(exceptionMessageObj, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<Object> handleAuthorizationExceptionMethod(Exception ex, WebRequest request) {

        ExceptionMessage exceptionMessageObj = new ExceptionMessage();

        ex.printStackTrace();

        exceptionMessageObj.setMessage(Message.ACCESS_DENIED.toString());
        exceptionMessageObj.setError(ex.getClass().getCanonicalName());
        exceptionMessageObj.setPath(((ServletWebRequest) request).getRequest().getServletPath());

        return new ResponseEntity<>(exceptionMessageObj, new HttpHeaders(), HttpStatus.FORBIDDEN);
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