package com.parkpersonally.controller;

import com.parkpersonally.model.ParkingOrder;
import com.parkpersonally.service.ParkingOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

@RestController
public class ParkingOrderController {

    @Autowired
    private ParkingOrderService parkingOrderService;

    @PostMapping("/parking-orders")
    public ParkingOrder createOrder(@RequestBody ParkingOrder order) {
        return parkingOrderService.createParkingOrder(order);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity handEntityValid(ConstraintViolationException e) {
        return new ResponseEntity(e, HttpStatus.BAD_REQUEST);
    }
}
