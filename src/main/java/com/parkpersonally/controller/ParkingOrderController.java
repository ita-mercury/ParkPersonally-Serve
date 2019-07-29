package com.parkpersonally.controller;

import com.parkpersonally.exception.GetParkingOrderException;
import com.parkpersonally.exception.NoSuchParkingBoyException;
import com.parkpersonally.exception.NoSuchParkingOrderException;
import com.parkpersonally.exception.ParkingLotIsFullException;
import com.parkpersonally.model.Comment;
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
    @GetMapping("/parking-orders/{parkingOrderId}")
    public ParkingOrder getOrderById(@PathVariable long parkingOrderId){

        return  parkingOrderService.findOrderById(parkingOrderId);
    }

    @GetMapping("/parking-orders")
    public List<ParkingOrder> getOrdersOfParkingBoy(@RequestParam("type")int type, @RequestParam("parkingBoyId") long parkingBoyId){
        ParkingBoy parkingBoy = parkingBoyService.findOneById(parkingBoyId);
        return  parkingOrderService.getAllParkingOrdersOfParkingBoy(parkingBoy,type,1);
    }

    @PostMapping("/parking-orders/{ordersId}/comments")
    public ResponseEntity appraiseOrder(@PathVariable("ordersId")long id, @RequestBody Comment comment){
        return ResponseEntity.ok(parkingOrderService.appraiseOrder(id,comment));
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



}
