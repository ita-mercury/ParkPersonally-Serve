package com.parkpersonally.controller;

import com.parkpersonally.model.Comment;
import com.parkpersonally.model.ParkingBoy;
import com.parkpersonally.model.ParkingOrder;
import com.parkpersonally.service.ParkingBoyService;
import com.parkpersonally.service.ParkingOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parking-orders")
public class ParkingOrderController {

    @Autowired
    private ParkingOrderService parkingOrderService;

    @Autowired
    private ParkingBoyService parkingBoyService;


    @PostMapping
    public ResponseEntity<ParkingOrder> createOrder(@RequestBody ParkingOrder order) {
        return ResponseEntity.ok(parkingOrderService.createParkingOrder(order));
    }
    @GetMapping("/{parkingOrderId}")
    public ParkingOrder getOrderById(@PathVariable long parkingOrderId){

        return  parkingOrderService.findOrderById(parkingOrderId);
    }

    @GetMapping
    public List<ParkingOrder> getOrdersOfParkingBoy(@RequestParam("type")int type, @RequestParam("parkingBoyId") long parkingBoyId){
        ParkingBoy parkingBoy = parkingBoyService.findOneById(parkingBoyId);
        return  parkingOrderService.filterParkingOrders(parkingBoy,type,1);
    }

    @PostMapping("/{ordersId}/comments")
    public ResponseEntity appraiseOrder(@PathVariable("ordersId")long id, @RequestBody Comment comment){
        return ResponseEntity.ok(parkingOrderService.appraiseOrder(id,comment));
    }

    @PutMapping("/{parkingOrderId}")
    public ResponseEntity<ParkingOrder> updateParkingOrderStatus(@PathVariable long parkingOrderId,@RequestBody ParkingOrder parkingOrder){

        return  ResponseEntity.ok(parkingOrderService.updateParkingOrderStatus(parkingOrderId,parkingOrder));
    }

    @PostMapping("/{orderId}/parking-boy")
    public ResponseEntity<ParkingOrder> parkingBoyGetParkingOrder(@PathVariable(name = "orderId") long orderId,
                                                                  @RequestBody ParkingBoy parkingBoy){
        return ResponseEntity.ok(parkingOrderService.parkingBoyGetParkingOrder(orderId, parkingBoy));
    }



}
