package com.parkpersonally.controller;

import com.parkpersonally.exception.GetParkingOrderException;
import com.parkpersonally.exception.NoSuchParkingBoyException;
import com.parkpersonally.exception.NoSuchParkingOrderException;
import com.parkpersonally.exception.ParkingLotIsFullException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(GetParkingOrderException.class)
    String handleGetParkingOrderException(GetParkingOrderException getParkingOrderException){
        return  getParkingOrderException.getMessage();
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchParkingBoyException.class)
    String handleNoSuchParkingBoyException(NoSuchParkingBoyException noSuchParkingBoyException){
        return  noSuchParkingBoyException.getMessage();
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchParkingOrderException.class)
    String handleNoSuchParkingOrderException(NoSuchParkingOrderException noSuchParkingOrderException ){
        return noSuchParkingOrderException.getMessage();
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ParkingLotIsFullException.class)
    String handleParkingLotIsFullException(ParkingLotIsFullException parkingLotIsFullException ){
        return parkingLotIsFullException.getMessage();
    }
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    String handleConstraintViolationException(ConstraintViolationException constraintViolationException ){
        return constraintViolationException.getMessage();
    }


}
