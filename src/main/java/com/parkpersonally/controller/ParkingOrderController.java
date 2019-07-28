package com.parkpersonally.controller;

import com.parkpersonally.exception.NoSuchParkingOrderException;
import com.parkpersonally.model.ParkingOrder;
import com.parkpersonally.service.ParkingOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;

@RestController
public class ParkingOrderController {

    @Autowired
    private ParkingOrderService parkingOrderService;

    @PostMapping("/parking-orders")
    public ResponseEntity<ParkingOrder> createOrder(@RequestBody ParkingOrder order) {
        return ResponseEntity.ok(parkingOrderService.createParkingOrder(order));
    }
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity handEntityValid(ConstraintViolationException e) {
        return new ResponseEntity(e, HttpStatus.BAD_REQUEST);
    }



    @GetMapping("/parking-orders/{parkingOrderId}")
    public ParkingOrder getOrderById(@PathVariable long parkingOrderId){

        return  parkingOrderService.findOrderById(parkingOrderId);
    }
    @ExceptionHandler(NoSuchParkingOrderException.class)
    public ResponseEntity handlNoSuchParkingOrderException(NoSuchParkingOrderException e) {
        return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
    }


    @PutMapping("/parking-orders/{ordersId}/comments")
    public ResponseEntity appraiseOrder(@PathVariable("ordersId")long id,ParkingOrder parkingOrder){
        return ResponseEntity.ok(parkingOrderService.appraiseOrder(id,parkingOrder));
    }



    @PutMapping("/orders/{orderId}")

    public ParkingOrder updateParkingOrder(@RequestBody ParkingOrder parkingOrder,@PathVariable long orderId){
        return parkingOrderService.updateParkingOrder(parkingOrder,orderId);
    }



}
