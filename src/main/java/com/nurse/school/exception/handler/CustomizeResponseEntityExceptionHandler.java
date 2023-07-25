package com.nurse.school.exception.handler;

import com.nurse.school.exception.DoesntMatchExcelFormException;
import com.nurse.school.exception.NoCreationDataException;
import com.nurse.school.exception.NotFoundException;
import com.nurse.school.response.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;
import java.util.Date;

@RestControllerAdvice // Rest API Controller 에서 발생하는 예외를
public class CustomizeResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception exp, WebRequest request){
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), exp.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NoCreationDataException.class)
    public final ResponseEntity<ExceptionResponse> handleNoCreationDataExceptions(Exception exp, WebRequest request){
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), exp.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DoesntMatchExcelFormException.class)
    public final ResponseEntity<ExceptionResponse> handleAboutExcelExceptions(Exception exp, WebRequest request){
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), exp.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(NotFoundException.class)
    public final ResponseEntity<ExceptionResponse> handleNotFoundExceptions(Exception exp, WebRequest request){
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), exp.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

}
