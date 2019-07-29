package com.parkpersonally.controller;

import com.parkpersonally.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GetParkingOrderException.class)
    public ResponseEntity<String> handleGetParkingOrderException(GetParkingOrderException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchParkingBoyException.class)
    public String handleNoSuchParkingBoyException(NoSuchParkingBoyException ex){
        return  ex.getMessage();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchParkingOrderException.class)
    public String handleNoSuchParkingOrderException(NoSuchParkingOrderException ex ){
        return ex.getMessage();
    }

    @ExceptionHandler(ParkingLotIsFullException.class)
    public ResponseEntity<String> handleParkingLotIsFullException(ParkingLotIsFullException ex ){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public String handleConstraintViolationException(ConstraintViolationException ex ){
        return ex.getMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CreateParkingOrderException.class)
    public String handleCreateParkingOrderException(CreateParkingOrderException ex ){
        return ex.getMessage();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchManagerException.class)
    public String handleNoSuchManagerException(NoSuchManagerException ex){
        return  ex.getMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UpdateParkingLotCapacitySmallerException.class)
    public String handleUpdateParkingLotCapacitySmallerException(UpdateParkingLotCapacitySmallerException ex ){
        return ex.getMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NoSuchParkingLotException.class)
    public String handleNoSuchParkingLotException(NoSuchParkingLotException ex ){
        return ex.getMessage();
    }

    @ExceptionHandler(ParkingBoyHasAOrderException.class)
    public ResponseEntity<String> handleParkingBoyHasAOrderException(ParkingBoyHasAOrderException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ParkingBoyIsIllegalException.class)
    public ResponseEntity<String> handleParkingBoyIsIllegalException(ParkingBoyHasAOrderException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
