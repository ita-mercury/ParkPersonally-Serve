package com.parkpersonally.controller;

import com.parkpersonally.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(GetParkingOrderException.class)
    String handleGetParkingOrderException(GetParkingOrderException ex){
        return  ex.getMessage();
    }


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchParkingBoyException.class)
    String handleNoSuchParkingBoyException(NoSuchParkingBoyException ex){
        return  ex.getMessage();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchParkingOrderException.class)
    String handleNoSuchParkingOrderException(NoSuchParkingOrderException ex ){
        return ex.getMessage();
    }


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ParkingLotIsFullException.class)
    String handleParkingLotIsFullException(ParkingLotIsFullException ex ){
        return ex.getMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    String handleConstraintViolationException(ConstraintViolationException ex ){
        return ex.getMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CreateParkingOrderException.class)
    String handleCreateParkingOrderException(CreateParkingOrderException ex ){
        return ex.getMessage();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchManagerException.class)
    String handleNoSuchManagerException(NoSuchManagerException ex){
        return  ex.getMessage();
    }
}
