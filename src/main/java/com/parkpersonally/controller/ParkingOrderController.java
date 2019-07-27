package com.parkpersonally.controller;

import com.parkpersonally.exception.NoSuchOrderException;
import com.parkpersonally.exception.NoSuchParkingOrderException;
import com.parkpersonally.model.ParkingOrder;
import com.parkpersonally.service.ParkingOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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


    @GetMapping("/parking-orders/{parkingOrderId}")
    public ParkingOrder getOrderById(@PathVariable long parkingOrderId){

        return  parkingOrderService.findOrderById(parkingOrderId);
    }
    @PutMapping("/parking-orders/{ordersId}/comments")
    public ResponseEntity appriaseOrder(@PathVariable("ordersId")long id,ParkingOrder parkingOrder){
        return ResponseEntity.ok(parkingOrderService.appraiseOrder(id,parkingOrder));
    }

    @ExceptionHandler(NoSuchOrderException.class)
    public ResponseEntity handleNoSuchOrderException(NoSuchOrderException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoSuchParkingOrderException.class)
    public ResponseEntity handlNoSuchParkingBoy(NoSuchParkingOrderException e) {
        return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @PutMapping("/orders/{orderId}")
    public ParkingOrder updateParkingOrder(@RequestBody ParkingOrder parkingOrder,@PathVariable long id){
        return parkingOrderService.updateParkingOrder(parkingOrder,id);
    }
}
