package com.parkpersonally.controller;

import com.parkpersonally.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(GetParkingOrderException.class)
    public String handleGetParkingOrderException(GetParkingOrderException ex){
        return ex.getMessage();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchParkingBoyException.class)
    public String handleNoSuchParkingBoyException(NoSuchParkingBoyException ex){
        return ex.getMessage();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchParkingOrderException.class)
    public String handleNoSuchParkingOrderException(NoSuchParkingOrderException ex ){
        return ex.getMessage();
    }

    @ExceptionHandler(ParkingLotIsFullException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleParkingLotIsFullException(ParkingLotIsFullException ex ){
        return ex.getMessage();
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

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ParkingBoyHasAOrderException.class)
    public String handleParkingBoyHasAOrderException(ParkingBoyHasAOrderException ex){
        return ex.getMessage();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ParkingBoyIsIllegalException.class)
    public String handleParkingBoyIsIllegalException(ParkingBoyIsIllegalException ex){
        return ex.getMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ChangeParkingLotStatusException.class)
    public String handleChangeParkingLotStatusException(ParkingBoyIsIllegalException ex){
        return ex.getMessage();
    }
}
