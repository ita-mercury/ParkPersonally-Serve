package com.parkpersonally.controller;

import com.parkpersonally.exception.GetParkingOrderException;
import com.parkpersonally.exception.NoSuchParkingBoyException;
import com.parkpersonally.exception.NoSuchParkingOrderException;
import com.parkpersonally.exception.ParkingLotIsFullException;
import com.parkpersonally.model.ParkingBoy;
import com.parkpersonally.model.ParkingOrder;
import com.parkpersonally.service.ParkingBoyService;
import com.parkpersonally.service.ParkingOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.util.List;

@RestController
public class ParkingOrderController {

    @Autowired
    private ParkingOrderService parkingOrderService;

    @Autowired
    private ParkingBoyService parkingBoyService;


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

    @GetMapping("/parking-orders")
    public List<ParkingOrder> getOrdersOfParkingBoy(@RequestParam("type")int type, @RequestParam("parkingBoyId") long parkingBoyId){
        ParkingBoy parkingBoy = parkingBoyService.findOneById(parkingBoyId);
        return  parkingOrderService.getAllParkingOrdersOfParkingBoy(parkingBoy,type,1);
    }

    @PutMapping("/parking-orders/{ordersId}/comments")
    public ResponseEntity appraiseOrder(@PathVariable("ordersId")long id, ParkingOrder parkingOrder){
        return ResponseEntity.ok(parkingOrderService.appraiseOrder(id,parkingOrder));
    }

    @PutMapping("/parking-orders/{parkingOrderId}")
    public ResponseEntity<ParkingOrder> updateParkingOrderStatus(@PathVariable long parkingOrderId,@RequestBody ParkingOrder parkingOrder){

        return  ResponseEntity.ok(parkingOrderService.updateParkingOrderStatus(parkingOrderId,parkingOrder));
    }

    @PostMapping("/parking-orders/{orderId}/parking-boy")
    public ResponseEntity<ParkingOrder> parkingBoyGetParkingOrder(@PathVariable(name = "orderId") long orderId,
                                                                  @RequestBody ParkingBoy parkingBoy){
        return ResponseEntity.ok(parkingOrderService.parkingBoyGetParkingOrder(orderId, parkingBoy));
    }

    @ExceptionHandler(ParkingLotIsFullException.class)
    public ResponseEntity handleParkingLotIsFull(ParkingLotIsFullException ex){
        return new ResponseEntity(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoSuchParkingBoyException.class)
    public ResponseEntity handleNoSuchParkingBoy(NoSuchParkingBoyException e) {
        return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(GetParkingOrderException.class)
    public ResponseEntity handleGetParkingOrderException(GetParkingOrderException e) {
        return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @PutMapping("/parking-orders/{orderId}/parking-lots/{parkingLotId}")
    public ResponseEntity setOrderStatusAndSetParkingLot(@PathVariable long orderId,
                                                         @PathVariable long parkingLotId){
        return null;

    }
}
