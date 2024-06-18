package com.example.AuthenticationProject.controlleradvice;
import com.example.AuthenticationProject.dto.ErrorObject;
import com.example.AuthenticationProject.exception.ConflictException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.OffsetDateTime;

@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorObject> onException(ConflictException e){
        ErrorObject er = ErrorObject.builder().offsetDateTime(OffsetDateTime.now()).message(e.getMessage()).build();
        return new ResponseEntity<>(er, HttpStatus.CONFLICT);
    }
// to  display the error msg thrown on console of postman
//    @ExceptionHandler(BadRequestException.class)
//    public ResponseEntity<String> onException1(BadRequestException e){
//        return ResponseEntity.badRequest().body(e.getMessage());
//    }



}
