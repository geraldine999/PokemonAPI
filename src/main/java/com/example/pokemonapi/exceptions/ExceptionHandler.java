package com.example.pokemonapi.exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.time.LocalDateTime;

@ControllerAdvice
@RestController
public class ExceptionHandler extends ResponseEntityExceptionHandler {


    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public ResponseEntity<Object> exceptionHandler(Exception e, WebRequest webRequest){
        ExceptionResponse response = new ExceptionResponse(e.getMessage(),
                webRequest.getDescription(false)
                ,HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return new ResponseEntity<>(response, response.getStatus());
    }


}




