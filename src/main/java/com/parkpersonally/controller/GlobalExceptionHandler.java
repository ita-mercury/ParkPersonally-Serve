package com.parkpersonally.controller;

import com.parkpersonally.exception.GetParkingOrderException;
import com.parkpersonally.exception.NoSuchParkingBoyException;
import com.parkpersonally.exception.NoSuchParkingOrderException;
import com.parkpersonally.exception.ParkingLotIsFullException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(GetParkingOrderException.class)
    String handleGetParkingOrderException(GetParkingOrderException getParkingOrderException){
        return  getParkingOrderException.getMessage();
    }


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchParkingBoyException.class)
    String handleNoSuchParkingBoyException(NoSuchParkingBoyException noSuchParkingBoyException){
        return  noSuchParkingBoyException.getMessage();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchParkingOrderException.class)
    String handleNoSuchParkingOrderException(NoSuchParkingOrderException noSuchParkingOrderException ){
        return noSuchParkingOrderException.getMessage();
    }


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ParkingLotIsFullException.class)
    String handleParkingLotIsFullException(ParkingLotIsFullException parkingLotIsFullException ){
        return parkingLotIsFullException.getMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    String handleConstraintViolationException(ConstraintViolationException constraintViolationException ){
        return constraintViolationException.getMessage();
    }
}
